package com.gwk.weathers.model;

import android.R.integer;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月1日上午11:31:47
 */
public class Province
{
	private int id;
	private String provinceName;
	private String provinceCode;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getProvinceName()
	{
		return provinceName;
	}

	public void setProvinceName(String provinceName)
	{
		this.provinceName = provinceName;
	}

	public String getProvinceCode()
	{
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode)
	{
		this.provinceCode = provinceCode;
	}

}
