package com.example.myapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("1.本应用为天气预报应用，使用了底部导航。应用涉及了网络请求、数据解析、数据展示、线程处理等功能。\n2.使用了NetUtil工具来获取网络接口，在AndroidManifest.xml文件中添加了网络权限");
    }

    public LiveData<String> getText() {
        return mText;
    }
}