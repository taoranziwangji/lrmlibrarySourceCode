package com.vdolrm.lrmlibrary;

import android.view.WindowManager;

/**
 * 用于带输入框edittext的界面的基类，防止edittext把上面标题栏顶上去的问题 带finish动画*/
public abstract class BaseInputEditActivity extends BaseGeneralActivity{
	
	public abstract void init2();
	

	@Override
	public final void init() {
		// TODO Auto-generated method stub
		//解决底部edittext把上面的标题顶上去的问题，不加这句如果中间不是scrollview而是listview则没有问题，但如果是scrollview则必须加上这句
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
		                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
				
		init2();
	}

	
	

}
