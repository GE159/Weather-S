package com.gwk.weathers.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月7日下午3:28:08
 */
public class MyFragmentPagerSnsPagerAdapter extends FragmentPagerAdapter
{
	private final int PAGER_COUNT = 2;
	private PicHotFragment picHotFragment=null;
	private PicLiveFragment picLiveFragment=null;

	public MyFragmentPagerSnsPagerAdapter(FragmentManager fm)
	{
		super(fm);
		picHotFragment=new PicHotFragment();
		picLiveFragment=new PicLiveFragment();
	}

	@Override
	public Fragment getItem(int arg0)
	{
		Fragment fragment = null;
		switch (arg0)
		{
		case 0:
			fragment = picLiveFragment;
			break;
		case 1:
			fragment = picHotFragment;
			break;
			}
		return fragment;
		
	}

	@Override
	public int getCount()
	{
		return PAGER_COUNT;
	}

}
