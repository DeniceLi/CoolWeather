package com.e.weather.util;

import android.text.TextUtils;
import android.util.Log;

import com.e.weather.db.City;
import com.e.weather.db.County;
import com.e.weather.db.Province;
import com.e.weather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*先使用JSONArray和JSONObject将数据解析出来，如何组装成实体类对象，
在调用save()方法将数据存储到数据库中。
由于这里的JSON数据结构比较简单，所以不适用GSON来进行解析*/
public class Utility {

    private static final String TAG = "Utility";
//    解析和处理服务器返回的省级数据
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                    Log.d(TAG,"Utility这是在干嘛1——1？");
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("Utility返回省级数据失败");
                Log.d(TAG,"Utility这是在干嘛1——2？");
            }
        }
        return false;
    }

//    解析和处理服务器返回的市级数据
    public static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                    Log.d(TAG,"Utility这是在干嘛2——1？");
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("Utility返回市级数据失败");
                Log.d(TAG,"Utility这是在干嘛2——2？");
            }
        }
        return false;
    }

//    解析和处理服务器返回的县级数据
    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("Utility返回县级数据失败");
            }
        }
        return false;
    }

//    将返回的JSON数据解析成Weather实体类
    public static Weather handleWeatherResponse(String response){
        try{
            Log.d(TAG,"Utility弄进去了吗?");
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
//           通过fromJson()方法直接将JSON数据转换成Weather对象
            return new Gson().fromJson(weatherContent,Weather.class);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
