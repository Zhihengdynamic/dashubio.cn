package com.dashubio.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import com.dashubio.commons.Session;
import com.dashubio.utils.Utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * ������ǻ�ȡAPI�������ݵĹ�������   
 * �����϶����õ�json
 */
public class ApiRequestFactory {

    private static ArrayList<Integer> S_XML_REQUESTS = new ArrayList<Integer>();
    private static ArrayList<Integer> S_JSON_REQUESTS = new ArrayList<Integer>();

    static {
    	// JSON
    	S_JSON_REQUESTS.add(HomeAPI.ACTION_DEVICE_REGISTER);
    	
    }
    // ����Ҫ���л����API
    public static ArrayList<Integer> API_NO_CACHE_MAP = new ArrayList<Integer>();
    static {
    	API_NO_CACHE_MAP.add(HomeAPI.ACTION_DEVICE_REGISTER);
    	
    }
    
    /**
     * ��ȡ API HttpReqeust 
     */
    public static HttpUriRequest getRequest(String url, int action, HttpEntity entity,
            Session session) throws IOException {
    	if (S_JSON_REQUESTS.contains(action)) {
    		
            HttpPost request = new HttpPost(url);
           
            // update the g-header
//            request.setHeader("User-Agent", session.getJavaApiUserAgent()); //G-Header
//            request.setHeader("Category", session.getJavaApiUserAgent1()); 
//            request.addHeader("Accept-Encoding", "gzip");
            request.addHeader("Content-Type", "application/x-www-form-urlencoded;");
            if (entity != null) {
            	request.setEntity(AndroidHttpClient.getCompressedEntity(entity.getContent()));
			}
            
            
            return request;
        }
		return null; 
    }
    
    /**
     * ��ȡ API HTTP ��������
     * 
     * @param action �����API Code
     * @param params �������?
     * @return ������ɵ���������?
     * @throws UnsupportedEncodingException ���粻֧��UTF8���뷽ʽ���׳����쳣
     */
    public static HttpEntity getRequestEntity(int action, Object params)
            throws UnsupportedEncodingException {

        if (S_XML_REQUESTS.contains(action)) {
            // ��ͨ��XML��������
            return getXmlRequest(params);
        } 
        else if (S_JSON_REQUESTS.contains(action)) {
            // ��ͨ��JSON��������
            return getJsonRequest(action, params);
        } 
        else {
            // ����Ҫ��������
            return null;
        }
    }
    
    /**
     * ��ȡ��׼��XML�������ݣ�����utf8���뷽ʽ
     * @return XML��������
     * @throws UnsupportedEncodingException ���粻֧��UTF8���뷽ʽ���׳����쳣
     */
    private static StringEntity getXmlRequest(Object params) throws UnsupportedEncodingException {
//        String body = generateXmlRequestBody(params);
    	String body = params.toString();
        Utils.D("generate XML request body is : " + body);
        return new StringEntity(body, HTTP.UTF_8);
    }
    
    /**
     * ��ȡ��׼��JSON�������ݣ�����utf8���뷽ʽ
     * @return JSON��������
     * @throws UnsupportedEncodingException ���粻֧��UTF8���뷽ʽ���׳����쳣
     */
    private static StringEntity getJsonRequest(int action, Object params) throws UnsupportedEncodingException {
//        String body = generateJsonRequestBody(params);
    	
    	if (params != null) {
    		 String body = params.toString();
    	        Utils.D("generate JSON request body is : " + body);
    	        return new StringEntity(body, HTTP.UTF_8);
		}
    	return null;
       
		
        
    }

    /**
     * Generate the API XML request body
     * ���ŵ������?
     */
    @SuppressWarnings("unchecked")
    private static String generateXmlRequestBody(Object params) {

        if (params == null) {
            return "<request version=\"2\"></request>";
        }

        HashMap<String, Object> requestParams;
        if (params instanceof HashMap) {
            requestParams = (HashMap<String, Object>) params;
        } else {
            return "<request version=\"2\"></request>";
        }

        final StringBuilder buf = new StringBuilder();

        // TODO: add local_version parameter if exist
        buf.append("<request version=\"2\"");
        if (requestParams.containsKey("local_version")) {
            buf.append(" local_version=\"" + requestParams.get("local_version") + "\" ");
            requestParams.remove("local_version");
        }
        buf.append(">");

        // ��Ӳ����ڵ�?
//        final Iterator<String> keySet = requestParams.keySet().iterator();
//        while (keySet.hasNext()) {
//            final String key = keySet.next();
//
//            if ("upgradeList".equals(key)) {
//                buf.append("<products>");
//                List<PackageInfo> productsList = (List<PackageInfo>) requestParams.get(key);
//                for (PackageInfo info : productsList) {
//                    buf.append("<product package_name=\"").append(info.packageName);
//                    buf.append("\" version_code=\"").append(info.versionCode).append("\"/>");
//                }
//                buf.append("</products>");
//                continue;
//            }
//
//            buf.append("<").append(key).append(">");
//            buf.append(requestParams.get(key));
//            buf.append("</").append(key).append(">");
//        }

        // add the enclosing quote
        buf.append("</request>");
        return buf.toString();
    }

    /**
     * Generate the API JSON request body 
     */
    @SuppressWarnings("unchecked")
    private static String generateJsonRequestBody(Object params) {

        if (params == null) {
            return "";
        }
//        Gson gson = new Gson();
//        String strJson = gson.toJson(params);
//        Log.i("strJson", strJson);
//        return strJson;
        return "";
    }

    private static final String[] REPLACE = { "&", "&amp;", "\"", "&quot;", "'", "&apos;", "<",
            "&lt;", ">", "&gt;" };
    
    private static String wrapText(String input) {

        if (!TextUtils.isEmpty(input)) {
            for (int i = 0, length = REPLACE.length; i < length; i += 2) {
                input = input.replace(REPLACE[i], REPLACE[i + 1]);
            }
            return input;
        } else {
            return "";
        }
    } 
}
