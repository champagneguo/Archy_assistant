package com.archy_assistant_question;

import java.util.ArrayList;
import android.app.Application;

public class ConstantArrayList extends Application {
	private ArrayList<ArrayList<Question01_Item>> DataItems;

	public ConstantArrayList() {
		DataItems = new ArrayList<ArrayList<Question01_Item>>();
	}

	public void setDataItems(ArrayList<ArrayList<Question01_Item>> DataItems) {
		this.DataItems = DataItems;
	}

	public ArrayList<ArrayList<Question01_Item>> getDataItems() {
		return DataItems;
	}

}
