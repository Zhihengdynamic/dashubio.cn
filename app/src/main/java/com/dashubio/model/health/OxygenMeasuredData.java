package com.dashubio.model.health;

import com.dashubio.model.DetectItem;
import com.dashubio.model.MeasuredData;
import com.google.gson.annotations.SerializedName;

/**
 * 血氧
 */
public class OxygenMeasuredData extends MeasuredData {

    @SerializedName("8")
    private DetectItem oxygen;//血氧

    @SerializedName("9")
    private DetectItem heartRate;//心率

    public DetectItem getOxygen() {
        return oxygen;
    }

    public void setOxygen(DetectItem oxygen) {
        this.oxygen = oxygen;
    }

    public DetectItem getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(DetectItem heartRate) {
        this.heartRate = heartRate;
    }
}
