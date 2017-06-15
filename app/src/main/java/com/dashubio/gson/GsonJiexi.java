package com.dashubio.gson;

import com.dashubio.model.HelpContent;
import com.dashubio.utils.City;
import com.dashubio.utils.LoginEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by 张梓彬 on 2016/11/26.
 */

public class GsonJiexi {
    public static List<HelpContent.DataBean> gsonData(String json){
        Gson gson = new Gson();
        HelpContent result = gson.fromJson(json,new TypeToken<HelpContent>(){}.getType());
        return result.getData();
    }


    public static List<UserGet.UserBean> gsonUserData(String json){
        Gson gson = new Gson();
        UserGet result = gson.fromJson(json,new TypeToken<UserGet>(){}.getType());
        return result.getData();
    }

    public static LoginEntity parserStateCode(String json) {
        LoginEntity result = GsonUtils.parseJsonWithGson(json, LoginEntity.class);
        return result;
    }



}

class GsonUtils {
    //将Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }
}

