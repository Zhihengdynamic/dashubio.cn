package com.dashubio.app.event;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

/**
 * 地址选择Event
 */
public class AddressSelectedEvent {

    public final Province province;
    public final City city;
    public final County county;

    public AddressSelectedEvent(Province prvnc, City cty, County cunty) {
        this.province = prvnc;
        this.city = cty;
        this.county = cunty;
    }

    public Province getProvince() {
        return province;
    }

    public City getCity() {
        return city;
    }

    public County getCounty() {
        return county;
    }
}
