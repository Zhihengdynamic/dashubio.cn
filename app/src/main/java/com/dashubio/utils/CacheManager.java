package com.dashubio.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Cache Manager
 * 
 * For caching something like drawables, objects... 
 */
public class CacheManager {

	private static CacheManager mInstance;
	
	// the max cached icon in the SD-Card
	private static final int MAX_IMAGE_IN_SDCARD = 200;

	// cache in the memory
    private LinkedHashMap<String, Bitmap> mDrawableMap = new LinkedHashMap<String, Bitmap>();
    private SoftReference<LinkedHashMap<String, Bitmap>> mDrawableCache = 
            new SoftReference<LinkedHashMap<String, Bitmap>>(mDrawableMap);
    
    private ArrayList<String> mNewIcons = new ArrayList<String>();
	
    // ͼƬ�б�
    private List<String> picList = new ArrayList<String>();
    
    
    
    private CacheManager() {
//        loadIconsFromSDCard();
    }
	
    public static CacheManager getInstance() {
        if (mInstance == null) {
            mInstance = new CacheManager();
        }
        return mInstance;
    }
    
    /**
     *	Get the icon list from memory cache 
     */
	private LinkedHashMap<String, Bitmap> getMemoCache() {

		if (mDrawableCache == null) {
			mDrawableCache = new SoftReference<LinkedHashMap<String, Bitmap>>(mDrawableMap);
			return mDrawableMap;
		}

		return mDrawableCache.get();
	}
	
    /**
     * Check whether the drawable had been cached
     * 
     * @param key
     *            drawable uri
     * @return ture : exist false : not exist
     */
    public boolean existsDrawable(String key) {

    	// fetch the drawable from memory
    	
//		HashMap<String, Bitmap> memoCache = getMemoCache();
//		loadIconsFromSDCard();
//		if (memoCache.containsKey(StringUtils.getFileNameFromUrl(key))) return true;
//        return false;
    	// �õ���·���ļ��������е��ļ�
    	  File mfile = new File(Utils.getSDPath()
    			  + "/CityTransportForUser/cache");
    	  if (!mfile.exists()) {  
	            mfile.mkdirs();  
	        } 
    	  
    	  File[] files = mfile.listFiles();
    	  if (files == null) {
			return false;
		}
    	 // �����е��ļ�����ArrayList��,����������ͼƬ��ʽ���ļ�
    	 for (int i = 0; i < files.length; i++) {
    	  File file = files[i];
    	  if (checkIsImageFile(file.getPath())) {
    	   picList.add(file.getPath());
    	  }
    	 }
//    	 Log.e("1111", StringUtils.getFileNameFromUrl(key));
//    	 Log.e("2222", Utils.getSDPath() + "/" +
//                 Constants.IMAGE_CACHE_DIR + "/" + StringUtils.getFileNameFromUrl(key));
    	 if (picList.contains(Utils.getSDPath() + "/" +
                 "CityTransportForUser/cache" + "/" + Utils.getFileNameFromUrl(key))) {
    		 return true;
		}
    	 return false;
    }
	
    // �����չ�����õ�ͼƬ��ʽ���ļ�?
    private boolean checkIsImageFile(String fName) {
     boolean isImageFile = false;

     // ��ȡ��չ��
     String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
       fName.length()).toLowerCase();
     if (FileEnd.equals("jpg") || FileEnd.equals("gif")
       || FileEnd.equals("png") || FileEnd.equals("jpeg")
       || FileEnd.equals("bmp")) {
      isImageFile = true;
     } else {
      isImageFile = false;
     }

