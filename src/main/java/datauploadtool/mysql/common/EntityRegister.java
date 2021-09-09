package datauploadtool.mysql.common;

import com.google.common.collect.Maps;

import java.util.Map;

public class EntityRegister {
    private Map<String, Class> entityMap = Maps.newHashMap();
    private static EntityRegister entityRegister;

    private EntityRegister() {
    }

    public static EntityRegister getInstance() {
        if (null == entityRegister) {
            synchronized (EntityRegister.class) {
                if (null == entityRegister) {
                    entityRegister = new EntityRegister();
                }
            }
        }
        return entityRegister;
    }

    public void register(IEntity iEntity) {
        this.entityMap.put(iEntity.getTableName(), iEntity.getClass());
    }

    public Map<String, Class> getEntityMap() {
        return this.entityMap;
    }
}
