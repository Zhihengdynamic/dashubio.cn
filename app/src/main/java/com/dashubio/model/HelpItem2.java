package com.dashubio.model;

/**
 * Created by zzb on 2017/2/9.
 */

public class HelpItem2 {
    private String title;

    public HelpItem2(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "HelpItem2{" +
                "title='" + title + '\'' +
                '}';
    }


}
