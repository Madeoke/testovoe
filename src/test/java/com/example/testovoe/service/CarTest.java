package com.example.testovoe.service;

import com.example.testovoe.domain.Car;
import com.example.testovoe.exceptions.NullIdException;
import com.example.testovoe.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    public void getCarInfoByUploadId() {
        Long id = 10L;
        when(carRepository.findAllByUploadInfo_Id(id)).thenReturn(Collections.singletonList(Car.builder().build()));

        try {
            carService.getCarInfoByUploadId(id);
        } catch (NullIdException e) {
            System.out.println(e.getMessage());
            fail();
        }

        verify(carRepository, times(1)).findAllByUploadInfo_Id(id);
    }

    @Test
    public void getCarInfoByUploadId_when_id_is_null() {
        Long id = null;

        try {
            carService.getCarInfoByUploadId(id);
        } catch (NullIdException e) {
            System.out.println(e.getMessage());
        }

        verify(carRepository, times(0)).findAllByUploadInfo_Id(id);
    }
}
