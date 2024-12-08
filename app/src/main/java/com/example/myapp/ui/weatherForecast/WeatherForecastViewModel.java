package com.example.myapp.ui.weatherForecast;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapp.bean.TodayWeatherBean;
import com.example.myapp.bean.FutureWeatherBean;
import com.example.myapp.util.NetUtil;
import com.google.gson.Gson;

public class WeatherForecastViewModel extends ViewModel {

    static private MutableLiveData<TodayWeatherBean> todayWeather = new MutableLiveData<>();
    static private MutableLiveData<FutureWeatherBean> futureWeather = new MutableLiveData<>();
    private Gson gson = new Gson();

    public MutableLiveData<TodayWeatherBean> getTodayWeather() {
        return todayWeather;
    }

    public MutableLiveData<FutureWeatherBean> getFutureWeather() {
        return futureWeather;
    }

    // 通用的Handler，用于更新数据
    private final Handler weatherHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String weatherData = (String) msg.obj;
            if (msg.what == 1) {
                // 更新今日天气
                TodayWeatherBean todayWeatherBean = gson.fromJson(weatherData, TodayWeatherBean.class);
                todayWeather.setValue(todayWeatherBean);
                Log.d("today","------获取到今日天气数据------"+todayWeatherBean);
            } else if (msg.what == 2) {
                // 更新未来天气
                FutureWeatherBean futureWeatherBean = gson.fromJson(weatherData, FutureWeatherBean.class);
                futureWeather.setValue(futureWeatherBean);
                Log.d("future","------获取到未来天气数据------"+futureWeatherBean);
            }
        }
    };

    // 获取今日天气
    public void getWeatherOfToday(String selectedCity) {
        new Thread(() -> {
            String weatherOfToday = NetUtil.getTodayWeather(selectedCity);
            Message message = weatherHandler.obtainMessage(1, weatherOfToday);
            weatherHandler.sendMessage(message);
        }).start();
    }

    // 获取未来天气
    public void getWeatherOfFuture(String selectedCity) {
        new Thread(() -> {
            String weatherOfFuture = NetUtil.getWeatherOfCity(selectedCity);
            Message message = weatherHandler.obtainMessage(2, weatherOfFuture);
            weatherHandler.sendMessage(message);
        }).start();
    }
}
