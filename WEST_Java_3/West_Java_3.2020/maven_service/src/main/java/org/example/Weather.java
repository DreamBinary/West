package org.example;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author CXQ
 * @date 2022/1/15
 */
public class Weather {
    @JSONField(ordinal = 1)
    private String fxDate;
    @JSONField(ordinal = 2)
    private String tempMax;
    @JSONField(ordinal = 3)
    private String tempMin;
    @JSONField(ordinal = 4)
    private String textDay;

    @Override
    public String toString() {
        return "Weather{" +
                "fxDate='" + fxDate + '\'' +
                ", tempMax='" + tempMax + '\'' +
                ", tempMin='" + tempMin + '\'' +
                ", textDay='" + textDay + '\'' +
                '}';
    }

    public String getFxDate() {
        return fxDate;
    }

    public void setFxDate(String fxDate) {
        this.fxDate = fxDate;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getTextDay() {
        return textDay;
    }

    public void setTextDay(String textDay) {
        this.textDay = textDay;
    }
}
