package com.vdolrm.lrmlibrary.test;

import java.util.Arrays;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.fragmentactivity.BaseGeneralFragmentActivity;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.widght.PagerSlidingTabStrip;

/**测试标签在底部时的测试类，有两种方式：1为下边写成linearlayout的方式 与viewpager绑定；
2为仿照TestTabFragmentActivity的方式使用PagerSlidingTabStrip 只是放在了底部*/
public class TestMainFragmentActivity extends BaseGeneralFragmentActivity {
	

	//private PagerSlidingTabStrip mPageTabs;//method2

	private static final int sizeFragment = 3;

	

	@Override
	public void bundleInOnCreate(Bundle savedInstanceState) {
	}

	@Override
	public void initView() {
		setContentView(R.layout.vdo_test_mainfragmentactivity);
	}

	@Override
	public void initActionBar() {
	}

	@Override
	public void init() {
		mViewPager = (ViewPager)findViewById(R.id.id_viewpager);
		
		//mPageTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);//method2
		
		findViewById(R.id.lin_tabbottom1).setOnClickListener(new MyOnClickListener(0));
		findViewById(R.id.lin_tabbottom2).setOnClickListener(new MyOnClickListener(1));
		findViewById(R.id.lin_tabbottom3).setOnClickListener(new MyOnClickListener(2));
	}
	
	
	@Override
	public void initEvent() {
		super.initEvent();
		//mPageTabs.setViewPager(mViewPager);//method2
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				MyLog.d("click "+position);
				resetTabBtn();
				switch (position){
				case 0:
					//((ImageButton) findViewById(R.id.btn_tab_bottom_weixin)).setImageResource(R.drawable.tab_dayi_pressed);
					((TextView)findViewById(R.id.tv_tabbottom1)).setTextColor(Color.parseColor("#ff0000"));
					break;
				case 1:
					//((ImageButton) findViewById(R.id.btn_tab_bottom_friend)).setImageResource(R.drawable.tab_pk_pressed);
					((TextView)findViewById(R.id.tv_tabbottom2)).setTextColor(Color.parseColor("#ff0000"));
					break;
				case 2:
					//((ImageButton) findViewById(R.id.btn_tab_bottom_contact)).setImageResource(R.drawable.tab_jizha_pressed);
					((TextView)findViewById(R.id.tv_tabbottom3)).setTextColor(Color.parseColor("#ff0000"));
					break;
				
				}
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
	
	
	protected void resetTabBtn(){
		
//		((ImageButton) findViewById(R.id.btn_tab_bottom_weixin)).setImageResource(R.drawable.tab_dayi_normal);
//		((ImageButton) findViewById(R.id.btn_tab_bottom_friend)).setImageResource(R.drawable.tab_pk_normal);
//		((ImageButton) findViewById(R.id.btn_tab_bottom_contact)).setImageResource(R.drawable.tab_jizha_normal);
//		((ImageButton) findViewById(R.id.btn_tab_bottom_setting)).setImageResource(R.drawable.tab_mine_normal);
		
		((TextView)findViewById(R.id.tv_tabbottom1)).setTextColor(Color.parseColor("#000000"));
		((TextView)findViewById(R.id.tv_tabbottom2)).setTextColor(Color.parseColor("#000000"));
		((TextView)findViewById(R.id.tv_tabbottom3)).setTextColor(Color.parseColor("#000000"));
		
	}

	@Override
	public int onScreenPageLimit() {
		return sizeFragment;
	}

	
	 /**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}
		@Override
		public void onClick(View v) {
			
			mViewPager.setCurrentItem(index);
		}
	}


	@Override
	public int onWhichAdapter() {//method2
		return ADAPTER_DEFAULT;//假如用第一种方法时 这里写ADAPTER_DEFAULT,method2的时候再根据需要写ADAPTER_TXT还是ADAPTER_ICON
	}

	@Override
	public List<Fragment> getList_Fragment(List<Fragment> list_mFragments) {
		for(int i=0;i<sizeFragment;i++){
			MyLog.d("添加fragment "+i);
			list_mFragments.add(TestFragment.newInstance("content"+i));
		}
		return list_mFragments;
	}

	@Override
	public List<String> getList_TabTitle(List<String> list_pageTitle_name) {//method2 
		return null;
		
	}

	@Override
	public List<Integer> getList_TabIcon(List<Integer> list_pageTitle_icon) {//method2
		return null;
	}

	/*@Override
	public Fragment fragmentPagerAdapter_getItem(int position) {
		// TODO Auto-generated method stub
		//return BaseFragment.newInstance("content"+position);
		return TestFragmentFactory.createFragment(TestFragmentFactory.TAB_MAIN, position, "content"+position);
	}*/
	
	
}
