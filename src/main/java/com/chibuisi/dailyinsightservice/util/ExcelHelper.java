package com.chibuisi.dailyinsightservice.util;

import com.chibuisi.dailyinsightservice.article.model.Article;
import com.chibuisi.dailyinsightservice.topic.model.TopicItemKey;
import com.chibuisi.dailyinsightservice.topic.model.TopicItemProperties;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

public class ExcelHelper {

    static String SHEET = "Sheet1";
    public static String type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

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

    public static boolean hasExcelFormat(MultipartFile file){
        if(!type.equals(file.getContentType()))
            return false;
        return true;
    }

    public static List<Article> rowsToObject(MultipartFile file, String topic) {
        try {
            InputStream is = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(is);

            //only one sheet at a time
            Sheet sheet = workbook.getSheetAt(0);

            // TODO: 3/27/2023 validate the sheet. topic, title or meaning should not be blank
            // topic, title must be present in header row

            Iterator<Row> rows = sheet.iterator();

            List<Article> articles = new ArrayList<>();
            Map<Integer, String> headerIndexMap = new HashMap<>();
            int titleIndex = -1, tagIndex = -1, dateTagIndex = -1;

            int rowNumber = 0;
            while (rows.hasNext()) {
                Article article = Article.builder().build();

                //grab current row
                Row currentRow = rows.next();

                Iterator<Cell> cellIterator = currentRow.iterator();

                if (rowNumber == 0) {
                    while(cellIterator.hasNext()){
                        Cell cell = cellIterator.next();
                        if(cell.getStringCellValue().equals("title")) {
                            titleIndex = cell.getColumnIndex();
                        }
                        else if(cell.getStringCellValue().equals("tag")) {
                            tagIndex = cell.getColumnIndex();
                        }
                        else if(cell.getStringCellValue().equals("dateTag")) {
                            dateTagIndex = cell.getColumnIndex();
                        }
                        else
                            headerIndexMap.put(cell.getColumnIndex(), cell.getStringCellValue());
                    }
                    rowNumber++;
                    continue;
                }
                //This is handled in the topic item service
                //topicItem.setTopicName(topic);
                List<Integer> topicAndTitleIndex = new ArrayList<>();

                if(titleIndex != -1){
                    article.setTitle(currentRow.getCell(titleIndex).getStringCellValue());
                    topicAndTitleIndex.add(titleIndex);
                }

                if(tagIndex != -1){
                    article.setTag(currentRow.getCell(tagIndex).getStringCellValue());
                    topicAndTitleIndex.add(tagIndex);
                }

                if(dateTagIndex != -1){
                    article.setDateTag(currentRow.getCell(dateTagIndex).getStringCellValue());
                    topicAndTitleIndex.add(dateTagIndex);
                }

                article.setPublicationDate(LocalDateTime.now());

                //grab the rest of the columns in topic item properties object
                List<TopicItemProperties> topicItemProperties = new ArrayList<>();

                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    int index = currentCell.getColumnIndex();
                    if(!topicAndTitleIndex.contains(index)){
                        String currCellValue = getCellValue(currentCell);
                        if(currCellValue != null && currCellValue.length() > 0) {
                            TopicItemProperties properties = TopicItemProperties.builder()
                                    .propertyKey(headerIndexMap.get(index)).propertyValue(currCellValue).build();
                            topicItemProperties.add(properties);
                        }
                    }
                }
                article.setTopicItemProperties(topicItemProperties);
                articles.add(article);
            }

            workbook.close();

            return articles;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    private static String getCellValue(Cell cell){
        CellType cellType = cell.getCellType();
        String value = "";
        switch (cellType){
            case STRING:
            case BLANK:
                value = cell.getStringCellValue();
                break;
            case NUMERIC:
                value = String.valueOf((int)cell.getNumericCellValue());
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            default:
                break;
        }
        return value;
    }
}
