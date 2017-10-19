package com.joking.selectlibrary.model;
/*
 * GeoFence     2017-10-11
 * Copyright (c) 2017 JoKing All right reserved.
 */

import java.io.Serializable;

public class GeoFence implements Serializable {
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
