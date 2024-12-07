package com.example.myapp.ui.weatherForecast;

import android.os.Looper;
import android.os.Message;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.myapp.adapter.FutureWeatherAdapter;
import com.example.myapp.bean.DayWeatherBean;
import com.example.myapp.bean.TodayWeatherBean;
import com.example.myapp.bean.WeatherBean;
import com.example.myapp.util.NetUtil;
import com.google.gson.Gson;


public class WeatherForecastViewModel extends ViewModel {

    private WeatherBean weatherBean;
    private FutureWeatherAdapter futureWeatherAdapter;
    private TodayWeatherBean todayWeatherBean;
    private String[] cities;
    private ArrayAdapter<String> spAdapter;
    private Gson gson=new Gson();
    private WeatherForecastFragment weatherForecastFragment;

    public Handler mhandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                String weatherOfToday=(String)msg.obj;
                todayWeatherBean=gson.fromJson(weatherOfToday, TodayWeatherBean.class);

            }
            if(msg.what==2){
                String weatherOfFuture=(String) msg.obj;
                weatherBean=gson.fromJson(weatherOfFuture, WeatherBean.class);
            }
            Log.d("msg1","-------今日的天气数据获取成功-------"+todayWeatherBean);
            Log.d("msg2","-------未来几天的天气数据获取成功-------"+weatherBean);
        if(todayWeatherBean!=null&&weatherBean.getData()!=null){weatherForecastFragment.updateUiOfWeather(todayWeatherBean,weatherBean);}
        }

    };

    public void getWeatherOfToday(String selectedCity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String weatherOfToday = NetUtil.getTodayWeather(selectedCity);
                Message message = new Message().obtain();
                message.what = 1;
                message.obj = weatherOfToday;
                mhandler.sendMessage(message);

            }
        }).start();
    }

    public void getWeatherOfFuture(String selectedCity) {

        new Thread(new Runnable() {
        @Override
        public void run() {
            String weatherOfFuture = NetUtil.getWeatherOfCity(selectedCity);
            Message message=new Message().obtain();
            message.what=2;
            message.obj=weatherOfFuture;
            mhandler.sendMessage(message);
        }
    }).start();
    }


}