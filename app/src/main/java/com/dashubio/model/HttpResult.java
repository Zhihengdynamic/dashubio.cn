package com.dashubio.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class HttpResult<T> {

    @SerializedName("code")
    protected int errorCode;//返回码

    protected String status;//返回状态

    protected String msg = "";//返回信息

    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
