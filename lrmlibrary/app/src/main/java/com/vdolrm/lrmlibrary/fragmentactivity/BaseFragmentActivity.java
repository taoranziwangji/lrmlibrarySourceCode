package com.vdolrm.lrmlibrary.fragmentactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.vdolrm.lrmlibrary.BaseActivity;
import com.vdolrm.lrmlibrary.BaseApplication;
import com.vdolrm.lrmlibrary.animation.ActivityAnimationUtil;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.utils.UIUtils;

public abstract class BaseFragmentActivity extends FragmentActivity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		bundleInOnCreate(savedInstanceState);
		windowFeature();
		MyLog.d("father fragmentactivity onCreate");
		//addActivity();
		BaseApplication.addActivity(this);
		initView();
		init();
		initActionBar();
		initEvent();
		MyLog.d("father fragmentactivity onCreate over");
		
		
	}

		//更改窗口样式 可以被重写
		public void windowFeature(){
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}

		//public abstract void addActivity();
		
		public  void bundleInOnCreate(Bundle savedInstanceState){};
		
		public abstract void init();

		public abstract void initView();

	public void initActionBar() {

	}

	public abstract void initEvent();
		
		

	@Override
	protected void onResume() {
		BaseActivity.mForegroundActivity = this;
		super.onResume();
	}

	@Override
	protected void onPause() {
		BaseActivity.mForegroundActivity = null;
		super.onPause();
	}
	
	public void toastNetError(){
		UIUtils.showToastSafe("网络不给力，请稍后重试");
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		ActivityAnimationUtil.ActivityCreate(this);
	}
}
