package com.e.weather.gson;

import com.google.gson.annotations.SerializedName;
//使用  @SerializedName注解方式来让JSON字段和Java字段之间建立映射关系
public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{
//        loc表示天气的更新时间
        @SerializedName("loc")
        public String updateTime;
    }
}
