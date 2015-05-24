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
				System.out.println("�ļ�������Ϊ" + Name);

				if (Name == null) {
					Toast.makeText(NewFolder.this, "�ļ���������Ϊ�գ�",
							Toast.LENGTH_LONG).show();
				} else {
					// �����ж�sdcard�Ƿ����
					String status = Environment.getExternalStorageState();
					if (status.equals(Environment.MEDIA_MOUNTED)) {
						if (Path.equals("")) {
							Path = Environment.getExternalStorageDirectory()
									.toString() + "/����ͼƬ/";
						}
						File desDir = new File(Path + Name);

						if (!desDir.exists()) {
							desDir.mkdirs();
							// ����Դ�е�ͼƬ�ļ�ʵ����ΪBitmap����
							Bitmap bitmap = BitmapFactory.decodeResource(
									getResources(), R.raw.example);
							try {
								// �Զ�д�ķ�ʽ���ļ���
								String file = Path + Name + "/example.jpg";
								FileOutputStream os = new FileOutputStream(file);
								bitmap.compress(Bitmap.CompressFormat.JPEG,
										100, os);

							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							System.out.println("�ļ��е�·��Ϊ" + desDir);

							Intent data = new Intent();
							data.putExtra("name", Name);
							setResult(10, data);
							finish();
						} else {
							Toast.makeText(NewFolder.this, "�����ļ���ʧ��",
									Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(NewFolder.this, "����SD��δ���룡",
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
