package com.vdolrm.lrmlibrary.test;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.utils.UIUtils;

import java.io.IOException;
import java.util.List;

public class TestListViewStateAdapterClassial extends BaseAdapter{
	private Context context;
	private List<TestListViewStateBean> list;
	public TestListViewStateAdapterClassial(Context ctx,List<TestListViewStateBean> lists) {
		context = ctx;
		list = lists;
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
		MyOnClickListener listener = null;
		if(convertView==null){
			holder= new ViewHolder();
			convertView = UIUtils.inflate(R.layout.vdo_test_listviewstate_item);
			holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
			holder.imv_state = (ImageView)convertView.findViewById(R.id.imv_state);
			listener = new MyOnClickListener(holder.imv_state,item,position);//A convertView为空时才创建listener,这样每个imv都只设置了一次监听，而且不用每次刷新都创建出listener，节约内存
			holder.imv_state.setOnClickListener(listener);
			convertView.setTag(holder);
			convertView.setTag(holder.imv_state.getId(), listener);//对监听对象保存
		}else{
			holder = (ViewHolder)convertView.getTag();
			listener = (MyOnClickListener)convertView.getTag(holder.imv_state.getId());//重新获得监听对象
		}
		
		//这个适用于进入页面时初始化，播放器的话是点击时初始化，所以不用这个了
		/*if(item.getMediaPlayer()==null){//复用了view 但没新建media的情况（即屏幕外边的那些item，初始时屏幕内的item在A处创建）
			MediaPlayer mp = listener.initMedia();
			item.setMediaPlayer(mp);
		}*/
		
		listener.setPosition(position);//解决点击滑动再回来时position和imv错乱问题
		listener.setBean(item);//解决点击滑动再回来时position和imv错乱问题
		//MyLog.e("panda","getView  pos="+position+",contentView="+(convertView==null?"null":convertView.getId())+",mp="+item.getMediaPlayer());
		
		holder.tv_name.setText(item.getName());
		if(item.isShowed()){
			holder.imv_state.setImageResource(R.drawable.ic_drawer);
		}else{
			holder.imv_state.setImageResource(R.drawable.ic_launcher);
			//item.destroyMediaPlayer();//写在这不准确 刷新时屏幕之外的不会被执行到
		}
		
		return convertView;
	}
	
	
	class MyOnClickListener implements View.OnClickListener{
		private ImageView imv_state_click;
		private TestListViewStateBean bean;
		private int position;
		
		public MyOnClickListener(ImageView imv,TestListViewStateBean b,int pos){
			imv_state_click = imv;
			bean = b;
			position = pos;
			//MyLog.d("panda","监听器构造：pos="+pos+",bean="+bean.getName()+",imv="+imv_state_click.getId());
			//initMedia();//这个适用于进入页面时初始化，播放器的话是点击时初始化，所以不用这个了
		}
		
		/**暴露出刷新pos的方法，getview时调用*/
		public void setPosition(int position) {
		      this.position = position;
		}
		
		/**暴露出刷新bean的方法，getview时调用*/
		public void setBean(TestListViewStateBean b){
			bean = b;
		}
		
		
		 private MediaPlayer initMedia(){
			 MediaPlayer mediaPlayer = new MediaPlayer();//写成全局的会有问题
				//MyLog.d("panda","新建mediaplayer="+mediaPlayer+",position="+position+",bean="+(bean==null?"null":bean.getName()));
				AssetManager assets = context.getAssets();
				try {
					AssetFileDescriptor fileDescriptor = assets.openFd("mo.mp3");
					mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
									fileDescriptor.getStartOffset(),
									fileDescriptor.getLength());
					mediaPlayer.prepare();
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalStateException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}catch(RuntimeException e1){
					e1.printStackTrace();
				}
				
				if(bean!=null){
					bean.setMediaPlayer(mediaPlayer);
				}
				
				return mediaPlayer;
				
			}

		@Override
		public void onClick(View v) {
			if(v.getId()==imv_state_click.getId()){
				//UIUtils.showToastSafe("点击了第"+position+"项，name="+bean.getName());
				MediaPlayer mp = bean.getMediaPlayer();
				if(bean.isShowed()){
					bean.destroyMediaPlayer();
				}else{
					if(mp==null){
						initMedia();
						mp = bean.getMediaPlayer();
					}
					if(mp!=null){
						mp.start();
					}
				}
				
				bean.setShowed(!bean.isShowed());
				onPlayServerAudio(position);
				notifyDataSetChanged();
			}
		}
		
	}

	class ViewHolder{
		TextView tv_name;
		ImageView imv_state;
	}
	
	
	public void onPlayServerAudio(int position) {
		for(int i=0;i<list.size();i++){
			if(i==position){
				
			}else{
				TestListViewStateBean bean = list.get(i);
				bean.destroyMediaPlayer();
				bean.setShowed(false);//刷新时会在getview中执行销毁
			}
		}
	}

}
