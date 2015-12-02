package com.gwk.weathers.util;
/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月2日上午11:09:27
 */
public interface HttpCallbackListener
{
void onFinish(String response);
void onError(Exception e);
}
