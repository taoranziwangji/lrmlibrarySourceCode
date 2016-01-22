package com.vdolrm.lrmlibrary.utils;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;

/**剪切板工具类*/
public class ClipboardManagerUtil {

	/**
	 * @param c
	 * @param s
	 * @return 是否成功
	 */
	@SuppressLint("NewApi")
	public static boolean copyToClipboard(Context c,final String s){
		try{
		//获取剪贴板管理服务
		final ClipboardManager cm =(ClipboardManager)c.getSystemService(Context.CLIPBOARD_SERVICE);
		//将文本数据复制到剪贴板
		if(android.os.Build.VERSION.SDK_INT>=11){
			UIUtils.runInMainThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					cm.setText(s);
					//Toast.makeText(AskActivity.this, "网址已复制到剪贴板", Toast.LENGTH_SHORT).show();
				}
			});
			
			return true;
		}else{
			//Toast.makeText(AskActivity.this, "您的系统版本不支持剪贴板功能", Toast.LENGTH_SHORT).show();
			return false;
		}
	
	}catch(RuntimeException e){
		e.printStackTrace();
		return false;
	}
	}
}
