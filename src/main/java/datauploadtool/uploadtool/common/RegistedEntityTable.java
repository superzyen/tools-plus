package datauploadtool.uploadtool.common;

import datauploadtool.entity.Member;

public class RegistedEntityTable {
    /**
     * 实体类登记表
     * 所有实体类的初始化表单
     */
    public static void initializeTable() {
        TableRegister tableRegister = TableRegister.getInstance();
        tableRegister.register(new Member());
    }
}
