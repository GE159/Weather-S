package com.gwk.weathers.model;
/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月12日下午1:07:25
 */
public class RecentWeather
{
	/**
	 * 当地日期
	 */
	private String dateStr;
	/**
	 * 最高温度 摄氏度
	 */
	private String tmpMax;
	/**
	 * 最低温度 摄氏度
	 */
	private String tmpMin;
	
	/**
	 * 天气状况代码
	 */
	private int condCode;
	
	/**
	 * 天气描述
	 */
	private String condTxt;

	public String getDateStr()
	{
		return dateStr;
	}

	public void setDateStr(String dateStr)
	{
		this.dateStr = dateStr;
	}

	public String getTmpMax()
	{
		return tmpMax;
	}

	public void setTmpMax(String tmpMax)
	{
		this.tmpMax = tmpMax;
	}

	public String getTmpMin()
	{
		return tmpMin;
	}

	public void setTmpMin(String tmpMin)
	{
		this.tmpMin = tmpMin;
	}

	public int getCondCode()
	{
		return condCode;
	}

	public void setCondCode(int condCode)
	{
		this.condCode = condCode;
	}

	public String getCondTxt()
	{
		return condTxt;
	}

	public void setCondTxt(String condTxt)
	{
		this.condTxt = condTxt;
	}
	

}
