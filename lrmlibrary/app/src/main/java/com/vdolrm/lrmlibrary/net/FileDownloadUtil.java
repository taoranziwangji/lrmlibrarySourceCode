package com.vdolrm.lrmlibrary.net;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import com.vdolrm.lrmlibrary.bean.FileDownloadInfo;
import com.vdolrm.lrmlibrary.file.FileDeleteUtil;
import com.vdolrm.lrmlibrary.file.FileUtils;
import com.vdolrm.lrmlibrary.file.GetFileMD5;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.task.AsyncTaskSimple;
import com.vdolrm.lrmlibrary.utils.Contant;
import com.vdolrm.lrmlibrary.utils.TimeUtil3;
import com.vdolrm.lrmlibrary.utils.UIUtils;
import com.vdolrm.lrmlibrary.utils.VersionUtil;

public abstract class FileDownloadUtil {

	public static final int FILEINSDCARDBUTOUTDB_REDOWNLOAD = 0;//当sd卡中存在文件，但数据库中不存在此文件的信息时的处理方式。0为重新下载，1为直接读取sd卡里的文件
	public static final int FILEINSDCARDBUTOUTDB_USETHESDCARDFILE= 1;//当sd卡中存在文件，但数据库中不存在此文件的信息时的处理方式。0为重新下载，1为直接读取sd卡里的文件

	private HttpHandler<File> handler_download_file;
	private DbUtils db ;


	public abstract void hasCacheMethod(String path);
	public abstract void netWorkError();
	
	public abstract void download_onStart();
	public abstract void download_onLoading(long total, long current, boolean isUploading);
	public abstract void download_onSuccess(ResponseInfo<File> responseInfo);
	public abstract void download_onFailure(HttpException error,String msg);
	
	private static final int STATE_MATCHING = 0;//文件和数据库匹配
	private static final int STATE_MISMATCHING = 1;//文件和数据库不匹配
	private static final int STATE_SDCARDHADBUTDBNONE = 2;//文件存在 但数据库里不存在，可能是卸载后又重新安装了应用
	
	private String str_mismatching = "文件内容发生改变，需要重新下载";
	public String getStr_mismatching() {
		return str_mismatching;
	}
	/**
	 * 需要在调用checkCacheAndDownload之前调用，用以设置文件和数据库不匹配时的提示语
	 * @param str_mismatching
	 */
	public void setStr_mismatching(String str_mismatching) {
		this.str_mismatching = str_mismatching;
	}
	public HttpHandler<File> getDownloadHandler(){
		return handler_download_file;
	}
	
	/**
	 * @param context
	 * @param directory 文件存储的目录
	 * @param path 文件存储的全路径
	 * @param url 文件的url
	 * @param flag 当sd卡中存在文件，但数据库中不存在此文件的信息时的处理方式。0为重新下载，1为直接读取sd卡里的文件
	 */
	public void checkCacheAndDownload(Context context,int flag,String directory,String path,String url,String dbName,int dbVersion){
		
		if(db==null){
			db = Xutils_singleCase.getDbUtilsInstance(context,dbName,dbVersion);
		}
		
		//先取文件 看有没有 没有的话就下载
		boolean isCunzai = FileUtils.isFileExists(path);
		MyLog.d("判断是否存在,path="+path+",isCunzai="+isCunzai);
		if(isCunzai){
			
			checkMd5(context,directory,path,url,flag);
			
		
			
		}else{
			
			boolean isCunzai_huancun = FileUtils.isFileExists(path+Contant.extensionName);
			if(isCunzai_huancun){//继续下载
				if(handler_download_file!=null){
	        		 if(!handler_download_file.isCancelled()) {
	        			 handler_download_file.cancel();
	                 } else {	                   
	                	 if(NetCheckUtil.isNetworkConnected(context)){
	 						downloadFile(directory,path,url);
	 					}else{
	 						netWorkError();
	 					}
	                 }
	        	}else{//从头下载
					if(NetCheckUtil.isNetworkConnected(context)){
						downloadFile(directory,path,url);
					}else{
						netWorkError();
					}
				}
			}else{//从头下载
				if(NetCheckUtil.isNetworkConnected(context)){
					downloadFile(directory,path,url);
				}else{
					netWorkError();
				}
			}
		}
	}
	
	
	

