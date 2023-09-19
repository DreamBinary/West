package org.example;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author CXQ
 * @date 2022/1/15
 */
public class City {
    @JSONField(ordinal = 1)
    private String name;
    @JSONField(ordinal = 2)
    private String id;
    @JSONField(ordinal = 3)
    private String lat;
    @JSONField(ordinal = 4)
    private String lon;

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }

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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
