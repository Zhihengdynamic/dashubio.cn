package com.dashubio.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 历史数据
 */
public class HistoricalDataCategory implements Serializable{

    @SerializedName("adevice")
    private CategoryItem title;

    @SerializedName("project")
    private List<CategoryItem> subItems;


    public static class CategoryItem{
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public CategoryItem getTitle() {
        return title;
    }

    public void setTitle(CategoryItem title) {
        this.title = title;
    }

    public List<CategoryItem> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<CategoryItem> subItems) {
        this.subItems = subItems;
    }
}
