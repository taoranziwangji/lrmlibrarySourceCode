package com.vdolrm.lrmlibrary.net;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;

import com.vdolrm.lrmlibrary.log.MyLog;


public abstract class FileDownloadMP3Util extends FileDownloadUtil {
	
	private MediaPlayer mediaPlayer;

	//这个方法会在有这个缓存或者下载完成后调用
	@Override
	public void hasCacheMethod(String path) {
		// TODO Auto-generated method stub
		MyLog.d("hasCacheMethod start path="+path+",mp="+mediaPlayer);//第一次执行时mp为空 第二次执行时(比如点击)就有值了，之后每次点击 mp的内存地址都一样
		if(mediaPlayer==null){
			mediaPlayer = new MediaPlayer();
		
		try {
			mediaPlayer.setDataSource(path);
			mediaPlayer.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		
		}else{
			playFromTheBeginning(mediaPlayer);
		}


		
		mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			 @Override
			public void onPrepared(MediaPlayer mp) {
				 MyLog.d("panda","onPrepared mp="+mp+",mediaPlayer="+mediaPlayer);
				 try{
					 if(mediaPlayer!=null){
						 mediaPlayer.start();
						 
						 onMediaStartFirst(mediaPlayer);
					 }
				 }catch(RuntimeException e){
					 e.printStackTrace();
				 }
		        }
		});
		
		
		
		mediaPlayer.setOnErrorListener(new OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// TODO Auto-generated method stub
				MyLog.e("vdolibrary","hasCacheMethod onError mp="+mp+",what="+what+",extra="+extra);
				onMediaError(mp,what,extra);
				try{
					mp.reset();
				}catch(RuntimeException e){
					e.printStackTrace();
				}
				return false;
			}
		});
		
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				onMediaCompletion(mp);
			}
		});
	}

	public abstract void onMediaCompletion(MediaPlayer mp);
	/**单指第一次播放*/
	public abstract void onMediaStartFirst(MediaPlayer mp);
	/**重播，即不是第一次播放*/
	public void onMediaStartRepeat(){

	}

	public void onMediaError(MediaPlayer mp, int what, int extra){

	}

	/**不是第一次播放时默认让他跳转到开始处开始播放*/
	public void playFromTheBeginning(MediaPlayer mp){
		MyLog.d("panda","father播放时mediaplayer="+mp);
		mp.seekTo(0);
		mp.start();
		onMediaStartRepeat();
	}

	public void destroyTheMediaPlayer(){
		mediaPlayer = null;
	}
}
