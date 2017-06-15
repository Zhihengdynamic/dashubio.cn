package com.dashubio.net;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import com.dashubio.commons.Session;
import com.dashubio.utils.Utils;




import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


/**
 * API
 */
public class ApiAsyncTask extends AsyncTask<Void, Void, Object> {

	// 链接超时
	public static final int TIMEOUT_ERROR = 600;
	// 业务错误
	public static final int BUSSINESS_ERROR = 610;

	private AndroidHttpClient mClient;
	private int mReuqestAction;
	private ApiRequestListener mHandler;
	private Object mParameter;
	private Session mSession;
	private Context mContext;
	private ResponseCacheManager mResponseCache;



    ApiAsyncTask(Context context, int action, ApiRequestListener handler, Object param) {
    	Log.e("-------2", "----------2");
        this.mContext = context;
        this.mSession = Session.getInstatnce(context);
        this.mReuqestAction = action;
        this.mHandler = handler;
        this.mParameter = param;
        this.mResponseCache = ResponseCacheManager.getInstance();
        this.mClient = HttpClientFactory.get().getHttpClient();
    }

	@Override
	protected Object doInBackground(Void... params) {
		Log.e("-------3", "----------3");
        if (!Utils.isNetworkAvailable(mContext)) {
            return TIMEOUT_ERROR;
        }
	    String requestUrl =  HomeAPI.API_URLS[mReuqestAction];
	    HttpEntity requestEntity = null;
	    try {
            requestEntity = ApiRequestFactory.getRequestEntity(mReuqestAction, mParameter);
        } catch (UnsupportedEncodingException e) {
            Utils.D("OPPS...This device not support UTF8 encoding.[should not happend]");
            return BUSSINESS_ERROR;
        }
        Object result = null;
        //����Ψһ��ʶ
        String cacheKey = "";
        if (!ApiRequestFactory.API_NO_CACHE_MAP.contains(mReuqestAction)) {
            final boolean isBodyEmpty = (requestEntity == null);
            if (isBodyEmpty) {
                // if no http entity then directly use the request url as cache key
                cacheKey = Utils.getMD5(requestUrl);
            } else {
                // we only cache string request
                if (requestEntity instanceof StringEntity) {
                    try {
                        cacheKey = Utils.getMD5(requestUrl + EntityUtils.toString(requestEntity));
                    } catch (ParseException e) {
                        Utils.W("have ParseException when get cache key", e);
                    } catch (IOException e) {
                        Utils.W("have IOException when get cache key", e);
                    }
                }
            }
            // fetch result from the cache
            result = mResponseCache.getResponse(cacheKey);
            if (result != null) {
                Utils.V("retrieve response from the cache");
                return result;
            }
        }
        
        HttpResponse response = null;
        HttpUriRequest request = null;
        try {
            request = ApiRequestFactory.getRequest(requestUrl, mReuqestAction, requestEntity, mSession);      
            response = mClient.execute(request);
            final int statusCode = response.getStatusLine().getStatusCode();
            Utils.D("requestUrl " + requestUrl + " statusCode: " + statusCode);

            if (HttpStatus.SC_OK != statusCode) {
                // ����������
                return statusCode;
            }

            // fetch result from remote server
           
			try {
				result = ApiResponseFactory.getResponse(mContext, mReuqestAction, response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            if (result != null && !ApiRequestFactory.API_NO_CACHE_MAP.contains(mReuqestAction)) {
                mResponseCache.putResponse(cacheKey, result);
            }
            // ����API Response�����������������BUSSINESS_ERROR��610��
            return result == null ? BUSSINESS_ERROR : result;
            
        } catch (IOException e) {
            Utils.D("Market API encounter the IO exception[mostly is timeout exception]", e);
            return TIMEOUT_ERROR;
        } finally {
            // release the connection
            if (request != null) {
                request.abort();
            }
            if (response != null) {
                try {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        entity.consumeContent();
                    }
                } catch (IOException e) {
                    Utils.D("release low-level resource error");
                }
            }
        }
	}

	@Override
	protected void onPostExecute(Object response) {
		Log.e("-------4", "----------4");
        if (mHandler == null) {
            return;
        }
        if (mContext instanceof Activity 
                && ((Activity) mContext).isFinishing()) {
            // ҳ���Ѿ����رգ�������к�������?
            return;
        }
		if (response == null) {
			mHandler.onError(this.mReuqestAction, BUSSINESS_ERROR);
			return;
		} else if (response instanceof Integer) {
		    
		    Integer statusCode = (Integer) response;
            if (!handleCommonError(statusCode)) {
                mHandler.onError(this.mReuqestAction, (Integer) response);
                return;
            }
		}
		mHandler.onSuccess(this.mReuqestAction, response);  
	}
	
    // 225 ��������ݲ�����?
    public static final int SC_DATA_NOT_EXIST = 225;
    // 232 �Ƿ��ظ�����
    public static final int SC_ILLEGAL_COMMENT = 232;
    // 421 ����ͷ����Ϊ�ջ������������User-Agent�ȣ�
    public static final int SC_ILLEGAL_USER_AGENT = 421;
    // 422 ����xml��������
    public static final int SC_XML_ERROR = 422;
    // 423 ����xml�в���ȱʧ��������ʹ���?
    public static final int SC_XML_PARAMS_ERROR = 423;
    // 427 ������ܻ�������?
    public static final int SC_ENCODE_ERROR = 427;
    // 520 DB���ʻ�SQLִ�г���
    public static final int SC_SERVER_DB_ERROR = 520;
    
    
    /**
     * ������Http Status Code
     * @param statusCode Http Status Code
     * @return ��Code�Ƿ񱻴���True���Ѿ�������
     */
	private boolean handleCommonError(int statusCode) {
	    
        if (statusCode == 200) {
            return true;
        }

	    return false;
	}
	

	//API���������?
	public interface ApiRequestListener {

		/**
		 * The CALLBACK for success RKMarket API HTTP response
		 * 
		 * @param response
		 *            the HTTP response
		 */
		void onSuccess(int method, Object obj);

		/**
		 * The CALLBACK for failure RKMarket API HTTP response
		 * 
		 * @param statusCode
		 *            the HTTP response status code
		 */
		void onError(int method, int statusCode);
	}

}