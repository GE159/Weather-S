package com.gwk.weathers.activity;

import java.util.ArrayList;
import java.util.List;

import com.gwk.weathers.app.Myconfig;
import com.gwk.weathers.app.R;
import com.gwk.weathers.db.WeatherSDB;
import com.gwk.weathers.db.kLog;
import com.gwk.weathers.model.City;
import com.gwk.weathers.model.County;
import com.gwk.weathers.model.Province;
import com.gwk.weathers.util.HttpCallbackListener;
import com.gwk.weathers.util.HttpUtil;
import com.gwk.weathers.util.Utility;

import android.R.anim;
import android.R.integer;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月2日下午4:32:21
 */
public class ChooseAreaActivity extends Activity
{
	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;
	public static final String PROVINCE="province";
	public static final String CITY="city";
	public static final String COUNTY="county";

	private ProgressDialog progressDialog;
	private TextView titTextView;
	private ListView listView;
	private ArrayAdapter<String> madapter;
	private WeatherSDB weatherSDB;
	private List<String> dataList;

	private List<Province> provinceslist;
	private List<City> citieslist;
	private List<County> countieslist;

	private Province selectedProvince;
	private City selectedCity;

	private int currentLevel;// 当前选中的级别

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		isCitySelected();
		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView()
	{	
		listView = (ListView) findViewById(R.id.listview_choose_area);
		titTextView = (TextView) findViewById(R.id.title_text);
		weatherSDB=WeatherSDB.getInstance(this);
		dataList = new ArrayList<String>();
		madapter = new ArrayAdapter<String>(ChooseAreaActivity.this,
				android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(madapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				if (currentLevel == LEVEL_PROVINCE)
				{// 省份列表下选择城市
					selectedProvince = provinceslist.get(position);
					queryCities();
				} else if (currentLevel == LEVEL_CITY)
				{// 城市列表下选择乡镇
					selectedCity = citieslist.get(position);
					queryCounties();
				} else if (currentLevel == LEVEL_COUNTY)
				{
					//String countyCode=countieslist.get(position).getCountyCode();
					String countyCode=countieslist.get(position).getCountyName();
					
					MainActivity.StartMainActivity(ChooseAreaActivity.this,countyCode);
					finish();
				}
			}

			
		});
		queryProvinces(); //加载省级数据
	}

	/**
	 * 加载省级数据
	 */
	private void queryProvinces()
	{
		// 先从数据库中查询
		provinceslist = weatherSDB.loadProvinces();
		if (provinceslist.size() > 0)
		{
			dataList.clear();
			for (Province province : provinceslist)
			{
				dataList.add(province.getProvinceName());
			}
			// 更新ListView数据
			madapter.notifyDataSetChanged();
			listView.setSelection(0);
			titTextView.setText("中国");
			// 标记级别
			currentLevel = LEVEL_PROVINCE;
		} else
		{

			queryFromServer(null, PROVINCE);
		}

	}

	/**
	 * 加载乡镇数据
	 */
	protected void queryCities()
	{
		citieslist = weatherSDB.loadCities(selectedProvince.getId());
		if (citieslist.size() > 0)
		{
			dataList.clear();
			for (City city : citieslist)
			{
				dataList.add(city.getCityName());
			}
			madapter.notifyDataSetChanged();
			listView.setSelection(0);
			titTextView.setText(selectedProvince.getProvinceName());
			currentLevel = LEVEL_CITY;
		} else
		{
			queryFromServer(selectedProvince.getProvinceCode(), CITY);
		}
	}

	/**
	 * 加载城市数据
	 */
	protected void queryCounties()
	{
		countieslist = weatherSDB.loadCounties(selectedCity.getId());
		if (countieslist.size() > 0)
		{
			dataList.clear();
			for (County county : countieslist)
			{
				dataList.add(county.getCountyName());
			}
			madapter.notifyDataSetChanged();
			listView.setSelection(0);
			titTextView.setText(selectedCity.getCityName());
			currentLevel = LEVEL_COUNTY;
		} else
		{
			queryFromServer(selectedCity.getCityCode(), COUNTY);
		}
	}

	/**
	 * 到服务器上查询省市县数据
	 * 
	 * @param code
	 *            代号
	 * @param type
	 *            级别
	 */
	private void queryFromServer(final String code, final String type)
	{
		String address;
		if (!TextUtils.isEmpty(code))
		{
			address = "http://www.weather.com.cn/data/list3/city" + code
					+ ".xml";
		} else
		{
			address = "http://www.weather.com.cn/data/list3/city.xml";
		}
		showProgressDialog();

		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			@Override
			public void onFinish(String response)
			{	// 请求成功处理返回信息
				Log.i("ChooseAreaActivity", response);
				boolean result = false;
				if (ChooseAreaActivity.PROVINCE.equals(type))
				{
					result = Utility.handleProvincesResponse(weatherSDB,
							response);
				} else if (ChooseAreaActivity.CITY.equals(type))
				{
					result = Utility.handleCitiesResponse(weatherSDB, response,
							selectedProvince.getId());

				} else if (ChooseAreaActivity.COUNTY.equals(type))
				{
					result = Utility.handleCountiesResponse(weatherSDB,
							response, selectedCity.getId());
				}
				if (result)
				{ // 请求成功，回到主线程处理逻辑
					runOnUiThread(new Runnable() {
						public void run()
						{
							closeProgressDialog();
							
							if (ChooseAreaActivity.PROVINCE.equals(type))
							{
								queryProvinces();
							} else if (ChooseAreaActivity.CITY.equals(type))
							{
								queryCities();

							} else if (ChooseAreaActivity.COUNTY.equals(type))
							{
								queryCounties();
							}
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
						closeProgressDialog();
						Toast.makeText(ChooseAreaActivity.this, "加载失败", 0)
								.show();
					}
				});
			}
		});
	}

	/**
	 * 显示进度对话框
	 */
	private void showProgressDialog()
	{
		if (progressDialog == null)
		{
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("正在加载>>>");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}

	/**
	 * 关闭进度对话框
	 */
	private void closeProgressDialog()
	{
		if (progressDialog != null)
		{
			progressDialog.dismiss();
		}
	}

	/**
	 * 捕获Back，根据当前级别来判断，此时该返回上级列表还是直接退出
	 */
	@Override
	public void onBackPressed()
	{
		if (currentLevel == LEVEL_COUNTY)
		{
			queryCities();
		} else if (currentLevel == LEVEL_CITY)
		{
			queryProvinces();
		} else
		{
			if (isFromWeatherActivity)
			{
				Intent intent=new Intent(this,MainActivity.class);
				startActivity(intent);
			}
			super.onBackPressed();
		}

	}
	/**
	 * 是否从WeatherActivity跳转来
	 */
	private boolean isFromWeatherActivity;
	
	/**
	 * 判断当前是否已经选择过城市,是否跳转到WeatherActivity
	 */
	private void isCitySelected()
	{
		isFromWeatherActivity=getIntent().getBooleanExtra(Myconfig.FROMWEATHERFRAG, false);
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
		//已经选择了城市，而且不是从WeatherActivity跳转过来的，直接跳转到WeatherActivity
		if (prefs.getBoolean("city_selected", false)&&!isFromWeatherActivity)
		{
			Intent intent=new Intent(this,MainActivity.class);
			startActivity(intent);
			finish();
			return;
		}
	}
	
	
}
