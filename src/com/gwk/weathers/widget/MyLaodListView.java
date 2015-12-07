package com.gwk.weathers.widget;

import com.gwk.weathers.app.R;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月7日下午10:02:19
 */
public class MyLaodListView extends ListView implements OnScrollListener
{
	View footer;// 底部布局
	int totalItemCount;// 总数量
	int lastVisibleItem;// 最后一个可见的item
	boolean isloading;// 是否正在加载
	ILoadListener iLoadListener;//加载跟多数据

	public MyLaodListView(Context context)
	{
		super(context);
		initView(context);
	}

	public MyLaodListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView(context);
	}

	public MyLaodListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initView(context);
	}

	/**
	 * 添加底部加载提示布局到listView
	 * 
	 * @param context
	 */
	private void initView(Context context)
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		footer = inflater.inflate(R.layout.footer_layout, null);
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
		this.addFooterView(footer);
		this.setOnScrollListener(this);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		if (totalItemCount == lastVisibleItem//在最低端
				&& scrollState == SCROLL_STATE_IDLE)
		{
			if (!isloading)
			{
				isloading = true;
				footer.findViewById(R.id.load_layout).setVisibility(
						view.VISIBLE);
				//加载更多数据
				iLoadListener.onLoad(); 
				
			}

		}
	}
	/**
	 *加载完毕
	 */
	public  void loadComplete(){
		isloading=false;
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
		
		
	}
	public void setInterface(ILoadListener iLoadListener){
		this.iLoadListener=iLoadListener;
		
	}
	/**
	 * 加载更多数据的回调接口
	 */
	public interface ILoadListener{
		/**
		 * 加载更多数据的回调方法
		 */
		public void onLoad();
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{
		this.lastVisibleItem = firstVisibleItem + visibleItemCount;
		this.totalItemCount = totalItemCount;
	}
	
	
	
}
