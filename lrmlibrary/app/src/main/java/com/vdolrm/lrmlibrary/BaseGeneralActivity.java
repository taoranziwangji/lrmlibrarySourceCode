package com.vdolrm.lrmlibrary;

import android.view.KeyEvent;

import com.vdolrm.lrmlibrary.utils.UIUtils;
import com.vdolrm.lrmlibrary.utils.VersionUtil;

/**一般的activity基类，有finish动画*/
public abstract class BaseGeneralActivity extends BaseActivity {

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    switch (keyCode) {
	        case KeyEvent.KEYCODE_BACK:
	        	/*this.finish();
	        	if(VersionUtil.getAndroidVerson() > 5) {//在子线程中不会出现动画效果
					UIUtils.runInMainThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							overridePendingTransition(com.vdolrm.lrmlibrary.R.anim.vdo_in_from_left, com.vdolrm.lrmlibrary.R.anim.vdo_out_to_right);
						}
					});
				}*/
				finishAndAnimation();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

	public void finishAndAnimation(){
		this.finish();
		if(VersionUtil.getAndroidVerson() > 5) {//在子线程中不会出现动画效果
			UIUtils.runInMainThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					overridePendingTransition(R.anim.vdo_in_from_left, R.anim.vdo_out_to_right);
				}
			});
		}
	}
}
