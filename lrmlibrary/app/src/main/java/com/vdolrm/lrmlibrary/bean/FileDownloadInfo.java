package com.vdolrm.lrmlibrary.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Author: wyouflf
 * Date: 13-7-25
 * Time: 下午7:06
 */
// 建议加上注解， 混淆后表名不受影响
//@Table(name = "fileDownloadInfo", execAfterTableCreated = "CREATE UNIQUE INDEX index_name ON parent(name,email)")
@Table(name = "fileDownloadInfo")
public class FileDownloadInfo extends EntityBase {

    @Column(column = "url") // 建议加上注解， 混淆后列名不受影响
    private String url;

   // @Column(column = "filename")
   // private String filename;
    
    @Column(column = "appSize")
    private long appSize = 0;//app的size
    
    @Column(column = "currentSize")
	private long currentSize = 0;//当前的size
    
    //@Column(column = "downloadState")
	//private int downloadState = 0;//下载的状态
    
    @Column(column = "path")
	private String path;//保存路径

    @Column(column = "time")
    private String time;

    @Column(column = "filemd5")
    private String filemd5;//文件的md5

   // @Finder(valueColumn = "id", targetColumn = "parentId")
   // public FinderLazyLoader<Child> children; // 关联对象多时建议使用这种方式，延迟加载效率较高。
    
    //@Finder(valueColumn = "id",targetColumn = "parentId")
    //public Child children;
    //@Finder(valueColumn = "id", targetColumn = "parentId")
    //private List<Child> children;

    
    


    @Override
    public String toString() {
        return "Parent{" +
                "id=" + getId_primaryKey() +
                //", filename='" + filename + '\'' +
                ", url='" + url + '\'' +
                ", appSize=" + appSize +
                ", time=" + time +
                ", path=" + path +
                ", currentSize=" + currentSize +
               // ", downloadState=" + downloadState +
                 ", filemd5=" + filemd5 +
                '}';
    }

    


	public String getFilemd5() {
		return filemd5;
	}




	public void setFilemd5(String filemd5) {
		this.filemd5 = filemd5;
	}




	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	/*public String getFilename() {
		return filename;
	}



	public void setFilename(String filename) {
		this.filename = filename;
	}*/



	public long getAppSize() {
		return appSize;
	}



	public void setAppSize(long appSize) {
		this.appSize = appSize;
	}



	public long getCurrentSize() {
		return currentSize;
	}



	public void setCurrentSize(long currentSize) {
		this.currentSize = currentSize;
	}



	/*public int getDownloadState() {
		return downloadState;
	}



	public void setDownloadState(int downloadState) {
		this.downloadState = downloadState;
	}*/



	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}
}
