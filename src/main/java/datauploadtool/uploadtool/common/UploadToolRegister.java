package datauploadtool.uploadtool.common;

import com.google.common.collect.Lists;
import datauploadtool.service.tool.IToolSelectionMode;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
public class UploadToolRegister {
    private List<IToolSelectionMode> toolSelectionModeList = Lists.newArrayList();
    private static UploadToolRegister uploadToolRegister;

    private UploadToolRegister() {

    }

    public static UploadToolRegister getInstance() {
        if (null == uploadToolRegister) {
            synchronized (UploadToolRegister.class) {
                if (null == uploadToolRegister) {
                    uploadToolRegister = new UploadToolRegister();
                }
            }
        }
        return uploadToolRegister;
    }

    public Boolean register(IToolSelectionMode iToolSelectionMode) {
        if (null != iToolSelectionMode) {
            log.info("工具类登记成功");
            toolSelectionModeList.add(iToolSelectionMode);
            return true;
        } else {
            return false;
        }
    }

    public List<IToolSelectionMode> getToolList() {
        if (null != this.toolSelectionModeList) {
            return this.toolSelectionModeList;
        } else {
            return Collections.emptyList();
        }
    }
}
