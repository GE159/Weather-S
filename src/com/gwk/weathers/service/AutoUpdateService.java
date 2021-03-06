package com.gwk.weathers.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.gwk.weathers.util.HttpCallbackListener;
import com.gwk.weathers.util.HttpUtil;
import com.gwk.weathers.util.Utility;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月3日下午7:52:22
 */
public class AutoUpdateService extends Service
{

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		new Thread(new Runnable() {
			public void run()
			{
				updateWeather();
			}
		}).start();
		AlarmManager manager=(AlarmManager) getSystemService(ALARM_SERVICE);
		int anHour=8*60*60*1000;
		long triggerAtTime=SystemClock.elapsedRealtime()+anHour;
		Intent i=new Intent(this, AutoUpdateService.class);
		PendingIntent pi=PendingIntent.getBroadcast(this, 0, i, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		
		return super.onStartCommand(intent, flags, startId);
		
	}

	/**
	 * 更新天气信息
	 */
	protected void updateWeather()
	{
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
		String weatherCode=prefs.getString(com.gwk.weathers.activity.WeatherActivity.WEATHERCODE, "");
		String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response)
			{
				Utility.handleWeatherResponse(AutoUpdateService.this, response);
			}
			
			@Override
			public void onError(Exception e)
			{
				e.printStackTrace();
			}
		});
	}

}
