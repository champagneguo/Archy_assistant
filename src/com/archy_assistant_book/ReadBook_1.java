package com.archy_assistant_book;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.http.util.EncodingUtils;
import com.archy_assistant.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ReadBook_1 extends Activity {
	private TextView title;
	private String path;
	private String filename;
	private TextView content;
	private StringBuilder Content;
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		filename = getIntent().getStringExtra("title");
		path = getIntent().getStringExtra("path");
		setContentView(R.layout.activity10_book);
		title = (TextView) findViewById(R.id.activity10_book_title);
		content = (TextView) findViewById(R.id.activity10_book_content);
		try {
			FileInputStream finputstream = new FileInputStream(path);
			byte[] buffer = new byte[1024];
			Content = new StringBuilder("");
			// read函数返回实际读取的字节数；
			while ((count = finputstream.read(buffer)) > 0) {
				Content.append(EncodingUtils.getString(buffer, 0, count,
						"utf-8"));
			}
			// 关闭文件流
			finputstream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		title.setText(filename.substring(0, filename.indexOf(".")));
		content.setText(Content);

	}

}
