package hpr.test.gui;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import hpr.test.excel.model.RCol;
import hpr.test.excel.model.RRow;
import hpr.test.excel.model.RSheet;
import hpr.test.excel.util.ExcelHelper;
import org.apache.xmlbeans.impl.store.CharUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author haopeiren
 * @since 2020/3/15
 */
public class CheckWork
{
    private static List<String> allStudent;

    private static List<String> workInfoList;

    private static File outFile;

    private static FileOutputStream fos;

    private static OutputStreamWriter osw;

    private static BufferedWriter bw;

    private static Set<String> dateSet = new HashSet<>();

    private static int shouldCount;

    public static void main(String[] args) throws Exception
    {
        check("");
    }
    public static void check(String excelPath) throws IOException
    {
        outFile = new File(new File(excelPath).getParent(), "异常记录.txt");
        fos = new FileOutputStream(outFile);
        osw = new OutputStreamWriter(fos);
        bw = new BufferedWriter(osw);
        excelPath = "D:/temp.xlsx";
        List<RSheet> rSheetList = getList(excelPath);
        RSheet rSheet = rSheetList.get(2);
        check(rSheet);
        System.out.println(rSheet.getRowList().size() - 3);
        bw.close();
        osw.close();
        fos.close();
    }

    public static void check(RSheet rSheet) throws IOException
    {
        List<RRow> rowList = rSheet.getRowList();
        int markIndex  = findMarkIndex(rowList.get(2));
        int nameIndex = findNameIndex(rowList.get(2));
        int noIndex = findNoIndex(rowList.get(2));
        int addrIndex = findAddrIndex(rowList.get(2));
        int dateIndex = findDateIndex(rowList.get(2));
        int dakaResultIndex = findDakaResult(rowList.get(2));
        shouldCount = dateCount(rowList, dateIndex) * 2;
        String lastName = null;
        Set<String> cityList = new HashSet<>();
        Map<String, String> noNameMap = new HashMap<>();
        AtomicInteger dakaCount = new AtomicInteger(0);

        checkAddrDiff(rowList);

        for (int i = 3; i < rowList.size(); i++)
        {
            RRow currentRow = rowList.get(i);
            List<RCol> colList = currentRow.getColList();
            String dakaResult = deleteSpace(colList.get(dakaResultIndex).getContent());
            if (!checkDakaResult(dakaResult))
            {
                continue;
            }
            String studentName = deleteSpace(colList.get(nameIndex).getContent());
            String studentNo = deleteSpace(colList.get(noIndex).getContent());
            noNameMap.put(studentNo, studentName);
            String mark = deleteSpace(colList.get(markIndex).getContent());
            String date = deleteSpace(colList.get(dateIndex).getContent());
            //检查打卡地点
            RCol addrCol = colList.get(addrIndex);
            String addr = deleteSpace(addrCol.getContent());
            if (StringUtils.isEmpty(addr))
            {
                System.out.println(studentName + "打卡数据为空， 数据行数 ： " + i);
                bw.write(studentName + "打卡数据为空， 数据行数 ： " + i);
                bw.newLine();
                continue;
            }
            int cityIndex = addr.indexOf("市");
            String currentCityName;
            if (cityIndex != -1)
            {
                currentCityName = addr.substring(0, cityIndex + 1);
            }
            else {
                currentCityName = addr;
            }
            if (!studentName.equals(lastName))
            {
                if ((dakaCount.intValue() != shouldCount) && (lastName != null))
                {
                    System.out.println(lastName + " 打卡记录异常，打卡次数 ： " + dakaCount);
                    bw.write(lastName + " 打卡记录异常，打卡次数 ： " + dakaCount);
                    bw.newLine();
                }
                if (cityList.size() > 1)
                {
                    System.out.println(lastName + " 共有" + cityList.size() + "个打卡地点:" + JSONObject.toJSONString(cityList));
                    bw.write(lastName + " 共有" + cityList.size() + "个打卡地点:" + JSONObject.toJSONString(cityList));
                    bw.newLine();
                }
                cityList.clear();
                dakaCount.set(0);
                lastName = studentName;
            }
            dakaCount.incrementAndGet();
            cityList.add(currentCityName);
            checkMark(mark, studentName, i, date);
        }
    }

    public static void checkAddrDiff(List<RRow> rowList)
    {
        int nameIndex = findNameIndex(rowList.get(2));
        int addrIndex = findAddrIndex(rowList.get(2));

        for (int i = 3; i < rowList.size(); i++)
        {
            RRow currentRow = rowList.get(i);
            String currentName = currentRow.getColList().get(nameIndex).getContent();
            String currentAddr = currentRow.getColList().get(addrIndex).getContent();

        }
    }



    public static int dateCount(List<RRow> rowList, int dateIndex)
    {
        for (int i = 3; i < rowList.size(); i++)
        {
            dateSet.add(rowList.get(i).getColList().get(dateIndex).getContent());
        }
        return dateSet.size();
    }

    public static boolean checkDakaResult(String dakaResult)
    {
        if ("外勤".equals(dakaResult) || "早退".equals(dakaResult))
        {
            return true;
        }
        return false;
    }

