package com.archy_assistant;

//实现考古通的功能
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

		// 初始化数据库，并打开数据库；将全部条目信息添加到listview中。
		DB = new MyDataBaseHelper(Activity11.this,
				"/data/data/com.archy_assistant/files/archy1.db", null, 1);
		cursor = FindAllElements();
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(Activity11.this,
				R.layout.activity_11_content, cursor,
				new String[] { "keywords" },
				new int[] { R.id.activity11_content_tv }, 0);
		lv.setAdapter(adapter);

		// 为输入添加TextWatcher监听文字变化
		et.addTextChangedListener(new TextWatcher() {

			// 文字变化时
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

			// 文字变化前
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			// 文字变化后
			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				Builder builder = new Builder(Activity11.this);
				builder.setTitle("请选择操作");
				builder.setItems(new String[] { "修改词条信息", "删除词条信息" },
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
		// 为Listview中每个条目添加监听器；
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (cursor.moveToPosition(position)) {
					Pop();// 弹出对话框；
				}
			}
		});

		// 为Button添加监听器；注意是View.OnClickListener()
		bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (et.getText().toString().length() == 0) {
					Toast.makeText(Activity11.this, "请输入查询关键字",
							Toast.LENGTH_SHORT).show();
				} else {
					if (cursor.moveToFirst() && (cursor.getCount() != 0)) {
						Pop();// 弹出对话框；
					} else {
						Toast.makeText(Activity11.this, "这个词条未找到！",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

	}

	// 弹出对话框；
	private void Pop() {
		String Details = cursor.getString(2);
		Builder builder = new Builder(Activity11.this);
		builder.setTitle(cursor.getString(1));
		builder.setMessage("	" + Details);
		builder.setPositiveButton("关闭", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// cursor.requery();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});
		builder.create().show();
	}

	// 添加增加条目菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * 利用反射机制调用MenuBuilder的setOptionalIconsVisible方法设置
		 * mOptionalIconsVisible为true， 给菜单设置图标时才可见
		 */
		setIconEnable(menu, true);

		menu.add(0, 0, 0, "	添加条目").setIcon(android.R.drawable.ic_menu_add);
		return super.onCreateOptionsMenu(menu);
	}

	// 为每个菜单添加点击事件
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == 0) {
			// create
			new CreateAction().execute();
		}
		return super.onOptionsItemSelected(item);
	}

	// enable为true时，菜单添加图标有效，enable为false时无效。4.0系统默认无效
	private void setIconEnable(Menu menu, boolean enable) {
		try {
			Class<?> clazz = Class
					.forName("com.android.internal.view.menu.MenuBuilder");
			Method m = clazz.getDeclaredMethod("setOptionalIconsVisible",
					boolean.class);
			m.setAccessible(true);

			// MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
			m.invoke(menu, enable);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 实现动作的抽象接口，即创建窗口；
	private class CreateAction implements Action {
		public void execute() {
			Intent intent = new Intent(Activity11.this, Activity11_add.class);
			startActivity(intent);
		}
	}

	// 实现动作接口，修改词条信息
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

	// 实现抽象接口，即删除功能
	private class DeleteAction implements Action {
		private int position;

		DeleteAction(int position) {
			this.position = position;
		}

		public void execute() {
			Builder builder = new AlertDialog.Builder(Activity11.this);
			builder.setTitle("确定删除");
			builder.setMessage("真的删除吗？");
			builder.setNegativeButton("取消", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			builder.setPositiveButton("确定", new OnClickListener() {

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

	// 查找所有元素的游标集合；
	private Cursor FindAllElements() {
		SQLiteDatabase db = DB.getReadableDatabase();
		Cursor cursor1 = db.rawQuery(
				"select * from dictionary order by keywords", null);
		return cursor1;
	}

	// 查找部分元素的游标
	private Cursor FindElements(String input_info) {
		// 模糊查询
		String Sql_sentence = "select * from dictionary where keywords like '"
				+ input_info + "%' order by keywords";
		SQLiteDatabase db = DB.getReadableDatabase();
		Cursor cursor2 = db.rawQuery(Sql_sentence, null);
		return cursor2;
	}

	// 删除某条记录
	private void DeleteElement(int id) {
		SQLiteDatabase db = DB.getReadableDatabase();
		db.execSQL("delete from dictionary where _id = " + id);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 退出程序时，关闭MyDatabaseHelper里的SQLiteDatebase
		if (DB != null) {
			DB.close();
		}
	}

}
