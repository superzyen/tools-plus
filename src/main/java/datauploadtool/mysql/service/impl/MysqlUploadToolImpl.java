package datauploadtool.mysql.service.impl;

import datauploadtool.mysql.service.tool.IToolSelectionMode;
import datauploadtool.mysql.excel.DefultExcelTemplate;
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
            File file = this.getFile("./upload.xlsx");
            String ap = file.getAbsolutePath();
            Runtime.getRuntime().exec("cmd /c start " + ap);
        } catch (Exception e) {
            log.error("打开excel文件时异常", e);
            throw e;
        }
    }

    private void uploadExcel() {
        try {

        } catch (Exception e) {
            log.error("更新上传数据时异常", e);
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

    private File creatDefultExcel(String filePath) throws IOException {
        DefultExcelTemplate.create(filePath);
        return new File(filePath);
    }

    private void init() {
        System.out.println("请输入序号");
        System.out.println("1. 打开excel");
        System.out.println("2. 更新上传数据");
    }
}
