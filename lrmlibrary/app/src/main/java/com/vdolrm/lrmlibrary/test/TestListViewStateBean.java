package com.vdolrm.lrmlibrary.test;

import android.media.MediaPlayer;

public class TestListViewStateBean {
	private String name = "";
	
	private boolean showed = false;//是否正在播放服务器返回回来的音频
	private MediaPlayer mediaPlayer;
	
	private boolean bofangluyining = false;
	private MediaPlayer mediaPlayer_bofangluyin;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public boolean isShowed() {
		return showed;
	}
	public void setShowed(boolean showed) {
		this.showed = showed;
	}
		
	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}
	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}
	
	
	
	public boolean isBofangluyining() {
		return bofangluyining;
	}
	public void setBofangluyining(boolean bofangluyining) {
		this.bofangluyining = bofangluyining;
	}
	
	public MediaPlayer getMediaPlayer_bofangluyin() {
		return mediaPlayer_bofangluyin;
	}
	public void setMediaPlayer_luyin(MediaPlayer mediaPlayer_bofangluyin) {
		this.mediaPlayer_bofangluyin = mediaPlayer_bofangluyin;
	}
	
	
	public void destroyMediaPlayer(){
		if(mediaPlayer!=null && mediaPlayer.isPlaying()){
			//MyLog.e("panda",pos+"被销毁, mp="+mp+",全局mp="+mediaPlayer);
			mediaPlayer.pause();
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer =null;
		}
	}
	
	
	public void destroyMediaPlayerBofangLuyin(){
		if(mediaPlayer_bofangluyin!=null && mediaPlayer_bofangluyin.isPlaying()){
			//MyLog.e("panda",pos+"被销毁, mp="+mp+",全局mp="+mediaPlayer);
			mediaPlayer_bofangluyin.pause();
			mediaPlayer_bofangluyin.stop();
			mediaPlayer_bofangluyin.release();
			mediaPlayer_bofangluyin =null;
		}
	}
	
	
	/*public void destroyMediaPlayer(Context context,int pos,MediaPlayer mp){
		if(mediaPlayer!=null && mediaPlayer.isPlaying()){
			MyLog.e("panda",pos+"被销毁, mp="+mp+",全局mp="+mediaPlayer);
			mediaPlayer.pause();
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer =null;
		}
	}*/

}
