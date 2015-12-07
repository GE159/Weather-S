package com.gwk.weathers.model;

import android.R.integer;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月7日下午5:27:26
 */
public class HotSpecial
{
	private int hotsid;
	private String imgurl;
	private int img;
	private String title;

	private String date;

	public String getImgurl()
	{
		return imgurl;
	}

	public void setImgurl(String imgurl)
	{
		this.imgurl = imgurl;
	}

	public int getImg()
	{
		return img;
	}

	public void setImg(int img)
	{
		this.img = img;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}


}
