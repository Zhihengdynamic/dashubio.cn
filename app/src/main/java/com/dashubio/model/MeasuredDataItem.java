package com.dashubio.model;

import com.google.gson.annotations.SerializedName;

/**
 * 测量的数据Item
 */
public class MeasuredDataItem {

    @SerializedName("val")
    private String value;//值

    private String unit;//单位
}
