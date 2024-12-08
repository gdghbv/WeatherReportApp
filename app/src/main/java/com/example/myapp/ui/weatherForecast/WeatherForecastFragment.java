package com.example.myapp.ui.weatherForecast;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.R;
import com.example.myapp.adapter.FutureWeatherAdapter;
import com.example.myapp.bean.DayWeatherBean;
import com.example.myapp.bean.TodayWeatherBean;
import com.example.myapp.bean.FutureWeatherBean;
import com.example.myapp.databinding.FragmentWeatherForecastBinding;
import com.example.myapp.util.GetIcon;

import java.util.List;

public class WeatherForecastFragment extends Fragment {

    private String[] cities;
    private AppCompatSpinner spinner;
    private ArrayAdapter<String> spAdapter;
    private TextView tv_tem, tv_weather, tv_temRange, tv_windCondition, tv_airCondition;
    private ImageView iv_weatherIcon;
    private RecyclerView rv_forecast;
    private FutureWeatherAdapter futureWeatherAdapter;

    private WeatherForecastViewModel mViewModel;
    private FragmentWeatherForecastBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // 初始化 viewModel
        mViewModel = new ViewModelProvider(this).get(WeatherForecastViewModel.class);

        // 与fragment_weather_forecast.xml文件进行绑定
        tv_tem = binding.tvTem;
        tv_temRange = binding.tvTemRange;
        tv_weather = binding.tvWeather;
        tv_windCondition = binding.tvWindCondition;
        tv_airCondition = binding.tvAirCondition;
        spinner = binding.spCity;
        rv_forecast = binding.rvForecast;
        iv_weatherIcon = binding.ivWeatherIcon;

        cities = getResources().getStringArray(R.array.cities);
        spAdapter = new ArrayAdapter<>(getContext(), R.layout.sp_item_layout, cities);
        spinner.setAdapter(spinner.getAdapter());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedCity = cities[position];
                mViewModel.getWeatherOfToday(selectedCity);
                mViewModel.getWeatherOfFuture(selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // 观察 ViewModel 中的 LiveData
        mViewModel.getFutureWeather().observe(getViewLifecycleOwner(), this::updateUiOfFutureWeather);
        mViewModel.getTodayWeather().observe(getViewLifecycleOwner(), this::updateUiOfTodayWeather);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // 更新未来天气的 UI
    public void updateUiOfFutureWeather(FutureWeatherBean weatherBean) {
        if (weatherBean == null) return;

        List<DayWeatherBean> dayWeatherBeans = weatherBean.getData();
        dayWeatherBeans.remove(0);  // 移除今天的天气数据

        futureWeatherAdapter = new FutureWeatherAdapter(getContext(), dayWeatherBeans);
        rv_forecast.setAdapter(futureWeatherAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_forecast.setLayoutManager(layoutManager);
    }

    // 更新今天的天气 UI
    public void updateUiOfTodayWeather(TodayWeatherBean TWB) {
        if (TWB == null) return;

        tv_tem.setText(TWB.getTem());
        tv_weather.setText(TWB.getWea() + " (" + TWB.getDate() + TWB.getWeek() + ")");
        tv_temRange.setText(TWB.getTem2() + "°C ~ " + TWB.getTem1() + "°C");
        tv_windCondition.setText(TWB.getWin() + " " + TWB.getWin_speed());
        tv_airCondition.setText("空气： " + TWB.getAir() + " " + TWB.getAir_level() + "\n" + TWB.getAir_tips());

        // 处理天气图标
        GetIcon getIcon = new GetIcon();
        iv_weatherIcon.setImageResource(getIcon.getImgResOfWeather(TWB.getWea_img()));
    }
}
