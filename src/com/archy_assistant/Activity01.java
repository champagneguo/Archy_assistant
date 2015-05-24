package com.archy_assistant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.archy_assistant_question.Fragment01;
import com.archy_assistant_question.Fragment02;
import com.archy_assistant_question.Fragment03;
import com.archy_assistant_question.Question01_Item;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;
import com.archy_assistant_question.ConstantArrayList;

public class Activity01 extends FragmentActivity {

	private boolean flag;
	private String TAG = "Activity01";
	private FragmentTabHost mTabHost;
	private RadioGroup mTabGg;
	private final Class[] fragments = { Fragment01.class, Fragment02.class,
			Fragment03.class };
	private RadioButton button2;
	public ArrayList<ArrayList<Question01_Item>> DataItems;
	public ConstantArrayList constantArrayList;
	public Fragment02 fragment02;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_01);
		initView();
		// ��ÿ��ҳ������ݽ��г�ʼ����
		initData();
		// ʹ��Application��ȫ�ֱ������洢���ݣ�
		constantArrayList = (ConstantArrayList) getApplication();
		constantArrayList.setDataItems(DataItems);

	}

	public void initView() {
		Log.e(TAG, "ִ��initView");
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

		Log.e(TAG, "�½�mtabHost����");
		mTabHost.setup(this, getSupportFragmentManager(),
				R.id.activity01_realtabcontent);
		Log.e(TAG, "mTabHost.setup");
		// Fragment�ĸ�����
		int count = fragments.length;
		for (int i = 0; i < count; i++) {
			// Ϊÿ��Tab����ͼ������ݣ�
			TabSpec tabSpect = mTabHost.newTabSpec(i + "").setIndicator(i + "");
			// ��Tab��ť��ӵ�Tabѡ��У�
			mTabHost.addTab(tabSpect, fragments[i], null);
			Log.e(TAG, "fragments" + i);
		}

		mTabGg = (RadioGroup) findViewById(R.id.activity01_radio_rg_menu);
		Log.e(TAG, "mTabGg:" + mTabGg);

		button2 = (RadioButton) findViewById(R.id.tab_rb_2);

		mTabGg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.tab_rb_1:
					mTabHost.setCurrentTab(0);
					Log.e(TAG, "R.id.tab_rb_1");
					break;
				case R.id.tab_rb_2:
					mTabHost.setCurrentTab(1);
					Log.e(TAG, "R.id.tab_rb_2");
					break;
				case R.id.tab_rb_3:
					mTabHost.setCurrentTab(2);
					Log.e(TAG, "R.id.tab_rb_3");
					break;
				default:
					break;
				}
			}
		});

		mTabHost.setCurrentTab(0);

	}

	// ��ÿ��ҳ������ݽ��г�ʼ����
	public void initData() {
		DataItems = new ArrayList<ArrayList<Question01_Item>>();
		ArrayList<ArrayList<String>> answers1 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> answers2 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> answers3 = new ArrayList<ArrayList<String>>();
		String textline;
		int i = 0, j = 0, k = 0;
		boolean flag = false;
		boolean flag1 = false;
		String temp = null;
		Question01_Item question01_item;
		Log.e(TAG, "onCreate():init():initData()");
		ArrayList<Question01_Item> dataItem1 = new ArrayList<Question01_Item>();
		ArrayList<Question01_Item> dataItem2 = new ArrayList<Question01_Item>();
		ArrayList<Question01_Item> dataItem3 = new ArrayList<Question01_Item>();

		// ��ȡ�ļ���������
		InputStream isQuestionnaire01 = this.getResources().openRawResource(
				R.raw.questionnaire1);
		InputStream isQuestionnaire02 = this.getResources().openRawResource(
				R.raw.questionnarire2);
		InputStream isQuestionnaire03 = this.getResources().openRawResource(
				R.raw.questionnaire3);

		InputStreamReader isQues01Reader = new InputStreamReader(
				isQuestionnaire01);
		InputStreamReader isQues02Reader = new InputStreamReader(
				isQuestionnaire02);
		InputStreamReader isQues03Reader = new InputStreamReader(
				isQuestionnaire03);
		// ����BufferedReader��ȡ��������
		BufferedReader brQues01 = new BufferedReader(isQues01Reader);
		BufferedReader brQues02 = new BufferedReader(isQues02Reader);
		BufferedReader brQues03 = new BufferedReader(isQues03Reader);

		Log.e(TAG, "��ȡ�ļ��ɹ�");
		try {
			while ((textline = brQues01.readLine()) != null) {
				j++;
				if (j == 1) {
					temp = textline;
					Log.e(TAG, "����" + textline);
				} else {
					Log.e(TAG, "����" + textline);
					if (textline.trim().equals("")) {
						question01_item = new Question01_Item();
						question01_item.setTitle(temp);
						question01_item.setQuestionNumber(1);
						question01_item.setType(0);
						dataItem1.add(question01_item);
						ArrayList<String> answer = new ArrayList<String>();
						answers1.add(answer);
						flag = true;
						Log.e(TAG, "����" + i);
						i++;
						continue;
					} else {
						if (flag) {
							dataItem1.get(i - 1).setVoteQuestion(textline);
							Log.e(TAG, "��ȡ��Ŀ:" + (i - 1) + "::" + textline);
							flag = false;
						} else {
							answers1.get(i - 1).add(textline);
							Log.e(TAG, "��ȡ��:" + (i - 1) + "::" + textline);
						}
					}

				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (k = 0; k < i; k++) {
			dataItem1.get(k).setVoteAnswers(answers1.get(k));
			// ��ʼ�������С��
			dataItem1.get(k)
					.initCount(dataItem1.get(k).getVoteAnswers().size());
		}

		// ��ʼ��������
		i = 0;
		j = 0;
		k = 0;
		try {
			while ((textline = brQues02.readLine()) != null) {
				j++;
				if (j == 1) {
					temp = textline;
					Log.e(TAG, "����" + textline);
				} else {
					Log.e(TAG, "����" + textline);
					if (textline.trim().equals("")) {
						question01_item = new Question01_Item();
						question01_item.setQuestionNumber(1);
						question01_item.setTitle(temp);
						question01_item.setType(1);
						dataItem2.add(question01_item);
						ArrayList<String> answer = new ArrayList<String>();
						answers2.add(answer);
						flag1 = true;
						Log.e(TAG, "����" + i);
						i++;
						continue;
					} else {
						if (flag1) {
							dataItem2.get(i - 1).setVoteQuestion(textline);
							Log.e(TAG, "��ȡ��Ŀ:" + (i - 1) + "::" + textline);
							flag1 = false;
						} else {
							answers2.get(i - 1).add(textline);
							Log.e(TAG, "��ȡ��:" + (i - 1) + "::" + textline);
						}
					}

				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (k = 0; k < i; k++) {
			dataItem2.get(k).setVoteAnswers(answers2.get(k));
			// ��ʼ�������С��
			dataItem2.get(k)
					.initCount(dataItem2.get(k).getVoteAnswers().size());
		}

		// ��ʼ��������
		i = 0;
		j = 0;
		k = 0;
		try {
			while ((textline = brQues03.readLine()) != null) {
				j++;
				if (j == 1) {
					temp = textline;
					Log.e(TAG, "����" + textline);
				} else {
					Log.e(TAG, "����" + textline);
					if (textline.trim().equals("")) {
						question01_item = new Question01_Item();
						question01_item.setQuestionNumber(1);
						question01_item.setTitle(temp);
						question01_item.setType(2);
						dataItem3.add(question01_item);
						ArrayList<String> answer = new ArrayList<String>();
						answers3.add(answer);
						flag1 = true;
						Log.e(TAG, "����" + i);
						i++;
						continue;
					} else {
						if (flag1) {
							dataItem3.get(i - 1).setVoteQuestion(textline);
							Log.e(TAG, "��ȡ��Ŀ:" + (i - 1) + "::" + textline);
							flag1 = false;
						} else {
							answers3.get(i - 1).add(textline);
							Log.e(TAG, "��ȡ��:" + (i - 1) + "::" + textline);
						}
					}

				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (k = 0; k < i; k++) {
			dataItem3.get(k).setVoteAnswers(answers3.get(k));
			// ��ʼ�������С��
			dataItem3.get(k)
					.initCount(dataItem3.get(k).getVoteAnswers().size());
		}

		DataItems.add(dataItem1);
		DataItems.add(dataItem2);
		DataItems.add(dataItem3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// ����ǰActivity���¼�������¼��أ�
	@Override
	protected void onResume() {
		Log.e(TAG, "onResume()");
		// TODO Auto-generated method stub
		if (flag) {

			button2.setChecked(true);
			mTabHost.setCurrentTab(1);
		}
		flag = false;
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (resultCode == 100) {
			Log.e(TAG, "onActivityResult");
			flag = true;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
