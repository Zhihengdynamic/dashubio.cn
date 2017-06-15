package com.dashubio.net;


import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import com.dashubio.net.ApiAsyncTask.ApiRequestListener;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

/**
 * API utility class
 */
public class HomeAPI {
	//[start]
	/** API��ַ */
	public static final String API_HOST_JAVA = "";

	// API URLS
	/* package */static final String[] API_URLS = {
	// ACTION_DEVICE_REGISTER 0     设备注册
	API_HOST_JAVA + "touchtop/Mobile/Login/register",
	};
	//[end]
	
	/** ACTION_DEVICE_REGISTER */
	public static final int ACTION_DEVICE_REGISTER = 0;
	
	/*
	 * 设备注册
	 * nid 设备编号
	 * phone  用户手机号
	 * password  用户密码
	 * msgcode 短信验证码
	 * 返回值为{"status":"success","code":1}
	 *	注册成功code用户id 
	 *	登录失败code为非正值
	 */
	public static void deviceRegister(Context context, ApiRequestListener handler,
			String nid, int phone, String password, int msgcode) {
		StringBuffer params = new StringBuffer();
		params.append("nid").append("=").append(nid);
		params.append("&phone").append("=").append(phone);
		params.append("&password").append("=").append(password);
		params.append("&msgcode").append("=").append(msgcode);
		new ApiAsyncTask(context, ACTION_DEVICE_REGISTER, handler, params)
				.execute();
	}
	
	
}