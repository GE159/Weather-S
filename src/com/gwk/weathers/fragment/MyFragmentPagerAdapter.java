package com.gwk.weathers.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年11月9日上午2:57:13
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter
{
	private final int PAGER_COUNT = 3;
	//private MainIndexFragment indexFragment=null;
	private MainIndexFragment2 indexFragment=null;
	private MainliveFragment liveFragment=null;
	private MainUserFragment userFragment=null;

	public MyFragmentPagerAdapter(FragmentManager fm)
	{
		super(fm);
		//indexFragment = new MainIndexFragment();
		indexFragment = new MainIndexFragment2();
		liveFragment = new MainliveFragment();
		userFragment = new MainUserFragment();
	}

	@Override
	public Fragment getItem(int position)
	{
		Fragment fragment = null;
		switch (position)
		{
		case 0:
			fragment = indexFragment;
			break;
		case 1:
			fragment = liveFragment;
			break;
		case 2:
			fragment = userFragment;
			break;

		}
		return fragment;
	}

	
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		
		super.destroyItem(container, position, object);
		
	}

	@Override
	public int getCount()
	{
		return PAGER_COUNT;
	}

}
