package com.example.testovoe.repository;

import com.example.testovoe.domain.UploadInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UploadRepository extends JpaRepository<UploadInfo,String> {
    List<UploadInfo> getUploadsByDateBetween(Date from, Date to);
}

