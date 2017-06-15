package com.dashubio.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by zzb on 2017/2/15.
 */

public class GsonTitle {
    public static ADTitle.DataBean gsonData(String json){
        Gson gson = new Gson();
        ADTitle result = gson.fromJson(json,new TypeToken<ADTitle>(){}.getType());
        return result.getData();
    }
}
