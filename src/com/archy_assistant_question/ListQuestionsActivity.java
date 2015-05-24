package com.archy_assistant_question;

import java.util.ArrayList;
import com.archy_assistant.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class ListQuestionsActivity extends Activity {

	private ListView listview;
	private int list;
	private ListQuestionsAdapter adapter;
	public ArrayList<ArrayList<Question01_Item>> DataItems;
	public ConstantArrayList constantArrayList;
	public ArrayList<EditText> editTexts;
	public PopupWindow popupWindow;
	public LinearLayout layouts[];
	public ArrayList<String> answers_string;
	public Handler handler;
	public int number = 0;
	public int i = 0;
	public String question_string;
	private String TAG = "ListQuestionsActivity";
	public boolean flag = true;
	public String temp;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listquestions);
		constantArrayList = (ConstantArrayList) getApplication();
		DataItems = constantArrayList.getDataItems();
		listview = (ListView) findViewById(R.id.listquestions_listview);
		list = getIntent().getIntExtra("position", 0);
		adapter = new ListQuestionsAdapter(this, DataItems.get(list));
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (DataItems.get(list).get(position).getTotalNumber() == 0) {
					Toast.makeText(ListQuestionsActivity.this,
							"参与人数为零，图表显示无意义。", Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(ListQuestionsActivity.this,
							ChartActivity.class);
					intent.putExtra("position", position);
					intent.putExtra("list", list);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 0, 0, "添加问题");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		int id = item.getItemId();
		Log.e(TAG, "onOptionsItemSelected:" + id);
		if (id == 0) {

			getpopupWindow();
			popupWindow.showAtLocation(findViewById(R.id.listquestions_layout),
					Gravity.CENTER, 0, 0);
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("HandlerLeak")
	public void getpopupWindow() {
		final EditText question;
		Button cancle, save, add;
		Log.e(TAG, "getpopupWindow()");
		// 注意第三个变量false or ture?;
		View popupWindow_view = getLayoutInflater().inflate(
				R.layout.listquestions_popup, null);
		int screenwidth = this.getWindowManager().getDefaultDisplay()
				.getWidth();
		int screenheight = this.getWindowManager().getDefaultDisplay()
				.getHeight();
		popupWindow = new PopupWindow(popupWindow_view, screenwidth,
				screenheight);
		layouts = new LinearLayout[7];
		layouts[0] = (LinearLayout) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearA);
		layouts[1] = (LinearLayout) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearB);
		layouts[2] = (LinearLayout) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearC);
		layouts[3] = (LinearLayout) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearD);
		layouts[4] = (LinearLayout) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearE);
		layouts[5] = (LinearLayout) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearF);
		layouts[6] = (LinearLayout) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearG);
		editTexts = new ArrayList<EditText>();
		editTexts.add((EditText) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearA_et));
		editTexts.add((EditText) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearB_et));
		editTexts.add((EditText) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearC_et));
		editTexts.add((EditText) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearD_et));
		editTexts.add((EditText) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearE_et));
		editTexts.add((EditText) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearF_et));
		editTexts.add((EditText) popupWindow_view
				.findViewById(R.id.listquestions_popup_linearG_et));

		// 当EditText无法输入时，修改popupWindow的焦点
		popupWindow.setFocusable(true);
		handler = new Handler() {
			@SuppressLint("ResourceAsColor")
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what == 0x123) {
					Log.e(TAG, "handleMessage()" + msg.what);
					layouts[number].setVisibility(View.VISIBLE);
					editTexts.get(number).requestFocus();
					number++;
					Log.e(TAG, "handleMessage(number)" + number);
				}
			}
		};
		question = (EditText) popupWindow_view
				.findViewById(R.id.listquestions_popup_question);
		cancle = (Button) popupWindow_view
				.findViewById(R.id.listquestions_popup_cancle);
		save = (Button) popupWindow_view
				.findViewById(R.id.listquestions_popup_confirm);
		add = (Button) popupWindow_view
				.findViewById(R.id.listquestions_popup_add);
		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				number = 0;
				popupWindow.dismiss();
			}
		});
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				question_string = question.getText().toString().trim();
				Log.e(TAG, "save.setOnClickListener:" + question_string);
				answers_string = new ArrayList<String>();
				flag = true;
				for (int i = 0; i < number; i++) {
					temp = editTexts.get(i).getText().toString().trim();
					Log.e(TAG, "save.setOnClickListener:temp:" + temp);
					if (temp == null | temp.equals("")) {
						flag = false;
					}
					answers_string.add(temp);
				}

				if (question_string == null | question_string.equals("")
						| number == 0 | flag == false) {
					Toast.makeText(ListQuestionsActivity.this, "请完善上述信息。",
							Toast.LENGTH_SHORT).show();
				} else {
					Question01_Item question_Item = new Question01_Item();

					if (DataItems.get(list).get(0).getQuestionNumber() == 0) {
						DataItems.get(list).remove(0);
						adapter.notifyDataSetChanged();
					}
					question_Item.setQuestionNumber(1);
					question_Item
							.setVoteQuestion((DataItems.get(list).size() + 1)
									+ "、" + question_string);
					question_Item.setVoteAnswers(answers_string);

					DataItems.get(list).add(question_Item);
					popupWindow.dismiss();
					adapter.notifyDataSetChanged();
					number = 0;
					Toast.makeText(ListQuestionsActivity.this, "试题添加完成。",
							Toast.LENGTH_SHORT).show();

				}
			}
		});
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (number >= 7) {
					Toast.makeText(ListQuestionsActivity.this, "只能添加7个选项",
							Toast.LENGTH_SHORT).show();
				} else {
					Message msg = new Message();
					msg.what = 0x123;
					handler.sendMessage(msg);
					Log.e(TAG, "add.setOnClickListener" + msg.what);
				}

			}
		});

	}
}
