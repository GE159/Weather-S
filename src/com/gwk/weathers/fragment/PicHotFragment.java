package com.gwk.weathers.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gwk.weathers.adapter.MyHotSpecialAdapter;
import com.gwk.weathers.app.R;
import com.gwk.weathers.model.HotSpecial;
import com.gwk.weathers.widget.MyLaodListView;
import com.gwk.weathers.widget.MyLaodListView.ILoadListener;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月7日下午3:45:18
 */
public class PicHotFragment extends Fragment implements ILoadListener
{
	View picHotFragment;
	MyLaodListView hotListview;
	ArrayList<HotSpecial> hotspecials;
	MyHotSpecialAdapter madapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		picHotFragment = inflater.inflate(R.layout.fragment_frag_pic_hot,
				container, false);
		initView();
		return picHotFragment;
	}

	private void initView()
	{
		getData();
		showListView(hotspecials);
		hotListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				HotSpecial hotspecial = hotspecials.get(position);
				Toast.makeText(getContext(), hotspecial.getTitle(), 0).show();

			}
		});

	}

	private void getData()
	{
		hotspecials = new ArrayList<HotSpecial>();
		for (int i = 0; i < 5; i++)
		{
			HotSpecial hotspecial = new HotSpecial();
			hotspecial.setImg(R.drawable.hotspcial_1);
			hotspecial.setTitle("雾霾爆表后的背景蓝" + "-" + i);
			hotspecial.setDate("2015-12-02");
			hotspecials.add(hotspecial);
		}
	}

	private void getLoadData()
	{

		for (int i = 0; i < 3; i++)
		{
			HotSpecial hotspecial = new HotSpecial();
			hotspecial.setImg(R.drawable.hotspcial_2);
			hotspecial.setTitle("跟新的数据" + "-" + i);
			hotspecial.setDate("2015-12-02");
			hotspecials.add(hotspecial);
		}
	}

	@Override
	public void onLoad()
	{
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run()
			{// 获取更多数据
				getLoadData();
				// 更新listView显示
				showListView(hotspecials);
				// 通知ListView加载完毕
				hotListview.loadComplete();
			}
		}, 2000);

	}

	private void showListView(ArrayList<HotSpecial> hotSpecials)
	{
		if (madapter == null)
		{
			hotListview = (MyLaodListView) picHotFragment
					.findViewById(R.id.listview_frag_pic_hot);
			// 接口
			hotListview.setInterface(this);

			madapter = new MyHotSpecialAdapter(getActivity(), hotspecials);
			hotListview.setAdapter(madapter);
		} else
		{
			madapter.onDateChange(hotSpecials);
		}
	}
}
