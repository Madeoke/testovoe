package com.example.testovoe.service;

import com.example.testovoe.repository.UploadRepository;
import com.example.testovoe.domain.Car;
import com.example.testovoe.domain.UploadInfo;
import com.example.testovoe.dto.FindParams;
import com.example.testovoe.exceptions.NullFileException;
import com.example.testovoe.exceptions.NullFilenameException;
import com.example.testovoe.parser.ParserImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadService {
    private final ParserImpl fileParser;
    private final UploadRepository uploadRepository;
    private final CarService carService;

    public Long uploadFile(MultipartFile multipartFile) throws NullFilenameException, NullFileException {
        if(multipartFile != null) {
            if (multipartFile.getOriginalFilename() != null) {
                String fileName = multipartFile.getOriginalFilename();
                UploadInfo upload = createNewUpload(fileName);
                List<Car> cars = new ArrayList<>();
                try {
                    if (fileName.endsWith(".xlsx")) {
                        cars = fileParser.parseExcel(multipartFile);

                    } else if (fileName.endsWith(".csv")) {
                        cars = fileParser.parseCSV(multipartFile);
                    }
                    if (!cars.isEmpty()) {
                        carService.saveMany(cars, upload);
                        return upload.getId();
                    }
                } catch (IOException e) {
                    log.error(e.getMessage());
                    return null;
                }
            }
            throw new NullFilenameException("file name is null");
        }
        else throw new NullFileException("file is null");
    }

    public List<UploadInfo> findByDate(FindParams findParams) {
        if (findParams.getFrom() != null) {
            if (findParams.getTo() == null) {
                return uploadRepository.getUploadsByDateBetween(
                        findParams.getFrom(),
                        new Date());
            } else if (findParams.getFrom().getTime() < findParams.getTo().getTime()) {
                return uploadRepository.getUploadsByDateBetween(
                        findParams.getFrom(),
                        findParams.getTo()
                );
            }
        } else {
            Date from = new Date();
            if (from.getTime() < findParams.getTo().getTime()) {
                return uploadRepository.getUploadsByDateBetween(
                        from,
                        findParams.getTo()
                );
            }
        }
        return null;
    }

    private UploadInfo createNewUpload(String fileName) {
        UploadInfo upload = UploadInfo.builder()
                .date(new Date())
                .filename(fileName)
                .build();
        uploadRepository.save(upload);
        return upload;
    }
}
