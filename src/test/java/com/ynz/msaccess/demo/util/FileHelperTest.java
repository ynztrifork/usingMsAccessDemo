package com.ynz.msaccess.demo.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FileHelperTest {

    private static final String mockExcelFileName = "mock-excel-file-name.xlsx";
    private static final String relativePath = "src/main/resources/";

    private static final Path mockFilePath = Paths.get(relativePath, mockExcelFileName);
    private Workbook workbook;

    @Autowired
    private FileHelper fileHelper;

    private void createExcelFile() throws IOException, InvalidFormatException {
        //mock an excel file.
        workbook = new WorkbookFactory().create(mockFilePath.toFile());
    }

    @BeforeEach
    public void setup() throws IOException, InvalidFormatException {
        createExcelFile();
    }

    @Test
    void testWorkbookWasCreated() throws IOException {
        assertNotNull(workbook);
        int sheetNums = workbook.getNumberOfSheets();

        assertThat(sheetNums, greaterThan(0));
        assertThat(sheetNums, is(1));
        workbook.close();
    }

    @Test
    void testFileHelperIsInjected() {
        assertNotNull(fileHelper);
    }

    @Test
    void testMockFilePath() {
        assertNotNull(mockFilePath);
        assertTrue(mockFilePath.endsWith(mockExcelFileName));
        assertTrue(mockFilePath.startsWith(relativePath));
    }

    @Test
    void givenPathToExcel_ThenReturnInputStreamNotNull() throws IOException {
        InputStream inputStream = fileHelper.convertFileIntoInputStream(mockFilePath);
        assertNotNull(inputStream);
    }

    @Test
    void givenPathToExcel_ThenReturnItsFileType() {
        String fileType = fileHelper.fetchingFileTypeFromPath(mockFilePath)
                .orElseThrow(() -> new NullPointerException("having no mime type."));
        assertThat(fileType, is("xlsx"));
        FileHelper fileHelper = new FileHelper();
        assertThat(fileType, is(in(fileHelper.getAllowedMimeType())));

    }

    @Test
    void givenPathToExcel_ThenReturnItsExcelWorkBook() throws IOException, InvalidFormatException {
        Workbook workbook = fileHelper.createExcelWorkBookFromPath(mockFilePath);
        assertNotNull(workbook);
        assertThat(workbook.getNumberOfSheets(), is(1));
        assertThat(workbook.getNumberOfNames(), is(0));

        Sheet sheet = workbook.getSheetAt(0);
        List<Row> rows = new ArrayList<>();
        sheet.forEach(rows::add);

        //I pre-inserted a single row in this excel file.
        assertThat(rows.size(), is(1));

        List<Cell> cells = new ArrayList<>();
        rows.get(0).forEach(cell -> cells.add(cell));

        assertThat(cells.size(), is(3));
        workbook.close();
    }

}