package com.chibuisi.dailyinsightservice.util;

import com.chibuisi.dailyinsightservice.topic.model.TopicItemKey;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelHelper {

    static String SHEET = "Sheet1";

    public static ByteArrayInputStream topicItemKeysToExcel(List<TopicItemKey> topicItemKeys) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < topicItemKeys.size(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(topicItemKeys.get(col).getKeyName());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
