package com.vdolrm.lrmlibrary.test;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

import com.vdolrm.lrmlibrary.BaseGeneralActivity;
import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.adapter.BaseExpandableAdapter;
import com.vdolrm.lrmlibrary.adapter.BaseViewHolder;
import com.vdolrm.lrmlibrary.utils.UIUtils;

/**简单的expandablelistview，未实现滑动时父标题固定在首行功能；
 * 高级的expandablelistview请参考：https://github.com/singwhatiwanna/PinnedHeaderExpandableListView*/
public class TestExpandableLVActivity extends BaseGeneralActivity{

	private ExpandableListView expandablelistview;
	private  String[] group = new String[] { "水浒传", "三国演义","隋唐英雄", "奥特曼","古惑仔", "喜洋洋"}; 
	private  String[][] child = new String[][] {//假如某一项特别多 点击时父标题会自动滑到首行
	        { "宋江","卢俊义","吴用","关胜","林冲","武松","鲁智深","公孙胜","孙二娘","时迁","阮小二","杨志"}, 
	        {"诸葛亮","刘备","张飞","关羽","赵云","马超","黄忠","荀彧"}, 
			{"单雄信","李密","王世充","翟让","罗成","王伯当","秦叔宝","李元霸","语文成都","程咬金","裴元庆"}, 
			{"泰罗","赛文","杰克","艾斯"}, 
			{"山鸡","陈浩南","包皮"}, 
			{"喜洋洋","美羊羊"}}; 
	
	@Override
	public void bundleInOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.vdo_test_expandablelistview);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		expandablelistview = (ExpandableListView)findViewById(R.id.lv_expandable);
	}


	@Override
	public void initActionBar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		expandablelistview.setGroupIndicator(null);//去掉默认的箭头
		//expandablelistview.setChildDivider(getResources().getDrawable(R.drawable.dayi_adapter_item_line_heng));
		ExpandableListAdapter adapter=new TestExpandableLVAdapter(this, group, child, R.layout.vdo_expandablelistview_child_item, R.layout.vdo_expandablelistview_group_item);
        expandablelistview.setAdapter(adapter);
        //分组展开
        expandablelistview.setOnGroupExpandListener(new OnGroupExpandListener(){
			public void onGroupExpand(int groupPosition) {
			}
        });
        //分组关闭
        expandablelistview.setOnGroupCollapseListener(new OnGroupCollapseListener(){
			public void onGroupCollapse(int groupPosition) {
			}
        });
        //子项单击
        expandablelistview.setOnChildClickListener(new OnChildClickListener(){
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int groupPosition, int childPosition, long arg4) {
				/*Toast.makeText(PersonalActivity.this, 
						group[groupPosition]+" : "+buddy[groupPosition][childPosition], 
						Toast.LENGTH_SHORT).show();*/
				
				
				UIUtils.showToastSafe(group[groupPosition]+" : "+child[groupPosition][childPosition]);
				
				return false;
			}

		
        });
	}
	
	
	
	class TestExpandableLVAdapter extends BaseExpandableAdapter<String>{
		
		private Drawable drawable_up,drawable_down;

		public TestExpandableLVAdapter(Context context, String[] group,
				String[][] child, int layoutId_child, int layoutId_parent) {
			super(context, group, child, layoutId_child, layoutId_parent);
			// TODO Auto-generated constructor stub
			
			drawable_up=context.getResources().getDrawable(R.drawable.vdo_jiantou_up);
			drawable_down=context.getResources().getDrawable(R.drawable.vdo_jiantou_down);
		}

		@Override
		public void convertChild(BaseViewHolder vh, String item,
				int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			
			TextView tv = (TextView)vh.getView(R.id.buddy_listview_child_nick);
			ImageView avatar = (ImageView)vh.getView(R.id.buddy_listview_child_avatar);
			
			tv.setText(item);
			//avatar.setImageResource(image_buddy[groupPosition][childPosition]);
		}

		@Override
		public void convertParent(BaseViewHolder vh, String string,
				int groupPosition, boolean isExpanded) {
			// TODO Auto-generated method stub
			
			 TextView groupNameTextView=(TextView) vh.getView(R.id.buddy_listview_group_name);	
			 groupNameTextView.setText(string);
			 
			// drawable_wodelianxi.setBounds(0, 0, drawable_wodelianxi.getMinimumWidth(), drawable_wodelianxi.getMinimumHeight());
			// drawable_wochudeti.setBounds(0, 0, drawable_wochudeti.getMinimumWidth(), drawable_wochudeti.getMinimumHeight());
				
			
				
				//drawable_down.setBounds(0, 0, drawable_down.getMinimumWidth(), drawable_down.getMinimumHeight());//调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
				groupNameTextView.setCompoundDrawables(null, null,compoundDrawables(drawable_down),null);
				
				//viewHolder.image.setImageResource(R.drawable.group_unfold_arrow);
				//更换展开分组图片
				if(!isExpanded){
				//	viewHolder.image.setImageResource(R.drawable.group_fold_arrow);
					//drawable_up.setBounds(0, 0, drawable_up.getMinimumWidth(), drawable_up.getMinimumHeight());//调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
					groupNameTextView.setCompoundDrawables(null, null,compoundDrawables(drawable_up), null);
				}
				
		}
		
		
		private Drawable compoundDrawables(Drawable d){
			if(d==null){
				return null;
			}
			d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
			return d;
		}
		
	}

}
