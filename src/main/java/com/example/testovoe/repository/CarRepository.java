package com.example.testovoe.repository;

import com.example.testovoe.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    List<Car> findAllByUploadInfo_Id(Long uploadId);
}
