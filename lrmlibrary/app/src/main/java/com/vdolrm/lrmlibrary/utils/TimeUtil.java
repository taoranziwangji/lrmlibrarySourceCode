package com.vdolrm.lrmlibrary.utils;

public class TimeUtil {

	/** 获取'时间'字符串 
	 * time:毫秒
	 * 返回值为min:sec**/
	public static String getStrTime(int time){
		int min = 0;
		int sec = 0;
		String strTime = "";
		time /= 1000;
		if(time < 60){
			min = 0;
			sec = time;
		}else{
			min = time / 60;
			sec = time % 60;
		}
		if(min < 10){
			strTime += "0" + min + ":" ;
		}else{
			strTime += min + ":" ;
		}
		if(sec<10){
			strTime += "0" + sec ;
		}else{
			strTime += sec ;
		}
		
		return strTime;
	}
	
	
	/** 获取'时间'字符串 
	 * time:毫秒
	 * max:99:59:59
	 * 返回值为min:sec**/
	public static String secToTime(int time) {  
        String timeStr = null;  
        int hour = 0;  
        int minute = 0;  
        int second = 0;  
        time /= 1000;
        if (time <= 0)  
            return "00:00";  
        else {  
            minute = time / 60;  
            if (minute < 60) {  
                second = time % 60;  
                timeStr = unitFormat(minute) + ":" + unitFormat(second);  
            } else {  
                hour = minute / 60;  
                if (hour > 99)  
                    return "99:59:59";  
                minute = minute % 60;  
                second = time - hour * 3600 - minute * 60;  
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);  
            }  
        }  
        return timeStr;  
    }  
  
    private static String unitFormat(int i) {  
        String retStr = null;  
        if (i >= 0 && i < 10)  
            retStr = "0" + Integer.toString(i);  
        else  
            retStr = "" + i;  
        return retStr;  
    }  
	
	
}
