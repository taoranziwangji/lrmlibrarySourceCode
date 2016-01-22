package com.vdolrm.lrmlibrary.test;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.utils.UIUtils;

/**跟TestListViewStateAdapterClassial1差不多 只是把onclicklistener独立出来了*/
public class TestListViewStateAdapterClassial3 extends BaseAdapter{
	private Context context;
	private List<TestListViewStateBean> list;
	private TestListViewStateAdapterClassial3 adapter;
	public TestListViewStateAdapterClassial3(Context ctx,List<TestListViewStateBean> lists) {
		context = ctx;
		list = lists;
		adapter = this;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public TestListViewStateBean getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TestListViewStateBean item = list.get(position);
		
		ViewHolder holder = null;
		TestListViewStateOnClickListener listener = null;
		if(convertView==null){
			holder= new ViewHolder();
			convertView = UIUtils.inflate(R.layout.vdo_test_listviewstate_item);
			holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
			holder.imv_state = (ImageView)convertView.findViewById(R.id.imv_state);
			holder.imv_luyin = (ImageView)convertView.findViewById(R.id.imv_luyin);
			holder.imv_bofangluyin = (ImageView)convertView.findViewById(R.id.imv_bofangluyin);
			
			listener = new TestListViewStateOnClickListener(context,holder.imv_state,holder.imv_bofangluyin,item,position,adapter,list);//A convertView为空时才创建listener,这样每个imv都只设置了一次监听，而且不用每次刷新都创建出listener，节约内存
			holder.imv_state.setOnClickListener(listener);
			holder.imv_bofangluyin.setOnClickListener(listener);
			holder.imv_luyin.setOnClickListener(listener);
			
			convertView.setTag(holder);
			convertView.setTag(holder.imv_state.getId(), listener);//对监听对象保存
		}else{
			holder = (ViewHolder)convertView.getTag();
			listener = (TestListViewStateOnClickListener)convertView.getTag(holder.imv_state.getId());//重新获得监听对象
		}
		
		//BBBBBBBBB 这个与onclicklistener中构造方法中的initMedia()适用于进入页面时初始化，播放器的话是点击时初始化，所以不用这个了
		/*if(item.getMediaPlayer()==null){//复用了view 但没新建media的情况（即屏幕外边的那些item，初始时屏幕内的item在A处创建）
			MediaPlayer mp = listener.initMedia();
			item.setMediaPlayer(mp);
		}*/
		
		listener.setPosition(position);//解决点击滑动再回来时position和imv错乱问题
		listener.setBean(item);//解决点击滑动再回来时position和imv错乱问题
		//MyLog.e("panda","getView  pos="+position+",contentView="+(convertView==null?"null":convertView.getId())+",mp="+item.getMediaPlayer());
		
		holder.tv_name.setText(item.getName());
		if(item.isShowed()){
			holder.imv_state.setImageResource(R.drawable.vdo_test_audio_stop);
		}else{
			holder.imv_state.setImageResource(R.drawable.vdo_test_play_image_selector);
			//item.destroyMediaPlayer();//写在这不准确 刷新时屏幕之外的不会被执行到
		}
		
		if(item.isBofangluyining()){
			holder.imv_bofangluyin.setImageResource(R.drawable.vdo_test_audio_stop);
		}else{
			holder.imv_bofangluyin.setImageResource(R.drawable.vdo_test_audition_image_selector);
		}
		
		return convertView;
	}
	
	

	class ViewHolder{
		TextView tv_name;
		ImageView imv_state;
		ImageView imv_luyin;
		ImageView imv_bofangluyin;
	}
	
	
	
	public void onDestroy(){
		for(TestListViewStateBean bean:list){
			bean.destroyMediaPlayer();
			bean.destroyMediaPlayerBofangLuyin();
		}	
	}

}
