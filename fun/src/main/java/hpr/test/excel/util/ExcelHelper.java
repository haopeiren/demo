package hpr.test.excel.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author haopeiren
 * @since 2020/2/9
 */
@Slf4j
@UtilityClass
public class ExcelHelper
{
    public void parseExcel(String filePath, String fileOut) throws IOException
    {
        log.info("start to parse file : " + filePath);
        parseExcel(new FileInputStream(filePath), new FileOutputStream(fileOut));
        log.info("parse end : " + filePath);
    }

    public void parseExcel(FileInputStream fis, FileOutputStream fos) throws IOException
    {
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext())
        {
            Sheet sheet = sheetIterator.next();
            if (sheet != null)
            {
                parseSheet(sheet);
            }
        }
        workbook.write(fos);
        fos.close();
        workbook.close();
    }

    public void parseSheet(Sheet sheet)
    {
        log.info("start to parse sheet : " + sheet.getSheetName());
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rowCount; i++)
        {
            Row row = sheet.getRow(i);
            if (row != null)
            {
                parseRow(row);
            }
        }
    }

    public void parseRow(Row row)
    {
        row.cellIterator().forEachRemaining(cell ->
        {
            if (cell != null)
            {
                parseAndUpdateCell(row, cell);
            }
        });
    }

    public void parseAndUpdateCell(Row row, Cell cell)
    {
//        String str = cell.getStringCellValue();
//        CellType cellType = cell.getCellType();
        cell.setCellValue("test");
    }
}
