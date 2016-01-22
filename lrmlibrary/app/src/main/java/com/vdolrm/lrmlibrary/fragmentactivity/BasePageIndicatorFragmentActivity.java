package com.vdolrm.lrmlibrary.fragmentactivity;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.vdolrm.lrmlibrary.fragment.BasePageIndicatorFragmentAdapter;
import com.vdolrm.lrmlibrary.utils.UIUtils;
import viewpagerindicator.PageIndicator;

/**实现引导页下面的圆圈跳转*/
public abstract class BasePageIndicatorFragmentActivity extends BaseFragmentActivity {
	
	private BasePageIndicatorFragmentAdapter mAdapter;
	
	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		 mAdapter = new BasePageIndicatorFragmentAdapter(getSupportFragmentManager(),getListTitle(),getListPic());

	     getViewPager().setAdapter(mAdapter);
	     getPageIndicator().setViewPager(getViewPager());
	}
	
	
		/**获取viewpager*/
		public abstract ViewPager getViewPager();
		/**获取PageIndicator*/
		public abstract PageIndicator getPageIndicator();
		/**获取标签字符串*/
		public abstract List<String> getListTitle();
		/**获取标签图片*/
		public abstract List<Integer> getListPic();

	
}
