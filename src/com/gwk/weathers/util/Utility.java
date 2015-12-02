package com.gwk.weathers.util;

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
	 * 解析和处理服务器返回的Province数据
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
	 * 解析和处理服务器返回的City数据
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
	 * 解析和处理服务器返回的County数据
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
					// 将解析出来的数据存储到City表
					weatherSDB.saveCounty(county);
				}
				judge = true;
			}
		}
		return judge;
	}

}
