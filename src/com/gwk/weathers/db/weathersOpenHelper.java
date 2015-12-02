package com.gwk.weathers.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月1日上午11:19:23
 */
public class WeathersOpenHelper extends SQLiteOpenHelper
{	
	public static final String CREATE_PROVINGCE = "create table Province(id integer primary key autoincrement,provice_name text,province_code text)";
	public static final String CREATE_CITY = "create table City(id integer primary key autoincrement,city_name text,city_code text,province_id integer)";
	public static final String CREATE_COUNTY="create table County(id integer primary key autoincrement,county_name text,county_code text,city_id integer)";
	
	public WeathersOpenHelper(Context context, String name,
			CursorFactory factory, int version)
	{
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_CITY);
		db.execSQL(CREATE_PROVINGCE);
		db.execSQL(CREATE_COUNTY);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub

	}

}
