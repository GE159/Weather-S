package com.gwk.weathers.fragment;

import org.androidannotations.annotations.EFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.gwk.weathers.activity.ChooseAreaActivity;
import com.gwk.weathers.activity.TendencyActivity;
import com.gwk.weathers.adapter.RecentweatherItemAdapter;
import com.gwk.weathers.app.Myconfig;
import com.gwk.weathers.app.R;
import com.gwk.weathers.db.kLog;
import com.gwk.weathers.model.IWeather;
import com.gwk.weathers.util.HttpCallbackListener;
import com.gwk.weathers.util.HttpUtil;
import com.gwk.weathers.util.MyJSON2Weather;
import com.gwk.weathers.util.Utility;
import com.gwk.weathers.util.WeatherPic;
import com.gwk.weathers.util.pbybUtil;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *电话：15928001543
 *时间：2015年11月18日下午5:02:28
 */
@EFragment(R.layout.fragment_index)
public class MainIndexFragment2 extends Fragment implements OnClickListener
{

	private View weatherFragmentView;
	/**
	 * 城市名
	 */
	private TextView cityname;
	/**
	 * 刷新天气Button
	 */
	private Button refreshWeather;
	/**
	 * 重新选择City的Button
	 */
	private ImageButton switchcity;

	/**
	 * 显示信息更新时间
	 */
	private TextView publishTime;

	/**
	 * 今日天气信息
	 */
	private View tempanddate;
	private ImageView todayWeatherIc;
	private TextView todayWeatherTxt;
	private TextView todayWeatherTmp;
	private TextView feelTemp;
	private TextView mpPolution;
	private TextView nowTmp;
	private ImageView tempIvminus;
	private ImageView tempIvOne;
	private ImageView tempIvTen;
	private ImageView tempIvHundred;
	private TableRow aqlinfoTB;

	/**
	 * //最近天气信息
	 */
	private View rencentdays;
	private ImageView renTodayIc;
	private TextView renTodayTxt;
	private TextView renTodayTmp;

	private ImageView renTomorrowIc;
	private TextView renTomorrowTxt;
	private TextView renTomorrowTmp;

	private ImageView renHoutianIc;
	private TextView renHoutianTxt;
	private TextView renHoutianTmp;
	/**
	 * 左边最近天气 List
	 */
	private ListView recentWeatherList;
	// Service对象

	private String cityNamestr;
	private String httpUrl;
	private IWeather getweather;

	public static final int show_response = 0;
	/**
	 * 请求返回的Json语句
	 */
	private String strResult;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		weatherFragmentView = inflater.inflate(R.layout.fragment_index,
				container, false);
		initView(weatherFragmentView);
		getDate();

