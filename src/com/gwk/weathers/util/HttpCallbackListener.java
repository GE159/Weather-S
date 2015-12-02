package com.gwk.weathers.util;
/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月2日上午11:09:27
 */
/**
 * 返回接口
 */
public interface HttpCallbackListener
{

/**
 * 请求成功
 * @param response 返回消息
 */
void onFinish(String response);
/**
 * 加载失败
 * @param e
 */
void onError(Exception e);
}
