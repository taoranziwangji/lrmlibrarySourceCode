package com.vdolrm.lrmlibrary.test;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.Toast;
import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;

import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.fragmentactivity.BaseFragmentActivity;
import com.vdolrm.lrmlibrary.log.MyLog;

public class TestLunboFragmentActivity extends BaseFragmentActivity implements BaseSliderView.OnSliderClickListener{

	 private  ArrayList<String> viewInfos_url;
	 private InfiniteIndicatorLayout mDefaultIndicator;
	 private InfiniteIndicatorLayout mAnimCircleIndicator;
	 private InfiniteIndicatorLayout mAnimLineIndicator;
	 
	@Override
	public void bundleInOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.vdo_test_lunboview);
	}
	@Override
	public void initActionBar() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		 viewInfos_url = new ArrayList<String>();
		 viewInfos_url.add("https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg");
		 viewInfos_url.add("https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg");
		 viewInfos_url.add("https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg");
		 viewInfos_url.add("https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg");

		 testDefaultIndicator();//空心圆
	     testAnimCircleIndicator();//实心圆
	     testAnimLineIndicator();//长线
	}
	    
	//To avoid memory leak ,you should release the res
    @Override
    protected void onPause() {
        super.onPause();
        mDefaultIndicator.stopAutoScroll();
        mAnimCircleIndicator.stopAutoScroll();
        mAnimLineIndicator.stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDefaultIndicator.startAutoScroll();
        mAnimCircleIndicator.startAutoScroll();
        mAnimLineIndicator.startAutoScroll();
    }
    
    
    private void testDefaultIndicator(){
    	mDefaultIndicator = (InfiniteIndicatorLayout)findViewById(R.id.infinite_anim_default);
    	 for(String name : viewInfos_url){
             TestLunboView sliderView = new TestLunboView(this, name);
             sliderView.setOnSliderClickListener(this);
             sliderView.getBundle().putString("extra", name);
             
             mDefaultIndicator.addSlider(sliderView);
          }
    	 mDefaultIndicator.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Top);//设置indicator的位置
    	 mDefaultIndicator.setInterval(2000);//切换时间间隔
    }
    
    private void testAnimCircleIndicator() {
        mAnimCircleIndicator = (InfiniteIndicatorLayout)findViewById(R.id.infinite_anim_circle);
        for(String name : viewInfos_url){
           TestLunboView sliderView = new TestLunboView(this, name);
           sliderView.setOnSliderClickListener(this);
           sliderView.getBundle().putString("extra", name);
           
            mAnimCircleIndicator.addSlider(sliderView);
        }
        mAnimCircleIndicator.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Right_Bottom);//设置indicator的位置
        mAnimCircleIndicator.setInterval(3000);//切换时间间隔
    }

    private void testAnimLineIndicator() {
        mAnimLineIndicator = (InfiniteIndicatorLayout)findViewById(R.id.infinite_anim_line);
        for(String name : viewInfos_url){       
        	TestLunboView sliderView = new TestLunboView(this, name);
        	 sliderView.setOnSliderClickListener(this);
        	sliderView.getBundle().putString("extra", name);
        	
        	mAnimLineIndicator.addSlider(sliderView);
        }
        mAnimLineIndicator.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center);//设置indicator的位置
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
    	MyLog.d("slider="+slider+",extra="+slider.getBundle().get("extra"));
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

}
