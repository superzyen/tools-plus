package datauploadtool.mysql.common;

import datauploadtool.mysql.service.impl.MysqlUploadToolImpl;

public class RegistedToolTable {

    /**
     * 工具类登记表
     * 所有工具共同维护的初始化表单
     */
    public static void initializeTable() {
        UploadToolRegister uploadToolRegister = UploadToolRegister.getInstance();
        uploadToolRegister.register(new MysqlUploadToolImpl());
    }
}
