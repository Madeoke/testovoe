package com.example.testovoe.parser;

import com.example.testovoe.domain.Car;
import com.example.testovoe.domain.CarModels;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
@Data
@AllArgsConstructor
public class ParserImpl implements ParserInterface {

    @Override
    public List<Car> parseExcel(MultipartFile multipartFile) throws IOException {
        InputStream excelIs = multipartFile.getInputStream();
        Workbook workbook = WorkbookFactory.create(excelIs);
        List<Car> cars = new ArrayList<>();

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();
                //Пропускаем первый ряд
                if (!currentRow.getCell(0).getStringCellValue().equals("Наименование")) {

                    Iterator<Cell> cellIterator = currentRow.cellIterator();
                    Car car = new Car();
                    boolean isRequired = false;
                    while (cellIterator.hasNext()) {
                        Cell currentCell = cellIterator.next();
                        //Пропускать значения вне справочника
                        if (currentCell.getCellType() == CellType.STRING &&
                                CarModels.BMW.getCars().stream().anyMatch(str -> str.equals(currentCell.getStringCellValue()))) {
                            car.setName(currentCell.getStringCellValue());
                            isRequired = true;
                        }
                        if (currentCell.getCellType() == CellType.NUMERIC && isRequired) {
                            if (DateUtil.isCellDateFormatted(currentCell)) {
                                car.setDate(DateUtil.getJavaDate(currentCell.getNumericCellValue()));
                            } else {
                                car.setId(Long.toString((long) currentCell.getNumericCellValue()));
                            }
                        }
                    }
                    if (car.getId() != null || car.getDate() != null || car.getName() != null) {
                        cars.add(car);
                    }
                }
            }
        }
        log.info("parsed xlsx file {}", multipartFile.getOriginalFilename());
        return cars;
    }

    @Override
    public List<Car> parseCSV(MultipartFile multipartFile) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
            CSVReader csvReader = new CSVReader(reader);
            List<Car> cars = new ArrayList<>();
            String[] str = csvReader.readNext();
            while (str != null) {
                str = csvReader.readNext();
                if (str != null) {
                    String[] values = str[0].split(";");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String name = values[0];
                    Date date = formatter.parse(values[1]);
                    String id = values[2];

                    if (CarModels.BMW.getCars().stream().anyMatch(s -> s.equals(name))) {
                        Car car = Car.builder()
                                .name(name)
                                .id(id)
                                .date(date)
                                .build();
                        if (car.getId() != null || car.getDate() != null || car.getName() != null) {
                            cars.add(car);
                        }
                    }
                }
            }
            return cars;
        } catch (IOException | CsvValidationException |
                ParseException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
