package com.vdolrm.lrmlibrary.widght.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MainViewPager extends ViewPager {

	public MainViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public MainViewPager(Context context) {
		super(context);
		
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		return false;
	}

}
