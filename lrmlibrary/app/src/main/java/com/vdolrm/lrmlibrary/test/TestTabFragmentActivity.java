package com.vdolrm.lrmlibrary.test;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.fragmentactivity.BaseGeneralFragmentActivity;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.widght.PagerSlidingTabStrip;
/**测试fragmentactivity
 * 标签在上方 
 * 可实现标签为纯文字或纯图片，只需更改onWhichAdapter方法中的返回值即可。
 * 既有文字又有图片的请参考TestMainFragmentActivity 用Linearlayout实现
 * */
public class TestTabFragmentActivity extends BaseGeneralFragmentActivity {

	private PagerSlidingTabStrip mPageTabs;
	private static final String[] tabsName = {"东风标致508.0","卡宴.1","雷克萨斯ES.2","歌诗图.3","马自达昂科塞拉.4","雅阁.5","奔驰GLA.6","福克斯.7"};

	@Override
	public void init() {
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mPageTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
	}

	
	@Override
	public void initView() {
		setContentView(R.layout.vdo_test_pagertab);
	}

	
	@Override
	public void initEvent() {
		super.initEvent();
		mPageTabs.setViewPager(mViewPager);//需要在viewpager设置完适配器之后添加
		
		mPageTabs.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				MyLog.d("tabs pageSelected"+arg0);//do
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
	}
	
	
	/*@Override
	public void onFragmentPageSelected(int position) {
		MyLog.d("fragment pageSelected"+position);//no do
	}*/

	@Override
	public int onScreenPageLimit() {
		//return tabsName.length;
		return 1;
	}

	@Override
	public void bundleInOnCreate(Bundle savedInstanceState) {
	}

	@Override
	public void initActionBar() {
	}

	@Override
	public int onWhichAdapter() {
		//return ADAPTER_ICON;
		return ADAPTER_TXT;
	}

	@Override
	public List<Fragment> getList_Fragment(List<Fragment> list_mFragments) {
		for(int i=0;i<tabsName.length;i++){
			list_mFragments.add(TestFragment.newInstance("content"+i));
		}
		return list_mFragments;
	}

	@Override
	public List<String> getList_TabTitle(List<String> list_pageTitle_name) {
		//List<String> list_pageTitle_name2 = new ArrayList<String>();
		list_pageTitle_name = Arrays.asList(tabsName);
		//list_pageTitle_name.addAll(Arrays.asList(tabsName));
		return list_pageTitle_name;
	}

	@Override
	public List<Integer> getList_TabIcon(List<Integer> list_pageTitle_icon) {
		for(int i=0;i<tabsName.length;i++){
			list_pageTitle_icon.add(R.drawable.ic_launcher);
		}
		return list_pageTitle_icon;
	}


	



}
