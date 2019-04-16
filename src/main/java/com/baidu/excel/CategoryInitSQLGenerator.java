package com.baidu.excel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 读取本地excel
 * @author mazhen
 * Created on 2019-04-12
 */
public class CategoryInitSQLGenerator {

    private static final String EXCEL_PATH = "/Users/mazhen/Documents/temp/immigrated_category.xlsx";
    private static final String SQL_PATH = "/Users/mazhen/Documents/temp/immigrated_category.txt";
    private static final String SQL_TEMPLATE = "INSERT INTO `category_info`(`category_id`, `category_name`, `category_pid`, `hierarchy`, `category_desc`, `create_time`, `update_time`) values(%s, '%s', %s, %s, '%s', %s, %s);";
    private static final long START_ID = 1000;

    public static void main(String[] args) throws IOException, InvalidFormatException {

        File xlsFile = new File(EXCEL_PATH);
        // 获得工作簿
        Workbook workbook = WorkbookFactory.create(xlsFile);
        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum() + 1;
        long categoryId = START_ID;

        String h1Name = null;
        String h2Name = null;
        String h3Name = null;
        long h1Id = -1;
        long h2Id = -1;
        List<CategorySQLBO> sqlboList = Lists.newArrayList();

        // 第1行是title，不要
        for (int indexRow = 1; indexRow < rowCount; indexRow++) {

            Row row = sheet.getRow(indexRow);
            boolean newH1 = false;
            boolean newH2 = false;
            if (StringUtils.isNotEmpty(row.getCell(0).getStringCellValue())) {
                h1Name = row.getCell(0).getStringCellValue();
                newH1 = true;
            }
            if (StringUtils.isNotEmpty(row.getCell(1).getStringCellValue())) {
                h2Name = row.getCell(1).getStringCellValue();
                newH2 = true;
            }
            h3Name = StringUtils.remove(row.getCell(2).getStringCellValue(), "\n");
            if (StringUtils.isEmpty(h3Name)) {
                System.out.println("Error!! h3Name is empty.");
                continue;
            }

            if (newH1) {
                h1Id = ++categoryId;
                sqlboList.add(new CategorySQLBO(h1Id, h1Name, 0, 1));
            }
            if (newH2) {
                h2Id = ++categoryId;
                sqlboList.add(new CategorySQLBO(h2Id, h2Name, h1Id, 2));
            }
            sqlboList.add(new CategorySQLBO(++categoryId, h3Name, h2Id, 3));

            System.out.println(String.format("h1:%s, h2:%s, h3:%s", h1Name, h2Name, h3Name));
        }

        writeSQL(sqlboList);
        System.out.println("End.");
    }

    public static void writeSQL(List<CategorySQLBO> sqlboList) throws IOException {

        long now = System.currentTimeMillis();

        File sqlFile = new File(SQL_PATH);
        if (!sqlFile.exists()) {
            sqlFile.createNewFile();
        }
        FileWriter sqlWriter = new FileWriter(SQL_PATH);

        sqlboList.stream().filter(Objects::nonNull).forEach(sqlbo -> {
            try {
                String sql = String.format(SQL_TEMPLATE, sqlbo.getId(), sqlbo.getName(), sqlbo.getPid(), sqlbo.getHierarchy(), "system_init", now, now);
                sqlWriter.write(sql);
                sqlWriter.write("\n");
                System.out.println(sql);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sqlWriter.close();
    }
}
