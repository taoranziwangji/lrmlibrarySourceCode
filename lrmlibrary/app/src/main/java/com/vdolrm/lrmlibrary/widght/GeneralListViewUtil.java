package com.vdolrm.lrmlibrary.widght;

import java.util.List;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.vdolrm.lrmlibrary.net.NetCheckUtil;
import com.vdolrm.lrmlibrary.widght.vdoCustomListView.OnLoadMoreListener;

/**
 * 通用listview的工具类，支持下拉刷新和加载更多
 * */
public abstract class GeneralListViewUtil implements OnLoadMoreListener{
	
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private ListView listview;
	private Context context;
	public abstract void netError();
	public abstract void refresh();

	/**
	 * 
	 * @param context
	 * @param listview
	 * @param mSwipeRefreshLayout
	 * @param list
	 * @param adapter
	 * @param color 下拉刷新时swipe的颜色 为int数组 默认为黑色
	 */
	public <T>GeneralListViewUtil(final Context context,final ListView listview,final SwipeRefreshLayout mSwipeRefreshLayout,List<T> list,final BaseAdapter adapter,final int...color){
		
		this.context = context;
		this.mSwipeRefreshLayout = mSwipeRefreshLayout;
		this.listview = listview;
		
		if(color.length>0 && mSwipeRefreshLayout!=null){
			mSwipeRefreshLayout.setColorSchemeResources(color);
		}
		
	
	}
	
	
	/**
	 * 开启swiperefreshlayout和listview的滚动监听方法*/
	public void listen(){
		if(mSwipeRefreshLayout!=null && context!=null){
			mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
	 			@Override
	 			public void onRefresh() {
	 				if(NetCheckUtil.isNetworkConnected(context)){
	 					//page = 0;
	 	 				
	 	 				refresh();
	 				}else{
	 					netError();
	 				}
	 			}
	 		});
		}
 		
		if(listview!=null){
	 		//防止listview不在顶部的时候mSwipeRefreshLayout就能刷新 而listview无法滚动
		 	listview.setOnScrollListener(new AbsListView.OnScrollListener() {
		            @Override
		             public void onScrollStateChanged(AbsListView absListView, int i) {
		 
		        }
		 
		            @Override
		             public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		            	if(mSwipeRefreshLayout!=null){
			            	if (firstVisibleItem == 0){
			                	mSwipeRefreshLayout.setEnabled(true);
			                }else{
			                	mSwipeRefreshLayout.setEnabled(false);
			                }
		            	}
		        }
		    });
		}
	}
	
	@Override
	public final void onCustomListViewScroll(int firstVisibleItem) {
		// TODO Auto-generated method stub
		if(mSwipeRefreshLayout!=null){
         	if (firstVisibleItem == 0){
             	mSwipeRefreshLayout.setEnabled(true);
         	} else{
             	mSwipeRefreshLayout.setEnabled(false);
             }
             }
	}

	
}
