package com.vdolrm.lrmlibrary;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.vdolrm.lrmlibrary.log.MyLog;

/**
 * 带侧边栏抽屉的activity基类，因没有继承fragmentactivity 所以不能在这里装载fragment*/
public abstract class BaseDrawerActivity extends BaseDoubleClickExitActivity {
	
	private DrawerLayout mDrawerLayout;
    private View view_drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
   // private boolean b_drawerArrowColor;
    
    public abstract DrawerLayout initDrawerLayout();
    public abstract View initDrawerView();
    public abstract Drawable drawable_titleBG();
	
	@Override
	public void windowFeature() {
		//重写windowFeature()为空。若不重写，则看不到顶部的可滑动的菜单栏；假如写下边这句的话还需要在布局中写上paddingtop = 60dp,不用下面那句就不用写了
		 requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);//不写这句的话 魅族下边的smartBar会显示不出返回按钮 后来又显示了 不知原因(但smartbar变成了黑色)
	}

	@SuppressLint("NewApi")
	@Override
	public void initActionBar() {
		// TODO Auto-generated method stub
		 ActionBar ab = getActionBar();
	        if(ab!=null){
	        	ab.setDisplayHomeAsUpEnabled(true);
	        	ab.setHomeButtonEnabled(true);
	        	ab.setBackgroundDrawable(drawable_titleBG());
	        }
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		mDrawerLayout = initDrawerLayout();
		view_drawer = initDrawerView();
		
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		if(mDrawerLayout==null){
			return ;
		}
		
		 drawerArrow = new DrawerArrowDrawable(this) {
	            @Override
	            public boolean isLayoutRtl() {
	                return false;//false是打开侧边栏 箭头冲左。true是冲右
	            }
	        };
	        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
	            drawerArrow, R.string.drawer_open,
	            R.string.drawer_close) {

	            @SuppressLint("NewApi")
				public void onDrawerClosed(View view) {
	                super.onDrawerClosed(view);
	                invalidateOptionsMenu();
	                
	                onMyDrawerClosed(view);
	            }

	            @SuppressLint("NewApi")
				public void onDrawerOpened(View drawerView) {
	                super.onDrawerOpened(drawerView);
	                invalidateOptionsMenu();
	                
	                onMyDrawerOpen(drawerView);
	            }
	        };
	        mDrawerLayout.setDrawerListener(mDrawerToggle);
	        mDrawerToggle.syncState();
	}

	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
		 MyLog.d("mDrawerLayout="+mDrawerLayout+",view_drawer="+view_drawer);
	        if (item.getItemId() == android.R.id.home && mDrawerLayout!=null && view_drawer!=null) {//这个其实是点击左上角的图标
	        	MyLog.d("lrm","isOpen="+mDrawerLayout.isDrawerOpen(view_drawer));
	            if (mDrawerLayout.isDrawerOpen(view_drawer)) {
	                mDrawerLayout.closeDrawer(view_drawer);
	            } else {
	                mDrawerLayout.openDrawer(view_drawer);
	            }
	        }
	        return super.onOptionsItemSelected(item);
	    }

	    @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        if(mDrawerToggle!=null)
	        	mDrawerToggle.syncState();
	    }

	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        if(mDrawerToggle!=null)
	        	mDrawerToggle.onConfigurationChanged(newConfig);
	    }
	

	
	    
	    @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_BACK){
				if(mDrawerLayout!=null && view_drawer!=null){
					if (mDrawerLayout.isDrawerOpen(view_drawer)) {
		                mDrawerLayout.closeDrawer(view_drawer);
		                return true;
		            } 
				}
			
			
			return super.onKeyDown(keyCode, event);
		
			}
			return super.onKeyDown(keyCode, event);
	    }
	    
	    
		public void onMyDrawerOpen(View view){
			
		}    
		  
		public void onMyDrawerClosed(View view){
			
		}
		
		/**关闭侧边栏 */
		public void closeTheDrawer(){
			if(mDrawerLayout!=null && view_drawer!=null){
				if (mDrawerLayout.isDrawerOpen(view_drawer)) {
	                mDrawerLayout.closeDrawer(view_drawer);
	              
	            } 
			}
		}
	    
}
