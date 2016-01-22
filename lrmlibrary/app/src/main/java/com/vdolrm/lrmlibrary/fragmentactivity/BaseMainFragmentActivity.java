package com.vdolrm.lrmlibrary.fragmentactivity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.widght.PagerSlidingTabStrip.IconTabProvider;
/**fragmentactivity的主要基类，可实现标签在底部、上部 并带滑动切换动画的功能*/
public abstract class BaseMainFragmentActivity extends BaseFragmentActivity {
	
	/**获取viewpager对象 由子类赋值*/
	public ViewPager mViewPager;
	
	private FragmentPagerAdapter mAdapter;
	
	private List<Fragment> list_mFragments = new ArrayList<Fragment>();
	private List<String> list_pageTitle_name = new ArrayList<String>();
	private List<Integer> list_pageTitle_icon = new ArrayList<Integer>();
	/**使用list<fragment>了和直接抽象出getitem，经测试，性能应该是一样的。
	 * 调用basefragment的newinstance方法并不会调用fragment的onCreate和onCreateView方法，
	 * 这两个方法是在fragmentpageradater的getItem中调用的（getItem和fragment中的onCreate只会调用一次，
	 * 之后只要展示过 再展示的话 假如onScreenPageLimit为1只会调用onCreateView，为list.size则onCreateView都不会执行。
	 * limit为1时则只初始化第0页和第1页（调用onCreate和onCreateView），为list.size时则调用所有fragment页的onCreate和onCreateView
	 * 
	 * 也就是说想要设置fragment的初始化页数只需要设置onScreenPageLimit就行，1为只初始化当前页和隔壁页，list.size为初始化所有页*/
	//public abstract Fragment fragmentPagerAdapter_getItem(int position);
	
	/**标签显示的是文字*/
	public static final int ADAPTER_TXT = 0;
	/**标签显示的是图片*/
	public static final int ADAPTER_ICON = 1;
	/**标签显示的是linearlayout自定义的东西 即TestMainFragmentActivity的法1*/
	public static final int ADAPTER_DEFAULT = 2;
	//public static final int ADAPTER_TXTICON = 2;
	
	/**OnPageChangeListener中执行的方法*/
		//public abstract void onFragmentPageSelected(int position);
	/**要缓存页面的个数，建议不要设置太大 一般传1就好 。但是假如不全缓存的话 会执行多次onCreateView,所以假如有在onCreateView中有请求网络数据等操作时会重复执行，所以可在fragment中添加标记位来解决*/
		public abstract int onScreenPageLimit();
	/**要使用哪个adapter,ADAPTER_TXT为标签显示文字的adapter,ADAPTER_ICON为标签显示图片的adapter*/
		public abstract int onWhichAdapter();
	
	
	/**获取Fragments的集合, 由子类添加 */
		public abstract List<Fragment> getList_Fragment(List<Fragment> list_mFragments);
	/**获取标签名字的集合, 由子类添加  假如使用图片可传空*/
		public abstract List<String> getList_TabTitle(List<String> list_pageTitle_name);
	/**获取标签图片的集合, 由子类添加  假如使用文字可为空*/
		public abstract List<Integer> getList_TabIcon(List<Integer> list_pageTitle_icon);
	
