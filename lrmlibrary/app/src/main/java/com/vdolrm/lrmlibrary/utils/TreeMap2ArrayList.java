package com.vdolrm.lrmlibrary.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import com.vdolrm.lrmlibrary.log.MyLog;

import android.util.Log;

public class TreeMap2ArrayList {

	public static ArrayList<Object> getList(TreeMap<Integer, Object> tree){
		ArrayList<Object> list = new ArrayList<Object>();
		if(tree==null){
			MyLog.d("lrm","return null");
			return list;
		}
		Iterator<Integer> iterator = tree.keySet().iterator();
		list.clear();
		while (iterator.hasNext()) {
			try {

				Object ob = iterator.next();
				Object val = (Object) tree.get(ob);
				list.add(val);
			} catch (Exception e) {
			}
		}
		
		return list;
		
	}
}
