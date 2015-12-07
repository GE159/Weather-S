package com.gwk.weathers.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gwk.weathers.app.R;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *电话：15928001543
 *时间：2015年11月18日下午5:02:28
 */
public class MainliveFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_pic, container,false);
	}
}
