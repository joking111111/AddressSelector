package com.joking.selectlibrary.model;
/*
 * GeoFenceList     2017-10-16
 * Copyright (c) 2017 JoKing All right reserved.
 */

import java.io.Serializable;
import java.util.List;

public class GeoFenceList implements Serializable {

    private int isGeoFence;//是否到达叶子节点
    private List<GeoFence> list;

    public int getIsGeoFence() {
        return isGeoFence;
    }

    public void setIsGeoFence(int isGeoFence) {
        this.isGeoFence = isGeoFence;
    }

    public List<GeoFence> getList() {
        return list;
    }

    public void setList(List<GeoFence> list) {
        this.list = list;
    }
}
