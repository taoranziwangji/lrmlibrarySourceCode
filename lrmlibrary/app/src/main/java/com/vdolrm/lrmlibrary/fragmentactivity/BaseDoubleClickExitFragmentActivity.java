package com.vdolrm.lrmlibrary.fragmentactivity;

import com.vdolrm.lrmlibrary.BaseApplication;
import com.vdolrm.lrmlibrary.utils.UIUtils;

import android.view.KeyEvent;
/**双击返回键退出的基类fragmentactivity,不带finish动画*/
public abstract class BaseDoubleClickExitFragmentActivity extends BaseMainFragmentActivity {

	private long i_time = 0;
	private int TIMESPACE = 2000;//时间间隔
	private String diaplay = "再按一次退出程序";

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			
			if (System.currentTimeMillis() - i_time > TIMESPACE) {
				
				UIUtils.showToastSafe(diaplay);
				i_time = System.currentTimeMillis();
				return true; // 如果只有这句话 则是让返回键无效，
			} else {
				//BaseApplication.exit(this);
				BaseApplication.getInstance().exit(this);
				return super.onKeyDown(keyCode, event);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	
	
	public int getTIMESPACE() {
		return TIMESPACE;
	}

	public void setTIMESPACE(int tIME) {
		TIMESPACE = tIME;
	}



	public String getDiaplay() {
		return diaplay;
	}

	public void setDiaplay(String diaplay) {
		this.diaplay = diaplay;
	}
	
	

	
}
