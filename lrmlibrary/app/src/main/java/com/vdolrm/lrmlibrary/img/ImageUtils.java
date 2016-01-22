package com.vdolrm.lrmlibrary.img;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;

public class ImageUtils {

	/**
	 * 图片灰度处理，好使*/
	public static Bitmap toGray(Bitmap bitmapOrg){
		//Bitmap bitmapOrg = BitmapFactory.decodeByteArray(rawData, 0, rawData.length);
        //Bitmap bitmapOrg = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		Bitmap bitmapNew = bitmapOrg.copy(Config.ARGB_8888, true); 
		//Bitmap bitmapNew = bitmapOrg.copy(Config.ARGB_8888, true);
		if(bitmapNew == null){
			return null;
		}
	
        for(int i = 0;i<bitmapNew.getWidth();i++)
        {
	  for(int j =0;j<bitmapNew.getHeight();j++)
	  {	
                int col = bitmapNew.getPixel(i, j);
                int alpha = col&0xFF000000;
                int red = (col&0x00FF0000)>>16;
                int green = (col&0x0000FF00)>>8;
                int blue = (col&0x000000FF);
                int gray = (int)((float)red*0.3+(float)green*0.59+(float)blue*0.11);
                int newColor = alpha|(gray<<16)|(gray<<8)|gray;
                bitmapNew.setPixel(i, j, newColor);
                //Log.v("tag",  Integer.toHexString(col));  
	  }
        }
      
      //  sendMsg(bitmapNew);
        
        return bitmapNew;
       /* File file = new File(Environment.getExternalStorageDirectory()+File.separator+"gray"+number+".jpg");
        OutputStream out;
		try {
			out = new FileOutputStream(file);
			if(bitmapNew.compress(Bitmap.CompressFormat.JPEG, 100, out))
				Log.i("TAG", "success");
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	}


		
	public static Bitmap ConvertGrayImg(Bitmap img1)    
    {    
        int width=img1.getWidth();
        int height=img1.getHeight();    
        int[] pix = new int[width * height];    
        img1.getPixels(pix, 0, width, 0, 0, width, height);     //第三个参数width为步长，它必须为位图的宽度
            
        int alpha=0xFF<<0;    
        for (int i = 0; i < height; i++) {      
            for (int j = 0; j < width; j++) {      
                int color = pix[width * i + j]; 
                //记住这里的Alpha值应设为00，不要设为ff，不然就不是灰度图了
                int red = ((color & 0x00FF0000) >> 16);      
                int green = ((color & 0x0000FF00) >>8);      
                int blue = color & 0x000000FF;      
              //这里有些人会将三个颜色分量的值相加然后除以3，但是这会存在一些极端的情况，所以以0.3 、 0.59 、 0.11的比例来计算
              //是比较好的
                color = (int)( (float)red*0.3 + (float)green*0.59 + (float)blue*0.11);
                color = alpha | (color << 16) | (color <<8) | color;      
                pix[width * i + j] = color;    
            }    
        }    
        Bitmap result=Bitmap.createBitmap(width, height, Config.RGB_565);    
        result.setPixels(pix, 0, width, 0, 0,width, height);    
        return result;    
    }   
	
}
