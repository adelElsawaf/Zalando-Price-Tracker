package org.example.FileHandler.Excel;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFOptimiser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxFile {
    XSSFWorkbook excelWorkBook;
    XSSFSheet spreadSheet;
    XSSFRow row;
    String fullFileLocation;
    String sheetName;

    public XlsxFile(String fullFileLocation , String sheetName) throws IOException {
        this.fullFileLocation = fullFileLocation;
        this.sheetName = sheetName;
        createWorkBook();
        createSheet();
    }

    private boolean isExist() {
        return new File(fullFileLocation).exists();
    }

    private void createSheet() {
        try {
            spreadSheet = excelWorkBook.createSheet(sheetName);
        } catch (IllegalArgumentException e) {
            spreadSheet = excelWorkBook.getSheet(sheetName);
        }
    }

    private void createWorkBook() throws IOException {
        if (isExist() == true) {
            excelWorkBook = new XSSFWorkbook(new FileInputStream(fullFileLocation));
        } else {
            excelWorkBook = new XSSFWorkbook();
        }
    }

    public void appendRow(List<String> values) throws IOException {
        row = spreadSheet.createRow(spreadSheet.getLastRowNum() + 1);
        for (int cellIndex = 0; cellIndex < values.size(); cellIndex++) {
            Cell cell = row.createCell(cellIndex);
            cell.setCellValue(values.get(cellIndex));
        }
        saveToFileSystem();
    }

    public void appendCell(int columnIndex,String value) throws IOException {
        row = spreadSheet.createRow(spreadSheet.getLastRowNum() + 1);
        Cell createdCell =row.createCell(columnIndex);
        createdCell.setCellValue(value);
        saveToFileSystem();
    }
    public List<String> getFileData(){

        List<List<String>>allData = new ArrayList<>();
        Iterator<Row> rowIterator = spreadSheet.iterator();
        while (rowIterator.hasNext())
        {
            List<String>rowData = new ArrayList<>();
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                rowData.add(cell.getStringCellValue());
            }
            allData.add(rowData);
        }
        List<String>flattedData = flatten2DList(allData);

        return removeDuplicates(flattedData);
    }
    private void saveToFileSystem() throws IOException {
        FileOutputStream out = new FileOutputStream(fullFileLocation);
        excelWorkBook.write(out);
        out.close();
    }
    private List<String>flatten2DList(List<List<String>> target){
        List<String>flatList  = target.stream().flatMap(List::stream).collect(Collectors.toList());
        return flatList;
    }
    private List<String>removeDuplicates(List<String> listWithDuplicates){
        List<String> listWithoutDuplicates = listWithDuplicates.stream()
                .distinct()
                .collect(Collectors.toList());
        return listWithoutDuplicates;
    }

}
