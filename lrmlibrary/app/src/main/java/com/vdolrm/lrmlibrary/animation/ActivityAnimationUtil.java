package com.vdolrm.lrmlibrary.animation;

import android.app.Activity;

import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.utils.UIUtils;
import com.vdolrm.lrmlibrary.utils.VersionUtil;

public class ActivityAnimationUtil {

	private ActivityAnimationUtil() {
		//throw new Error("请不要实例化我！");
	}
	
	public static void ActivityFinish(final Activity context){
		MyLog.d("finish from left");
		UIUtils.runInMainThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(VersionUtil.getAndroidVerson() >= 5 && context!=null) {// && context.isFinishing()==false
					context.overridePendingTransition(R.anim.vdo_in_from_left, R.anim.vdo_out_to_right);
				}
			}
		});
	}
	
	public static void ActivityCreate(final Activity context){
		UIUtils.runInMainThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(VersionUtil.getAndroidVerson() >= 5 && context!=null && context.isFinishing()==false) {
					context.overridePendingTransition(R.anim.vdo_in_from_right, R.anim.vdo_out_to_left);
				}
			}
		});
	}



	public static void ActivityFinishFromTop(final Activity context){
		MyLog.d("finish from top");
		UIUtils.runInMainThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				MyLog.d("finish from top 要执行动画 context="+context);
				if(VersionUtil.getAndroidVerson() >= 5 && context!=null) {// && context.isFinishing()==false
					MyLog.d("finish from top 执行动画");
					context.overridePendingTransition(R.anim.vdo_in_from_top, R.anim.vdo_out_to_bottom);
				}
			}
		});
	}

	public static void ActivityCreateFromBottom(final Activity context){
		UIUtils.runInMainThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(VersionUtil.getAndroidVerson() >= 5 && context!=null && context.isFinishing()==false) {
					context.overridePendingTransition(R.anim.vdo_in_from_bottom, R.anim.vdo_out_to_top);
				}
			}
		});
	}
}
