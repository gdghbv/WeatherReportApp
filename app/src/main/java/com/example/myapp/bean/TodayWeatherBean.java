package com.example.myapp.bean;

import lombok.Data;

@Data
public class TodayWeatherBean {
    private String cityid;
    private String city;
    private String tem;
    private String tem2;//当日最低温
    private String tem1;//当日最高温
    private String wea;
    private String wea_img;
    private String win;
    private String win_speed;
    private String air;
    private String air_level;
    private String air_tips;
    private String date;
    private String week;

}
