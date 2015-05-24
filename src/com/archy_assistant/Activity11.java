package com.archy_assistant;

//ʵ�ֿ���ͨ�Ĺ���
import java.lang.reflect.Method;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.archy_assistant_search.Action;
import com.archy_assistant_search.Activity11_add;
import com.archy_assistant_search.Activity11_update;
import com.archy_assistant_search.MyDataBaseHelper;

public class Activity11 extends Activity {
	private MyDataBaseHelper DB;
	private Button bt;
	private EditText et;
	private ListView lv;
	Cursor cursor;

	@SuppressLint("SdCardPath")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_11);
		et = (EditText) findViewById(R.id.activity_11_et);
		bt = (Button) findViewById(R.id.activity_11_bt);
		lv = (ListView) findViewById(R.id.activity_11_lv);

		// ��ʼ�����ݿ⣬�������ݿ⣻��ȫ����Ŀ��Ϣ��ӵ�listview�С�
		DB = new MyDataBaseHelper(Activity11.this,
				"/data/data/com.archy_assistant/files/archy1.db", null, 1);
		cursor = FindAllElements();
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(Activity11.this,
				R.layout.activity_11_content, cursor,
				new String[] { "keywords" },
				new int[] { R.id.activity11_content_tv }, 0);
		lv.setAdapter(adapter);

		// Ϊ�������TextWatcher�������ֱ仯
		et.addTextChangedListener(new TextWatcher() {

			// ���ֱ仯ʱ
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (et.getText() != null) {
					String input_info = et.getText().toString();
					cursor = FindElements(input_info);
					lv.setAdapter(new SimpleCursorAdapter(Activity11.this,
							R.layout.activity_11_content, cursor,
							new String[] { "keywords" },
							new int[] { R.id.activity11_content_tv }));
				}

			}

			// ���ֱ仯ǰ
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			// ���ֱ仯��
			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				Builder builder = new Builder(Activity11.this);
				builder.setTitle("��ѡ�����");
				builder.setItems(new String[] { "�޸Ĵ�����Ϣ", "ɾ��������Ϣ" },
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == 0) {
									// Update
									new UpdateAction(position).execute();
								} else if (which == 1) {
									// Delete
									new DeleteAction(position).execute();
								}
							}
						}).create().show();
				return true;
			}
		});
		// ΪListview��ÿ����Ŀ��Ӽ�������
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (cursor.moveToPosition(position)) {
					Pop();// �����Ի���
				}
			}
		});

		// ΪButton��Ӽ�������ע����View.OnClickListener()
		bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (et.getText().toString().length() == 0) {
					Toast.makeText(Activity11.this, "�������ѯ�ؼ���",
							Toast.LENGTH_SHORT).show();
				} else {
					if (cursor.moveToFirst() && (cursor.getCount() != 0)) {
						Pop();// �����Ի���
					} else {
						Toast.makeText(Activity11.this, "�������δ�ҵ���",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

	}

	// �����Ի���
	private void Pop() {
		String Details = cursor.getString(2);
		Builder builder = new Builder(Activity11.this);
		builder.setTitle(cursor.getString(1));
		builder.setMessage("	" + Details);
		builder.setPositiveButton("�ر�", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// cursor.requery();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		builder.create().show();
	}

	// ���������Ŀ�˵�
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * ���÷�����Ƶ���MenuBuilder��setOptionalIconsVisible��������
		 * mOptionalIconsVisibleΪtrue�� ���˵�����ͼ��ʱ�ſɼ�
		 */
		setIconEnable(menu, true);

		menu.add(0, 0, 0, "	�����Ŀ").setIcon(android.R.drawable.ic_menu_add);
		return super.onCreateOptionsMenu(menu);
	}

	// Ϊÿ���˵���ӵ���¼�
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == 0) {
			// create
			new CreateAction().execute();
		}
		return super.onOptionsItemSelected(item);
	}

	// enableΪtrueʱ���˵����ͼ����Ч��enableΪfalseʱ��Ч��4.0ϵͳĬ����Ч
	private void setIconEnable(Menu menu, boolean enable) {
		try {
			Class<?> clazz = Class
					.forName("com.android.internal.view.menu.MenuBuilder");
			Method m = clazz.getDeclaredMethod("setOptionalIconsVisible",
					boolean.class);
			m.setAccessible(true);

			// MenuBuilderʵ��Menu�ӿڣ������˵�ʱ����������menu��ʵ����MenuBuilder����(java�Ķ�̬����)
			m.invoke(menu, enable);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ʵ�ֶ����ĳ���ӿڣ����������ڣ�
	private class CreateAction implements Action {
		public void execute() {
			Intent intent = new Intent(Activity11.this, Activity11_add.class);
			startActivity(intent);
		}
	}

	// ʵ�ֶ����ӿڣ��޸Ĵ�����Ϣ
	private class UpdateAction implements Action {

		private int position;

		UpdateAction(int position) {
			this.position = position;
		}

		public void execute() {
			cursor.moveToPosition(position);
			String Id = cursor.getString(0);
			String Keys = cursor.getString(1);
			String Content = cursor.getString(2);
			Intent intent = new Intent(Activity11.this, Activity11_update.class);
			Bundle bundle = new Bundle();
			bundle.putString("id", Id);
			bundle.putString("keys", Keys);
			bundle.putString("content", Content);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

	// ʵ�ֳ���ӿڣ���ɾ������
	private class DeleteAction implements Action {
		private int position;

		DeleteAction(int position) {
			this.position = position;
		}

		public void execute() {
			Builder builder = new AlertDialog.Builder(Activity11.this);
			builder.setTitle("ȷ��ɾ��");
			builder.setMessage("���ɾ����");
			builder.setNegativeButton("ȡ��", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			builder.setPositiveButton("ȷ��", new OnClickListener() {

				@SuppressWarnings("deprecation")
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (cursor.moveToPosition(position)) {
						int id = cursor.getInt(0);
						DeleteElement(id);
						cursor.requery();
					}
				}
			});
			builder.create().show();
		}

	}

	// ��������Ԫ�ص��α꼯�ϣ�
	private Cursor FindAllElements() {
		SQLiteDatabase db = DB.getReadableDatabase();
		Cursor cursor1 = db.rawQuery(
				"select * from dictionary order by keywords", null);
		return cursor1;
	}

	// ���Ҳ���Ԫ�ص��α�
	private Cursor FindElements(String input_info) {
		// ģ����ѯ
		String Sql_sentence = "select * from dictionary where keywords like '"
				+ input_info + "%' order by keywords";
		SQLiteDatabase db = DB.getReadableDatabase();
		Cursor cursor2 = db.rawQuery(Sql_sentence, null);
		return cursor2;
	}

	// ɾ��ĳ����¼
	private void DeleteElement(int id) {
		SQLiteDatabase db = DB.getReadableDatabase();
		db.execSQL("delete from dictionary where _id = " + id);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// �˳�����ʱ���ر�MyDatabaseHelper���SQLiteDatebase
		if (DB != null) {
			DB.close();
		}
	}

}
