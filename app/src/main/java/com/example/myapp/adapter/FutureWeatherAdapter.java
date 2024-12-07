package com.example.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapp.R;
import com.example.myapp.bean.DayWeatherBean;
import com.example.myapp.util.GetIcon;

import java.util.List;

public class FutureWeatherAdapter extends RecyclerView.Adapter<FutureWeatherAdapter.WeatherViewHolder> {
    private Context context;
    private List<DayWeatherBean> dayWeatherBeans;

    public FutureWeatherAdapter(Context context, List<DayWeatherBean> dWB) {
        this.context = context;
        this.dayWeatherBeans=dWB;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(context).inflate(R.layout.weather_item_layout, parent, false);
        WeatherViewHolder holder=new WeatherViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
DayWeatherBean dayWeatherBean=dayWeatherBeans.get(position);
holder.tv_weather.setText(dayWeatherBean.getWea());
holder.tv_date.setText(dayWeatherBean.getDate());
holder.tv_tem_range.setText(dayWeatherBean.getTem_night()+"~"+dayWeatherBean.getTem_day()+"°C");
holder.tv_windCondition.setText(dayWeatherBean.getWin()+dayWeatherBean.getWin_speed());
GetIcon getIcon=new GetIcon();
holder.iv_weatherIcon.setImageResource(getIcon.getImgResOfWeather(dayWeatherBean.getWea_img()));
    }


    @Override
    public int getItemCount() {
        if(dayWeatherBeans==null){return 0;}
        return dayWeatherBeans.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder{


        TextView tv_date,tv_tem_range,tv_weather,tv_windCondition;
        ImageView iv_weatherIcon;

    public WeatherViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_date=itemView.findViewById(R.id.tv_date);
        tv_tem_range=itemView.findViewById(R.id.tv_tem_range);
        tv_weather=itemView.findViewById(R.id.tv_weather);
        tv_windCondition=itemView.findViewById(R.id.tv_windCondition);
        iv_weatherIcon=itemView.findViewById(R.id.iv_weatherIcon);

    }

}
}
