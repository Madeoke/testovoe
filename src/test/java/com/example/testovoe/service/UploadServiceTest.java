package com.example.testovoe.service;
import com.example.testovoe.domain.UploadInfo;
import com.example.testovoe.dto.FindParams;
import com.example.testovoe.parser.ParserImpl;
import com.example.testovoe.repository.UploadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UploadServiceTest {

//    @Mock
//    private ParserImpl fileParser;
    @Mock
    private UploadRepository uploadInfoRepository;
//    @Mock
//    private CarService carInfoService;
    @InjectMocks
    private UploadService uploadService;

    UploadInfo uploadInfo = UploadInfo.builder()
            .date(new Date(150L))
            .filename("upload fileName")
            .id(123L)
            .build();


    @Test
    public void findByDate() {
        Date from = new Date(100L);
        Date to = new Date(250L);
        FindParams findParams = new FindParams(from, to);
        when(uploadInfoRepository.getUploadsByDateBetween(from, to)).thenReturn(Collections.singletonList(uploadInfo));

        uploadService.findByDate(findParams);

        verify(uploadInfoRepository, times(1)).getUploadsByDateBetween(from, to);
    }

}
