package com.gwk.weathers.adapter;

import java.util.List;

import com.gwk.weathers.app.R;
import com.gwk.weathers.model.RecentWeather;
import com.gwk.weathers.util.WeatherPic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月12日下午3:14:16
 */
public class RecentweatherItemAdapter extends ArrayAdapter<RecentWeather>
{

	public RecentweatherItemAdapter(Context context, int textViewResourceId,
			List<RecentWeather> recentWeather)
	{
		super(context, textViewResourceId, recentWeather);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		RecentWeather recent = getItem(position);
		Viewholder holder;
		if (convertView == null)
		{
			holder = new Viewholder();
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.recentweather_fgin_list_item, null);
			holder.recentDate = (TextView) convertView
					.findViewById(R.id.tv_list_recent_date);
			holder.recentCond = (ImageView) convertView
					.findViewById(R.id.iv_list_recent_cond);
			holder.recentCondTxt = (TextView) convertView
					.findViewById(R.id.tv_list_recent_condTxt);
			holder.rencentTmp = (TextView) convertView
					.findViewById(R.id.tv_list_rencent_tmp);
			convertView.setTag(holder);

		} else
		{
			holder = (Viewholder) convertView.getTag();
		}
		holder.recentDate.setText(recent.getDateStr());
		holder.recentCond.setImageBitmap(WeatherPic.getPic(getContext(),
				recent.getCondCode(), 0));
		holder.recentCondTxt.setText(recent.getCondTxt());
		holder.rencentTmp
				.setText(recent.getTmpMin() + "~" + recent.getTmpMax());

		return convertView;
	}

	class Viewholder
	{
		TextView recentDate;
		ImageView recentCond;
		TextView recentCondTxt;
		TextView rencentTmp;

	}
}
