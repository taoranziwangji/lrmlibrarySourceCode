package com.vdolrm.lrmlibrary.cache.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import com.vdolrm.lrmlibrary.file.MemoryUtil;
import com.vdolrm.lrmlibrary.utils.Contant;
import com.vdolrm.lrmlibrary.utils.Md5Util;

/**
 * 本地缓存
 * 
 * @author Kevin
 * 
 */
public class LocalCacheUtils {

	public static final String CACHE_PATH = Contant.mulu_file_cache;
	

	/**
	 * 从本地sdcard读图片
	 */
	public Bitmap getBitmapFromLocal(String url) {
		
		try {
			String fileName = Md5Util.getMD5(url);
			File file = new File(CACHE_PATH, fileName);

			if (file.exists()) {
				Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
						file));
				return bitmap;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 向sdcard写图片
	 * 
	 * @param url
	 * @param bitmap
	 */
	public void setBitmapToLocal(String url, Bitmap bitmap) {
		try {
			String fileName = Md5Util.getMD5(url);

			File file = new File(CACHE_PATH, fileName);

			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {// 如果文件夹不存在, 创建文件夹
				parentFile.mkdirs();
			}

			// 将图片保存在本地
			bitmap.compress(CompressFormat.JPEG, 100,
					new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
