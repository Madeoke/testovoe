package com.example.testovoe.parser;

import com.example.testovoe.domain.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ParserInterface {
    List<Car> parseExcel(MultipartFile multipartFile) throws IOException;
    List<Car> parseCSV(MultipartFile multipartFile) throws IOException;
}
