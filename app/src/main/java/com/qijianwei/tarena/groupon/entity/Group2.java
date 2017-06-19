package com.qijianwei.tarena.groupon.entity;

import java.util.List;

/**
 * Created by tarena on 2017/6/19.
 */

public class Group2 {


    @Override
    public String toString() {
        return "Group2{" +
                "status='" + status + '\'' +
                ", count=" + count +
                ", id_list=" + id_list +
                '}';
    }

    /**
     * status : OK
     * count : 7388
     * id_list : ["1-120239","1-121039","1-87299","1-139605","1-141938","......"]
     */

    private String status;
    private int count;
    private List<String> id_list;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getId_list() {
        return id_list;
    }

    public void setId_list(List<String> id_list) {
        this.id_list = id_list;
    }
}
