package com.ssu.howabouthere.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Board {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    private String title;
    // UTF8MB4
    private String contents;
    @NonNull
    @Size(max = 20)
    private String name;
    @NonNull
    private Double longitude;
    @NonNull
    private Double latitude;

    private String region_1st_name;
    private String region_2nd_name;
    private String region_3rd_name;
    private String region_4th_name;

    @NonNull
    private LocalDateTime publishedDate;
}
