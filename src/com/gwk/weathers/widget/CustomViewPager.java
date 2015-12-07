package com.gwk.weathers.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/*
 *可以静止左右滑动的Viewpager
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *电话：15928001543
 *时间：2015年11月19日下午2:13:34
 */

public class CustomViewPager extends ViewPager
{
	private boolean isCanScroll = false;

	public CustomViewPager(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param isCanScroll 是否可以滑动
	 */
	public void setScanScroll(boolean isCanScroll)
	{
		this.isCanScroll = isCanScroll;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0)
	{
		if (isCanScroll)
		{
			return super.onTouchEvent(arg0);
		} else
		{
			return false;
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0)
	{
		if (isCanScroll) {
			return super.onInterceptTouchEvent(arg0);
		} else {
			return false;
		}
	}
	
}
