package com.dashubio.app.event;

import com.dashubio.model.IDCard;

/**
 * 自动注册事件
 */
public class AutoRegisterMessageEvent {

    public IDCard mIDCard;

    public AutoRegisterMessageEvent(IDCard idCard) {
        this.mIDCard = idCard;
    }


}
