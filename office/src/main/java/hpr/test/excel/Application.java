package hpr.test.excel;

import hpr.test.excel.util.ExcelHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * deal daily for my wife
 * @author daopeiren
 * @since 2020/2/9
 */
@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner
{

    private static final String filePath = "C:\\Users\\haopeiren-pc\\Desktop\\test.xlsx";
    private static final String fileOut = "C:\\Users\\haopeiren-pc\\Desktop\\fileOut                                                                                      .xlsx";

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

    private void execute(String[] args)
    {
        try {
            ExcelHelper.parseExcel(filePath, fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception
    {
        log.info("application start...");
        if (args != null)
        {
            for (String arg : args)
            {
                log.info("param list : ");
                log.info(arg);
            }
        }
        execute(args);
        log.info("application end");
    }
}
