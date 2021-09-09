package datauploadtool.mysql;

import datauploadtool.mysql.service.tool.IToolSelectionMode;
import datauploadtool.mysql.common.RegistedEntityTable;
import datauploadtool.mysql.common.RegistedToolTable;
import datauploadtool.mysql.common.UploadToolRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Component
public class UploadTool {

    public void toolMain() {

        init();
        List<IToolSelectionMode> iToolSelectionModeList = UploadToolRegister.getInstance().getToolList();
        while (true) {
            loopMethod(iToolSelectionModeList);
        }


    }

    private static void loopMethod(List<IToolSelectionMode> iToolSelectionModeList) {
        System.out.println("请输入以下功能前的数字");
        int i = 1;
        for (IToolSelectionMode tool : iToolSelectionModeList) {
            System.out.print(i + ". ");
            tool.introduceTool();
            i++;
        }

        Scanner scanner = new Scanner(System.in);
        try {
            int selection = scanner.nextInt();
            IToolSelectionMode toolSelectionMode = iToolSelectionModeList.get((selection - 1));
            toolSelectionMode.invoke();
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("请输入正确的数字");
            return;
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println("请输入范围内可使用工具的序号");
            return;
        } catch (Exception e) {
            log.error("主页面流程选择工具时异常", e);
            throw e;
        }
    }

    private static void init() {
        //初始化工具表单
        RegistedToolTable.initializeTable();
        //初始化实体类表单
        RegistedEntityTable.initializeTable();
    }
}
