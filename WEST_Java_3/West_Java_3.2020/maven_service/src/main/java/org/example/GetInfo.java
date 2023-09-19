package org.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.IOException;

/**
 * @author CXQ
 * @date 2022/1/13
 */
public class GetInfo {
    int type;
    String city;


    public GetInfo(int type, String city) {
        this.type = type;
        this.city = city;
    }

    public void init() {
        boolean existCity = new CityCheck().check(city);
        if (type == 1 && !existCity) {
            getCityInfo();
        } else if (type == 2) {
            if (!existCity) {
                getCityInfo();
            }
            getWeatherInfo();
        }
    }

    public void getCityInfo() {
        MyOkHttp myOkHttp = new MyOkHttp();
        String info = null;
        String url = "https://geoapi.qweather.com/v2/city/lookup?key=" +
                "96b47b24b3634692b9d0b3ffc1c71772" +
                "&location=" + city;
        try {
            info = myOkHttp.run(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
            JSONArray list = JSON.parseObject(info).getJSONArray("location");
            // String in = "{\"name\":\"福州\",\"id\":\"101230101\",\"lat\":\"26.07530\",\"lon\":\"119.30623\",\"adm2\":\"福州\",\"adm1\":\"福建省\",\"country\":\"中国\",\"tz\":\"Asia/Shanghai\",\"utcOffset\":\"+08:00\",\"isDst\":\"0\",\"type\":\"city\",\"rank\":\"11\",\"fxLink\":\"http://hfx.link/34w1\"}";
            String in = list.getString(0);
            City city = JSON.parseObject(in, City.class);
            String name = city.getName();
            String id = city.getId();
            String lat = city.getLat();
            String lon = city.getLon();
            new AddMySql().addCity(name, id, lat, lon);
        }

    public void getWeatherInfo() {
        MyOkHttp myOkHttp = new MyOkHttp();
        String info = null;
        String cityId = new GetCityId().getCityId(city);
        String url = "https://devapi.qweather.com/v7/weather/3d?key=" +
                "96b47b24b3634692b9d0b3ffc1c71772" +
                "&location=" + cityId;
        try {
            info = myOkHttp.run(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray list = JSON.parseObject(info).getJSONArray("daily");
        AddMySql addMySql = new AddMySql();
        for (int i = 0; i < list.size(); i++) {
            Weather weather = list.getObject(i, Weather.class);
            String fxDate = weather.getFxDate();
            String tempMax = weather.getTempMax();
            String tempMin = weather.getTempMin();
            String textDay = weather.getTextDay();
            addMySql.addWeather(city, fxDate, tempMax, tempMin, textDay);
        }
    }
}
