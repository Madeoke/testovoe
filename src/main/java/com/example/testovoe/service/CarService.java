package com.example.testovoe.service;



import com.example.testovoe.repository.CarRepository;
import com.example.testovoe.domain.Car;
import com.example.testovoe.domain.UploadInfo;
import com.example.testovoe.exceptions.NullIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carInfoRepository;

    public List<Car> getCarInfoByUploadId(Long uploadId) throws NullIdException {
        if (uploadId == null) {
            throw new NullIdException("upload id is null");
        }
        return carInfoRepository.findAllByUploadInfo_Id(uploadId);
    }

    public void save(Car carInfo) {
        carInfoRepository.save(carInfo);
    }

    public void saveMany(List<Car> cars, UploadInfo upload) {
        cars.forEach(car -> {
            car.setUploadInfo(upload);
            save(car);
        });
        log.info("saved cars with upload id {}", upload.getId());
    }
}