	@Override
	public void initEvent(){	
		
		MyLog.d("initEvent");
		
			//list_mFragments.addAll(getList_Fragment(list_mFragments));
			list_mFragments = getList_Fragment(list_mFragments);
			if(list_mFragments==null){
				list_mFragments = new ArrayList<Fragment>();
			}
			
			//list_pageTitle_name.addAll(getList_TabTitle(list_pageTitle_name));//不能用addAll方法，因为假如子类实现getList_Fragment等三个抽象方法时并没有给形参直接赋值而是采用给形参addAll方法添加数据再返回的话，那么这里得到的数据会翻倍，因为list的传递是引用传递
			list_pageTitle_name = getList_TabTitle(list_pageTitle_name);
			
			if(list_pageTitle_name==null){
				list_pageTitle_name = new ArrayList<String>();
			}
			MyLog.d("pageTitle_name...size="+list_pageTitle_name.size());
		
			list_pageTitle_icon = getList_TabIcon(list_pageTitle_icon);
			if(list_pageTitle_icon==null){
				list_pageTitle_icon = new ArrayList<Integer>();
			}
		
		switch(onWhichAdapter()){
		case ADAPTER_TXT:
			mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
			break;
		case ADAPTER_ICON:
			mAdapter = new MyFragmentPagerAdapter_icon(getSupportFragmentManager());
			break;
		/*case ADAPTER_TXTICON:
			mAdapter = new MyFragmentPagerAdapter_txticon(getSupportFragmentManager());
			break;*/
		default:
			mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
			break;
		}
		
		if(mViewPager ==null ){
			throw new NullPointerException("viewpager is null");
		}
		
		mViewPager.setAdapter(mAdapter);
		
		
		/*由子类自己去实现吧，因为PagerSlidingTabStrip中也内部实现了一个onpagechangelistener 并对外暴露了一个新的onpagechangelistener，有冲突
		 * mViewPager.setOnPageChangeListener(new OnPageChangeListener(){
				private int currentIndex;
				@Override
				public void onPageSelected(int position){
					onFragmentPageSelected(position);
					currentIndex = position;
				}
	
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2){
				}
	
				@Override
				public void onPageScrollStateChanged(int arg0){
				}
		});*/
		
		mViewPager.setCurrentItem(0);
		mViewPager.setOffscreenPageLimit(onScreenPageLimit());//里面的参数是你要缓存页面的个数，建议不要设置太大！(不会多次执行oncreateview)
			//如果不缓存的话每次切换都会重新执行oncreateview
		//setOffscreenPageLimit 这个方法不接受小于1的参数 当传递的参数小于1时 会设置为默认值1
		
		
		
	}

		

	private class MyFragmentPagerAdapter extends FragmentPagerAdapter{
				
		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
			/*if(list_mFragments.size()!=list_pageTitle_name.size()){
				throw new IllegalStateException("fragment's size is not equal to title's size");
			}*/
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return list_pageTitle_name.get(position);
			//return super.getPageTitle(position);
		}

		@Override
		public int getCount() {
			switch(onWhichAdapter()){
			case ADAPTER_TXT:
				return list_pageTitle_name.size();
			case ADAPTER_ICON:
				return list_pageTitle_icon.size();
			default:
				return list_mFragments.size();
			}
			//return list_mFragments.size();
		}

		//getItem并不是每次滑动到这页的时候都调用，假如之前已经调用过了 就不会再调用了 所以不用担心这里写new的话会new出很多对象. 详情参考http://bbs.51cto.com/thread-1084869-1.html
		//所以不使用list<fragment>了，直接抽象出getitem，这样可以防止一进来时把所有fragment都初始化，节约性能
		@Override
		public Fragment getItem(int position) {
			MyLog.d("FragmentPagerAdapter getItem"+position);
			return list_mFragments.get(position);
			//return fragmentPagerAdapter_getItem(position);
		}

		

	}
		
	
	
	private class MyFragmentPagerAdapter_icon extends FragmentPagerAdapter implements IconTabProvider{
		
		public MyFragmentPagerAdapter_icon(FragmentManager fm) {
			super(fm);
			/*if(list_mFragments.size()!=list_pageTitle_name.size()){
				throw new IllegalStateException("fragment's size is not equal to title's size");
			}*/
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return list_pageTitle_name.get(position);
		}

		@Override
		public int getCount() {
			//return list_mFragments.size();
			switch(onWhichAdapter()){
			case ADAPTER_TXT:
				return list_pageTitle_name.size();
			case ADAPTER_ICON:
				return list_pageTitle_icon.size();
			default:
				return list_pageTitle_name.size();
			}
		}

		@Override
		public Fragment getItem(int position) {
			return list_mFragments.get(position);
			//return fragmentPagerAdapter_getItem(position);
		}

		@Override
		public int getPageIconResId(int position) {
			// TODO Auto-generated method stub
			return list_pageTitle_icon.get(position);
		}

	}
		
	
	
	
	
	/*private class MyFragmentPagerAdapter_txticon extends FragmentPagerAdapter implements TxtandIconTabProvider{
		
		public MyFragmentPagerAdapter_txticon(FragmentManager fm) {
			super(fm);
			if(list_mFragments.size()!=list_pageTitle_name.size()){
				throw new IllegalStateException("fragment's size is not equal to title's size");
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return list_pageTitle_name.get(position);
		}

		@Override
		public int getCount() {
			return list_mFragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return list_mFragments.get(position);
		}

		@Override
		public int getPageIconResId(int position) {
			// TODO Auto-generated method stub
			return list_pageTitle_icon.get(position);
		}

		@Override
		public String getPageTxt(int position) {
			// TODO Auto-generated method stub
			return list_pageTitle_name.get(position);
		}

	}*/

	
}
