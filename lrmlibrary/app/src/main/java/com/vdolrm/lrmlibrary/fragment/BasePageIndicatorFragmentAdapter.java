package com.vdolrm.lrmlibrary.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vdolrm.lrmlibrary.test.TestFragment;
import viewpagerindicator.IconPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class BasePageIndicatorFragmentAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

	private List<String> list_txt;
	private List<Integer> list_pic;

  //  private int mCount = CONTENT.length;

    public BasePageIndicatorFragmentAdapter(FragmentManager fm,List<String> list_txt,List<Integer> list_pic) {
        super(fm);
        this.list_txt = list_txt;
        this.list_pic = list_pic;
        
        if(list_txt==null){
        	list_txt = new ArrayList<String>();
        }
        
        if(list_pic==null){
        	list_pic = new ArrayList<Integer>();
        }
    }

    @Override
    public Fragment getItem(int position) {
       // return TestFragment.newInstance(CONTENT[position % CONTENT.length]);
    	return TestFragment.newInstance(list_txt.get(position % list_txt.size()));
    }

    @Override
    public int getCount() {
       // return mCount;
    	return list_txt.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
     // return BaseFragmentAdapter.CONTENT[position % CONTENT.length];
    	return list_txt.get(position % list_txt.size());
    }

    @Override
    public int getIconResId(int index) {
      //return ICONS[index % ICONS.length];
      return list_pic.get(index % list_pic.size());
    }

  /*  public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }*/
}
