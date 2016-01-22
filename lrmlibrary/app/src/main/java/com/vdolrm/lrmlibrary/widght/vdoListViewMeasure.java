package com.vdolrm.lrmlibrary.widght;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class vdoListViewMeasure extends ListView {

	public vdoListViewMeasure(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public vdoListViewMeasure(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public vdoListViewMeasure(Context context) {
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
