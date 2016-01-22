package com.vdolrm.lrmlibrary;

import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * 如需使用屏蔽外围点击和返回键的activity可以使用这个，假如是对话框样式则需要在manifest中写明theme为customdialog样式*/
public abstract class BaseDialogOutSideCannotBeClickActivity extends BaseActivity { 

	
	
	
	@Override 
	public boolean onTouchEvent(MotionEvent event){ //屏蔽外围点击~
	 
		return true; 
	 
	} 
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {//屏蔽返回键~
		
			if(keyCode == KeyEvent.KEYCODE_BACK){
				return false;
			}
		
		return super.onKeyDown(keyCode, event);
	}

}
