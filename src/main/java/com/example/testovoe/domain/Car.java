package com.example.testovoe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Car {
    @Id
    private String id;
    private String name;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "upload_id")
    private UploadInfo uploadInfo;
}
