package com.dashubio.app.event;

/**
 * 尿液分析仪事件
 */
public class UrineMessageEvent {

    public final String message;

    public UrineMessageEvent(String msg) {
        this.message = msg;
    }


}
