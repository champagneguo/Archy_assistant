package com.archy_assistant_network;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.archy_assistant.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	public String Username = null;
	public String Password = null;
	public boolean Is_Store_account = true;
	public boolean Is_Store_password = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		final EditText username = (EditText) findViewById(R.id.login_ed1);
		final EditText password = (EditText) findViewById(R.id.login_ed2);
		CheckBox is_Store_account = (CheckBox) findViewById(R.id.login_cB1);
		CheckBox is_Store_password = (CheckBox) findViewById(R.id.login_cB2);
		Button Login = (Button) findViewById(R.id.login_sumbit);
		Button Cancle = (Button) findViewById(R.id.login_cancle);

		// �ж��Ƿ������洢�����˻���Ϣ��
		is_Store_account
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Is_Store_account = true;
						} else {
							Is_Store_account = false;
						}
					}
				});

		// �ж��Ƿ������洢�������룻
		is_Store_password
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Is_Store_password = true;
						} else {
							Is_Store_password = false;
						}

					}
				});

		// ���õ���ύ�¼�
		Login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Username = username.getText().toString().trim();
				Password = password.getText().toString().trim();
				System.out.println(Username);
				System.out.println(Password);

				if (Username != null && Password != null) {
					if (isExit()) {
						Intent data = new Intent();
						data.putExtra("username_login", Username);
						setResult(10, data);
						finish();
					} else {
						Toast.makeText(Login.this, "��¼ʧ�ܣ��û��������벻��ȷ��",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(Login.this, "����ȷ��д�û��������룡",
							Toast.LENGTH_LONG).show();
				}
			}

		});

		// ���õ��ȡ���¼�
		Cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �رյ�ǰActivity��
				finish();
			}
		});
	}

	public boolean isExit() {
		try {
			JSONObject jsonobject = search(Username, Password);
			if (jsonobject.getInt("user_id") > 0) {
				return true;
			}
		} catch (Exception e) {
			Toast.makeText(this, "�����������쳣��", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return false;
	}

	public JSONObject search(String username, String password)
			throws JSONException, Exception {
		// ʹ��Map��װ�������
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);

		// ���巢������
		String url = HttpClient.Base_url + "LoginServlet";
		// ��������
		System.out.println(url);
		
		String answer = HttpClient.postRequest(url, map);
		System.out.println(answer);
		JSONObject jsonObject = new JSONObject(answer);
		System.out.println(jsonObject);
		return jsonObject;
	}

}
