package com.gwk.weathers.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gwk.weathers.activity.ChooseAreaActivity;
import com.gwk.weathers.activity.Myconfig;
import com.gwk.weathers.activity.WeatherActivity;
import com.gwk.weathers.app.R;
import com.gwk.weathers.db.kLog;
import com.gwk.weathers.receiver.AutoUpdateReceiver;
import com.gwk.weathers.util.HttpCallbackListener;
import com.gwk.weathers.util.HttpUtil;
import com.gwk.weathers.util.Utility;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *电话：15928001543
 *时间：2015年11月18日下午5:02:28
 */
public class MainIndexFragment extends Fragment implements OnClickListener
{
	private View weatherFragmentView;
	private LinearLayout weatherInfoLayout;
	private TextView cityNameText;
	private TextView publishText;
	private TextView weatherDespText;
	private TextView temp1Text;
	private TextView temp2Text;
	private TextView currentDateText;
	
	private Button refreshbtn;
	private ImageButton  citybtn;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		weatherFragmentView = inflater.inflate(
				R.layout.activity_weather_layout, container, false);
		initView();
		return weatherFragmentView;
	}

	private void initView()
	{
		weatherInfoLayout=(LinearLayout) weatherFragmentView.findViewById(R.id.weather_info_layout);
		cityNameText=(TextView) weatherFragmentView.findViewById(R.id.city_name);
		publishText=(TextView) weatherFragmentView.findViewById(R.id.publish_text);
		weatherDespText=(TextView) weatherFragmentView.findViewById(R.id.weather_desp);
		temp1Text=(TextView) weatherFragmentView.findViewById(R.id.temp1);
		temp2Text=(TextView) weatherFragmentView.findViewById(R.id.temp2);
		currentDateText=(TextView) weatherFragmentView.findViewById(R.id.current_date);
		
		citybtn=(ImageButton) weatherFragmentView.findViewById(R.id.weather_btn_switch_city);
		refreshbtn=(Button) weatherFragmentView.findViewById(R.id.weather_btn_refresh_weather);
		citybtn.setOnClickListener(this);
		refreshbtn.setOnClickListener(this);
		
		//处理传入的县级代号
		String countyCode=getActivity().getIntent().getStringExtra(Myconfig.COUNTYCODE);
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
	 * 根据CountyCode查询对应的天气代号
	 * @param countyCode
	 */
	private void queryWeatherCode(String countyCode)
	{
		String address ="http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
		queryFromServer(address,Myconfig.COUNTYCODE);
	}
	

	/**
	 * 根据天气代号查询对应的天气信息
	 *  @param weatherCode
	 */
	private void queryWeatherInfo(String weatherCode)
	{
		String address="http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		queryFromServer(address, Myconfig.WEATHERCODE);
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
				if (Myconfig.COUNTYCODE.equals(type))
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
					}else if (Myconfig.WEATHERCODE.equals(type)) {
						kLog.i("WeatherAvtivity....", response);
						Utility.handleWeatherResponse(getActivity(), response);
						getActivity().runOnUiThread(new Runnable() {
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
				getActivity().runOnUiThread(new Runnable() {
					public void run()
					{
						publishText.setText("同步失败");
					}
				});
			}
		});
	}

	/**
	 * 从SharedPreferences文件中读取存储的天气信息，并显示到界面上
	 */
	private void showWeather()
	{
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getActivity());
		cityNameText.setText(prefs.getString("city_name", ""));
		temp1Text.setText(prefs.getString("temp1", ""));
		temp2Text.setText(prefs.getString("temp2", ""));
		weatherDespText.setText(prefs.getString("weather_desp", ""));
		publishText.setText("今天"+prefs.getString("publish_time", "")+"发布");
		currentDateText.setText(prefs.getString("current_date", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);
		
//		//激活AutoUpdateService服务
//		Intent intent=new Intent(this,AutoUpdateReceiver.class);
//		startService(intent);
	}

	@Override
	public void onClick(View v)
	{
		 switch (v.getId())
		{
		case R.id.weather_btn_refresh_weather:
			publishText.setText("同步中>>>");
			SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getActivity());
			String weatherCode=prefs.getString(Myconfig.WEATHERCODE, "");
			if (!TextUtils.isEmpty(weatherCode))
			{
				queryWeatherCode("contryCode");
			}
			break;
			
		case R.id.weather_btn_switch_city:
			
			Intent intent= new Intent(getActivity(),ChooseAreaActivity.class);
			intent.putExtra(Myconfig.FROMWEATHERFRAG, true);
			startActivity(intent);
			getActivity().finish();
			break;

		default:
			break;
		}
		
	}
}
