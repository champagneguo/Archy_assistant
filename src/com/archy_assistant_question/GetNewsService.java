package com.archy_assistant_question;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

public class GetNewsService extends NetService {

	/**
	 * ͨ��page���������б���
	 * 
	 * @param newsName
	 *            ������������
	 * @param page
	 *            ��������ҳ��
	 * @return
	 */
	public static List<Fragment03News> getNewsByPage(String newsName, int page) {

		JSONObject[] jsonObjects = getJsonObjectsByUrl("http://m.baidu.com/news?tn=bdapisearch&word="
				+ newsName + "&pn=" + 15 * page + "&rn=20&t=1386838893136");
		List<Fragment03News> newss = new ArrayList<Fragment03News>();
		try {
			if (jsonObjects != null && jsonObjects.length > 0) {
				for (JSONObject jsonObject : jsonObjects) {
					Fragment03News news = new Fragment03News();
					news.setTitle(jsonObject.getString("title"));
					news.setSource(jsonObject.getString("author"));
					news.setUrl(jsonObject.getString("url"));
					news.setPhotoUrl(jsonObject.getString("imgUrl"));
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm", Locale.CHINA);
					news.setDate(dateFormat.format(new Date(jsonObject
							.getLong("sortTime") * 1000)));
					newss.add(news);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newss;
	}
}
