package com.gwk.weathers.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.gwk.weathers.app.R;

public class LoadingActivity extends Activity
{
	ImageView imageView;
	AnimationDrawable butterfly;

	// 蝴蝶坐标
	private float nextX = 500;
	private float nextY = 0;
	private float curX = 500;
	private float curY = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loading);
		initView();
		initLoadingImg();

	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			// 横向一直向右
			if (msg.what == 0)
			{
				if (nextX < 0)
				{
					curX = nextX = 500;
				} else
				{
					nextX -= 10;
				}
				// 纵向随机上下
				nextY = curY + (float) (Math.random() * 10 - 5);
				TranslateAnimation anim = new TranslateAnimation(curX, nextX,
						curY, nextY);
				curX = nextX;
				curY = nextY;
				anim.setDuration(200);
				imageView.startAnimation(anim);
			}
		}
	};

	/**
	 * 初始化控件
	 */
	private void initView()
	{
		imageView = (ImageView) findViewById(R.id.na_butterfly);
		// loadingImg.setImageResource(R.animator.na_butterfly_list);

	}

	/**
	 * 初始化动画
	 */
	private void initLoadingImg()
	{
		butterfly = (AnimationDrawable) imageView.getBackground();

		butterfly.start();
		new Timer().schedule(new TimerTask() {

			@Override
			public void run()
			{
				handler.sendEmptyMessage(0);
			}
		}, 0, 150);

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
		butterfly.stop();
		// animationDrawable.setCallback(null);
		butterfly = null;
		super.finish();
	}

}
