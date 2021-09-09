package datauploadtool.mysql.common;

import com.google.common.collect.Maps;

import java.util.Map;

public class TableRegister {
    private Map<String, Class> entityMap = Maps.newHashMap();
    private static TableRegister tableRegister;

    private TableRegister() {
    }

    public static TableRegister getInstance() {
        if (null == tableRegister) {
            synchronized (TableRegister.class) {
                if (null == tableRegister) {
                    tableRegister = new TableRegister();
                }
            }
        }
        return tableRegister;
    }

    public void register(IEntity iEntity) {
        this.entityMap.put(iEntity.getTableName(), iEntity.getClass());
    }

    public Map<String, Class> getEntityMap() {
        return this.entityMap;
    }
}
