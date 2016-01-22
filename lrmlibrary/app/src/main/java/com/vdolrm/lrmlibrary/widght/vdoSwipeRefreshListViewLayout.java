package com.vdolrm.lrmlibrary.widght;

import com.vdolrm.lrmlibrary.utils.VersionUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

public class vdoSwipeRefreshListViewLayout extends android.support.v4.widget.SwipeRefreshLayout{

	//private Context context;
	
	private vdoCustomListView cListView;


	public vdoSwipeRefreshListViewLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		init(context);
	}

	public vdoSwipeRefreshListViewLayout(Context context) {
		super(context);
		
		init(context);
	}

	
	@SuppressLint("NewApi")
	private void init(Context context) {
		// TODO Auto-generated method stub
		
		if(cListView==null){
			cListView = new vdoCustomListView(context);
			cListView.setScrollBarStyle(SCROLLBARS_OUTSIDE_OVERLAY);
			
			cListView.setCacheColorHint(android.R.color.transparent);
			cListView.setSelector(android.R.color.transparent);
			cListView.setDivider(new ColorDrawable(android.R.color.transparent));
			cListView.setDividerHeight(0);
			if(VersionUtil.getAndroidVerson()>8){
				cListView.setOverScrollMode(OVER_SCROLL_IF_CONTENT_SCROLLS);
			}
		}
		
		if(getChildCount()==0){
			addView(cListView);
		}
	}
	
	
	public ListView getListView(){
		return cListView;
	}
	
}
