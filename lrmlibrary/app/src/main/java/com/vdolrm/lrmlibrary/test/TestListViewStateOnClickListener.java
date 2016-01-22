package com.vdolrm.lrmlibrary.test;

import java.io.IOException;
import java.util.List;

import com.vdolrm.lrmlibrary.utils.UIUtils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class TestListViewStateOnClickListener implements View.OnClickListener{
	
	private static final String url_music1 = "http://music-zhizhi.oss-cn-beijing.aliyuncs.com/%E6%BC%82%E6%B4%8B%E8%BF%87%E6%B5%B7%E6%9D%A5%E7%9C%8B%E4%BD%A0.mp3";//漂洋过海
	private static final String url_music2 = "http://music-zhizhi.oss-cn-beijing.aliyuncs.com/%E5%91%A8%E6%9D%B0%E4%BC%A6-%E9%BB%98_clip.mp3";//默

	private Context context;
	private ImageView imv_state_click;
	private ImageView imv_bofangluyin;
	private TestListViewStateBean bean;
	private int position;
	private BaseAdapter adapter;
	private List<TestListViewStateBean> list;
	
	public TestListViewStateOnClickListener(Context c,ImageView imv,ImageView imv_bofangluyin,TestListViewStateBean b,int pos,BaseAdapter adapter,List<TestListViewStateBean> list){
		context = c;
		imv_state_click = imv;
		this.imv_bofangluyin = imv_bofangluyin;
		bean = b;
		position = pos;
		this.adapter = adapter;
		this.list = list;
		//initMedia();//这个与TestListViewStateAdapterClassial getview中的BBBBBBBBB代码段 适用于进入页面时初始化，播放器的话是点击时初始化，所以不用这个了
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
		MediaPlayer mediaPlayer = getMediaplayer("mo.mp3");
		if(bean!=null){
			bean.setMediaPlayer(mediaPlayer);
		}
			return mediaPlayer;
	 }
	 
	 
	 private MediaPlayer initMediaLuyin(){
			MediaPlayer mediaPlayer = getMediaplayer("piaoyangguohai.mp3");
			if(bean!=null){
				bean.setMediaPlayer_luyin(mediaPlayer);
			}
				return mediaPlayer;
		 }

	

	@Override
	public void onClick(View v) {
		if(v.getId()==imv_state_click.getId()){
			MediaPlayer mp = bean.getMediaPlayer();
			if(bean.isShowed()){
				bean.destroyMediaPlayer();
			}else{
				if(mp==null){
					initMedia();
					mp = bean.getMediaPlayer();
				}
				if(mp!=null){//prepare成功后播放
					mp.start();
				}
			}
			
			bean.setShowed(!bean.isShowed());
			onPlayServerAudio(position);
			onPlayServerAudioLuyin(-1);
			adapter.notifyDataSetChanged();
			
		}else if(v.getId() == imv_bofangluyin.getId()){
			MediaPlayer mp = bean.getMediaPlayer_bofangluyin();
			if(bean.isBofangluyining()){
				bean.destroyMediaPlayerBofangLuyin();
			}else{
				if(mp==null){
					initMediaLuyin();
					mp = bean.getMediaPlayer_bofangluyin();
				}
				if(mp!=null){//prepare成功后播放
					mp.start();
				}
			}
			
			bean.setBofangluyining(!bean.isBofangluyining());
			onPlayServerAudio(-1);
			onPlayServerAudioLuyin(position);
			adapter.notifyDataSetChanged();
		}else{
			UIUtils.showToastSafe("click "+position);
		}
	}
	
	
	private void onPlayServerAudio(int position) {
		for(int i=0;i<list.size();i++){
			if(i==position){
				
			}else{
				TestListViewStateBean bean = list.get(i);
				bean.destroyMediaPlayer();
				bean.setShowed(false);
			}
		}
	}
	
	
	private void onPlayServerAudioLuyin(int position) {
		for(int i=0;i<list.size();i++){
			if(i==position){
				
			}else{
				TestListViewStateBean bean = list.get(i);
				bean.destroyMediaPlayerBofangLuyin();
				bean.setBofangluyining(false);
			}
		}
	}
	
	
	
	private MediaPlayer getMediaplayer(String nameInAssets) {
		MediaPlayer mediaPlayer = new MediaPlayer();//写成全局的会有问题
		AssetManager assets = context.getAssets();
		try {
			AssetFileDescriptor fileDescriptor = assets.openFd(nameInAssets);
			mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
							fileDescriptor.getStartOffset(),
							fileDescriptor.getLength());
			//mediaPlayer.setDataSource(url_music1);
			mediaPlayer.prepare();
			//mediaPlayer.prepareAsync();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}catch(RuntimeException e1){
			e1.printStackTrace();
		}
		
		/*mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
			}
		});*/
		
		return mediaPlayer;
	}
}
