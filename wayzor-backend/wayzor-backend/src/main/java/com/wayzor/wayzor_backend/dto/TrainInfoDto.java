package com.wayzor.wayzor_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainInfoDto {
    private String from_sta;
    private String train_number;
    private String train_name;
    private List<String> run_days;
    private String train_src;
    private String train_dstn;
    private String from;
    private String to;
    private String from_station_name;
    private String to_station_name;
    private String from_std;
    private String to_sta;
    private String duration;
    private List<String> class_type;
    private String train_date;
    private boolean has_pantry;
    // add other fields if needed
}

