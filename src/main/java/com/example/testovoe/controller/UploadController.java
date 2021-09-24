package com.example.testovoe.controller;

import com.example.testovoe.domain.UploadInfo;
import com.example.testovoe.dto.FindParams;
import com.example.testovoe.exceptions.NullFileException;
import com.example.testovoe.exceptions.NullFilenameException;
import com.example.testovoe.service.UploadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
@Slf4j
public class UploadController {
    private final UploadService uploadService;

    @PostMapping(path = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long uploadFile(@RequestParam("files") MultipartFile file) {
        try{
            return uploadService.uploadFile(file);
        }
        catch (NullFilenameException | NullFileException e){
            log.error(e.getMessage());
            return 0L;
        }
    }

    @PostMapping(path = "upload/get/by/date")
    public List<UploadInfo> findByDate(@RequestBody FindParams findParams) {
        return uploadService.findByDate(findParams);
    }

}