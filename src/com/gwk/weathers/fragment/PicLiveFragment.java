package com.gwk.weathers.fragment;

import com.gwk.weathers.app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月7日下午3:45:41
 */
public class PicLiveFragment extends Fragment
{
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState)
{
	View picLiveFragment=inflater.inflate(R.layout.fragment_frag_pic_live, container,false);
	return picLiveFragment;
}
}
