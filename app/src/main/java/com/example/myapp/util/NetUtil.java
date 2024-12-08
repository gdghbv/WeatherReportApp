package com.example.myapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtil {
    //第一个天气数据url用来获取七天简略天气数据，第二个天气数据url用来获取当天详细天气数据
    public static final String WEATHER_API_URL = "http://v1.yiketianqi.com/free/week?unescape=1&appid=58541194&appsecret=5ez6u7Sr";
    public static final String TODAY_WEATHER_API_URL = "http://gfeljm.tianqiapi.com/free/v2030?cityid=&adcode=&appid=58541194&appsecret=5ez6u7Sr&lng=&lat=&aqi=&hours=";

    public static String doGet(String urlStr) {
        String result = "";
        //连接网络
        HttpURLConnection connection = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            URL urL = new URL(urlStr);
            connection = (HttpURLConnection) urL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            //从连接中读取数据，此时得到的是二进制数据
            InputStream inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
//二进制流送入缓冲区
            bufferedReader = new BufferedReader(inputStreamReader);
//从缓存区中一行行读取字符串
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return result;
    }

    public static String getWeatherOfCity(String city) {

        //拼接出天气数据url
        //http://v1.yiketianqi.com/free/week?unescape=1&appid=58541194&appsecret=5ez6u7Sr
        String weatherUrl = WEATHER_API_URL + "&city=" + city;
        String weatherResult = doGet(weatherUrl);
//        Log.d("test", "-----------weatherUrl----------" + weatherUrl);
//        Log.d("test", "-----------weatherResult----------" + weatherResult);

        return weatherResult;
    }
    public static String getTodayWeather(String city){
        String todayWeatherUrl=TODAY_WEATHER_API_URL+"&city="+city;
        String todayWeatherResult=doGet(todayWeatherUrl);
//        Log.d("test", "-----------todayWeatherUrl----------" + todayWeatherUrl);
//        Log.d("test", "-----------todayWeatherResult----------" + todayWeatherResult);
        return todayWeatherResult;
    }
}
