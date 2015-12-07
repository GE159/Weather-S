package com.gwk.weathers.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.gwk.weathers.app.R;

public class LoadingActivity extends Activity
{
	ImageView loadingImg, skipImg;
	AnimationDrawable animationDrawable;
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loading);
		initView();
		initLoadingImg();

	}

	/**
	 * 初始化控件
	 */
	private void initView()
	{
		loadingImg = (ImageView) findViewById(R.id.loading_img);
		loadingImg.setImageResource(R.animator.animation_pull_refresh_feiji_flying);
		skipImg = (ImageView) findViewById(R.id.loading_skip);
		skipImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(LoadingActivity.this,
						ChooseAreaActivity.class));
				finish();
			}
		});
	}

	/**
	 * 初始化动画
	 */
	private void initLoadingImg()
	{
		animationDrawable = (AnimationDrawable) loadingImg.getDrawable();
		animationDrawable.start();

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run()
			{
				// 五秒后关闭动画，跳转到Mainactivity
				try
				{
					Thread.sleep(5000);
					if (!LoadingActivity.this.isFinishing())
					{
						handler.post(new Runnable() {

							@Override
							public void run()
							{
								startActivity(new Intent(LoadingActivity.this,
										ChooseAreaActivity.class));
								finish();
							}
						});
					}

				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		thread.start();
	}

	/**
	 * 释放资源 关闭activity
	 */
	@Override
	public void finish()
	{
		animationDrawable.stop();
		//animationDrawable.setCallback(null);
		animationDrawable = null;
		loadingImg = skipImg = null;
		super.finish();
	}

	}
