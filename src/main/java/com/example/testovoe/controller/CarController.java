package com.example.testovoe.controller;

import com.example.testovoe.domain.Car;
import com.example.testovoe.exceptions.NullIdException;
import com.example.testovoe.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
@Slf4j
public class CarController {
    private final CarService carInfoService;

    @GetMapping("/upload/{uploadId}")
    public List<Car> getCarInfoByUploadId(@PathVariable Long uploadId) {
        try {
            return carInfoService.getCarInfoByUploadId(uploadId);
        } catch (NullIdException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
