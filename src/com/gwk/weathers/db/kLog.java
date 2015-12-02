package com.gwk.weathers.db;

import android.util.Log;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月3日上午12:54:58
 */
public  class kLog 
{
	private static boolean infoFLAG = true;

	public static void i(String tag, String msg)
	{
		if (infoFLAG)
		{
			Log.i(tag, msg);
		}
	}

	public static void w(String tag, String msg)
	{
		if (infoFLAG)
		{
			Log.w(tag, msg);
		}
	}
	public static void e(String tag, String msg)
	{
		if (infoFLAG)
		{
			Log.e(tag, msg);
		}
	}

}
