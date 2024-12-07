package com.example.myapp.ui.weatherForecast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.myapp.bean.TodayWeatherBean;
import com.example.myapp.bean.WeatherBean;
import com.example.myapp.databinding.FragmentWeatherForecastBinding;

public class WeatherForecastFragment extends Fragment {
    private String [] cities;
private AppCompatSpinner spinner;
private ArrayAdapter<String>  spAdapter;
private TextView tv_tem,tv_weather,tv_temRange,tv_windCondition,tv_airCondition;
private ImageView iv_weather;
private RecyclerView rv_forecast;
private FutureWeatherAdapter futureWeatherAdapter;

    private WeatherForecastViewModel mViewModel;

    private FragmentWeatherForecastBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding=FragmentWeatherForecastBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
//与fragment_weather_forecast.xml文件进行绑定
        tv_tem=binding.tvTem;
        tv_temRange=binding.tvTemRange;
        tv_weather=binding.tvWeather;
        tv_windCondition=binding.tvWindCondition;
        tv_airCondition=binding.tvAirCondition;
        spinner=binding.spCity;
        rv_forecast=binding.rvForecast;
        iv_weather=binding.ivWeatherIcon;
        cities=getResources().getStringArray(R.array.cities);
        spAdapter=new ArrayAdapter<>(getContext(),R.layout.sp_item_layout,cities);
        spinner.setAdapter(spinner.getAdapter());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedCity=cities[position];
              mViewModel.getWeatherOfToday(selectedCity);
              mViewModel.getWeatherOfFuture(selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//
        return root;
    }

    @Override
  public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    public void updateUiOfWeather(TodayWeatherBean todayWeatherBean, WeatherBean weatherBean) {

    }
}