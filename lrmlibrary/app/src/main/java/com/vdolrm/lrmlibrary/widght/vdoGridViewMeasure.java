package com.vdolrm.lrmlibrary.widght;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class vdoGridViewMeasure extends GridView {

	public vdoGridViewMeasure(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public vdoGridViewMeasure(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public vdoGridViewMeasure(Context context) {
		super(context);
	}

	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		try{
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
		}catch(RuntimeException e){
			e.printStackTrace();
		}

	}

}
