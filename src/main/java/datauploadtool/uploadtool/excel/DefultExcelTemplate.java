package datauploadtool.uploadtool.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

@Slf4j
public class DefultExcelTemplate {

    public static void create(String filePath) {
        try {
            FileOutputStream fopStream = new FileOutputStream(filePath);
            SXSSFWorkbook workbook = new SXSSFWorkbook();

            Sheet firstSheet = workbook.createSheet("upload");
            //第一行用来填写数据库的表名,要与数据库的完全一致
            Row tableRow = firstSheet.createRow(0);
            Cell tableSignCell = tableRow.createCell(0);
            tableSignCell.setCellValue("表名");

            //第二行是数据库字段名，要与数据库完全一致
            Row fieldRow = firstSheet.createRow(1);
            Cell fieldCell = fieldRow.createCell(0);
            fieldCell.setCellValue("字段名");

            //第三行开始都是字段的值
            Row valueRow = firstSheet.createRow(2);
            Cell valueCell = valueRow.createCell(0);
            valueCell.setCellValue("字段值");

            workbook.write(fopStream);
            fopStream.flush();
            fopStream.close();
            System.out.println("excel创建成功");
        } catch (Exception e) {
            log.error("创建默认excel模板文件时异常", e);
            System.out.println("创建excel失败");
        }
    }

    public static void read(String filePath) {
        try {
            FileInputStream fipStream = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fipStream);
            Sheet firstSheet = workbook.getSheetAt(0);

            Row tableRow = firstSheet.getRow(0);
            String tableName = tableRow.getCell(1).getStringCellValue();

        } catch (Exception e) {
            log.error("文件读取时异常", e);
            System.out.println("excel读取失败");
        }
    }
}
