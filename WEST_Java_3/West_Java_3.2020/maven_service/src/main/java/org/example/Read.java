package org.example;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author CXQ
 * @date 2022/1/16
 */
public class Read {

    public void read(int type, String city) {
        ReadMySql readMySql = new ReadMySql();
        if (type == 1) {
            Map map = readMySql.readCity(city);
            String name = map.get("name").toString();
            String id = map.get("id").toString();
            String lat = map.get("lat").toString();
            String lon = map.get("lon").toString();
            City aCity = new City();
            aCity.setName(name);
            aCity.setId(id);
            aCity.setLat(lat);
            aCity.setLon(lon);
            String strCity = JSONObject.toJSONString(aCity);
            System.out.println(strCity);
        } else if (type == 2) {
            ArrayList<Map> arrayList = readMySql.readWeather(city);
            for (int i = 0; i < arrayList.size(); i++) {
                String fxDate = arrayList.get(i).get("fxDate").toString();
                String tempMax = arrayList.get(i).get("tempMax").toString();
                String tempMin = arrayList.get(i).get("tempMin").toString();
                String textDay = arrayList.get(i).get("textDay").toString();
                Weather weather = new Weather();
                weather.setFxDate(fxDate);
                weather.setTempMax(tempMax);
                weather.setTempMin(tempMin);
                weather.setTextDay(textDay);
                String strWeather = JSONObject.toJSONString(weather);
                System.out.println(strWeather);
            }
        }
    }

}
