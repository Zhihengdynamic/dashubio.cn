package com.dashubio.gson;

import java.util.List;

/**
 * Created by zzb on 2017/2/23.
 */

public class HealthReportComprehensive {

    /**
     * status : success
     * data : [{"year":"2017","mouth":["2","1"]},{"year":"2016","mouth":["12"]}]
     */

    private String status;
    private List<HealthReportEntity> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<HealthReportEntity> getData() {
        return data;
    }

    public void setData(List<HealthReportEntity> data) {
        this.data = data;
    }

    public static class HealthReportEntity {
        /**
         * year : 2017
         * mouth : ["2","1"]
         */

        private String year;
        private List<String> mouth;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public List<String> getMouth() {
            return mouth;
        }

        public void setMouth(List<String> mouth) {
            this.mouth = mouth;
        }
    }
}
