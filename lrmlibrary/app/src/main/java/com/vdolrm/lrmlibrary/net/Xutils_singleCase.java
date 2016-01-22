package com.vdolrm.lrmlibrary.net;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.DbUtils.DaoConfig;

//硬汉单例模式
public class Xutils_singleCase {
	
	private Xutils_singleCase() {
		
	}
	
	
	/**
	 * 连接超时时间
	 */
	private static int time = 1000*15;
	
	public static void setTime(int time) {
		Xutils_singleCase.time = time;
	}


	private static HttpUtils http;
	public static DbUtils db;

	public synchronized static HttpUtils getHttpUtilsInstance() {
		if(http ==null){
			http = new HttpUtils(time);
		}
		return http;
	}

	
	
	public synchronized static DbUtils getDbUtilsInstance(Context context,String dbName,int dbVersion){
		if(db==null){
			//db = DbUtils.create(context);
			DaoConfig config = new DaoConfig(context);
			config.setDbName(dbName);
			config.setDbVersion(dbVersion);//xutils增加表不用修改数据库版本,表内减少字段不用修改数据库版本,表内增加字段需要递增数据库版本
			db = DbUtils.create(config);
		}
		return db;
	}
	
}
