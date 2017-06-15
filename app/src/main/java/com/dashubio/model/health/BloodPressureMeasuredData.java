package com.dashubio.model.health;

import com.dashubio.model.DetectItem;
import com.dashubio.model.MeasuredData;
import com.google.gson.annotations.SerializedName;

/**
 * 血压
 */
public class BloodPressureMeasuredData extends MeasuredData {

    @SerializedName("5-1")
    private DetectItem lowPressure;//低压

    @SerializedName("5-2")
    private DetectItem highPressure;//高压

    public DetectItem getLowPressure() {
        return lowPressure;
    }

    public void setLowPressure(DetectItem lowPressure) {
        this.lowPressure = lowPressure;
    }

    public DetectItem getHighPressure() {
        return highPressure;
    }

    public void setHighPressure(DetectItem highPressure) {
        this.highPressure = highPressure;
    }

//    @SerializedName("5-1")
//   private List<DetectItem> lowPressure;//低压
//
//    @SerializedName("5-2")
//    private List<DetectItem> highPressure;//高压
//
//
//    public List<DetectItem> getLowPressure() {
//        return lowPressure;
//    }
//
//    public void setLowPressure(List<DetectItem> lowPressure) {
//        this.lowPressure = lowPressure;
//    }
//
//    public List<DetectItem> getHighPressure() {
//        return highPressure;
//    }
//
//    public void setHighPressure(List<DetectItem> highPressure) {
//        this.highPressure = highPressure;
//    }
}
