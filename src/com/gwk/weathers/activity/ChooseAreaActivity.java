package com.gwk.weathers.activity;

import java.util.ArrayList;
import java.util.List;

import com.gwk.weathers.app.R;
import com.gwk.weathers.db.WeatherSDB;
import com.gwk.weathers.model.City;
import com.gwk.weathers.model.County;
import com.gwk.weathers.model.Province;

import android.R.anim;
import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

	private ProgressDialog progressDialog;
	private TextView titTextView;
	private ListView listView;
	private ArrayAdapter<String> madapter;
	private WeatherSDB weatherSDB;
	private List<String> dataList = new ArrayList<String>();

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
		initView();
	}

	private void initView()
	{
		listView = (ListView) findViewById(R.id.list_view);
		titTextView = (TextView) findViewById(R.id.title_text);
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
				} else if (currentLevel == LEVEL_COUNTY)
				{// 城市列表下选择乡镇
					selectedCity = citieslist.get(position);
					queryCounties();
				}
			}
		});

		queryProvinces();
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

			queryFromServer(null, "province");
		}

	}

	/**
	 * 加载乡镇数据
	 */
	protected void queryCities()
	{
		citieslist=weatherSDB.loadCities(selectedProvince.getId());
		if (citieslist.size()>0)
		{
			dataList.clear();
			for (City city : citieslist)
			{
				dataList.add(city.getCityName());
			}
			madapter.notifyDataSetChanged();
			listView.setSelection(0);
			titTextView.setText(selectedProvince.getProvinceName());
			currentLevel=LEVEL_CITY;
		} else
		{
			queryFromServer(selectedProvince.getProvinceCode(), "city");
		}
	}
	
	/**
	 * 加载城市数据
	 */
	protected void queryCounties()
	{
		countieslist=weatherSDB.loadCounties(selectedCity.getId());
		if (countieslist.size()>0)
		{
			dataList.clear();
			for (County county : countieslist)
			{
				dataList.add(county.getCountyName());
			}
			madapter.notifyDataSetChanged();
			listView.setSelection(0);
			titTextView.setText(selectedCity.getCityName());
			currentLevel=LEVEL_COUNTY;
		} else
		{
			queryFromServer(selectedCity.getCityCode(), "county");
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
		
	}
}
