package com.vdolrm.lrmlibrary.fragmentactivity;

import android.view.KeyEvent;

import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.R.anim;
import com.vdolrm.lrmlibrary.animation.ActivityAnimationUtil;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.utils.UIUtils;
import com.vdolrm.lrmlibrary.utils.VersionUtil;

/**一般的Fragmentactivity基类，有finish动画*/
public abstract class BaseGeneralFragmentActivity extends BaseMainFragmentActivity {

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    switch (keyCode) {
	        case KeyEvent.KEYCODE_BACK:
	        	this.finish();
				ActivityAnimationUtil.ActivityFinish(this);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}


	public void finishAndAnimation(){
		MyLog.d("basegeneralFragmentActivity finishAndAnimation");
		this.finish();
		ActivityAnimationUtil.ActivityFinish(this);
	}

}
