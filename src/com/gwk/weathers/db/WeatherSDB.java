package com.gwk.weathers.db;

import java.util.ArrayList;
import java.util.List;

import com.gwk.weathers.model.City;
import com.gwk.weathers.model.County;
import com.gwk.weathers.model.Province;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月2日上午9:33:42
 */
import android.database.sqlite.SQLiteDatabase;

/**
 * 城市天气数据库操作
 */
public class WeatherSDB
{
	public static final String DB_NAME = "Weather_S";
	public static final int VERSION = 1;
	private static WeatherSDB weatherSDB;
	private SQLiteDatabase db;

	/**
	 * 私有构造方法
	 */
	private WeatherSDB(Context context)
	{
		WeathersOpenHelper dbHelper = new WeathersOpenHelper(context, DB_NAME,
				null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * 单例模式获取实例
	 */
	public static WeatherSDB getInstance(Context context)
	{
		if (weatherSDB == null)
		{
			synchronized (WeatherSDB.class)
			{
				if (weatherSDB == null)
				{
					weatherSDB = new WeatherSDB(context);
				}
			}
		}
		return weatherSDB;
	}

	/**
	 * 将Province实例存储到数据库
	 */
	public void saveProvince(Province province)
	{
		if (province != null)
		{
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	/**
	 * 从数据库中读取全国所有的省份信息
	 */
	public List<Province> loadProvinces()
	{
		List<Province> provliceList = new ArrayList<Province>();
		Cursor cursor = db
				.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst())
		{
			do
			{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor
						.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("province_code")));
				provliceList.add(province);
			} while (cursor.moveToNext());
		}
		// 循环添加完成，关闭资源
		if (cursor != null)
		{
			cursor.close();
		}
		return provliceList;
	}

	/**
	 * 将City实例存储的到数据库
	 */
	public void saveCity(City city)
	{
		if (city != null)
		{
			ContentValues values = new ContentValues();
			values.put("City_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("Province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}

	/**
	 * 从数据库中读取某省份下的所有City信息
	 */
	public List<City> loadCities(int provinceId)
	{
		List<City> cityList = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id=?", new String[]
		{ String.valueOf(provinceId) }, null, null, null);
		if (cursor.moveToFirst())
		{
			do
			{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor
						.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor
						.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				cityList.add(city);
			} while (cursor.moveToNext());
			if (cursor != null)
			{
				cursor.close();
			}
		}
		return cityList;
	}
	
	/**County实例存储的到数据库
	 */
	public void saveCounty(County county)
	{
		if (county != null)
		{
			ContentValues values = new ContentValues();
			values.put("City_name", county.getCountyName());
			values.put("city_code", county.getCountyCode());
			values.put("City_id", county.getCityId());
			db.insert("County", null, values);
		}
	}

	/**
	 * 从数据库中读取某City下的所有County信息
	 */
	public List<County> loadCounties(int cityId)
	{
		List<County> countyList = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "city_id=?", new String[]
		{ String.valueOf(cityId) }, null, null, null);
		if (cursor.moveToFirst())
		{
			do
			{
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cityId);
			} while (cursor.moveToNext());
			if (cursor != null)
			{
				cursor.close();
			}
		}
		return countyList;
	}
}
