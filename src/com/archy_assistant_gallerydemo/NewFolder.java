package com.archy_assistant_gallerydemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.archy_assistant.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("WorldReadableFiles")
public class NewFolder extends Activity {

	static String Path_example = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity04_newfolder);
		final EditText name = (EditText) findViewById(R.id.activity04_newfolder_name);
		final EditText path = (EditText) findViewById(R.id.activity04_newfolder_path);
		Button submit = (Button) findViewById(R.id.activity04_newfolder_submit);
		Button cancle = (Button) findViewById(R.id.activity04_newfolder_cancle);

		submit.setOnClickListener(new OnClickListener() {

			@SuppressLint("SdCardPath")
			@Override
			public void onClick(View v) {
				String Name = name.getText().toString().trim();
				String Path = path.getText().toString().trim();
				System.out.println("文件夹名称为" + Name);

				if (Name == null) {
					Toast.makeText(NewFolder.this, "文件夹名不能为空！",
							Toast.LENGTH_LONG).show();
				} else {
					// 首先判断sdcard是否插入
					String status = Environment.getExternalStorageState();
					if (status.equals(Environment.MEDIA_MOUNTED)) {
						if (Path.equals("")) {
							Path = Environment.getExternalStorageDirectory()
									.toString() + "/考古图片/";
						}
						File desDir = new File(Path + Name);

						if (!desDir.exists()) {
							desDir.mkdirs();
							// 将资源中的图片文件实例化为Bitmap对象；
							Bitmap bitmap = BitmapFactory.decodeResource(
									getResources(), R.raw.example);
							try {
								// 以读写的方式打开文件；
								String file = Path + Name + "/example.jpg";
								FileOutputStream os = new FileOutputStream(file);
								bitmap.compress(Bitmap.CompressFormat.JPEG,
										100, os);

							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							System.out.println("文件夹的路径为" + desDir);

							Intent data = new Intent();
							data.putExtra("name", Name);
							setResult(10, data);
							finish();
						} else {
							Toast.makeText(NewFolder.this, "创建文件夹失败",
									Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(NewFolder.this, "您的SD卡未插入！",
								Toast.LENGTH_LONG).show();
					}

				}
			}
		});

		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

}
