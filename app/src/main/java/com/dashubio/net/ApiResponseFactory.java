package com.dashubio.net;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.json.JSONException;

import com.dashubio.model.entity.DeviceRegister;
import com.dashubio.utils.SharePreferenceUtil;
import com.dashubio.utils.Utils;

import android.content.Context;
import android.util.Log;


/**
 * API ��Ӧ������������࣬���е�API��Ӧ���������Ҫ�ڴ���ɡ�
 */
public class ApiResponseFactory {
	// private static final String TAG = "ApiResponseFactory";

	/**
	 * �����г�API��Ӧ���?
	 * 
	 * @param action
	 *            ����API����
	 * @param response
	 *            HTTP Response
	 * @return ������Ľ���������������᷵��Null��
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public static Object getResponse(Context context, int action,
			HttpResponse response) throws JSONException,  IOException {

		InputStream i = null;
		Log.i("action", "" + action);
		i = Utils.getInputStreamResponse(response);
		if (i == null) {
			return null;
		}
		String in = Utils.convertStreamToString(i);
		Log.i("in", in);
		String requestMethod = "";
		Object result = null;
		SharePreferenceUtil sharePreferenceUtil = new SharePreferenceUtil(context, Utils.SAVE_USER);
		switch (action) {
		case HomeAPI.ACTION_DEVICE_REGISTER:
			// ACTION_DEVICE_REGISTER
			requestMethod = "ACTION_DEVICE_REGISTER";
			result = in;
			result = parserJsonResult(in, DeviceRegister.class);
			break;
		
		default:
			break;
		}
		if (result != null) {
			Utils.D(requestMethod + "'s Response is : " + result.toString());
		} else {
			Utils.D(requestMethod + "'s Response is null");
		}
		return result;
	}

	/*
	 * ����api��Ӧ�� ������һ����json
	 */
	private static <T> T parserJsonResult(String in, Class<T> cls) {
		// TODO Auto-generated method stub
		T t = null;
		if (in == null) {
			return null;
		}
//		try {
//			Gson gson = new Gson();
//			t = gson.fromJson(in, cls);
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.i("1111", e.getMessage());
//		}
		return t;
	}
	
	

}