package com.dashubio.model.health;

import com.dashubio.model.DetectItem;
import com.dashubio.model.MeasuredData;
import com.google.gson.annotations.SerializedName;

/**
 * 体温
 */
public class TemperatureMeasuredData extends MeasuredData {

    @SerializedName("6")
    private DetectItem temperature;//体温

    public DetectItem getTemperature() {
        return temperature;
    }

    public void setTemperature(DetectItem temperature) {
        this.temperature = temperature;
    }
}
