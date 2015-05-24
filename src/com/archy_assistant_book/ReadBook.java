package com.archy_assistant_book;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.util.EncodingUtils;
import com.archy_assistant.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ReadBook extends Activity {
	public String Title = null;
	public String Content = null;
	public int count_group = -1;
	public int count_child = -1;
	public int flag = 0;
	public int length = -1;
	public String Name[][] = {
			{ "《风雪定陵》", "《中国新石器时代》", "《夏商周断代工程》", "《中国考古学通论》", "《最早的中国》" },
			{ "《呼吸艺术》", "《明式家具》", "《中国博物馆学》", "《礼仪注释》", "《中国物质遗产名录》" },
			{ "《营造法式》", "《中国传统文化》", "《中国古代天文与人文》", "《中国建筑史》", "《文物考古》" },
			{ "《城记》", "《到古代中国旅行》", "《古峡迷雾》", "《去古人庭院散步》", "《唐朝穿越指南》" },
			{ "《考古笔记》", "《考古的故事》", "《考古中国》", "《田野考古规程》", "《人类考古发现》" } };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity10_book);
		TextView title = (TextView) findViewById(R.id.activity10_book_title);
		TextView content = (TextView) findViewById(R.id.activity10_book_content);
		count_group = this.getIntent().getExtras().getInt("groupPosition");
		count_child = this.getIntent().getExtras().getInt("childPosition");
		title.setText(Name[count_group][count_child]);

		InputStream is[][] = new InputStream[5][5];
		is[0][0] = getResources().openRawResource(R.raw.is00);
		is[0][1] = getResources().openRawResource(R.raw.is01);
		is[0][2] = getResources().openRawResource(R.raw.is02);
		is[0][3] = getResources().openRawResource(R.raw.is03);
		is[0][4] = getResources().openRawResource(R.raw.is04);
		is[1][0] = getResources().openRawResource(R.raw.is10);
		is[1][1] = getResources().openRawResource(R.raw.is11);
		is[1][2] = getResources().openRawResource(R.raw.is12);
		is[1][3] = getResources().openRawResource(R.raw.is13);
		is[1][4] = getResources().openRawResource(R.raw.is14);
		is[2][0] = getResources().openRawResource(R.raw.is20);
		is[2][1] = getResources().openRawResource(R.raw.is21);
		is[2][2] = getResources().openRawResource(R.raw.is22);
		is[2][3] = getResources().openRawResource(R.raw.is23);
		is[2][4] = getResources().openRawResource(R.raw.is24);
		is[3][0] = getResources().openRawResource(R.raw.is30);
		is[3][1] = getResources().openRawResource(R.raw.is31);
		is[3][2] = getResources().openRawResource(R.raw.is32);
		is[3][3] = getResources().openRawResource(R.raw.is33);
		is[3][4] = getResources().openRawResource(R.raw.is34);
		is[4][0] = getResources().openRawResource(R.raw.is40);
		is[4][1] = getResources().openRawResource(R.raw.is41);
		is[4][2] = getResources().openRawResource(R.raw.is42);
		is[4][3] = getResources().openRawResource(R.raw.is43);
		is[4][4] = getResources().openRawResource(R.raw.is44);

		try {
			length = is[count_group][count_child].available();
			byte[] buffer = new byte[length];
			is[count_group][count_child].read(buffer);
			Content = EncodingUtils.getString(buffer, "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			is[count_group][count_child].close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		content.setText(Content);

	}
}
