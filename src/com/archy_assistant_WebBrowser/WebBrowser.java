package com.archy_assistant_WebBrowser;

import com.archy_assistant.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebBrowser extends Activity {
	private WebView wv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity04_web);
		wv = (WebView) findViewById(R.id.webView);
		String url = "http://image.baidu.com/i?word=考古图片&ie=utf-8&tn=baiduimage";
		 //打开指定URL的html文件;
		wv.loadUrl(url);
	}
}
