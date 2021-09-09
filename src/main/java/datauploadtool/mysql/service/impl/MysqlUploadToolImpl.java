package datauploadtool.mysql.service.impl;

import datauploadtool.mysql.common.FilePathConstants;
import datauploadtool.mysql.common.TableRecord;
import datauploadtool.mysql.excel.ExcelFactory;
import datauploadtool.mysql.excel.IExcelTemplate;
import datauploadtool.mysql.mapper.CommMapper;
import datauploadtool.mysql.service.tool.IToolSelectionMode;
import datauploadtool.mysql.util.EntityUtils;
import datauploadtool.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class MysqlUploadToolImpl implements IToolSelectionMode {

    @Override
    public Boolean invoke() {
        while (true) {
            this.init();
            try {
                Scanner scanner = new Scanner(System.in);
                int selection = scanner.nextInt();
                switch (selection) {
                    case 1:
                        this.openExcel();
                        break;
                    case 2:
                        this.uploadExcel();
                        break;
                    default:
                        return true;
                }

            } catch (InputMismatchException inputMismatchException) {
                System.out.println("请输入正确的数字");
            } catch (Exception e) {
                log.error("mysql工具主流程异常", e);
            }
        }
    }

    @Override
    public void introduceTool() {
        System.out.println("mysql data upload tool");
    }

    private void openExcel() throws IOException {
        try {
            File file = this.getFile(FilePathConstants.DEFULT_EXCEL_PATH);
            String ap = file.getAbsolutePath();
            Runtime.getRuntime().exec("cmd /c start " + ap);
        } catch (Exception e) {
            log.error("打开excel文件时异常", e);
            throw e;
        }
    }

    private void uploadExcel() throws Exception {
        try {
            IExcelTemplate excelTemplate = ExcelFactory.newInstance().getExcelTemplate();
            TableRecord tableRecord = excelTemplate.read(FilePathConstants.DEFULT_EXCEL_PATH);
            String sql = EntityUtils.analyse(tableRecord.getTable()).getSqlStr(tableRecord);
            log.info(sql);
            CommMapper mapper = BeanUtils.getBean(CommMapper.class);
            mapper.insert(sql);
            log.info("更新成功");
        } catch (Exception e) {
            log.error("更新上传失败", e);
            throw e;
        }
    }

    private File getFile(String filePath) throws IOException {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file = this.creatDefultExcel(filePath);
            }
            return file;
        } catch (Exception e) {
            log.error("获取文件时异常, filePath:{}", filePath, e);
            throw e;
        }
    }

    /**
     * 如果没有该文件，则生成默认的文件上传模板
     *
     * @author wenzy
     * @date 2021/9/9 11:43
     */
    private File creatDefultExcel(String filePath) throws IOException {
        IExcelTemplate excelTemplate = ExcelFactory.newInstance().getExcelTemplate();
        excelTemplate.create(filePath);
        return new File(filePath);
    }

    private void init() {
        System.out.println("请输入序号(输入其他数字返回上一层)");
        System.out.println("1. 打开excel");
        System.out.println("2. 更新上传数据");
    }
}
