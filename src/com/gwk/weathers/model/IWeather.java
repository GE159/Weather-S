package com.gwk.weathers.model;

import java.io.Serializable;
import java.util.List;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月11日上午9:40:45
 */
public class IWeather implements Serializable
{
	private boolean isNight;
	private int picIndex;//今日天气图片编号
	
	private String city;
	private String cityid;
	private String refreshDate;
	private String refreshTime;//发布时间
	private String refreshWeek;
	
	private String comfortable;//舒适度描述
	private String comforfeeltemp;//舒适度
	private String pm25Polution;//pm2.5
	private String tomorrowTemperature;//当前温度
	private String qlty;//空气质量
	private String tomorrowWeather;
	private String todayTemperature;
	private String todayWeather;
	
	private List<RecentWeather> recentWeatherList;
	private List<String> temperatureMax;
	private List<String> temperatureMin;
	private List<String> weather;
	private List<String> wind;
	
	private List<Integer> maxlist;
	private List<Integer> minlist;
	
	private List<Integer> topPic;
	private List<Integer> lowPic;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRefreshDate() {
		return refreshDate;
	}
	public void setRefreshDate(String refreshDate) {
		this.refreshDate = refreshDate;
	}
	public String getRefreshTime() {
		return refreshTime;
	}
	public List<String> getWeather() {
		return weather;
	}
	public void setWeather(List<String> weather) {
		this.weather = weather;
	}
	public List<String> getWind() {
		return wind;
	}
	public void setWind(List<String> wind) {
		this.wind = wind;
	}
	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}
	public String getRefreshWeek() {
		return refreshWeek;
	}
	public void setRefreshWeek(String refreshWeek) {
		this.refreshWeek = refreshWeek;
	}
	public String getTomorrowTemperature() {
		return tomorrowTemperature;
	}
	public void setTomorrowTemperature(String tomorrowTemperature) {
		this.tomorrowTemperature = tomorrowTemperature;
	}
	public String getTomorrowWeather() {
		return tomorrowWeather;
	}
	public void setTomorrowWeather(String tomorrowWeather) {
		this.tomorrowWeather = tomorrowWeather;
	}
	public String getComfortable() {
		return comfortable;
	}
	public void setComfortable(String comfortable) {
		this.comfortable = comfortable;
	}
	public List<String> getTemperatureMax() {
		return temperatureMax;
	}
	public void setTemperatureMax(List<String> temperatureMax) {
		this.temperatureMax = temperatureMax;
	}
	public List<String> getTemperatureMin() {
		return temperatureMin;
	}
	public void setTemperatureMin(List<String> temperatureMin) {
		this.temperatureMin = temperatureMin;
	}
	public List<Integer> getMaxlist() {
		return maxlist;
	}
	public void setMaxlist(List<Integer> maxlist) {
		this.maxlist = maxlist;
	}
	public List<Integer> getMinlist() {
		return minlist;
	}
	public void setMinlist(List<Integer> minlist) {
		this.minlist = minlist;
	}
	public int getPicIndex() {
		return picIndex;
	}
	public void setPicIndex(int picIndex) {
		this.picIndex = picIndex;
	}
	public List<Integer> getLowPic() {
		return lowPic;
	}
	public void setLowPic(List<Integer> lowPic) {
		this.lowPic = lowPic;
	}
	public List<Integer> getTopPic() {
		return topPic;
	}
	public void setTopPic(List<Integer> topPic) {
		this.topPic = topPic;
	}
	public boolean isNight() {
		return isNight;
	}
	public void setNight(boolean isNight) {
		this.isNight = isNight;
	}
	public String getTodayTemperature() {
		return todayTemperature;
	}
	public void setTodayTemperature(String todayTemperature) {
		this.todayTemperature = todayTemperature;
	}
	public String getTodayWeather() {
		return todayWeather;
	}
	public void setTodayWeather(String todayWeather) {
		this.todayWeather = todayWeather;
	}
	public String getComforfeeltemp()
	{
		return comforfeeltemp;
	}
	public void setComforfeeltemp(String comforfeeltemp)
	{
		this.comforfeeltemp = comforfeeltemp;
	}
	public String getPm25Polution()
	{
		return pm25Polution;
	}
	public void setPm25Polution(String pm25Polution)
	{
		this.pm25Polution = pm25Polution;
	}
	public String getQlty()
	{
		return qlty;
	}
	public void setQlty(String qlty)
	{
		this.qlty = qlty;
	}
	public List<RecentWeather> getRecentWeatherList()
	{
		return recentWeatherList;
	}
	public void setRecentWeatherList(List<RecentWeather> recentWeatherList)
	{
		this.recentWeatherList = recentWeatherList;
	}
	public String getCityid()
	{
		return cityid;
	}
	public void setCityid(String cityid)
	{
		this.cityid = cityid;
	}
	
}