		return weatherFragmentView;
	}

	private void initView(View v)
	{
		// 标题栏
		cityname = (TextView) v.findViewById(R.id.tv_fgin_cityname);
		refreshWeather = (Button) v.findViewById(R.id.btn_fgin_refreshweather);
		refreshWeather.setOnClickListener(this);
		switchcity = (ImageButton) v.findViewById(R.id.ib_fgin_switchcity);
		switchcity.setOnClickListener(this);
		publishTime = (TextView) v.findViewById(R.id.tv_fgin_publish_time);

		// 今日天气信息
		tempanddate = v.findViewById(R.id.tempanddate);
		todayWeatherIc = (ImageView) v
				.findViewById(R.id.iv_fgin_today_weather_icon);
		todayWeatherTxt = (TextView) v
				.findViewById(R.id.tv_fgin_today_weather_txt);
		todayWeatherTmp = (TextView) v
				.findViewById(R.id.tv_fgin_today_weather_tmp);
		feelTemp = (TextView) v.findViewById(R.id.tv_fgin_feeltemp);
		mpPolution = (TextView) v.findViewById(R.id.tv_fgin_polution);
		nowTmp = (TextView) v.findViewById(R.id.tv_now_temp);
		tempIvminus = (ImageView) v.findViewById(R.id.iv_temp_minus);
		tempIvOne = (ImageView) v.findViewById(R.id.iv_fgin_temp_one);
		tempIvTen = (ImageView) v.findViewById(R.id.iv_fgin_temp_ten);
		tempIvHundred = (ImageView) v.findViewById(R.id.iv_fgin_temp_hundred);
		aqlinfoTB = (TableRow) v.findViewById(R.id.tb_today_aqlinfo);

		// 最近天气信息
		rencentdays = v.findViewById(R.id.fragment_main_weather_rencentdays);
		renTodayIc = (ImageView) v
				.findViewById(R.id.tv_fgin_rencent_today_week);
		renTodayTxt = (TextView) v
				.findViewById(R.id.tvldr_fgin_rencent_today_txt);
		renTodayTmp = (TextView) v.findViewById(R.id.tv_fgin_rencent_today_tmp);
		renTomorrowIc = (ImageView) v
				.findViewById(R.id.tv_fgin_rencent_tomorrow_week);
		renTomorrowTxt = (TextView) v
				.findViewById(R.id.tv_fgin_rencent_tomorrow_txt);
		renTomorrowTmp = (TextView) v
				.findViewById(R.id.tv_fgin_rencent_tomorrow_tmp);
		renHoutianIc = (ImageView) v
				.findViewById(R.id.tv_fgin_rencent_houtian_week);
		renHoutianTxt = (TextView) v
				.findViewById(R.id.tv_fgin_rencent_houtian_txt);
		renHoutianTmp = (TextView) v
				.findViewById(R.id.tv_fgin_rencent_houtian_tmp);

		// 左边List
		recentWeatherList = (ListView) v
				.findViewById(R.id.index_recentweather_listview);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case show_response:
				getweather = (IWeather) msg.obj;

				tempanddate.setVisibility(View.VISIBLE);

				cityname.setText(getweather.getCity());
				publishTime.setText(getweather.getRefreshDate()
						+ getweather.getRefreshTime());
				todayWeatherTxt.setText(getweather.getTodayWeather());
				todayWeatherIc.setImageBitmap(WeatherPic.getPic(getContext(),
						getweather.getPicIndex(), 0));

				todayWeatherTmp.setText(getweather.getComfortable());
				feelTemp.setText(getweather.getComforfeeltemp());

				if (!TextUtils.isEmpty(getweather.getPm25Polution()))
				{
					aqlinfoTB.setVisibility(View.VISIBLE);
					mpPolution.setText(getweather.getPm25Polution() + " "
							+ getweather.getQlty());
				}

				// nowTmp.setText(weather.getTodayTemperature());
				if (!TextUtils.isEmpty(getweather.getTodayTemperature()))
				{
					int noTmpint = Integer.parseInt(getweather
							.getTodayTemperature());
					if (noTmpint < 0)
					{
						tempIvminus.setVisibility(View.VISIBLE);
						noTmpint = -noTmpint;
					}
					tempIvOne.setVisibility(View.VISIBLE);
					tempIvOne.setImageBitmap(WeatherPic.getPic(getActivity(),
							noTmpint % 10, 0));

					if (noTmpint / 10 > 0)
					{// 两位数
						tempIvTen.setVisibility(View.VISIBLE);
						tempIvTen.setImageBitmap(WeatherPic.getPic(
								getActivity(), noTmpint / 10, 0));
					}

					rencentdays.setVisibility(View.VISIBLE);
					// 未来四天天气
					for (int i = 0; i < 3; i++)
					{
						String weatx = getweather.getWeather().get(i);
						String weama = getweather.getTemperatureMax().get(i);
						String weami = getweather.getTemperatureMin().get(i);
						int weapic = getweather.getTopPic().get(i);
						switch (i)
						{
						case 0:
							renTodayIc.setImageBitmap(WeatherPic.getPic(
									getContext(), weapic, 1));
							renTodayTxt.setText(weatx);
							renTodayTmp.setText(weami + "~" + weama);
							break;
						case 1:
							renTomorrowIc.setImageBitmap(WeatherPic.getPic(
									getContext(), weapic, 1));
							renTomorrowTxt.setText(weatx);
							renTomorrowTmp.setText(weami + "~" + weama);
							break;
						case 2:
							renHoutianIc.setImageBitmap(WeatherPic.getPic(
									getContext(), weapic, 1));
							renHoutianTxt.setText(weatx);
							renHoutianTmp.setText(weami + "~" + weama);
							break;

						default:
							break;
						}
					}
					recentWeatherList.setAdapter(new RecentweatherItemAdapter(
							getActivity(),
							R.layout.recentweather_fgin_list_item, getweather
									.getRecentWeatherList()));
					recentWeatherList
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id)
								{
									TendencyActivity.StartTendencyActivity(getActivity(), getweather);
								}
							});
				}

				break;

			default:
				break;
			}
		};
	};

	/**
	 * 获取天气信息
	 * 
	 * @return
	 */
	private void getDate()
	{
		// 处理传入的县级代号
		String countyCode = getActivity().getIntent().getStringExtra(
				Myconfig.COUNTYCODE);

		if (!TextUtils.isEmpty(countyCode))
		{ // 有CountyCode时就去查询天气信息
			cityname.setText(countyCode);
			publishTime.setText("同步中>>>");
			tempanddate.setVisibility(View.GONE);
			rencentdays.setVisibility(View.GONE);
			queryWeatherCode(countyCode);

		} else
		{
			// 没有CountyCode就直接显示本地存储的天气
			getActivity().runOnUiThread(new Runnable() {
				public void run()
				{
					showWeather();
				}
			});
		}

	}

	/**
	 * 根据CountyCode生成对应的http查询地址
	 * 
	 * @param countyCode
	 */
	private void queryWeatherCode(String countyName)
	{
		this.cityNamestr = countyName;
		String countyCode = pbybUtil.converterToSpell(countyName);
		StringBuilder sb = new StringBuilder(
				"https://api.heweather.com/x3/weather?city=");
		httpUrl = sb.append(countyCode).append(Myconfig.MYKEY).toString();
		kLog.i("HttpURL", httpUrl);
		queryFromServer(httpUrl, cityNamestr);
	}

	/**
	 * 根据传入的地址和类型查询天气消息
	 * 
	 * @param address
	 * @param type
	 */
	private void queryFromServer(final String address, final String cityNamestr)
	{
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			@Override
			public void onFinish(String response)
			{
				if (!TextUtils.isEmpty(response))
				{

					// 把数据保存到本地
					Utility.saveWeatherInfo(getContext(), cityNamestr, response);
					// 解析从服务器返回的数据,并显示
					getActivity().runOnUiThread(new Runnable() {
						public void run()
						{
							showWeather();
						}
					});

				} else
				{
					publishTime.setText("同步失败");
				}
			}

			@Override
			public void onError(Exception e)
			{
				getActivity().runOnUiThread(new Runnable() {
					public void run()
					{
						publishTime.setText("同步失败");
					}
				});
			}
		});
	}

	/**
	 * 从SharedPreferences文件中读取存储的天气信息，解析出具体数据并显示到界面上
	 */
	private void showWeather()
	{

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());

		strResult = prefs.getString(Myconfig.WEATHERJSON, "");
		IWeather weather = MyJSON2Weather.parseJson(strResult);

		Message message = new Message();
		message.what = show_response;
		message.obj = weather;

		handler.sendMessage(message);

		/*
		 * ;
		 * 
		 * renTodayIc; renTodayTxt; renTodayTmp;
		 * 
		 * renTomorrowIc; renTomorrowTxt; renTomorrowTmp;
		 * 
		 * renHoutianIc; renHoutianTxt; renHoutianTmp;
		 */
		// //激活AutoUpdateService服务
		// Intent intent=new Intent(this,AutoUpdateReceiver.class);
		// startService(intent);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_fgin_refreshweather:
			// 刷新当前城市的天气数据
			publishTime.setText("同步中>>>");
			tempanddate.setVisibility(View.GONE);
			rencentdays.setVisibility(View.GONE);
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(getActivity());
			String cityName = prefs.getString(Myconfig.COUNTYCODE, "");
			if (!TextUtils.isEmpty(cityName))
			{
				queryWeatherCode(cityName);
			}
			break;

		case R.id.ib_fgin_switchcity:
			Intent intent = new Intent(getActivity(), ChooseAreaActivity.class);
			intent.putExtra(Myconfig.FROMWEATHERFRAG, true);
			startActivity(intent);
			getActivity().finish();
			break;

		default:
			break;
		}

	}

}
