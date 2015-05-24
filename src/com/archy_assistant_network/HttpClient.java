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
	// ����HttpClient����
	public static DefaultHttpClient httpClient = new DefaultHttpClient();
	public static final String Base_url = "http://10.10.135.238:8080/archaeologist/";

	/**
	 * 
	 * @param url
	 *            ���������Url
	 * @return ��������Ӧ�ֶ�
	 * @throws Exception
	 */

	public static String getRequest(final String url) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					public String call() throws Exception {
						// ����HttpGet����
						HttpGet get = new HttpGet(url);
						// ����Get����
						HttpResponse httpResponse = httpClient.execute(get);
						// ����������ɹ�������Ӧ
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							// ��ȡ��������Ӧ�ַ�����
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
	 * ���������url
	 * @param param
	 * �������
	 * @return ��������Ӧ�ַ���
	 * @throws Exception
	 */

	public static String postRequest(final String url,
			final Map<String, String> rawParams) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						// ����HttpPost����
						HttpPost post = new HttpPost(url);
						// ������ݲ��������Ƚ϶࣬���ԶԴ��ݵĲ������з�װ
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						for (String key : rawParams.keySet()) {
							// �����װ����
							params.add(new BasicNameValuePair(key, rawParams
									.get(key)));
						}
						// �����������
						System.out.println("����Ϊparams��ֵ��");
						System.out.println(params.get(0).getName());
						System.out.println(params.get(0).getValue());
						System.out.println(params.get(1).getName());
						System.out.println(params.get(1).getValue());
						
						post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
						// ����Post����
						HttpResponse httpResponse = httpClient.execute(post);
						// ����������ɹ��ط�����Ӧ
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							// ��ȡ��������Ӧ�ַ���
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
