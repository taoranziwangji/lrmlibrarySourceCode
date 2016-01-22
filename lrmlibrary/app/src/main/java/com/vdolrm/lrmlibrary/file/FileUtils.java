package com.vdolrm.lrmlibrary.file;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.utils.Contant;

public class FileUtils {
	
	
	 /** 
     * 保存文件 
     * @param lujing为路径
     * @param fileName 
     * @param isfileNameAFullName filename是完整路径+名字吗
     * @throws IOException 
     */  
    public static boolean saveBitmapToFile(String lujing,Bitmap bm, String fileName,boolean isfileNameAFullName) throws IOException {  
        
    	/*File rootFile = new File(Contant.mulu_root);
    	if(!rootFile.exists()){
    		rootFile.mkdir();
    	}*/
    	
    	File dirFile = new File(lujing);  
        if(!dirFile.exists()){  
            dirFile.mkdir();  
        }  
        
        File myCaptureFile;
        if(isfileNameAFullName){
        	myCaptureFile = new File(fileName); 
        }else{
        	myCaptureFile = new File(lujing + fileName); 
        }
       
        if(myCaptureFile !=null){
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        if(bos !=null && bm!=null){
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
        bos.flush();  
        bos.close();  
        
        return true;
        }
        }
		return false;
    } 
    
  
	
	

	/**
	 * 判断文件或文件夹是否存在*/
	public static boolean isFileExists(String path){
        try{
                File f=new File(path);
                if(!f.exists()){
                        return false;
                }
                
        }catch (Exception e) {
        	e.printStackTrace();
                return false;
        }
        return true;
	}
	
	
	
	
	/** 
	    * 判断文件夹是否存在 
	    */  
	/*   public void isDirExist(String dir){  
	       File file = new File(SDCardRoot + dir + File.separator);  
	       if(!file.exists())  
	           file.mkdir();  //如果不存在则创建  
	         
	   }  */
	
	
	
	/**
	 * 获取文件夹内所有的文件*/
	public static List<String> getAllPathFromSD(String path) {
	 // 图片列表
	 List<String> picList = new ArrayList<String>();

	 try{
	 // 得到该路径文件夹下所有的文件
	  File mfile = new File(path);
	  File[] files = mfile.listFiles();
	  if(files==null){
		  return picList;
	  }

	 // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
	 for (int i = 0; i < files.length; i++) {
	  File file = files[i];
	 // if (checkIsImageFile(file.getPath())) {
	   picList.add(file.getPath());
	//  }

	 }
	 }catch(RuntimeException e){
		 e.printStackTrace();
	 }

	 // 返回得到的图片列表
	 return picList;

	}

	
	
	
	// 检查扩展名，得到图片格式的文件
	public static boolean checkIsImageFile(String fName) {
	 boolean isImageFile = false;

	 // 获取扩展名
	 String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
	   fName.length()).toLowerCase();
	 if (FileEnd.equals("jpg") || FileEnd.equals("gif")
	   || FileEnd.equals("png") || FileEnd.equals("jpeg")
	   || FileEnd.equals("bmp")) {
	  isImageFile = true;
	 } else {
	  isImageFile = false;
	 }

	 return isImageFile;

	}
	
	
	 /** 
     * 创建文件夹 
     *  
     * @param file 
     *            要创建的根目录 
     */ 
	public static void createFileDirectory(String path){
		 File dirFirstFile=new File(path);//新建一级主目录
         if(!dirFirstFile.exists()){//判断文件夹目录是否存在
              dirFirstFile.mkdir();//如果不存在则创建
         }
	}
	
	
	/** 
     * 指定目录为非媒体文件夹
     *  
     * @param file 
     *            .nomedia放置的的目录 
     */ 
	public static void createNomediaFile(String path){
		try {
			boolean bb = new File(path, ".nomedia").createNewFile();//false为已经创建过了
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	   /** 
	     * 递归删除文件和文件夹 
	     *  
	     * @param file 
	     *            要删除的根目录 
	     */  
    public static void DeleteFile(File file) {  
        if (file.exists() == false) {   
            return;  
        } else {  
            if (file.isFile()) {  
            	final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
            	file.renameTo(to);
            	to.delete();
                //file.delete();  //把这一句改成上面三句 解决偶尔某些机型报android  device or resource busy的错误
                return;  
            }  
            if (file.isDirectory()) {  
                File[] childFile = file.listFiles();  
                if (childFile == null || childFile.length == 0) {  
                	final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
                	file.renameTo(to);
                	to.delete();
                  //  file.delete();  
                    return;  
                }  
                for (File f : childFile) {  
                    DeleteFile(f);  
                }  
                final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
                file.renameTo(to);
                to.delete();
                //file.delete();  
            }  
        }  
    }
	
}
