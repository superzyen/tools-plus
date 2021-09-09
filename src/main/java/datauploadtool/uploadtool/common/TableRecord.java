package datauploadtool.uploadtool.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class TableRecord {
    private List<String> fields;
    private Map<Integer, Map<String, String>> records;
    private final static int RECORD_INIT_INDEX = 0;
    private final static int RECORD_CAPACITY = 999;
    private int size = RECORD_INIT_INDEX;

    public TableRecord() {
        this.fields = Lists.newArrayList();
        this.records = Maps.newHashMap();
    }

    public void setRecord(Map<String, String> record) {
        if (size < RECORD_CAPACITY) {
            this.records.put(size, record);
            size++;
        }
    }

    public Map<Integer, Map<String, String>> getRecords() {
        return this.records;
    }

    public List<String> getFields() {
        return this.fields;
    }
}
