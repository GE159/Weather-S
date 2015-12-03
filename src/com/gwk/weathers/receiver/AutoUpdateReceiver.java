package com.gwk.weathers.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月3日下午8:04:59
 */
public class AutoUpdateReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Intent i=new Intent(context,AutoUpdateReceiver.class);
		context.startService(i);
	}

}
