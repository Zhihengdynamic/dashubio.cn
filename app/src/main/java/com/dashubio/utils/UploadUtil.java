package com.dashubio.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.dashubio.R;
import com.dashubio.model.UploadPhotoResponse;
import com.dashubio.utils.log.Logger;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class UploadUtil {
	//private static final String TAG = "uploadFile";
	private static final int TIME_OUT = 10 * 1000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码
	static String end ="\r\n";
	static String twoHyphens ="--";
	static String boundary ="*****";
	public static UploadPhotoResponse uploadBitmap(final Context context , final Map<String,String> params, final Map<String, File> fileMap, final String RequestURL) {
		final UploadPhotoResponse response = new UploadPhotoResponse();
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				int res = 0;
				String result = null;
				String BOUNDARY = "*****"; // 边界标识 随机生成
				String PREFIX = "--", LINE_END = "\r\n";
				String CONTENT_TYPE = "multipart/form-data"; // 内容类型
				//String CONTENT_TYPE ="Accept:*/*";

				try {
					URL url = new URL(RequestURL);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
				/* 允许Input、Output，不使用Cache */
					con.setDoInput(true);
					con.setDoOutput(true);
					con.setUseCaches(false);
				/* 设置传送的method=POST */
					con.setRequestMethod("POST");

				/* setRequestProperty */
					con.setRequestProperty("Connection", "Keep-Alive");
					con.setRequestProperty("accept", "application/json");
					con.setRequestProperty("Charset", "UTF-8");
					con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				/* 设置DataOutputStream */
					DataOutputStream ds = new DataOutputStream(con.getOutputStream());
//					HashMap<String,String> params = new HashMap<String, String>();
//					params.put("roleType", "1");
					writeStringParams(params,ds);
//					HashMap<String, File> fileMap = new HashMap<String, File>();
//					fileMap.put("card_img", new File(imgFile));
					writeFileParams(fileMap, ds);
					paramsEnd(ds);

					ds.flush();
					/**
					 * 获取响应码 200=成功 当响应成功，获取响应的流
					 */
					res = con.getResponseCode();
					Logger.i("upload response code:" + res);
					if (res == 200) {
						Logger.i("upload request success");
						InputStream input = con.getInputStream();
						StringBuffer sb1 = new StringBuffer();
						int ss;
						while ((ss = input.read()) != -1) {
							sb1.append((char) ss);
						}
						result = sb1.toString();
						response.setState(result);

						final JSONObject jsonObject = new JSONObject(result);
						final String code = jsonObject.getString("code");
						if("success".equals(jsonObject.getString("status"))){
							((Activity)context).runOnUiThread(new Runnable() {
								@Override
								public void run() {
									ToastUtils.show(context, R.string.register_success_please_wait_for_background_audit);
								}
							});
						}else{
							((Activity)context).runOnUiThread(new Runnable() {
								@Override
								public void run() {
									ToastUtils.show(context, context.getString(R.string.operation_failed) + " 错误码：" + code);
								}
							});
						}


						Logger.i("upload response.status : " + response.getState());
					} else {
						Logger.i("upload request error");
					}
				} catch (Exception e) {
					e.printStackTrace();
					res = 0;
				}
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
		return response;
	}

	// 普通字符串数据
	private static void writeStringParams(Map<String, String> params, DataOutputStream ds) throws Exception
	{
		ChangeCharset changeCharset = new ChangeCharset();
		Set<String> keySet = params.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();)
		{
			String name = it.next();
			String value = params.get(name);
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; name=\"" + name +"\""+ end);
			ds.writeBytes(end);
			//ds.writeBytes(URLEncoder.encode(value,"utf-8") + end);
			String tranfValue = "";
//			if(!name.equals("p_opera")){
				tranfValue = changeCharset.toISO_8859_1(value);
//			}else{
//				tranfValue = value;
//			}
			ds.writeBytes(tranfValue + end);
		}
	}
	// 文件数据
	private  static void writeFileParams(Map<String, File> params, DataOutputStream ds) throws Exception
	{
		Set<String> keySet = params.keySet();
		for (Iterator<String> it = keySet.iterator(); it.hasNext();)
		{
			String name = it.next();
			File value = params.get(name);
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + URLEncoder.encode(value.getName()) + "\""+end);
			ds.writeBytes("Content-Type: " + "image/jpg" + end);
			ds.writeBytes(end);
			ds.write(getBytes(value));
			ds.writeBytes(end);
		}
	}
	// 获取文件的上传类型，图片格式为image/png,image/jpg等。非图片为application/octet-stream
	private String getContentType(File f) throws Exception
	{
		return "image/jpg";
	}
	// 把文件转换成字节数组
	private static byte[] getBytes(File f) throws Exception
	{
		FileInputStream in = new FileInputStream(f);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int n;
		while ((n = in.read(b)) != -1)
		{
			out.write(b, 0, n);
		}
		in.close();
		return out.toByteArray();
	}
	//添加结尾数据
	private static void  paramsEnd( DataOutputStream ds) throws Exception {
		ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
		ds.writeBytes(end);
	}
	public static UploadPhotoResponse uploadBitmap(Context context ,Bitmap bitmap,String picName, String RequestURL) {
		UploadPhotoResponse response = new UploadPhotoResponse();

		return new UploadPhotoResponse();
	}
}
