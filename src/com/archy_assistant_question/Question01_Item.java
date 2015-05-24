package com.archy_assistant_question;

import java.util.ArrayList;

public class Question01_Item {
	// 问卷调查的题目
	public String title;
	// 参与问题调查的总人数；
	private int total_number = 0;
	// 标题栏之下新闻内容ID
	private int itemId;
	// 问题
	private String voteQuestion;
	// 问题所对应的答案
	private ArrayList<String> voteAnswers;
	// 每套题总共多少问题
	public int question_number = 0;
	// 每个答案所对应的个数；
	private int[] count;
	// 试卷类型；
	private int type;

	public Question01_Item() {
		voteAnswers = new ArrayList<String>();
		// TODO Auto-generated constructor stub
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getVoteQuestion() {
		return voteQuestion;
	}

	public void setVoteQuestion(String voteQuestion) {
		this.voteQuestion = voteQuestion;
	}

	public ArrayList<String> getVoteAnswers() {
		return voteAnswers;
	}

	public void setVoteAnswers(ArrayList<String> voteAnswers) {
		this.voteAnswers = voteAnswers;
	}

	public void initCount(int length) {
		count = new int[length];
	}

	public void setCountNext(int choice) {
		count[choice]++;
	}

	public int[] getCount() {
		return count;
	}

	public void setTotalNumber() {
		total_number++;
	}

	public int getTotalNumber() {
		return total_number;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setQuestionNumber(int i) {
		question_number = question_number + i;
	}

	public int getQuestionNumber() {
		return question_number;
	}

}
