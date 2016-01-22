package com.vdolrm.lrmlibrary.test;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.vdolrm.lrmlibrary.BaseGeneralActivity;
import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.adapter.BaseExpandableAdapter;
import com.vdolrm.lrmlibrary.adapter.BaseViewHolder;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.utils.UIUtils;
import com.vdolrm.lrmlibrary.widght.vdoPinnedHeaderExpandableListView;
import com.vdolrm.lrmlibrary.widght.vdoPinnedHeaderExpandableListView.OnHeaderUpdateListener;

/**简单的expandablelistview，未实现滑动时父标题固定在首行功能；
 * 高级的expandablelistview请参考：https://github.com/singwhatiwanna/PinnedHeaderExpandableListView*/
public class TestHoverExpandableLVActivity extends BaseGeneralActivity implements
ExpandableListView.OnGroupClickListener,
OnHeaderUpdateListener{

	private vdoPinnedHeaderExpandableListView expandablelistview;
	private  String[] group = new String[] { "水浒传", "三国演义","隋唐英雄", "奥特曼","古惑仔", "喜洋洋"}; 
	private  String[][] child = new String[][] {
	        { "宋江","卢俊义","吴用","关胜","林冲","武松","鲁智深","公孙胜","孙二娘","时迁","阮小二","杨志"}, 
	        {"诸葛亮","刘备","张飞","关羽","赵云","马超","黄忠","荀彧"}, 
			{"单雄信","李密","王世充","翟让","罗成","王伯当","秦叔宝","李元霸","语文成都","程咬金","裴元庆"}, 
			{"泰罗","赛文","杰克","艾斯"}, 
			{"山鸡","陈浩南","包皮"}, 
			{"喜洋洋","美羊羊"}};
	private ExpandableListAdapter adapter; 
	
	@Override
	public void bundleInOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.vdo_test_expandablelistview_hover);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		expandablelistview = (vdoPinnedHeaderExpandableListView)findViewById(R.id.lv_expandable);
		
		
	}


	@Override
	public void initActionBar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		expandablelistview.setGroupIndicator(null);//去掉默认的箭头
		adapter = new TestExpandableLVAdapter(this, group, child, 
				R.layout.vdo_hoverexpandablelistview_child, R.layout.vdo_hoverexpandablelistview_group);
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
        
     // 展开所有group
        for (int i = 0, count = expandablelistview.getCount(); i < count; i++) {
        	expandablelistview.expandGroup(i);
        }
        expandablelistview.setOnHeaderUpdateListener(this);
		expandablelistview.setOnGroupClickListener(this);
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
			
			TextView tv = (TextView)vh.getView(R.id.name);
			ImageView avatar = (ImageView)vh.getView(R.id.buddy_listview_child_avatar);
			
			tv.setText(item);
			//avatar.setImageResource(image_buddy[groupPosition][childPosition]);
		}

		@Override
		public void convertParent(BaseViewHolder vh, String string,
				int groupPosition, boolean isExpanded) {
			// TODO Auto-generated method stub
			
			 TextView groupNameTextView=(TextView) vh.getView(R.id.group);	
			 groupNameTextView.setText(string);
			
			 //groupNameTextView.setCompoundDrawables(null, null,compoundDrawables(drawable_down),null);
				
				//更换展开分组图片
				if(!isExpanded){
					//groupNameTextView.setCompoundDrawables(null, null,compoundDrawables(drawable_up), null);
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



	@Override
    public View getPinnedHeader() {
		MyLog.d("getPinnedHeader");
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.vdo_hoverexpandablelistview_group, null);
        headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
    	MyLog.d("updatePinnedHeader firstVisibleGroupPos="+firstVisibleGroupPos);
        String firstVisibleGroup = (String)adapter.getGroup(firstVisibleGroupPos);
        TextView textView = (TextView) headerView.findViewById(R.id.group);
        textView.setText(firstVisibleGroup);
    }

   

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
