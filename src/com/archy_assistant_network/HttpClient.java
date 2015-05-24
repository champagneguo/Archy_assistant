package com.archy_assistant_network;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClient {
	// 创建HttpClient对象
	public static DefaultHttpClient httpClient = new DefaultHttpClient();
	public static final String Base_url = "http://10.10.135.238:8080/archaeologist/";

	/**
	 * 
	 * @param url
	 *            发送请求的Url
	 * @return 服务器响应字段
	 * @throws Exception
	 */

	public static String getRequest(final String url) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					public String call() throws Exception {
						// 创建HttpGet对象
						HttpGet get = new HttpGet(url);
						// 发送Get请求
						HttpResponse httpResponse = httpClient.execute(get);
						// 如果服务器成功返回响应
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							// 获取服务器相应字符串；
							String result = EntityUtils.toString(httpResponse
									.getEntity());
							return result;
						}
						return null;
					};
				});
		new Thread(task).start();
		return task.get();
	}

	/**
	 * @param url
	 * 发送请求的url
	 * @param param
	 * 请求参数
	 * @return 服务器相应字符串
	 * @throws Exception
	 */

	public static String postRequest(final String url,
			final Map<String, String> rawParams) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						// 创建HttpPost对象
						HttpPost post = new HttpPost(url);
						// 如果传递参数个数比较多，可以对传递的参数进行封装
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						for (String key : rawParams.keySet()) {
							// 请求封装参数
							params.add(new BasicNameValuePair(key, rawParams
									.get(key)));
						}
						// 设置请求参数
						System.out.println("以下为params的值：");
						System.out.println(params.get(0).getName());
						System.out.println(params.get(0).getValue());
						System.out.println(params.get(1).getName());
						System.out.println(params.get(1).getValue());
						
						post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
						// 发送Post请求
						HttpResponse httpResponse = httpClient.execute(post);
						// 如果服务器成功地返回响应
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							// 获取服务器相应字符串
							String result = EntityUtils.toString(httpResponse
									.getEntity());
							System.out.println(result);
							return result;
						}
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}
}
