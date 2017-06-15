package com.dashubio.commons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.dashubio.R;
import com.dashubio.utils.Utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Session extends Application{
	
	private Bitmap bm;
    
    /** Ӧ�ó��������� */
    private Context mContext;
    
    /** OS�İ汾 */
    private int osVersion;
    
    /** The user-visible version string. E.g., "1.0" */
    private String buildVersion;
    
    /**
     * The mobile model such as "Nexus One" Attention: some model type may have
     * illegal characters
     */
    private String model;
    
    public static boolean isBack = false;
    
    /** The singleton instance */
    public static Session mInstance;
    
    /** The Http User-Agent */
    private String userAgent;
    
    /** The mobile IMEI code */
    private String imei;

    /** The mobile sim code */
    private String sim;
    
//    public LocationClient mLocationClient;
//    public MyLocationListener mMyLocationListener;

	private NotificationManager mNotificationManager;
	public static File PHOTO_DIR = new File(Utils.getSDPath()
			+ "/dashudio/temp");
	public static File cacheDirectory = new File(Utils.getSDPath()
            + "/dashudio/cache");
	

	
	
    /**
     * default constructor
     * @param context
     */
    private Session(Context context) {
        
        synchronized (this) {
            mContext = context;
            osVersion = Build.VERSION.SDK_INT;
            buildVersion = Build.VERSION.RELEASE;
            try {
                model = URLEncoder.encode(Build.MODEL, "UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
            // 调试模式
            Utils.sDebug = true;
        }
        
        if (!PHOTO_DIR.exists()) {
			PHOTO_DIR.mkdirs(); 
		}
        if (!cacheDirectory.exists()) {
        	cacheDirectory.mkdirs(); 
		}
        
    }
	

	public NotificationManager getNotificationManager() {
		if (mNotificationManager == null)
			mNotificationManager = (NotificationManager) getSystemService(android.content
					.Context.NOTIFICATION_SERVICE);
		return mNotificationManager;
	}
 

   
	// 单例
	public static Session getInstatnce(Context context) {
		if (mInstance == null) {
            mInstance = new Session(context);
        }
        return mInstance;
	}
	
	
	private void getApplicationInfomation() {
       TelephonyManager telMgr = (TelephonyManager) mContext
               .getSystemService(Context.TELEPHONY_SERVICE);
       imei = telMgr.getDeviceId();
       sim = telMgr.getSimSerialNumber();
    }
	
	public String getModel() {
        return model;
    }
	
	public String getBuildVersion() {
        return buildVersion;
    }
	
	public String getIMEI() {
        if (TextUtils.isEmpty(imei)) {
        	getApplicationInfomation();
        }
        return imei;
    }
	
    public String getSim() {
        if (TextUtils.isEmpty(sim)) {
        	getApplicationInfomation();
        }
        return sim;
    }
    
    /**
     * 获取版本名称
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return this.getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
    }
	
	public String getJavaApiUserAgent() {
		if (TextUtils.isEmpty(userAgent)) {
            StringBuilder buf = new StringBuilder();
            final String splash = "/";
            buf.append(getModel()).append(splash).append(getBuildVersion()).append(splash)
                    .append(mContext.getString(R.string.app_name)).append(splash)
                    .append(getIMEI()).append(splash).append(getSim());
            return buf.toString();
        }
        return userAgent;
    }
	
	public String getJavaApiUserAgent1() {
		if (TextUtils.isEmpty(userAgent)) {
            StringBuilder buf = new StringBuilder();
            buf.append(mContext.getString(R.string.app_name));
            return buf.toString();
        }
        return userAgent;
    }
	
    
    public void screenShot(View v) throws IOException {
    	// �õ���ǰview����view�ṹ�еĸ�view
		v.setDrawingCacheEnabled(true);
		bm = v.getDrawingCache();
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");       
		String date = sDateFormat.format(new java.util.Date());   
		saveBitmap(bm, date);
    }
    
    @SuppressLint("SdCardPath")
	public void saveBitmap(Bitmap bitmap, String bitName) throws IOException {
    	String sdPath = Utils.getSDPath();
    	String filePath = "/CityTransportForUser/screenShot";
    	String imagePath = sdPath + filePath; //����ͼƬ���ļ���·��
		File path = new File(imagePath);
		if (!path.exists()) {
			path.mkdirs();
		}
		File file = new File(imagePath + "/" + bitName + ".jpg");
//		Toast.makeText(mContext, "保存图片吗?", Toast.LENGTH_SHORT).show();
		FileOutputStream out;
		if (!file.exists()) {
			try {
				out = new FileOutputStream(file);
				if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
//					Toast.makeText(mContext, "成功存入相册,路径为：" + imagePath,
//							Toast.LENGTH_SHORT).show();
					out.flush();
					out.close();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

