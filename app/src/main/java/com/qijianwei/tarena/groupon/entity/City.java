package com.qijianwei.tarena.groupon.entity;

import java.util.List;

/**
 * Created by tarena on 2017/6/21.
 */

public class City {

    /**
     * status : OK
     * cities : ["阿坝","鞍山","安康","安庆","安阳","......"]
     */

    private String status;
    private List<String> cities;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }
}
