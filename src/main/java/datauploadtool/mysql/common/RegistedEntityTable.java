package datauploadtool.mysql.common;

import datauploadtool.mysql.entity.Member;
import datauploadtool.mysql.entity.News;

public class RegistedEntityTable {
    /**
     * 实体类登记表
     * 所有实体类的初始化表单
     */
    public static void initializeTable() {
        EntityRegister entityRegister = EntityRegister.getInstance();
        entityRegister.register(new Member());
        entityRegister.register(new News());
    }
}