	@SuppressLint("NewApi")
	private void checkMd5(final Context context,final String directory,final String path,final String url,final int flag) {
		// TODO Auto-generated method stub
		if(db==null){
			return ;
		}
		
			AsyncTaskSimple<String, Integer> async = new AsyncTaskSimple<String, Integer>() {

					@Override
					public Integer doInBackground2(String... params) {
						// TODO Auto-generated method stub
						if(params==null || params.length<=0){
							return STATE_MISMATCHING;
						}
						
						FileDownloadInfo bean = null;
						try {
							bean = db.findFirst(Selector.from(FileDownloadInfo.class).where("url","=",url));
						} catch (DbException e1) {
							e1.printStackTrace();
						}
						if(bean!=null){
							File f = new File(path);
							if(f!=null && f.isFile()){
								MyLog.d("sdcard.file.size="+f.length()+",db.file.size="+bean.getAppSize());
								if(bean.getAppSize()==f.length()){//假如size一样 再做MD5检查，size不一样直接返回不匹配
									String sdCardMd5 = null;
									try {
										sdCardMd5 = GetFileMD5.getFileMD5String(params[0]);
									} catch (IOException e) {
										e.printStackTrace();
									}
									
									String dbMd5 = bean.getFilemd5();
									
									if(sdCardMd5==null || dbMd5==null){
										return STATE_MISMATCHING;
									}
									
									if(sdCardMd5.equals(dbMd5)){
										return STATE_MATCHING;
									}
								}else{
									return STATE_MISMATCHING;
								}
							}
							
						}else{
							return STATE_SDCARDHADBUTDBNONE;//有文件但数据库里没有，可能是卸载又重新安装了应用 导致数据库问文件不匹配 这时需要重新下载,或者把文件信息保存到数据库中,或者根本就不用管直接读取文件系统里的文件。这里使用重新下载
						}
		
						return STATE_MISMATCHING;
					}

					@Override
					public void onPostExecute2(Integer result) {
						// TODO Auto-generated method stub
						if(result == STATE_MATCHING){
							MyLog.d("文件与数据库md5相同");
							hasCacheMethod(path);
						}else if(result ==STATE_MISMATCHING){
							
							if(getStr_mismatching()!=null && getStr_mismatching().equals("")==false){
								UIUtils.showToastSafe(getStr_mismatching());
							}
							delOldAndDownload(context,directory,path,url);
							
						}else if(result == STATE_SDCARDHADBUTDBNONE){
							delOldAndDownload_sdCardHadButDBNone(context, directory, path, url,flag);
						}else{
							
						}
					}

					@Override
					public void onCanceled() {
						// TODO Auto-generated method stub
						
					}
				};
				
				if(VersionUtil.getAndroidVerson() <=11){
					async.execute(path);
				}else{
					async.executeOnExecutor(AsyncTaskSimple.getExecutorServiceInstance(),path);
				}
			
	
	}
	
	
	/**
	 * 	用于 有文件但数据库里没有 这种情况，可能是卸载又重新安装了应用 导致数据库问文件不匹配 这时需要重新下载,或者把文件信息保存到数据库中,或者根本就不用管直接读取文件系统里的文件。
	 * ---第二项不成立，无法把文件信息保存到数据库中，因为无法获取到文件的url，恰好查询数据库是根据url来查询的，所以此方法pass
	 */
	private void delOldAndDownload_sdCardHadButDBNone(Context context, String directory, String path, String url,int flag){
		MyLog.d("flag="+flag);
		if(flag ==FILEINSDCARDBUTOUTDB_REDOWNLOAD){
			delOldAndDownload(context, directory, path, url);
		}else if(flag ==FILEINSDCARDBUTOUTDB_USETHESDCARDFILE){
			hasCacheMethod(path);
		}else{
			
		}
	}
	
	private void delOldAndDownload(Context context, String directory, String path, String url){
		boolean b = FileDeleteUtil.DeleteFolder(path);
		MyLog.d("文件md5发生改变，删除成功？"+b);
		
		MyLog.d("文件md5发生改变，开始重新下载");
		if(NetCheckUtil.isNetworkConnected(context)){
			downloadFile(directory,path,url);
		}else{
			netWorkError();
		}
	}
	
