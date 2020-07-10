package com.hisign.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: ExcelUtils
 * @package: com.hisign.utils
 * @describe: excel工具类
 * @author: huangyuanli
 * @date: 2019/9/5 9:43
 **/
@Component
public class ExcelUtils {

    /**
     * xlsx格式Excel
     */
    private static final String EXCEL_XLSX = "xlsx";

    /**
     * xls格式Excel
     */
    private static final String EXCEL_XLS = "xls";

    /**
     * @describe: excel文件读取和封装
     * @param: File
     * @return: Map<String, Object>
     * @author: huangyuanli
     * @date: 2019/9/5 9:43
     **/
    public static Map<String, Object> readExcel(InputStream inputStream, String fileName) throws Exception {
        Map<String, Object> result;
        // 获取文件后缀名
        String postfixName = StringUtils.getFilenameExtension(fileName);
        if (EXCEL_XLSX.equalsIgnoreCase(postfixName)) {
            result = readXlsxExcel(inputStream);
        } else if (EXCEL_XLS.equalsIgnoreCase(postfixName)) {
            result = readXlsExcel(inputStream);
        } else {
            result = new HashMap<>();
        }
        return result;
    }

    /**
     * @describe: xlsx格式的读取封装
     * @param: inputStream
     * @return: Map<String, Object>
     * @author: huangyuanli
     * @date: 2019/9/5 9:43
     **/
    private static Map<String, Object> readXlsxExcel(InputStream inputStream) throws Exception {
        // excel数据封装结果
        Map<String, Object> xlsxResult = new HashMap<>();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
        XSSFSheet xssfSheet;
        List<String> list;
        int numberOfSheets = xssfWorkbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            // 获取sheet页
            xssfSheet = xssfWorkbook.getSheetAt(i);
            // 最后一行的索引
            int rowLen = xssfSheet.getLastRowNum();
            if (0 == rowLen) {
                // 空白sheet
                continue;
            }

            // excel行数据
            XSSFRow row;
            // 每一个数据表的数据
            list = new ArrayList<>(rowLen);
            for (int rowNum = 0; rowNum <= rowLen; rowNum++) {
                row = xssfSheet.getRow(rowNum);
                if (null != row) {
                    // 第一列
                    XSSFCell cell = row.getCell(0);
                    // 把单元格内容作为字符串处理
                    list.add(cell.toString());
                }
            }
            xlsxResult.put(String.valueOf(i), list);
        }

        return xlsxResult;
    }

    /**
     * @describe: xls格式的读取封装
     * @param: inputStream
     * @return: Map<String, Object>
     * @author: huangyuanli
     * @date: 2019/9/5 9:43
     **/
    private static Map<String, Object> readXlsExcel(InputStream inputStream) throws Exception {
        // excel数据封装结果
        Map<String, Object> xlsResult = new HashMap<>(3);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
        List<String> list;
        HSSFSheet hssfSheet;
        int numberOfSheets = hssfWorkbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            // 获取sheet页
            hssfSheet = hssfWorkbook.getSheetAt(i);
            // 最后一行的索引
            int rowLen = hssfSheet.getLastRowNum();
            if (0 == rowLen) {
                // 空白sheet
                continue;
            }

            // excel行数据
            HSSFRow row;
            list = new ArrayList<>(rowLen);
            for (int rowNum = 0; rowNum <= rowLen; rowNum++) {
                row = hssfSheet.getRow(rowNum);
                if (null != row) {
                    // 第一列
                    HSSFCell cell = row.getCell(0);
                    // 把单元格内容作为字符串处理
                    list.add(cell.toString());
                }
            }
            xlsResult.put(String.valueOf(i), list);
        }
        return xlsResult;
    }
}
