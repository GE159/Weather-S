package com.gwk.weathers.activity;

import com.gwk.weathers.app.R;
import com.gwk.weathers.app.R.id;
import com.gwk.weathers.app.R.layout;
import com.gwk.weathers.db.kLog;
import com.gwk.weathers.receiver.AutoUpdateReceiver;
import com.gwk.weathers.util.HttpCallbackListener;
import com.gwk.weathers.util.HttpUtil;
import com.gwk.weathers.util.Utility;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity implements OnClickListener
{
	private LinearLayout weatherInfoLayout;
	private TextView cityNameText;
	private TextView publishText;
	private TextView weatherDespText;
	private TextView temp1Text;
	private TextView temp2Text;
	private TextView currentDateText;
	
	public static final String COUNTYCODE="county_code";
	public static final String WEATHERCODE="weather_Code";
		

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weather_layout);
		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView()
	{
		weatherInfoLayout=(LinearLayout) findViewById(R.id.weather_info_layout);
		cityNameText=(TextView) findViewById(R.id.city_name);
		publishText=(TextView) findViewById(R.id.publish_text);
		weatherDespText=(TextView) findViewById(R.id.weather_desp);
		temp1Text=(TextView) findViewById(R.id.temp1);
		temp2Text=(TextView) findViewById(R.id.temp2);
		currentDateText=(TextView) findViewById(R.id.current_date);
		
		
		//处理传入的县级代号
		String countyCode=getIntent().getStringExtra(COUNTYCODE);
//		Toast.makeText(WeatherActivity.this, countyCode, 0).show();
		if (!TextUtils.isEmpty(countyCode))
		{	//有CountyCode时就去查询天气信息
			publishText.setText("同步中>>>");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			queryWeatherCode(countyCode);
			
		} else
		{
			//没有CountyCode就直接显示本地存储的天气
			showWeather();
		}
	}
	/**
	 * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上
	 */
	private void showWeather()
	{
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
		cityNameText.setText(prefs.getString("city_name", ""));
		temp1Text.setText(prefs.getString("temp1", ""));
		temp2Text.setText(prefs.getString("temp2", ""));
		weatherDespText.setText(prefs.getString("weather_desp", ""));
		publishText.setText("今天"+prefs.getString("publish_time", "")+"发布");
		currentDateText.setText(prefs.getString("current_date", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);
		
		//激活AutoUpdateService服务
		Intent intent=new Intent(this,AutoUpdateReceiver.class);
		startService(intent);
	}

	/**
	 * 根据CountyCode查询对应的天气代号
	 * @param countyCode
	 */
	private void queryWeatherCode(String countyCode)
	{
		String address ="http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
		queryFromServer(address,COUNTYCODE);
	}
	/**
	 * 根据天气代号查询对应的天气信息
	 *  @param weatherCode
	 */
	private void queryWeatherInfo(String weatherCode)
	{
		String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		queryFromServer(address, WEATHERCODE);
	}
	
	/**
	 * 根据传入的地址和类型查询天气消息
	 * @param address
	 * @param type
	 */
	private void queryFromServer(final String address,final String type)
	{
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response)
			{
				if (COUNTYCODE.equals(type))
				{
					if (!TextUtils.isEmpty(response))
					{
						//从服务器返回的数据中解析出天气代号
						String[] array=response.split("\\|");
						if (array!=null&&array.length==2)
						{
							String weatherCode=array[1];
							queryWeatherInfo(weatherCode);
						}}
					}else if (WEATHERCODE.equals(type)) {
						kLog.i("WeatherAvtivity....", response);
						Utility.handleWeatherResponse(WeatherActivity.this, response);
						runOnUiThread(new Runnable() {
							public void run()
							{
								showWeather();
							}
						});
					}
			}
			
			@Override
			public void onError(Exception e)
			{
				runOnUiThread(new Runnable() {
					public void run()
					{
						publishText.setText("同步失败");
					}
				});
			}
		});
	}
	/**
	 * 传入Countcode,启动WeatherActivity界面
	 * @param countyCode
	 */
	public static void StartWeatherActivity(Context context,String countyCode)
	{
		Intent intent=new Intent(context,WeatherActivity.class);
		intent.putExtra(COUNTYCODE, countyCode);
		context.startActivity(intent);
	}

	@Override
	public void onClick(View v)
	{
		 switch (v.getId())
		{
		case R.id.weather_btn_refresh_weather:
			publishText.setText("同步中>>>");
			SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
			String weatherCode=prefs.getString(WEATHERCODE, "");
			if (!TextUtils.isEmpty(weatherCode))
			{
				queryWeatherInfo(weatherCode);
			}
			break;
		case R.id.weather_btn_switch_city:
			Intent intent= new Intent(this,ChooseAreaActivity.class);
			intent.putExtra("from_weather_activity", true);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
		
	}
}
