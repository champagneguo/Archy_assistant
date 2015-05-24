package com.archy_assistant_question;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

//ÖØÐ´ViewPager½ûÖ¹Æä×óÓÒ»¬¶¯£»

public class Question01_Viewpager extends ViewPager {

	private boolean isScrollable = false;

	public boolean getScrollable() {
		return isScrollable;
	}

	public void setScrollable(boolean isScrollable) {
		this.isScrollable = isScrollable;
	}

	public Question01_Viewpager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Question01_Viewpager(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (!isScrollable)
			return false;
		return super.onTouchEvent(arg0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (!isScrollable)
			return false;
		return super.onInterceptTouchEvent(arg0);
	}

}
