package com.gwk.weathers.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gwk.weathers.app.R;
import com.gwk.weathers.model.HotSpecial;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月7日下午6:54:13
 */
public class MyHotSpecialArrayAdapter extends BaseAdapter {
	ArrayList<HotSpecial> hotSpecials;
	LayoutInflater inflater;

	public MyHotSpecialArrayAdapter(Context context, ArrayList<HotSpecial> hotSpecials) {
		this.hotSpecials = hotSpecials;
		this.inflater = LayoutInflater.from(context);
	}

	public void onDateChange(ArrayList<HotSpecial> hotSpecials) {
		this.hotSpecials = hotSpecials;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return hotSpecials.size();
	}

	@Override
	public Object getItem(int position) {
		return hotSpecials.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		HotSpecial hs = hotSpecials.get(position);
		ViewHolder holder = new ViewHolder();
		if (convertView == null)
		{
			convertView = inflater.inflate(
					R.layout.hotspecial_item_layout, parent, false);
			holder.hotspecial_img = (ImageView) convertView
					.findViewById(R.id.hotspecial_imageview);
			holder.hotspecial_title = (TextView) convertView
					.findViewById(R.id.hotspecial_textview_title);
			holder.hotspecial_data = (TextView) convertView
					.findViewById(R.id.hotspecial_textview_data);

			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.hotspecial_img.setBackgroundResource(hs.getImg());
		holder.hotspecial_title.setText(hs.getTitle());
		holder.hotspecial_data.setText(hs.getDate());
		return convertView;
	}

	private class ViewHolder
	{
		ImageView hotspecial_img;
		TextView hotspecial_title;
		TextView hotspecial_data;

	}

}
