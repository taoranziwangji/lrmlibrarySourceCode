package com.vdolrm.lrmlibrary.listener;

import com.vdolrm.lrmlibrary.adapter.BaseMyAdapter;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;


/**
 * @author Administrator
 *自定义的onScrollListener 可以实现listview滚动时不加载
 * @param 需传入BaseMyAdapter的子类（里面有setInit方法）
 */
public class LVScrollNoLoadListener<T> implements OnScrollListener{
	private BaseMyAdapter<T> adapter;
	
	public LVScrollNoLoadListener(BaseMyAdapter<T> adapter){
		this.adapter = adapter;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		adapter.setScrolling(true);
        switch (scrollState) {
        case OnScrollListener.SCROLL_STATE_IDLE:// 滑动停止
           
        	adapter.setScrolling(false);
           
            break;
        case OnScrollListener.SCROLL_STATE_FLING://滚动做出了抛的动作 即滚一下松手
        	break;
        case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://正在滚动
        	break;
        default:
            break;
        }
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}

}
