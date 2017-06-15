package com.dashubio.utils;

import android.os.Handler;
import android.os.Message;

import com.dashubio.app.ErrorCode;
import com.dashubio.model.ucenter.User;
import com.dashubio.ui.activity.LoginAndRegisterActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/15 0015.
 */
public class HttpUtils {


    private static String PATH = "http://dashubio.returnlive.com/Mobile/Login/login"; // 服务端地址
    private static URL url;

    public HttpUtils() {
        super();
    }

    // 静态代码块实例化url
    static {
        try {
            url = new URL(PATH);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息体到服务端
     *
     * @param params
     * @param encode
     * @return
     */
    public static String sendPostMessage(Map<String, String> params, String encode, Handler handler) {
        StringBuilder stringBuilder = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    stringBuilder
                            .append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), encode))
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url
                        .openConnection();
                urlConnection.setConnectTimeout(3000);
                urlConnection.setRequestMethod("POST"); // 以post请求方式提交
                urlConnection.setDoInput(true); // 读取数据
                urlConnection.setDoOutput(true); // 向服务器写数据
                // 获取上传信息的大小和长度
                byte[] myData = stringBuilder.toString().getBytes();
                // 设置请求体的类型是文本类型,表示当前提交的是文本数据
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Length",
                        String.valueOf(myData.length));
                // 获得输出流，向服务器输出内容
                OutputStream outputStream = urlConnection.getOutputStream();
                // 写入数据
                outputStream.write(myData, 0, myData.length);
                outputStream.close();
                // 获得服务器响应结果和状态码
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    // 取回响应的结果
                    String result = changeInputStream(urlConnection.getInputStream(), encode);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String status = jsonObject.getString("status");
                        if(ErrorCode.SUCCESS.equals(status)){
                            Message msg = Message.obtain(handler);
                            msg.what = LoginAndRegisterActivity.MSG_SUCCESS;
                            User user = new User();
                            user.setUserid(jsonObject.getString("code"));
                            user.setT_session(jsonObject.getString(Utils.T_SESSION + user.getUserid()));
                            msg.obj = user;
                            msg.sendToTarget();
                        }else{
                            Message msg = Message.obtain(handler);
                            msg.what = LoginAndRegisterActivity.MSG_FAILURE;
                            msg.arg1 = jsonObject.getInt("code");
                            msg.sendToTarget();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Message msg = Message.obtain(handler);
                        msg.what = LoginAndRegisterActivity.MSG_FAILURE;
                        msg.sendToTarget();
                    }
                    return result;
                }else{
                    Message msg = Message.obtain(handler);
                    msg.what = LoginAndRegisterActivity.MSG_FAILURE;
                    msg.sendToTarget();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Message msg = Message.obtain(handler);
                msg.what = LoginAndRegisterActivity.MSG_FAILURE;
                msg.sendToTarget();
            }

        }
        return "";
    }



    /**
     * 发送消息体到服务端
     *
     * @param params
     * @param encode
     * @return
     */
    public static String sendGetMessage(String myUrl, Map<String, String> params, String encode, Handler handler) {
        StringBuilder stringBuilder = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    stringBuilder
                            .append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), encode))
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            try {
                url = new URL(myUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(3000);
                urlConnection.setRequestMethod("GET"); // 以post请求方式提交
                urlConnection.setDoInput(true); // 读取数据
                urlConnection.setDoOutput(true); // 向服务器写数据
                // 获取上传信息的大小和长度
                byte[] myData = stringBuilder.toString().getBytes();
                // 设置请求体的类型是文本类型,表示当前提交的是文本数据
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Length",
                        String.valueOf(myData.length));
                // 获得输出流，向服务器输出内容
                OutputStream outputStream = urlConnection.getOutputStream();
                // 写入数据
                outputStream.write(myData, 0, myData.length);
                outputStream.close();
                // 获得服务器响应结果和状态码
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    // 取回响应的结果
                    String result = changeInputStream(urlConnection.getInputStream(), encode);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String status = jsonObject.getString("status");
                        if(ErrorCode.SUCCESS.equals(status)){
//                            Message msg = Message.obtain(handler);
//                            msg.what = LoginAndRegisterActivity.MSG_SUCCESS;
//                            User user = new User();
//                            user.setUserid(jsonObject.getString("code"));
//                            user.setT_session(jsonObject.getString(Utils.T_SESSION + user.getUserid()));
//                            msg.obj = user;
//                            msg.sendToTarget();
                        }else{
                            Message msg = Message.obtain(handler);
                            msg.what = LoginAndRegisterActivity.MSG_FAILURE;
                            msg.arg1 = jsonObject.getInt("code");
                            msg.sendToTarget();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Message msg = Message.obtain(handler);
                        msg.what = LoginAndRegisterActivity.MSG_FAILURE;
                        msg.sendToTarget();
                    }
                    return result;
                }else{
                    Message msg = Message.obtain(handler);
                    msg.what = LoginAndRegisterActivity.MSG_FAILURE;
                    msg.sendToTarget();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Message msg = Message.obtain(handler);
                msg.what = LoginAndRegisterActivity.MSG_FAILURE;
                msg.sendToTarget();
            }

        }
        return "";
    }

    /**
     * 将一个输入流转换成指定编码的字符串
     *
     * @param inputStream
     * @param encode
     * @return
     */
    private static String changeInputStream(InputStream inputStream, String encode) {

        // 内存流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = null;
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, len);
                }
                result = new String(byteArrayOutputStream.toByteArray(), encode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("username", "admin");
//        map.put("password", "123456");
//        String result = sendPostMessage(map, "UTF-8");
//        System.out.println(">>>" + result);
//    }

}