    public static void checkMark(String mark, String studentName, int i, String date) throws IOException
    {
        List<String> errorMsg = new ArrayList<>();
        //检查备注信息
        if (mark.isEmpty() || mark.length() < 90)
        {
            System.out.println(studentName +" 备注异常(缺)，备注信息 ：" + mark + ", 数据行数:" + i);
            bw.write(studentName +" 备注异常(缺)，备注信息 ：" + mark + ", 数据行数:" + i);
            bw.newLine();
            return;
        }
        //检查备注  有/无
        if (mark.contains("有"))
        {
            errorMsg.add("有");
        }

        int yueFirstIndex = mark.indexOf("月");
        int yueLastIndex = mark.lastIndexOf("月");
        if (yueFirstIndex == yueLastIndex)
        {
            errorMsg.add("日期");
        }

        int lastSplitIndex = date.lastIndexOf("-");
        String dateYue = date.substring(lastSplitIndex - 1, lastSplitIndex);
        String dateRi = date.substring(lastSplitIndex + 1, lastSplitIndex + 3);
        String dealRi = Integer.parseInt(dateRi) + "";
        int subLength;
        if (dateRi.length() != dealRi.length())
        {
            subLength = 1;
        }
        else
        {
            subLength = 2;
        }

        int lastYueIndex = mark.lastIndexOf("月");
        String markYue = mark.substring(lastYueIndex - 1, lastYueIndex);
        String markRi = mark.substring(lastYueIndex + 1, lastYueIndex + 1 + subLength);

        if (!dateYue.equals(markYue) || !dealRi.equals(markRi))
        {
            errorMsg.add("日期");
        }

        if (!errorMsg.isEmpty())
        {
            System.out.println(studentName +" 备注异常"+JSONObject.toJSONString(errorMsg)+"，备注信息 ：" + mark + ", 数据行数:" + i);
            bw.write(studentName +" 备注异常"+JSONObject.toJSONString(errorMsg)+"，备注信息 ：" + mark + ", 数据行数:" + i);
            bw.newLine();
        }
        checkTemp(studentName, mark);
    }

    public static void checkTemp(String studentName, String mark) throws IOException
    {
        int tempIndex = findTempIndex(studentName, mark);
        StringBuilder sb = new StringBuilder();
        for (int i = tempIndex; i < mark.length(); i++)
        {
            if (isTempChar(mark.charAt(i)))
            {
                sb.append(mark.charAt(i));
            }
            else
            {
                break;
            }
        }
        String tempStr = sb.toString().replace("．", ".");
        if (StringUtils.isEmpty(tempStr))
        {
            System.out.println(studentName + " 体温数据异常 ： " + mark);
            bw.write(studentName + " 体温数据异常 ： " + mark);
            bw.newLine();
        }
        Double temp;
        try
        {
            temp = Double.parseDouble(tempStr);
        }
        catch (Exception e)
        {
            System.out.println(studentName + " 获取体温失败 ： " + mark);
            bw.write(studentName + " 获取体温失败 ： " + mark);
            bw.newLine();
            return;
        }
        if (temp <= 35 || temp >= 37)
        {
            System.out.println(studentName + " 温度异常 ：" + temp);
            bw.write(studentName + " 温度异常 ：" + temp);
            bw.newLine();
        }

    }

    public static boolean isTempChar(char c)
    {
        if ((c >= '0' && c <= '9') || c == '.' || c == '．')
        {
            return true;
        }
        return false;
    }

    private static int findTempIndex(String studentName, String mark) throws IOException
    {
        String aim = "体温为";
        String aim2 = "体温";
        String aim3 = "体温是";
        int aimIndex;
        int tempIndex;
        aimIndex = mark.indexOf(aim);
        if (aimIndex != -1)
        {
            tempIndex = aimIndex + 3;
        }
        else if (mark.contains(aim2))
        {
            aimIndex = mark.indexOf(aim2);
            tempIndex = aimIndex + 2;
        }
        else  if (mark.contains(aim3))
        {
            aimIndex = mark.indexOf(aim3);
            tempIndex = aimIndex + 3;
        }
        else
        {
            System.out.println(studentName + " 体温数据异常 ： " + mark);
            bw.write(studentName + " 体温数据异常 ： " + mark);
            bw.newLine();
            return -1;
        }
        return tempIndex;

    }

    public static int findMarkIndex(RRow rRow)
    {
        String aim = "打卡备注";
        return find(rRow, aim);
    }

    public static int findNameIndex(RRow row)
    {
        String aim = "姓名";
        return find(row, aim);
    }

    public static int findAddrIndex(RRow row)
    {
        String aim = "打卡地址";
        return find(row, aim);
    }

    public static int findDateIndex(RRow row)
    {
        String aim = "考勤日期";
        return find(row, aim);
    }

    public static int findDakaResult(RRow row)
    {
        String aim = "打卡结果";
        return find(row, aim);
    }

    public static int findNoIndex(RRow row)
    {
        String aim = "工号";
        return find(row, aim);
    }

    private static int find(RRow rRow, String aim)
    {
        List<RCol> colList = rRow.getColList();
        for (int i = 0; i < colList.size(); i++)
        {
            if (aim.equalsIgnoreCase(colList.get(i).getContent()))
            {
                return i;
            }
        }
        System.out.println("parse excel failed, can not find : " + aim);
        return -1;
    }

    public static List<RSheet> getList(String excelPath) throws IOException
    {
        List<RSheet> rSheetList = null;
        rSheetList = ExcelHelper.parseExcel(excelPath);
        return rSheetList;
    }

    public static String deleteSpace(String content)
    {
        if (content == null)
        {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < content.length(); i++)
        {
            char c = content.charAt(i);
            if (CharUtil.isWhiteSpace(c) || ((int) c) == 160)
            {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
