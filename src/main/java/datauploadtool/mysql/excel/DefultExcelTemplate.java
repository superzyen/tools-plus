package datauploadtool.mysql.excel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import datauploadtool.mysql.common.EntityRegister;
import datauploadtool.mysql.common.TableRecord;
import datauploadtool.mysql.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
public class DefultExcelTemplate implements IExcelTemplate {

    @Override
    public void info() {
        System.out.println("默认excel模板，版本号：1.0");
    }

    public void create(String filePath) throws IOException {
        try {
            FileOutputStream fopStream = new FileOutputStream(filePath);
            SXSSFWorkbook workbook = new SXSSFWorkbook();

            //创建第一页，第一页默认上传页
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

            //从第二页开始就是所有注册过的实体类的页面
            Map<String, Class> registedMap = EntityRegister.getInstance().getEntityMap();
            Sheet commSheet = null;
            for (String tableName : registedMap.keySet()) {
                List<String> tableFields = EntityUtils.analyse(tableName).getTableFields();

                commSheet = workbook.createSheet(tableName);
                //第一行用来填写数据库的表名,要与数据库的完全一致
                Row commRow = commSheet.createRow(0);
                Cell commCell = commRow.createCell(0);
                commCell.setCellValue("表名");
                commCell = commRow.createCell(1);
                commCell.setCellValue(tableName);

                //第二行是数据库字段名，要与数据库完全一致
                commRow = commSheet.createRow(1);
                commCell = commRow.createCell(0);
                commCell.setCellValue("字段名");
                int i = 1;
                for (String tableField : tableFields) {
                    commCell = commRow.createCell(i);
                    commCell.setCellValue(tableField);
                    i++;
                }

                //第三行开始都是字段的值
                commRow = commSheet.createRow(2);
                commCell = commRow.createCell(0);
                commCell.setCellValue("字段值");
            }

            workbook.write(fopStream);
            fopStream.flush();
            fopStream.close();
            log.info("excel创建成功");
        } catch (Exception e) {
            log.error("创建默认excel模板失败", e);
            throw e;
        }
    }

    public TableRecord read(String filePath) throws IOException {
        try {
            TableRecord tableRecord = new TableRecord();
            FileInputStream fipStream = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fipStream);
            Sheet firstSheet = workbook.getSheetAt(0);

            Row tableRow = firstSheet.getRow(0);
            String table = tableRow.getCell(1).getStringCellValue();
            tableRecord.setTable(table);

            Row fieldRow = firstSheet.getRow(1);
            List<String> fields = Lists.newArrayList();
            Iterator<Cell> cellIterator = fieldRow.cellIterator();
            //第一个单元格是标头，不计入field
            cellIterator.next();
            while (cellIterator.hasNext()) {
                Cell fieldCell = cellIterator.next();
                fieldCell.setCellType(Cell.CELL_TYPE_STRING);
                String val = fieldCell.getStringCellValue();
                fields.add(val);
            }
            tableRecord.setFields(fields);

            Iterator<Row> rowIterator = firstSheet.rowIterator();
            Row commRow = null;
            //因为第一行是table，在上面已经处理过了
            //第二行是field，在上面也已经处理过了
            rowIterator.next();
            rowIterator.next();
            Map<String, String> record = null;
            while (rowIterator.hasNext()) {
                //第第二个单元格对应的field下标是0
                record = Maps.newHashMap();
                int index = 0;
                int size = fields.size();

                commRow = rowIterator.next();
                //第一个单元格是标头，不计入values
                for (int i = index; i < size; i++) {
                    Cell valCell = commRow.getCell((i + 1));
                    if (null == valCell) {
                        record.put(fields.get(i), "");
                    } else {
                        valCell.setCellType(Cell.CELL_TYPE_STRING);
                        String val = valCell.getStringCellValue();
                        record.put(fields.get(i), val);
                    }
                }
                tableRecord.setRecord(record);
            }

            log.info("读取成功");
            return tableRecord;
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            log.error("请确保数据与字段的数量保持一致", arrayIndexOutOfBoundsException);
            throw arrayIndexOutOfBoundsException;
        } catch (Exception e) {
            log.error("excel读取失败", e);
            throw e;
        }
    }
}
