package com.gwk.weathers.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gwk.weathers.db.kLog;


import com.gwk.weathers.model.IWeather;
import com.gwk.weathers.model.RecentWeather;

import android.util.Log;



/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月9日下午11:08:41
 */
public class MyJSON2Weather
{
	public static IWeather parseJson(String strResult){
		IWeather weather=null;
		kLog.e("Json", strResult);
		JSONObject jsonObj;
		try
		{
			weather = new IWeather();
			jsonObj = new JSONObject(strResult);
			JSONArray service = jsonObj
					.getJSONArray("HeWeather data service 3.0");
			kLog.e("service", service.toString());

			for (int i = 0; i < service.length(); i++)
			{
				JSONObject jsonObject = (JSONObject) service.get(i);

				// 空气质量指数
				int isaqi=service.toString().indexOf("aqi");
				if (isaqi > 0&&isaqi<2500)
				{
					JSONObject jaqi = (JSONObject) jsonObject
							.getJSONObject("aqi");

					JSONObject jcity = (JSONObject) jaqi.getJSONObject("city");
					weather.setPm25Polution(jcity.getString("pm25"));
					weather.setQlty(jcity.getString("qlty"));
					kLog.e("aqi", jaqi.toString());
				}

				// 城市基本信息
				JSONObject jbasic = (JSONObject) jsonObject.get("basic");
				kLog.e("basic", jbasic.toString());
				JSONObject jupdate = (JSONObject) jbasic.get("update");

				weather.setCity(jbasic.getString("city"));
				String date = jupdate.getString("loc"); // 更新时间（整点）【更新时间确定temp属于哪天】

				int ftime = Integer.parseInt(date.substring(11, 13)); // 更新时间（整点）【更新时间确定temp属于哪天】

				if (ftime >= 18 || ftime < 8)
				{
					weather.setNight(true);
				}
				weather.setRefreshDate(getDate()); // 更新日期
				weather.setRefreshTime(getTime()); // 更新时间
				weather.setRefreshWeek(getWeek()); // 更新星期

				// 天气预报
				JSONArray jdaily_forecast = (JSONArray) jsonObject
						.get("daily_forecast");
				Log.e("daily_forecast", jdaily_forecast.toString());

				List<RecentWeather> recentWeatherList = new ArrayList<RecentWeather>(); // 未来几天天气
				List<Integer> topPic = new ArrayList<Integer>(); // 最高温时的图片编号
				List<Integer> lowPic = new ArrayList<Integer>(); // 最低温时的图片编号
				List<String> tempListMax = new ArrayList<String>();// 未来最高温度集合（有℃符号）
				List<String> tempListMin = new ArrayList<String>();// 未来最低温度集合（有℃符号）
				List<String> weatherList = new ArrayList<String>();// 未来天气
				List<String> windList = new ArrayList<String>(); // 未来风力

				for (int j = 0; j < jdaily_forecast.length(); j++)
				{
					RecentWeather recen=new RecentWeather();
					JSONObject jdailyObject = (JSONObject) jdaily_forecast
							.get(j);
					
					String jdate = jdailyObject.getString("date");
					recen.setDateStr(jdate);
					
					JSONObject jcond = (JSONObject) jdailyObject.get("cond");
					int code_d = jcond.getInt("code_d");
					int code_n = jcond.getInt("code_n");
					String txt_d = jcond.getString("txt_d");

					topPic.add(code_d);
					lowPic.add(code_n);
					recen.setCondCode(code_d);
					weatherList.add(txt_d);
					recen.setCondTxt(txt_d);

					JSONObject jtmp = (JSONObject) jdailyObject.get("tmp");
					String max = jtmp.getString("max") + "℃";
					String min = jtmp.getString("min") + "℃";
					tempListMax.add(max);
					tempListMin.add(min);
					recen.setTmpMax(max);
					recen.setTmpMin(min);

					JSONObject jwind = (JSONObject) jdailyObject.get("wind");
					String sc = jwind.getString("sc");
					windList.add(sc);

					
					recentWeatherList.add(recen);
					}
				weather.setRecentWeatherList(recentWeatherList);
				weather.setTopPic(topPic);
				weather.setLowPic(lowPic);
				weather.setTemperatureMax(tempListMax); // 未来四天最高温度集合（有℃符号）
				weather.setTemperatureMin(tempListMin); // 未来四天最低温度集合（有℃符号）
				weather.setWeather(weatherList); // 未来四天天气
				weather.setTomorrowWeather(weatherList.get(1));// 明天天气
				// 明天温度（包括最高温和最低温）
				weather.setTomorrowTemperature(tempListMin.get(1) + "~"
						+ tempListMax.get(1));
				weather.setWind(windList); // 未来四天风力
				weather.setMaxlist(transplate(tempListMax)); // 未来四天最高温度集合（无℃符号）
				weather.setMinlist(transplate(tempListMin)); // 未来四天最低温度集合（无℃符号）

				// 实况天气
				JSONObject jnow = (JSONObject) jsonObject.get("now");
				kLog.i("jnow天气状况", jnow.toString());

				// 天气状况
				JSONObject jcond = (JSONObject) jnow.get("cond");

				weather.setPicIndex(jcond.getInt("code")); // 当天天气图片编号
				weather.setTodayTemperature(jnow.getString("tmp")); // 当天温度（实时）
				weather.setTodayWeather(jcond.getString("txt")); // 当天天气描述（实时）
				
				weather.setComforfeeltemp(jnow.getString("fl"));//舒适度
				// 返回码
				String status = jsonObject.getString("status");
				kLog.e("status", status);

				// 生活指数*
				JSONObject jsuggestion = (JSONObject) jsonObject
						.get("suggestion");
				 kLog.i("suggestion", jsuggestion.toString());
				JSONObject jcomf = (JSONObject) jsuggestion.get("comf");
				kLog.i("comf", jcomf.toString());
				
				weather.setComfortable(jcomf.getString("brf"));
			}
		} 
			
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return weather;
	} 
		
	
	private static String getDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日 EEE", Locale.CHINA);  
	    String date=sdf.format(new java.util.Date()); 
	    System.out.println(date);
		return date;
	}
	private static String getTime(){
	    SimpleDateFormat sdf=new SimpleDateFormat("HH:mm", Locale.CHINA);  
	    String time=sdf.format(new java.util.Date()) + " " + "发布"; 
	    System.out.println(time);
		return time;
	}
	private static String getWeek(){
//		SimpleDateFormat sdf=new SimpleDateFormat("EE", Locale.CHINA);  
//		String week=sdf.format(new java.util.Date());
//		return week;
		return null;
	}
	//去除最高温度和最低温度里的℃符号
		private static List<Integer> transplate(List<String> strList){
			List<Integer> intList = new ArrayList<Integer>();
			for(String temp : strList){
				intList.add(Integer.valueOf(temp.split("℃")[0]));
			}
			return intList;
		}
}
