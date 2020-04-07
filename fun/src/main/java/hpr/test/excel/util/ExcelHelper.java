package hpr.test.excel.util;

import hpr.test.excel.model.RCol;
import hpr.test.excel.model.RRow;
import hpr.test.excel.model.RSheet;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author haopeiren
 * @since 2020/2/9
 */
@Slf4j
@UtilityClass
public class ExcelHelper
{
    public List<RSheet> parseExcel(String filePath) throws IOException
    {
        log.info("start to parse file : " + filePath);
        List<RSheet> sheetList = parseExcel(new FileInputStream(filePath));
        return sheetList;
    }

    public List<RSheet> parseExcel(FileInputStream fis) throws IOException
    {
        List<RSheet> rSheetList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext())
        {
            Sheet sheet = sheetIterator.next();
            if (sheet != null)
            {
                rSheetList.add(parseSheet(sheet));
            }
        }
        return rSheetList;
    }

    public RSheet parseSheet(Sheet sheet)
    {
        RSheet rSheet = new RSheet();
        List<RRow> rowList = new ArrayList<>();
        log.info("start to parse sheet : " + sheet.getSheetName());
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rowCount; i++)
        {
            Row row = sheet.getRow(i);
            if (row != null)
            {
                rowList.add(parseRow(row));
            }
        }
        rSheet.setRowList(rowList);
        return rSheet;
    }

    public RRow parseRow(Row row)
    {
        RRow rRow = new RRow();
        List<RCol> colList = new ArrayList<>();
        row.cellIterator().forEachRemaining(col ->
        {
            RCol rCol = new RCol();
            if (col.getCellType() == CellType.NUMERIC)
            {
                rCol.setContent(col.getNumericCellValue() + "");
            }
            else
            {
                rCol.setContent(col.getStringCellValue());
            }
            colList.add(rCol);
        });
        rRow.setColList(colList);
        return rRow;
    }
}
