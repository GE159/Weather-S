package com.gwk.weathers.activity;

import com.gwk.weathers.app.R;
import com.gwk.weathers.app.R.layout;
import com.gwk.weathers.model.IWeather;
import com.gwk.weathers.widget.TrendView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class TendencyActivity extends Activity
{
	IWeather weather;
	
	private TrendView trendview;
	private TextView day1;
	private TextView day2;
	private TextView day3;
	private TextView day4;
	private TextView wea1;
	private TextView wea2;
	private TextView wea3;
	private TextView wea4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tendency);
		weather=(IWeather) getIntent().getSerializableExtra("weather");
		initView();
		
	}
	
	private void initView()
	{
		trendview=(TrendView) findViewById(R.id.trendView);
		int screenWidth = getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：480px）
		int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
		trendview.setWidthHeight(screenWidth, screenHeight);
		trendview.setTemperature(weather.getMaxlist(),
				weather.getMinlist());
		trendview.setBitmap(weather.getTopPic(), weather.getLowPic());
	}

	public static void StartTendencyActivity  (Context c, IWeather weather){
		Intent intent =new Intent(c,TendencyActivity.class);
		intent.putExtra("weather", weather);
		c.startActivity(intent);
		
	}
}
