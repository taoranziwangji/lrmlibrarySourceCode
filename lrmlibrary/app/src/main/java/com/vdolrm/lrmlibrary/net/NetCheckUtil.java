package com.vdolrm.lrmlibrary.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetCheckUtil {
	public static boolean isNetworkConnected(Context context) {  
	     if (context != null) {  
	         ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                 .getSystemService(Context.CONNECTIVITY_SERVICE);  
	         NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	         if (mNetworkInfo != null) {  
	              return mNetworkInfo.isAvailable();  
	          }  
	      }  
	     return false;  
	 }
	
	/**获取联网类型*/
	public static String getNetType(Context context){
		String nettypeName = "";
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info != null){
			nettypeName = info.getTypeName();//联网类型=				
		}
		return nettypeName==null?"":nettypeName;
	}
	
	/**获取联网类型*/
	public static int getNetType2(Context context){
		int type = -1;
	
		ConnectivityManager connectMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		 NetworkInfo info = connectMgr.getActiveNetworkInfo();
		 if(info!=null){
			type = info.getType();//ConnectivityManager.TYPE_WIFI
			//int subtype = info.getSubtype();
		 }
		return type;
	}
}
