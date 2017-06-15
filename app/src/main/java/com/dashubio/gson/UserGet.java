package com.dashubio.gson;

import java.util.List;

/**
 * Created by zzb on 2017/5/2.
 */

public class UserGet {

    /**
     * status : success
     * count : 8
     * data : [{"id":"281","card_id":"320125199209183412","name":"离线一","sex":"0"},{"id":"280","card_id":"320125199867453218","name":"离线注册","sex":"0"},{"id":"279","card_id":"320125199867453218","name":"离线注册","sex":"0"},{"id":"276","card_id":"320125199345672345","name":"体温离线","sex":"1"},{"id":"240","card_id":"320125188208123456","name":"离线1","sex":"2"},{"id":"238","card_id":"320125199209886523","name":"张啧啧啧","sex":"1"},{"id":"237","card_id":"320125199209232314","name":"离线1","sex":"1"},{"id":"113","card_id":"320125199209181235","name":"测试2","sex":"1"}]
     */

    private String status;
    private String count;
    private List<UserBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<UserBean> getData() {
        return data;
    }

    public void setData(List<UserBean> data) {
        this.data = data;
    }

    public static class UserBean {
        /**
         * id : 281
         * card_id : 320125199209183412
         * name : 离线一
         * sex : 0
         */

        public String id;
        public String card_id;
        public String name;
        public String sex;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCard_id() {
            return card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