     return isImageFile;

    } 
    
    /**
     * ��ʼ����ͼƬ 
     */
    public synchronized void cacheDrawable(String url, Bitmap image) {

        if (image == null) {
            return;
        }

        // put the icon in the memory
        LinkedHashMap<String, Bitmap> memoCache = getMemoCache();
        
        if (memoCache == null) {
            return;
        }
        String key = Utils.getFileNameFromUrl(url);
        if(memoCache.size() < MAX_IMAGE_IN_SDCARD) {
            memoCache.put(key, image);
            mNewIcons.add(key);
            return;
        }
        
        // reach here means L1 is full, reorder current stack
        Iterator<String> iterator = memoCache.keySet().iterator();
        if (iterator.hasNext()) {
            // recycle the bitmap
            String oldBitmapUrl = (String) iterator.next();
            memoCache.remove(oldBitmapUrl);
            
            // reorder the new image into L1 cache stack
            memoCache.put(key, image);
            mNewIcons.add(key);
        }
        return;
    }

    /**
     * �ӻ����л�ȡͼƬ 
     * @param url ͼƬ��HTTP��Դ�ĵ�ַ
     */
    public Bitmap getDrawableFromCache(String url, Context context) {

        // retrieve bitmap from cache
//        loadIconsFromSDCard();
//        final String urlFileName = StringUtils.getFileNameFromUrl(url);
//        HashMap<String, Bitmap> memoCache = getMemoCache();
//        return memoCache.get(urlFileName);
        ImageCompress compress = new ImageCompress();
        ImageCompress.CompressOptions options = new ImageCompress.CompressOptions();
        Bitmap addbmp = compress.compressFromUri(context, options, 
        		Utils.getSDPath() + "/" +
                        "CityTransportForUser/cache" + "/" + Utils.getFileNameFromUrl(url));
        return addbmp;
    }
	
	/**
	 * ������ͼƬ����SD��
	 */
    public void close() {

        // check the sd-card status
        if (!Utils.isSdcardWritable()) {
            clearFromMemory();
            Utils.D("save icons failed because sd card is not writable");
            return;
        }

        new Thread() {

            @Override
            public void run() {
                // make this thread in background priority
                setPriority(Process.THREAD_PRIORITY_BACKGROUND);

                File cacheDirectory = new File(Utils.getSDPath()
                		+ "/CityTransportForUser/cache");

                if (!cacheDirectory.exists()) {
                    Utils.V("cache dir not exist, make it immediately success");
                    if(cacheDirectory.mkdirs()) {
                        Utils.V("cache dir not exist, make it immediately success");
                        
                        HashMap<String, Bitmap> memoCache = getMemoCache();
                        if (memoCache != null) {
                            final int length = mNewIcons.size();
                            
                            try {
                                for (int i = 0; i < length; i++) {
                                    String key = mNewIcons.get(i);
                                    final Bitmap bmp = memoCache.get(key);
                                    if (bmp == null) {
                                        continue;
                                    }
                                    FileOutputStream fos = null;
                                    File file = new File(cacheDirectory, key);
                                    if (file.exists()) {
                                        continue;
                                    }
                                    fos = new FileOutputStream(file);
                                    bmp.compress(CompressFormat.PNG, 100, fos);
                                    fos.close();
                                    
                                }
                            } catch (FileNotFoundException e) {
                                Utils.D("save icon fail because make file error", e);
                            } catch (IOException e) {
                                Utils.D("save icon fail because IOException", e);
                            }
                        }
                    } else {
                        Utils.V("cache dir not exist, make it immediately fail");
                    }
                }else {
                	HashMap<String, Bitmap> memoCache = getMemoCache();
                    if (memoCache != null) {
                        final int length = mNewIcons.size();
                        if (length > 0) {
                        	 try {
                                 for (int i = 0; i < length; i++) {
                                     String key = mNewIcons.get(i);
                                     final Bitmap bmp = memoCache.get(key);
                                     if (bmp == null) {
                                         continue;
                                     }
                                     FileOutputStream fos = null;
                                     File file = new File(cacheDirectory, key);
                                     if (file.exists()) {
                                         continue;
                                     }
                                     fos = new FileOutputStream(file);
                                     bmp.compress(CompressFormat.PNG, 100, fos);
                                     fos.close();
                                 }
                             } catch (FileNotFoundException e) {
                                 Utils.D("save icon fail because make file error", e);
                             } catch (IOException e) {
                                 Utils.D("save icon fail because IOException", e);
                             }
						}
                       
                    }
				}
                // clear all the cache
//                clearFromMemory();
            }
        }.start();
    }
	

    
    /**
     * ���ڴ治��ʱ������л������Ӧ���˳�ʱ
     */
    public void clearFromMemory() {
        if (mNewIcons != null) {
            mNewIcons.clear();
        }
        if (mDrawableMap != null) {
            mDrawableMap.clear();
            mDrawableMap = null;
        }
        if (mDrawableCache != null) {
            mDrawableCache.clear();
            mDrawableCache = null;
        }
        mInstance = null;
    }
    
    /**
     * �Ӵ����������?
     */
    public void clearFromFile() {
        Thread clearTask = new Thread() {
            @Override
            public void run() {
                File cacheDirectory = new File(Utils.getSDPath()
                        + "/CityTransportForUser/cache");
                if (cacheDirectory.exists()) {
                    File[] files = cacheDirectory.listFiles();
                    for (File file : files) {
                        file.delete();
                    }
                }
            }
        };
        clearTask.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
        clearTask.start();
    }
}

