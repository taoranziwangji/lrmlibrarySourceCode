package com.vdolrm.lrmlibrary.test;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.vdolrm.lrmlibrary.BaseApplication;
import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.fragmentactivity.BaseGeneralFragmentActivity;
import com.vdolrm.lrmlibrary.fragmentactivity.BasePageIndicatorFragmentActivity;
import viewpagerindicator.CirclePageIndicator;
import viewpagerindicator.PageIndicator;

public class TestPageIndicatorFragmentActivity extends BasePageIndicatorFragmentActivity {
	
	private ViewPager mPager;
    private CirclePageIndicator mIndicator;
    
    private List<String> list_txt = new ArrayList<String>();
	private List<Integer> list_pic = new ArrayList<Integer>();

	/*@Override
	public void addActivity() {
		// TODO Auto-generated method stub
		BaseApplication.addActivity(this);
	}*/

	@Override
	public void init() {
		// TODO Auto-generated method stub
		 mPager = (ViewPager)findViewById(R.id.pager);
	     mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
	    // mIndicator.setBackgroundColor(Color.parseColor("#000000"));//indicator所占的一横条的背景色
	     mIndicator.setStrokeColor(Color.parseColor("#ff0000"));//空心圈的颜色
	     mIndicator.setFillColor(Color.parseColor("#00ff00"));//实心小圆点的颜色
	   //  mIndicator.setPageColor(Color.parseColor("#0000ff"));//空心圈的内芯颜色
	   //  mIndicator.setStrokeWidth(2);//空心圈的圆的横截宽度
	   //  mIndicator.setRadius(5);//空心圆的半径
	   //  mIndicator.setSnap(true);//实心圈跳的时候的动画
	     mIndicator.setCentered(true);//好像没效果
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		 setContentView(R.layout.vdo_test_pageindicatorfragmentactivity);
	}

	@Override
	public void initActionBar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		
		for(int i=0;i<5;i++){
			list_txt.add("title"+i);
			//list_pic.add(R.drawable.ic_launcher);
		}
		
		super.initEvent();
	}

	@Override
	public ViewPager getViewPager() {
		// TODO Auto-generated method stub
		return mPager;
	}

	@Override
	public PageIndicator getPageIndicator() {
		// TODO Auto-generated method stub
		return mIndicator;
	}

	@Override
	public List<String> getListTitle() {
		// TODO Auto-generated method stub
		return list_txt;
	}

	@Override
	public List<Integer> getListPic() {
		// TODO Auto-generated method stub
		return list_pic;
	}

	@Override
	public void bundleInOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}

	

	

}
