package com.vdolrm.lrmlibrary.test;

import java.util.ArrayList;
import java.util.List;

import android.widget.ListView;

import com.vdolrm.lrmlibrary.BaseGeneralActivity;
import com.vdolrm.lrmlibrary.R;

public class TestListViewStateActivity extends BaseGeneralActivity{
	private ListView lv;
	//private TestListViewStateAdapter adapter;
	private TestListViewStateAdapterClassial3 adapter;
	private List<TestListViewStateBean> list = new ArrayList<TestListViewStateBean>();

	@Override
	public void initView() {
		setContentView(R.layout.vdo_test_listviewstateactivity);
	}

	@Override
	public void init() {
		lv = (ListView)findViewById(R.id.listview);
	}

	@Override
	public void initEvent() {
		list.clear();
		for(int i=1;i<=20;i++){
			TestListViewStateBean bean = new TestListViewStateBean();
			if(i%2!=0){
				bean.setName("奇数name"+i);
				//bean.setShowed(true);
			}else{
				bean.setName("偶数name"+i);
				//bean.setShowed(false);
			}
			
			list.add(bean);
		}
		
		//adapter = new TestListViewStateAdapter(this, list, R.layout.vdo_test_listviewstate_item);
		adapter = new TestListViewStateAdapterClassial3(this, list);
		lv.setAdapter(adapter);
	}

	@Override
	protected void onDestroy() {
		adapter.onDestroy();
		super.onDestroy();
	}
	
	
	

}