	/**
	 * @param directory 文件存储的目录
	 * @param path 文件存储的全路径
	 * @param url 文件的url
	 */
	private void downloadFile(String directory,String path,final String url) {
		File dirFirstFile6=new File(directory);
        if(!dirFirstFile6.exists()){
             dirFirstFile6.mkdir();
        }	
		
	    String target = path+Contant.extensionName;//文件名
	        
    	HttpUtils http = new HttpUtils();
    	handler_download_file = http.download(url.replace(" ", "_"),
    	    target,
    	    true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
    	    false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。 如果有重复文件，则会在前面自动加时间戳  ！！！！！！！不能重命名 否则没有文件名的话没法判断文件是否存在
    	    new RequestCallBack<File>() { 
    	        @Override
    	        public void onStart() {
    	            MyLog.d("xutil","onStart: download");
    	           
    	            download_onStart();
    	        }

    	        @Override
    	        public void onLoading(long total, long current, boolean isUploading) {
    	            MyLog.d("xutil","onLoading:"+current + "/" + total+",isUploading="+isUploading);
	    	        download_onLoading(total, current, isUploading);
    	        }

    	        @Override
    	        public void onSuccess(ResponseInfo<File> responseInfo) {
    	            MyLog.d("xutil","onSuccess:path="+responseInfo.result.getPath());
    	             String downloadfile = null;
    	            //重命名 去掉缓存的.temp后缀
    	            File file = new File(responseInfo.result.getPath());
    	            int index_temp = responseInfo.result.getPath().indexOf(Contant.extensionName);
    	           
    	            if(index_temp>0){
    	            	downloadfile = responseInfo.result.getPath().substring(0,index_temp);
    	            	file.renameTo(new File(downloadfile));
    	            }

    	           
					createFileDownloadInfoBean(url, downloadfile);
					
					
    	            download_onSuccess(responseInfo);
    	            
    	            hasCacheMethod(downloadfile);
    	            
    	        }

				@Override
				public void onFailure(HttpException error,String msg) {
					download_onFailure(error, msg);
				}
    	});
	}

	
	@SuppressLint("NewApi")
	private void createFileDownloadInfoBean(final String url,String downloadfile){
		 //把文件信息保存到数据库
        AsyncTaskSimple<String,FileDownloadInfo> async = new AsyncTaskSimple<String,FileDownloadInfo>() {
			@Override
			public FileDownloadInfo doInBackground2(String... params) {
				// TODO Auto-generated method stub
				if(params==null || params.length<=0){
					return null;
				}
				String downloadfile = params[0];
				File f = new File(downloadfile);
				if(downloadfile ==null || downloadfile.equals("") || f==null || f.isFile()==false){
					return null;
				}
				 FileDownloadInfo bean = new FileDownloadInfo(); //这里需要注意的是FileDownloadInfo对象必须有id属性，或者有通过@ID注解的属性
    	            bean.setAppSize(f.length());
    	            bean.setCurrentSize(f.length());
    	            //bean.setDownloadState(downloadState);
    	            //bean.setFilename(downloadfile);
    	            bean.setPath(downloadfile);
    	            bean.setTime(TimeUtil3.getStringToday());
    	            bean.setUrl(url);
    	            try {
						bean.setFilemd5(GetFileMD5.getFileMD5String(downloadfile));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
    	            
    	            MyLog.d("子线程创建文件信息的实例：bean="+bean.toString());
				return bean;
			}

			@Override
			public void onPostExecute2(FileDownloadInfo result) {
				// TODO Auto-generated method stub
				MyLog.d("准备保存到数据库");
				if(result==null){
					return;
				}
				 try {
						//db.save(result);// 使用saveBindingId保存实体时会为实体的id赋值
						db.saveOrUpdate(result);
						MyLog.d("成功保存到数据库");
					} catch (DbException e) {
						e.printStackTrace();
					} 
			}
			
			@Override
			public void onCanceled() {
				// TODO Auto-generated method stub
				
			}
		};
		
		//async.executeOnExecutor(AsyncTaskSimple.getExecutorServiceInstance(),downloadfile);
		if(VersionUtil.getAndroidVerson() <=11){
			async.execute(downloadfile);
			}else{
				async.executeOnExecutor(AsyncTaskSimple.getExecutorServiceInstance(),downloadfile);
			}
	}
	
	
}
