package com.dashubio.utils.log;

/**
 * Created by ZXB on 2015/7/24.
 */
public interface ILog {
    public void write(String level, String tag, String message, Throwable tr,
                      long time);
}
