package com.gwk.weathers.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.gwk.weathers.app.R;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *电话：15928001543
 *时间：2015年11月18日下午5:02:28
 */
public class MainliveFragment extends Fragment implements
		OnCheckedChangeListener, OnPageChangeListener
{
	private View fragment_pic;
	private RadioGroup radioTabGroup;
	private RadioButton sns_tab_normal, sns_tab_hot;
	private ViewPager sns_pager;
	private MyFragmentPagerSnsPagerAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		fragment_pic = inflater
				.inflate(R.layout.fragment_pic, container, false);
		initView();
		return fragment_pic;
	}

	private void initView()
	{
		radioTabGroup = (RadioGroup) fragment_pic.findViewById(R.id.tab_group);
		sns_tab_normal = (RadioButton) fragment_pic
				.findViewById(R.id.sns_tab_normal);
		sns_tab_hot = (RadioButton) fragment_pic.findViewById(R.id.sns_tab_hot);
		radioTabGroup.setOnCheckedChangeListener(this);

		sns_pager = (ViewPager) fragment_pic.findViewById(R.id.sns_pager);
		mAdapter = new MyFragmentPagerSnsPagerAdapter(getFragmentManager());
		sns_pager.setAdapter(mAdapter);
		sns_pager.setCurrentItem(0);
		sns_pager.addOnPageChangeListener(this);

	}

	@Override
	public void onPageScrollStateChanged(int paramInt)
	{
		// paramInt的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
		if (paramInt == 2)
		{
			switch (sns_pager.getCurrentItem())
			{
			case 0:
				sns_tab_normal.setChecked(true);
				break;
			case 1:
				sns_tab_hot.setChecked(true);
				break;

			}
		}
	}

	@Override
	public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int paramInt)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{

		switch (checkedId)
		{
		case R.id.sns_tab_normal:
			sns_pager.setCurrentItem(0);
			break;
		case R.id.sns_tab_hot:
			sns_pager.setCurrentItem(1);
			break;

		}

	}
}
