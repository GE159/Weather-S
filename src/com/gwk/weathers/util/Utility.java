package com.gwk.weathers.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.gwk.weathers.db.WeatherSDB;
import com.gwk.weathers.model.City;
import com.gwk.weathers.model.County;
import com.gwk.weathers.model.Province;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月2日下午3:46:19
 */
public class Utility
{
	
	/**
	 * 解析和处理服务器返回的Province数据,存储到Province表
	 * @return boolean 是否存储成功
	 */
	public synchronized static boolean handleProvincesResponse(
			WeatherSDB weatherSDB, String response)
	{
		boolean judge = false;
		if (!TextUtils.isEmpty(response))
		{
			String[] allProvincse = response.split(",");
			if (allProvincse != null && allProvincse.length > 0)
			{
				for (String p : allProvincse)
				{
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					// 将解析出来的数据存储到Province表
					weatherSDB.saveProvince(province);
				}
				judge = true;
			}
		}
		return judge;
	}

	/**
	 * 解析和处理服务器返回的City数据,存储到City表
	 * @return boolean 是否存储成功
	 */
	public synchronized static boolean handleCitiesResponse(
			WeatherSDB weatherSDB, String response, int provinceId)
	{
		boolean judge = false;
		if (!TextUtils.isEmpty(response))
		{
			String[] allCities = response.split(",");
			if (allCities != null && allCities.length > 0)
			{
				for (String c : allCities)
				{
					String[] array = c.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					// 将解析出来的数据存储到City表
					weatherSDB.saveCity(city);
				}
				judge = true;
			}
		}
		return judge;
	}

	/**
	 * 解析和处理服务器返回的County数据,存储到County表
	 * @return boolean 是否存储成功
	 */
	public synchronized static boolean handleCountiesResponse(
			WeatherSDB weatherSDB, String response, int CityId)
	{
		boolean judge = false;
		if (!TextUtils.isEmpty(response))
		{
			String[] allCounties = response.split(",");
			if (allCounties != null && allCounties.length > 0)
			{
				for (String c : allCounties)
				{
					String[] array = c.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(CityId);
					// 将解析出来的数据存储到County表
					weatherSDB.saveCounty(county);
				}
				judge = true;
			}
		}
		return judge;
	}
	
	/**
	 * 解析服务器返回的JSON数据，并将解析出的数据储存到本地
	 */
	public static void handleWeatherResponse(Context context,String response){
		try
		{
			JSONObject jsonObject=new JSONObject(response);
			JSONObject weatherinfo=jsonObject.getJSONObject("weatherinfo");
			String cityName=weatherinfo.getString("city");
			String weatherCode=weatherinfo.getString("cityid");
			String temp1=weatherinfo.getString("temp1");
			String temp2=weatherinfo.getString("temp2");
			String weatherDesp=weatherinfo.getString("weather");
			String publishTime=weatherinfo.getString("ptime");
			saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 将服务器返回的所有天气信息存储到SharedPreferences中
	 */
	private static void saveWeatherInfo(Context context, String cityName,
			String weatherCode, String temp1, String temp2, String weatherDesp,
			String publishTime)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
	}

}
