package com.vdolrm.lrmlibrary.file;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.ActivityManager;
import android.content.Context;

import com.vdolrm.lrmlibrary.utils.UIUtils;

public class MemoryUtil {

	// 获得可用的内存
    public static long getmem_UNUSED(Context mContext) {
        long MEM_UNUSED;
	// 得到ActivityManager
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
	// 创建ActivityManager.MemoryInfo对象  

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);

	// 取得剩余的内存空间 

        MEM_UNUSED = mi.availMem / 1024;
        return MEM_UNUSED;
    }

    // 获得总内存
    public static long getmem_TOLAL() {
        long mTotal;
        // /proc/meminfo读出的内核信息进行解释
        String path = "/proc/meminfo";
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                content = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // beginIndex
        int begin = content.indexOf(':');
        // endIndex
        int end = content.indexOf('k');
        // 截取字符串信息

	content = content.substring(begin + 1, end).trim();
        mTotal = Integer.parseInt(content);
        return mTotal;
    }
    
    
    /**
	 * 获取存储空间的根目录，有SD卡则为外部存储空间，否则为内部存储空间
	 * 
	 * @return
	 */
    public static String getRootPath(Context context){
		String path = "";
		if(isExitsSdcard())
			path = getExternalRootPath();
		else
			//path = getDataRootPath(context);
			path = getDataRootCachePath(context);
		return path;
	}
    
    /**
	 * 获取存储空间的根目录，为外部存储空间
	 * 
	 * @return
	 */
    public static String getExternalRootPath(){
		String path  = android.os.Environment.getExternalStorageDirectory().getPath();
		return path;
	}

    
    /**并非获取/data/packagename/下的目录，废弃*/
    @Deprecated
    public static String getDataRootPath(Context context){
        String path = android.os.Environment.getDataDirectory() + "/data/" + getPackageName(context);
        return path;
    }

    /**并非获取/data/packagename/下的目录，废弃*/
    @Deprecated
    public static String getDataRootPath(){
        String path = android.os.Environment.getDataDirectory() + "/data/" + UIUtils.getContext().getPackageName();
        return path;
    }

    /**获取/data/packagename/下自定义名字name的目录,有的话会直接返回，没有的话会创建（但文件夹会带app_前缀）*/
    public static String getDataRootPath(Context context,String name){
        String path = context.getDir(name,Context.MODE_PRIVATE).toString();
        return path;
    }

    /**获取/data/packagename/下自定义名字name的目录,有的话会直接返回，没有的话会创建（但文件夹会带app_前缀）*/
    public static String getDataRootPath(String name){
        String path = UIUtils.getContext().getDir(name, Context.MODE_PRIVATE).toString();
        return path;
    }


    /**
     * 获取/data/packagename/cache目录
     *
     * @return
     */
    public static String getDataRootCachePath(Context c){
        String path = c.getCacheDir().toString();
        return path;
    }
    /**
     * 获取/data/packagename/cache目录
     *
     * @return
     */
    public static String getDataRootCachePath(){
        String path = UIUtils.getContext().getCacheDir().toString();
        return path;
    }


    private static String getPackageName(Context context){
        if(context!=null)
		    return context.getPackageName();
        return "";
	}
    
    
    /**
	 * 判断Sdcard是否存在
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}
}
