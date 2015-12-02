package com.gwk.weathers.model;

import javax.security.auth.PrivateCredentialPermission;

import android.R.integer;
/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月1日上午11:36:41
 */
public class County
{
	private int id;
	private String countyName;
	private String countyCode;
	private int cityId;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getCountyName()
	{
		return countyName;
	}
	public void setCountyName(String countyName)
	{
		this.countyName = countyName;
	}
	public String getCountyCode()
	{
		return countyCode;
	}
	public void setCountyCode(String countyCode)
	{
		this.countyCode = countyCode;
	}
	public int getCityId()
	{
		return cityId;
	}
	public void setCityId(int cityId)
	{
		this.cityId = cityId;
	}
}
