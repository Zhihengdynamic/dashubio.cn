package com.dashubio.model;

import java.util.List;

/**
 * Created by zzb on 2017/2/10.
 */

public class HelpContent {

    /**
     * status : success
     * count : 4
     * data : [{"id":"4","c_id":"1","d_id":"3","title":"wqedneo","content":"去我都不会去I我的","status":"1","addtime":"1477882300"},{"id":"3","c_id":"1","d_id":"2","title":"123456","content":"1234567","status":"1","addtime":"1477645248"},{"id":"2","c_id":"1","d_id":"2","title":"1234","content":"123456","status":"1","addtime":"1477644917"},{"id":"1","c_id":"1","d_id":"0","title":"修改第一次q这种","content":"第一次添加哦","status":"1","addtime":"1477638715"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 4
         * c_id : 1
         * d_id : 3
         * title : wqedneo
         * content : 去我都不会去I我的
         * status : 1
         * addtime : 1477882300
         */

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
