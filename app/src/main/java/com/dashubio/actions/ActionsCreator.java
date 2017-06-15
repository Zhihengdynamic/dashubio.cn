package com.dashubio.actions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Android Studio
 * User: Xinbin Zhang
 * Date: 2016-08-06
 * Time: 12:14
 */
public class ActionsCreator implements Serializable{

    private static Map<String, ActionsCreator> mActionsCreatorMap = new HashMap<>();

    public synchronized static <T> T getInstance(Class<T> cls) {
        if (null == mActionsCreatorMap.get(cls.getName())) {
            try {
                mActionsCreatorMap.put(cls.getName(), (ActionsCreator) cls.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return (T) mActionsCreatorMap.get(cls.getName());
    }


}
