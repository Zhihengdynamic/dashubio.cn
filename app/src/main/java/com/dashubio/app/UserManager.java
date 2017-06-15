package com.dashubio.app;

import android.content.Context;

import com.dashubio.model.ucenter.User;
import com.dashubio.utils.PreferencesUtils;
import com.dashubio.utils.Utils;

/**
 * Created by tangjie on 2016/2/2.
 */
public class UserManager {

    public static String USER_PREFERENCE_NAME = "userPref";

    public static String KEY_USERID = "KeyUserID";//用户ID
    public static String KEY_USERNAME = "KeyUsername";//用户名
    public static String KEY_PASSWORD = "KeyPassword";//用户密码
    public static String KEY_SESSION = "KeySession";//session
    public static String KEY_COMPANY = "KeyCompany";//Company

    private static UserManager instance;

    public synchronized static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private UserManager() {
        PreferencesUtils.PREFERENCE_NAME = USER_PREFERENCE_NAME;
    }

    /**
     * 是否登录
     *
     * @return
     */
    public synchronized boolean isLogin() {
        String userId = PreferencesUtils.getString(AppApplication.getContext(), KEY_USERID);
        int uid = Utils.string2Int(userId);
        boolean isLogin = false;
        if (uid > 0) {
            isLogin = true;
        }
        return isLogin;
    }

    /**
     * 获取Uid
     *
     * @return
     */
    public synchronized String getUid() {
        return PreferencesUtils.getString(AppApplication.getContext(), KEY_USERID);
    }

    /**
     * 获取username
     *
     * @return
     */
    public synchronized String getUsername() {
        return PreferencesUtils.getString(AppApplication.getContext(), KEY_USERNAME);
    }

    /**
     * 获取密码
     *
     * @return
     */
    public synchronized String getPassword() {
        return PreferencesUtils.getString(AppApplication.getContext(), KEY_PASSWORD);
    }

    /**
     * 获取session
     *
     * @return
     */
    public synchronized String getSession() {
        return PreferencesUtils.getString(AppApplication.getContext(), KEY_SESSION);
    }

    /**
     * 获取Company
     *
     * @return
     */
    public synchronized String getCompany() {
        return PreferencesUtils.getString(AppApplication.getContext(), KEY_COMPANY);
    }


    /**
     * 保存用户信息到本地
     *
     * @param user
     */
    public synchronized static void saveUser(User user) {
        PreferencesUtils.putString(AppApplication.getContext(), KEY_USERID, user.getUserid());
        PreferencesUtils.putString(AppApplication.getContext(), KEY_USERNAME, user.getUsername());
        PreferencesUtils.putString(AppApplication.getContext(), KEY_PASSWORD, user.getPassword());
        PreferencesUtils.putString(AppApplication.getContext(), KEY_SESSION, user.getT_session());
        PreferencesUtils.putString(AppApplication.getContext(), KEY_COMPANY, user.getCompany());
    }

    /**
     * 清空数据
     */
    public synchronized void clearData(Context context) {
        PreferencesUtils.clearData(context);
    }
}
