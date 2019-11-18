package com.ynz.msaccess.demo.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Component
public class FileHelper {

    private Set<String> allowedMimeTypes;

    public FileHelper() {
        allowedMimeTypes = Stream.of("xls", "xlsx").collect(toSet());
    }

    public Set<String> getAllowedMimeType() {
        return allowedMimeTypes;
    }

    private InputStream convertFileIntoInputStream(File file, String mimeType) throws FileNotFoundException {
        boolean anyMatched = allowedMimeTypes.stream().anyMatch(am -> am.contentEquals(mimeType));
        if (!anyMatched) throw new IllegalArgumentException("wrong file type.");

        InputStream inputStream = new FileInputStream(file);
        return inputStream;
    }

    public InputStream convertFileIntoInputStream(Path path) throws IOException {
        //If the given file is not a Excel file, then throw an exception.
        String contentType = fetchingFileTypeFromPath(path)
                .orElseThrow(() -> new IllegalArgumentException("missing mime type."));

        boolean anyMatched = allowedMimeTypes.stream().anyMatch(am -> am.contentEquals(contentType));
        if (!anyMatched) throw new IllegalArgumentException("wrong file type.");

        //converting the path into input stream with mark and reset.
        return new BufferedInputStream(Files.newInputStream(path));
    }

    public Optional<String> fetchingFileTypeFromPath(Path path) {
        String[] r = path.toString().split("[.]");
        return Optional.ofNullable(r[1]);
    }

    private Workbook createWorkBookFromFileInputStream(InputStream inputStream) throws IOException, InvalidFormatException {
        return WorkbookFactory.create(inputStream);
    }

    public Workbook createExcelWorkBookFromPath(Path path) throws IOException, InvalidFormatException {
        InputStream inputStream = convertFileIntoInputStream(path);
        return createWorkBookFromFileInputStream(inputStream);
    }

}
