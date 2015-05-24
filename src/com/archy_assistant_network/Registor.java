package com.archy_assistant_network;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.archy_assistant.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registor extends Activity {

	public String Email = null;
	public String Username = null;
	public String Password = null;
	public String Confirm = null;

	public static String JSONTokener(String in) {
		// consume an optional byte order mark (BOM) if it exists
		if (in != null && in.startsWith("\ufeff")) {
			in = in.substring(1);
		}
		return in;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registor);

		final EditText email = (EditText) findViewById(R.id.registor_email);
		final EditText username = (EditText) findViewById(R.id.registor_username);
		final EditText password = (EditText) findViewById(R.id.registor_password);
		final EditText confirm = (EditText) findViewById(R.id.registor_confirm);
		Button submit = (Button) findViewById(R.id.registor_submit);
		Button cancle = (Button) findViewById(R.id.registor_cancle);

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 两天时间debug，再点击之后获取框内数据；
				Email = email.getText().toString().trim();
				Username = username.getText().toString().trim();
				Password = password.getText().toString().trim();
				Confirm = confirm.getText().toString().trim();

				if (Email == null || Username == null || Password == null
						|| Confirm == null) {
					Toast.makeText(Registor.this, "注册信息不完整", Toast.LENGTH_SHORT)
							.show();
				} else if (!Password.equals(Confirm)) {
					Toast.makeText(Registor.this, "密码输入有误", Toast.LENGTH_SHORT)
							.show();
				} else {
					// 将数据传入服务器；
					AlertDialog.Builder builder = new AlertDialog.Builder(
							Registor.this);
					builder.setTitle("提示信息：");
					if (registor_is_success()) {

						builder.setMessage("注册成功！");
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										//返回数据到上一级Activity
										Intent data = new Intent();
										data.putExtra("username_register", Username);
										setResult(20, data);
										finish();
									}
								});
						builder.setNegativeButton("再次注册",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Intent intent = new Intent(
												Registor.this, Registor.class);
										startActivity(intent);
										finish();
									}
								});
						builder.create().show();
					} else {
						builder.setMessage("注册失败！");
						builder.setPositiveButton("重新注册！",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Intent intent = new Intent(
												Registor.this, Registor.class);
										startActivity(intent);
										finish();
									}
								});
						builder.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}
								});
						builder.create().show();
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

	public boolean registor_is_success() {
		try {
			JSONObject jsonobject = insert(Email, Username, Password, Confirm);
			if (jsonobject.getString("username").equals(Username)) {
				return true;
			}
		} catch (Exception e) {
			Toast.makeText(this, "服务器连接异常！", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return false;
	}

	public JSONObject insert(String email, String username, String password,
			String confirm) throws JSONException, Exception {
		// 使用Map封装请求参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("username", username);
		map.put("password", password);
		map.put("confirm", confirm);

		// 定义发送请求；
		String url = HttpClient.Base_url + "RegisterServlet";
		// 发送请求；
		System.out.println(url);
		String answer = JSONTokener(HttpClient.postRequest(url, map));
		System.out.println(answer);
		JSONObject jsonObject = new JSONObject(answer);
		System.out.println(jsonObject);
		return jsonObject;
	}

}
