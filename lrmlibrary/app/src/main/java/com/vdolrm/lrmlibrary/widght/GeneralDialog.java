package com.vdolrm.lrmlibrary.widght;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdolrm.lrmlibrary.R;

public class GeneralDialog {
	private View mConvertView;
	private AlertDialog ad;
	//private TextView messageView;
	//private TextView messageView2;
	private Window window;
	
	public GeneralDialog(Context context,int layoutId) {
		mConvertView = LayoutInflater.from(context).inflate(layoutId,null);  
		ad=new AlertDialog.Builder(context).create();
		ad.show();
		window = ad.getWindow();
		window.setContentView(mConvertView);
		//messageView=(TextView)window.findViewById(R.id.dialog_tv1);
		//messageView2=(TextView)window.findViewById(R.id.dialog_tv2);
	}
	
	 public View getConvertView(){
	     return mConvertView;
	 }
	 
	 public Window getWindow(){
		 return window;
	 }
 
	/*public void setMessage(String message){
		messageView.setText(message);
	}
	
	public void setMessage2(String message){
		messageView2.setText(message);
	}
	
	
	public void setClickMsg1(View.OnClickListener listener){
		
		messageView.setOnClickListener(listener);
	}
	
	public void setClickMsg2(View.OnClickListener listener){
			
			messageView2.setOnClickListener(listener);
	}
		*/
	
	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		if(ad.isShowing()){
		ad.dismiss();
		}
	}
 
}