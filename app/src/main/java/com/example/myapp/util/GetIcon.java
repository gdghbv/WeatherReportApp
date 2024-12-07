package com.example.myapp.util;


import com.example.myapp.R;

public class GetIcon {
    public  int getImgResOfWeather(String weaStr){
        int result=0;
        switch(weaStr){
            case "xue":
                result= R.drawable.xue;
                break;
            case "lei":
                result=R.drawable.lei;
                break;
            case "shacen":
                result=R.drawable.shachen;
                break;
            case "wu":
                result=R.drawable.wu;
            case "bingbao":
                result=R.drawable.bingbao;
                break;
            case "yun":
                result=R.drawable.yun;
                break;
            case "yu":
                result=R.drawable.yu;
                break;
            case"yin"   :
                result=R.drawable.yin;
                break;
            case "qing":
                result=R.drawable.qing;

        }
        return result;
    }
}
