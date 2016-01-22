package com.vdolrm.lrmlibrary.file;


import java.io.File;
import java.text.DecimalFormat;
import java.io.FileInputStream;

public class GetFileSizeUtil{
	
	/**取得文件大小
	 */   
	public static long getFileSize(File f) throws Exception{//取得文件大小
        long s=0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
           s= fis.available();
           
           fis.close();
        } else {
            f.createNewFile();
            System.out.println("文件不存在");
        }
        
        
       
        return s;
    }
    
	/**取得文件夹大小
	 */ 
    public static long getFileDocumentSize(File f)throws Exception  // 递归
    {
    	long size = 0;  

        File[] fileList = f.listFiles();

        for (int i = 0; i < fileList.length; i++)  

        {  

            if (fileList[i].isDirectory())  

            {  

                size = size + getFileDocumentSize(fileList[i]);  

            } else  

            {  

                size = size + fileList[i].length();  

            }  

        }  

        return size; 
    }
    
    /**转换文件大小
	 */ 
    public static String FormetFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
   
    
    /**递归求取目录文件个数
	 */ 
    public static long getlist(File f){//递归求取目录文件个数
        long size = 0;
        File flist[] = f.listFiles();
        size=flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;

    }
}