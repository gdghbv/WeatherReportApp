package com.example.myapp.bean;

import java.util.List;

import lombok.Data;

@Data

public class FutureWeatherBean {
    private String cityid;
    private String city;
    private String update_time;
    private List<DayWeatherBean> data;

}
