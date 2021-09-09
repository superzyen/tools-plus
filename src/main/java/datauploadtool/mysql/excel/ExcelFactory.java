package datauploadtool.mysql.excel;

public class ExcelFactory implements IExcelFactory {

    private static final IExcelTemplate excelTemplate = new DefultExcelTemplate();
    private static ExcelFactory excelFactory;

    private ExcelFactory() {
    }

    public static ExcelFactory newInstance() {
        if (null == excelFactory) {
            synchronized (ExcelFactory.class) {
                if (null == excelFactory) {
                    excelFactory = new ExcelFactory();
                }
            }
        }
        return excelFactory;
    }

    public IExcelTemplate getExcelTemplate() {
        return excelTemplate;
    }
}
