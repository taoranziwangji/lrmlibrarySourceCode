package com.vdolrm.lrmlibrary.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.vdolrm.lrmlibrary.observer.Observable;

public class SecondActivity extends Activity{

	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(new TextView(this));
	     
		
		Observable.getInstance().notifyStateChanged("testtest");
	      
	 }
}
