package com.gwk.weathers.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
 *作者：葛文凯
 *邮箱：651517957@qq.com
 *时间：2015年12月2日上午11:07:17
 */

public class HttpUtil
{	
	
	/**
	 * 向服务器发送请求获取数据信息
	 */
	public static void sendHttpRequest(final String address,
			final HttpCallbackListener listener)
	{
		new Thread(new Runnable() {

			@Override
			public void run()
			{
				HttpURLConnection connection = null;
				try
				{
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null)
					{
						response.append(line);
					}
					if (listener != null)
					{
						listener.onFinish(response.toString());
					}

				} catch (Exception e)
				{
					if (listener != null)
					{
						listener.onError(e);
					}
					e.printStackTrace();
				} finally
				{
					if (connection != null)
					{
						connection.disconnect();
					}
				}

			}
		}).start();
	}
}
