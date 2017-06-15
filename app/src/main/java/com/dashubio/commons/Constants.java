package com.dashubio.commons;

import java.io.File;

import com.dashubio.utils.Utils;




public interface Constants {
	
	/**************************************************************************/
	
	
	/** 根目录 */
    static final String ROOT_DIR = "dashudio";
	
	 /** 缓存目录 */
    static final String IMAGE_CACHE_DIR = ROOT_DIR + "/cache";
    
    /** The MIME type of image */
    public static final String MIMETYPE_IMAGE = "image/*";
    public static  File GUIDE_DIR = new File(Utils.getSDPath() + "/dashudio/guide");
	
   
}
