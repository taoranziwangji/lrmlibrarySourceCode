package com.vdolrm.lrmlibrary.utils;


import android.content.Context;
import android.graphics.Typeface;

public class TypefaceUtil {
	/*private static Context c;
	public TypefaceUtil(Context c){
		this.c = c;
	}*/
	
	private static Typeface t1,t2; 
	private static Typeface t3,t4,t5;

	public static Typeface gettype_english(Context c){
		//return null;
		if(t1==null)
	//	 t1 = Typeface.createFromAsset(c.getAssets(),"fonts/wrjzy.TTF");//微软简中圆
	//	 t1 = Typeface.createFromAsset(c.getAssets(),"fonts/cyjht.TTF");//创意简黑体
	//	t1 = Typeface.createFromAsset(c.getAssets(),"fonts/gbk.TTF");//口语用的字体 好像是华文细黑
	//	t1 = Typeface.createFromAsset(c.getAssets(),"fonts/fzhtjt.TTF");//方正黑体简体
		t1 = Typeface.createFromAsset(c.getAssets(),"fonts/lkssjht.TTF");//雷克萨斯简黑体
		return t1;
	}
	
	public static Typeface gettype_chinese(Context c){
		//return null;
		if(t2==null)
	//	 t2 = Typeface.createFromAsset(c.getAssets(),"fonts/wrjzy.TTF");
	//	 t2 = Typeface.createFromAsset(c.getAssets(),"fonts/cyjht.TTF");
	//	t2 = Typeface.createFromAsset(c.getAssets(),"fonts/gbk.TTF");
	//	t2 = Typeface.createFromAsset(c.getAssets(),"fonts/fzhtjt.TTF");
		t2 = Typeface.createFromAsset(c.getAssets(),"fonts/lkssjht.TTF");
		return t2;
	}
	
	
	
	//public static Typeface gettype_segoeui(Context c){
	//	return null;
		/*if(t5==null)
		t5 = Typeface.createFromAsset(c.getAssets(),"fonts/segoeui.ttf");
		return t5;*/
	//}
}
