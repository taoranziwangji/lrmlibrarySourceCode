package com.vdolrm.lrmlibrary.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.vdolrm.lrmlibrary.BaseDrawerActivity;
import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.adapter.BaseMyAdapter;
import com.vdolrm.lrmlibrary.adapter.BasePartRefreshMyAdapter;
import com.vdolrm.lrmlibrary.adapter.BaseViewHolder;
import com.vdolrm.lrmlibrary.file.MemoryUtil;
import com.vdolrm.lrmlibrary.img.ImageLoaderUtil;
import com.vdolrm.lrmlibrary.listener.LVScrollNoLoadListener;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.net.FileDownloadMP3Util;
import com.vdolrm.lrmlibrary.net.XutilsHttpUtil;
import com.vdolrm.lrmlibrary.observer.Observable;
import com.vdolrm.lrmlibrary.observer.Observable.ObserverListener;
import com.vdolrm.lrmlibrary.task.AsyncTaskSimple;
import com.vdolrm.lrmlibrary.test.TestWeatherBean.TestBean;
import com.vdolrm.lrmlibrary.utils.UIUtils;
import com.vdolrm.lrmlibrary.utils.VersionUtil;
import com.vdolrm.lrmlibrary.widght.GeneralListViewUtil;
import com.vdolrm.lrmlibrary.widght.GeneralPopWindow;
import com.vdolrm.lrmlibrary.widght.vdoCustomListView;
import com.vdolrm.lrmlibrary.widght.vdoSoftKeyBoardSatusView;
import com.vdolrm.lrmlibrary.widght.vdoSoftKeyBoardSatusViewUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends  BaseDrawerActivity{

	private DrawerLayout mDrawerLayout;
	private ListView list_cebianlan;
	    
	private FrameLayout frame1;
	private List<myAsyncTask> list_myAsyncTask = new ArrayList<myAsyncTask>();
	private MediaPlayer mediaPlayer;
	private FileDownloadMP3Util fileDownloadMP3Util;
	private TestAdapter adapter;
	

	
	@Override
	public void init() {
		
		//MyLog.d("init2");
		frame1 = (FrameLayout)findViewById(R.id.frame_1);
		
		 mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	     list_cebianlan = (ListView) findViewById(R.id.navdrawer);
	     
	     super.init();//要写在初始化抽屉和侧边栏view的后面 
	}

	@Override
	public void initView() {
		//MyLog.d("initView2");
		setContentView(R.layout.vdo_activity_main);
	}

	@Override
	public void initActionBar() {
		super.initActionBar();
		//MyLog.d("initActionBar2");
		/*ActionBar mActionBar = getSupportActionBar();
		if(mActionBar!=null){
			mActionBar.setTitle(getString(R.string.app_name));//设置标题
			mActionBar.setDisplayHomeAsUpEnabled(true);//设置显示左侧按钮
			mActionBar.setHomeButtonEnabled(true);//设置左侧按钮可点
		}*/
	}
	
	
	
	@Override
	public void initEvent() {
		super.initEvent();
		//TODO
	//	MyLog.d("initEvent2");
		
		/*使用这个功能时需要写上
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
               重写BaseActivity中的windowFeature即可*/
		//test_softKeyBoradStatus();//测试点击edtitext输入文字时 输入法正好弹出在某个按钮（如登录）的下边	
		
		/*list_myAsyncTask.clear();//测试asynctask是否能同时执行大于5个，并且测试onCannel能否被调用(双击退出时会调用 在onDestroy中写了操作)
		for(int i=0;i<10;i++){
			test_asyncTask();
		}*/
		
		/*findViewById(com.vdolrm.lrmlibrary.R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				test_fileDownloadAndCacheAndPlay();//测试用xutils下载并缓存音频并播放
			}
		});*/
		
		
	//	test_listview();//测试封装好的adapter和viewholder以及下拉刷新和加载更多 ;第二个适配器为测试局部刷新
		
	//	test_xutilsHttp();
		
	//	test_imageloader(null,((ImageView)findViewById(R.id.imageView1)),"http://c.hiphotos.baidu.com/image/pic/item/7acb0a46f21fbe09f9d1479869600c338644adab.jpg");
		
		test_drawerLayout();//测试侧边栏里边的内容（list+adapter 可以改成scrollview）
		
		//测试主界面是fragmentactivity 菜单在下边
		//Intent in  = new Intent(this,TestMainFragmentActivity.class);
		//startActivity(in);
		
		//测试主界面是fragmentactivity 菜单在上边
		//Intent in  = new Intent(this,TestTabFragmentActivity.class);
		//startActivity(in);
		
		//测试引导页的小圆圈切换
		//Intent in  = new Intent(this,TestPageIndicatorFragmentActivity.class);
		//startActivity(in);
		
		//测试观察者模式
		//test_observer();
		
		//测试轮播图
		//Intent i = new Intent(this,TestLunboFragmentActivity.class);
		//startActivity(i);
		
		//测试可折叠的listview(expandableListview)
		//Intent in  = new Intent(this,TestExpandableLVActivity.class);
		//startActivity(in);
		
		//测试大图裁剪
		//Intent in = new Intent(this,TestBigPicShowActivity.class);
		//startActivity(in);
		
		//测试popwindow
		//test_popwindow();
		
		//测试listview的item带按钮和状态的（比如播放、暂停什么的）
		//Intent in = new Intent(this,TestListViewStateActivity.class);
		//startActivity(in);
	}
	



	private void test_popwindow() {
		// TODO Auto-generated method stub
		
		final GeneralPopWindow<String> pop = new GeneralPopWindow<String>(this) {
			@Override
			public View getRootView() {
				// TODO Auto-generated method stub
				final ArrayList<String> list = new ArrayList<String>();
				String[] array = new String[] { "停发", "延期", "删除", "备注", "修改","add1","add2" };
				for (String s : array)
					list.add(s);
				
				View v = UIUtils.inflate(R.layout.vdo_test_popmenu);
				ListView listView = (ListView) v.findViewById(R.id.listView);
				listView.setAdapter(new BaseMyAdapter<String>(MainActivity.this,list,android.R.layout.simple_list_item_1) {
					@Override
					public void convert(BaseViewHolder vh, String item,
							int position) {
						// TODO Auto-generated method stub
						TextView tv = vh.getView(android.R.id.text1);
						tv.setText(item);
					}
				});
				listView.setFocusableInTouchMode(true);
				listView.setFocusable(true);
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
						dismiss();
					}
				});
				return v;
			}
		};
		
//		pop.setPopWidth(80);
//		pop.setPopShowXoff(5);
//		pop.setPopShowYoff(10);
//		pop.setBgDrawable(new ColorDrawable(Color.parseColor("#ff0000")));
		//pop.getPopupWindow().setAnimationStyle(R.style.popwindow_AnimationFade);//设置动画效果
		pop.init();
		
		
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.showAsDropDown(v);
			}
		});
		
		
	}

	private void test_observer() {
		// TODO Auto-generated method stub
		Observable.getInstance().registerObserver(new ObserverListener() {
			@Override
			public <T> void onUpdate(T info) {
				// TODO Auto-generated method stub
				MyLog.d("测试观察者模式：收到消息"+info);
				
			}
		});
		Intent in_ob = new Intent(this,SecondActivity.class);
		startActivity(in_ob);
	}

	private void test_drawerLayout() {
		// TODO Auto-generated method stub
		String[] values = new String[]{
	            "Stop Animation (Back icon)",
	            "Stop Animation (Home icon)",
	            "Start Animation",
	            "Change Color",
	            "GitHub Page",
	            "Share",
	            "Rate"
	        };
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_1, android.R.id.text1, values);
	        list_cebianlan.setAdapter(adapter);
	        list_cebianlan.setOnItemClickListener(new OnItemClickListener() {
	            @SuppressLint("ResourceAsColor")
				@Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                                    int position, long id) {
	                switch (position) {
	                    case 0:
	                       // mDrawerToggle.setAnimateEnabled(false);
	                        //drawerArrow.setProgress(1f);
	                        break;
	                    case 1:
	                        //mDrawerToggle.setAnimateEnabled(false);
	                       // drawerArrow.setProgress(0f);
	                        break;
	                    case 2:
	                       // mDrawerToggle.setAnimateEnabled(true);
	                       // mDrawerToggle.syncState();
	                        break;
	                    case 3:
	                        /*if (b_drawerArrowColor) {
	                        	b_drawerArrowColor = false;
	                            drawerArrow.setColor(R.color.ldrawer_color);
	                        } else {
	                        	b_drawerArrowColor = true;
	                            drawerArrow.setColor(R.color.drawer_arrow_second_color);
	                        }
	                        mDrawerToggle.syncState();*/
	                        break;
	                    case 4:
	                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/IkiMuhendis/LDrawer"));
	                        startActivity(browserIntent);
	                        break;
	                    case 5:
	                      /*  Intent share = new Intent(Intent.ACTION_SEND);
	                        share.setType("text/plain");
	                        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                        share.putExtra(Intent.EXTRA_SUBJECT,
	                            getString(R.string.app_name));
	                        share.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_description) + "\n" +
	                            "GitHub Page :  https://github.com/IkiMuhendis/LDrawer\n" +
	                            "Sample App : https://play.google.com/store/apps/details?id=" +
	                            getPackageName());
	                        startActivity(Intent.createChooser(share,getString(R.string.app_name)));*/
	                        break;
	                    case 6:
	                        String appUrl = "https://play.google.com/store/apps/details?id=" + getPackageName();
	                        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
	                        startActivity(rateIntent);
	                        break;
	                }

	            }
	        });
	}

	@Override
	public DrawerLayout initDrawerLayout() {
		// TODO Auto-generated method stub
		return mDrawerLayout;
	}

	@Override
	public View initDrawerView() {
		// TODO Auto-generated method stub
		return list_cebianlan;
	}
	
	@Override
	public Drawable drawable_titleBG() {
		// TODO Auto-generated method stub
		return new ColorDrawable(Color.parseColor("#ff0000"));
	}
	
	private void test_imageloader(vdoCustomListView lv,ImageView imv,String url) {
		// TODO Auto-generated method stub
		//ImageView imageView = new ImageView(this);
		//frame1.addView(imageView);
		//String url = "http://a.hiphotos.baidu.com/image/pic/item/32fa828ba61ea8d37dec6f39930a304e241f58c4.jpg";
		 ImageLoaderUtil imageloaderUtil = ImageLoaderUtil.getImageLoaderInstance("/bnys/");
		
		 imageloaderUtil.loadImage(url, imv,new ImageLoaderUtil.MyRequestCallBack() {
			
			@Override
			public void onSuccess(String imageUri, View view, Bitmap loadedImage) {
				// TODO Auto-generated method stub
			//	MyLog.d("onSuccess imageUri="+imageUri+"");
			}
			
			@Override
			public void onStart(String imageUri, View view) {
				// TODO Auto-generated method stub
			//	MyLog.d("onStart imageUri="+imageUri+"");
			}
			
			@Override
			public void onFailure(String message, String imageUri, View view,FailReason failReason) {
				// TODO Auto-generated method stub
				MyLog.d("onFailure imageUri="+imageUri+",message="+message+",failReason="+failReason.toString());
			}
			
			@Override
			public void onCancelled(String imageUri, View view) {
				// TODO Auto-generated method stub
				MyLog.d("onCancelled imageUri="+imageUri+"");
			}
		});
		 
		// imageloaderUtil.setScrollNoLoad(lv,lv);//居然无效
		 
		 /**
		  * 第一个参数是listview，用来设置imageloader对listview的滑动时不加载的监听，不过不是预想的效果；
		  * 第二个参数是vdoCustomListView的自动加载更多监听
		  * 第三个参数是自己写的滚动时不加载的监听，实现滚动时设为默认图片
		  */
		 imageloaderUtil.setScrollNoLoad(lv,lv,new LVScrollNoLoadListener<TestListViewBean>(adapter));
		
		
	}

	private void test_xutilsHttp() {
		// TODO Auto-generated method stub
		XutilsHttpUtil<TestWeatherBean> httpUtil = new XutilsHttpUtil<TestWeatherBean>(this,TestContant.dbName,TestContant.dbVersion);
		RequestParams params = new RequestParams("UTF-8");
		params.addHeader("Content-Type","application/json");//如果json写成xml可能会报415错误
		//Map<String,String> map = new HashMap<String,String>();
		//map.put...
		//params.setBodyEntity(new StringBody(map.toString()));
		//params.addQueryStringParameter(name, value);// user this
		httpUtil.send(TestContant.url_jingweidu, null, TestWeatherBean.class, httpUtil.new MyRequestCallBack<TestWeatherBean>() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				MyLog.d("onStart");
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				// TODO Auto-generated method stub
				MyLog.d("onLoading total="+total+",current="+current+",isUploading="+isUploading);
			}

			@Override
			public void onSuccessByGson(TestWeatherBean responseInfo) {
				// TODO Auto-generated method stub
				if(responseInfo==null){
					return ;
				}
				//MyLog.d("onSuccess isFromCache="+isFromCache+","+responseInfo.toString());
				List<TestBean> list= responseInfo.getList();
				if(list!=null){
					//...
				}
			}

			@Override
			public void onFailure(Exception error, String msg,int flag) {
				// TODO Auto-generated method stub
				MyLog.d("onFailure flag="+flag+",error="+error.toString()+",msg="+msg);
			}

			@Override
			public void onNetError() {
				// TODO Auto-generated method stub
				
			}

			/*@Override
			public void onSuccessSingleDataFromCache(List list) {

			}*/


		});
		
	}

	private void test_listview() {		
		/*ListView lv = new ListView(this);//new出来不能设置宽高为fill_parent
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.FILL_PARENT);
		lp.width =  AbsListView.LayoutParams.FILL_PARENT;//没效果
		lp.height = AbsListView.LayoutParams.FILL_PARENT;//没效果
		lv.setLayoutParams(lp);
		frame1.addView(lv);*/
		
		List<TestListViewBean> list = new ArrayList<TestListViewBean>();
		for(int i=0;i<20;i++){
			TestListViewBean bean = new TestListViewBean();
			bean.setName("object"+i);
			bean.setUrl("http://a.hiphotos.baidu.com/image/pic/item/32fa828ba61ea8d37dec6f39930a304e241f58c4.jpg");
			list.add(bean);
		}
		
		View v2 = UIUtils.inflate(R.layout.vdo_refreshlistview);
		frame1.addView(v2);
		SwipeRefreshLayout swipeRefreshView = (SwipeRefreshLayout)v2.findViewById(R.id.swiperefreshview);
		final vdoCustomListView lv = ((vdoCustomListView)v2.findViewById(R.id.listview));
		adapter = new TestAdapter(this, list, R.layout.vdo_test_listview_item1,lv);//测试滑动时不加载
		//final TestAdapter_partrefresh adapter = new TestAdapter_partrefresh(this, list, R.layout.vdo_test_listview_item_part);//测试局部刷新
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//adapter.setdataChanged(position);
			}
		});
		
		//测试局部刷新
		/*adapter.setUpdateCallback(new UpdateCallback<MainActivity.TestListViewBean>() {
			@Override
			public void startProgress(TestListViewBean model, final int position) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					@Override
					public void run() {
						for(int i = 0;i<=100;i++){
							updateProgressPartly(lv,i,position);
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();
			}
		});*/
		
		//加载更多
		MyListViewUtil<TestListViewBean> root_lv = new MyListViewUtil<TestListViewBean>(this, lv, swipeRefreshView,list,adapter); 
		root_lv.listen();
		lv.setOnLoadListener(root_lv);
		lv.setOnScrollListener(lv);//不加这句不能实现自动加载更多
		
		View footview = lv.getFootView();
		if(list.size()==0 && footview!=null){
			lv.removeFooterView(footview);//list为空时去掉加载更多的按钮。注意使用时在下拉刷新方法里继续判断list大小，假如>0了要写上setCanLoadMore方法来加上footview，
		}
	
	}
	
	
/**测试局部刷新时 更新进度条的进度*/
	private void updateProgressPartly(final ListView listview,final int progress,final int position){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				int firstVisiblePosition = listview.getFirstVisiblePosition();
				int lastVisiblePosition = listview.getLastVisiblePosition();
				//position是不变的
				Log.d("lrm","position="+position+",firstVisibleP="+firstVisiblePosition+",lastVisibleP="+lastVisiblePosition);
				
				if(position>=firstVisiblePosition && position<=lastVisiblePosition){//划上去之后position就比first小了 所以下面就取不到了 不执行 达到局部刷新的目的
					View view = listview.getChildAt(position - firstVisiblePosition);
					if(view.getTag() instanceof BaseViewHolder){
						BaseViewHolder vh = (BaseViewHolder)view.getTag();
						((ProgressBar)vh.getView(R.id.test_progressbar)).setProgress(progress);
					}
				}
			}
		});
		
		
	}

	private void test_fileDownloadAndCacheAndPlay() {
		// TODO Auto-generated method stub
		if(fileDownloadMP3Util==null){
			fileDownloadMP3Util = new FileDownloadMP3Util() {//FileDownloadMP3Util为匿名内部类
				@Override
				public void netWorkError() {
					// TODO Auto-generated method stub
					MyLog.d("netWorkError");
				}
				
				@Override
				public void download_onSuccess(ResponseInfo<File> responseInfo) {
					// TODO Auto-generated method stub
					MyLog.d("onSuccess");
				}
				
				@Override
				public void download_onStart() {
					// TODO Auto-generated method stub
					MyLog.d("onStart");
				}
				
				@Override
				public void download_onLoading(long total, long current, boolean isUploading) {
					// TODO Auto-generated method stub
					MyLog.d("onLoading total="+total+",current="+current+",isUploading="+isUploading);
				}
				
				@Override
				public void download_onFailure(HttpException error, String msg) {
					// TODO Auto-generated method stub
					MyLog.d("onFailure error="+error+",msg="+msg);
				}
				
				@Override
				public void onMediaCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					MyLog.d("onMediaCompletion mp="+mp);
				}
	
				@Override
				public void onMediaStartFirst(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mediaPlayer = mp;
				}
			};
		}
		fileDownloadMP3Util.checkCacheAndDownload(this,1,MemoryUtil.getRootPath(this)+"/bnys/", MemoryUtil.getRootPath(this)+"/bnys/test.mp3",
					"http://music-zhizhi.oss-cn-beijing.aliyuncs.com/%E6%BC%82%E6%B4%8B%E8%BF%87%E6%B5%B7%E6%9D%A5%E7%9C%8B%E4%BD%A0.mp3",TestContant.dbName,TestContant.dbVersion);
		
	}

	/**测试asynctask*/
	  @SuppressLint("NewApi")
	  private void test_asyncTask() {
		  myAsyncTask myAsync = new myAsyncTask();
		  list_myAsyncTask.add(myAsync);
		  if(VersionUtil.getAndroidVerson() <=11){
			  myAsync.execute();
			}else{
			  myAsync.executeOnExecutor(myAsyncTask.getExecutorServiceInstance());
			}
	  }


	class myAsyncTask extends AsyncTaskSimple<Object,String>{//AsyncTaskSimple<String>为匿名内部类
		  public myAsyncTask(){
			super();
		  }
		@Override
		public String doInBackground2(Object... params) {
			// TODO Auto-generated method stub
			if(params==null || params.length>0){
				MyLog.d("params0="+params[0]);
			}
			if(isCancelled()==false){
				String a = "a" ;
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return a;
			}else{
				return "";
			}
			
		}

		@Override
		public void onPostExecute2(String result) {
			// TODO Auto-generated method stub
			MyLog.d(result);
		}
		@Override
		public void onCanceled() {
			// TODO Auto-generated method stub
			MyLog.d("oncanceled");
		}
		 
	 }
	


	/**测试点击edtitext输入文字时 输入法正好弹出在某个按钮（如登录）的下边*/
	private void test_softKeyBoradStatus() {
		// TODO Auto-generated method stub
		 final vdoSoftKeyBoardSatusView softKeyBoardSatusView;
		final Button btn;
		final ScrollView scroll;
		 final TextView forget;
		View view_testsoftkeybrad = UIUtils.inflate(R.layout.vdo_test_softkeyboard);
		btn = (Button)view_testsoftkeybrad.findViewById(R.id.btn_login);
		scroll = (ScrollView)view_testsoftkeybrad.findViewById(R.id.scrollView1);
		forget = (TextView)view_testsoftkeybrad.findViewById(R.id.textView_forget);
		
		softKeyBoardSatusView = (vdoSoftKeyBoardSatusView)view_testsoftkeybrad.findViewById(R.id.login_soft_status_view);
		softKeyBoardSatusView.setSoftKeyBoardListener(new vdoSoftKeyBoardSatusViewUtil() {
			@Override
			public View getWillHideView() {
				// TODO Auto-generated method stub
				return forget;
			}
			
			@Override
			public View getSoftKeyBoradUnderThisView() {
				// TODO Auto-generated method stub
				return btn;
			}
			
			@Override
			public ScrollView getScrollView() {
				// TODO Auto-generated method stub
				return scroll;
			}
		});
		
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in2 = new Intent(MainActivity.this,TestTabFragmentActivity.class);
				startActivity(in2);
			}
		});
		
		frame1.addView(view_testsoftkeybrad);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		for(myAsyncTask myAsync :list_myAsyncTask){
			if(myAsync!=null && myAsync.isCancelled()==false){
				myAsync.cancel(true);//不会立即停止线程。但当调用cancel()后，在doInBackground（）return后 我们将会调用onCancelled(Object) 不在调用onPostExecute(Object)
			}
		}
		
		if(mediaPlayer!=null){
			try {
				if(mediaPlayer.isPlaying()){
					mediaPlayer.pause();
				}
				mediaPlayer.stop();
				mediaPlayer.release();
				mediaPlayer=null;
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		if(fileDownloadMP3Util!=null &&fileDownloadMP3Util.getDownloadHandler()!=null && !fileDownloadMP3Util.getDownloadHandler().isCancelled()){
			fileDownloadMP3Util.getDownloadHandler().cancel();
		}
		
		super.onDestroy();
	}

	
	class TestAdapter extends BaseMyAdapter<TestListViewBean>{
		private int position;
		private vdoCustomListView listview;
		
		public TestAdapter(Context ctx, List<TestListViewBean> lists, int layoutId,vdoCustomListView listview) {//父类只有多个参数的构造方法，所以子类也必须重写自己的构造方法
			super(ctx, lists, layoutId);
			this.listview = listview;
		}

		@Override
		public void convert(BaseViewHolder vh, TestListViewBean item,int position) {
			TextView tv = vh.getView(R.id.textView1);
			ImageView imv = vh.getView(R.id.imageView1);
			tv.setText(item.getName());
			
			if(isScrolling==false){
				test_imageloader(listview,imv, item.getUrl());
			}else{
				imv.setImageResource(R.drawable.ic_launcher);
			}
			
			if(this.position== position){
				tv.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
			}else{
				tv.setTextColor(getResources().getColor(android.R.color.black));
			}
		}
		
		public void setdataChanged(int paramInt){
		    this.position = paramInt;
		    notifyDataSetChanged();
		  }
		
	}
	
	
	class TestAdapter_partrefresh extends BasePartRefreshMyAdapter<TestListViewBean>{
		public TestAdapter_partrefresh(Context ctx,List<TestListViewBean> lists, int layoutId) {
			super(ctx, lists, layoutId);
		}

		@Override
		public void convert(BaseViewHolder vh, final TestListViewBean item,
				final int position) {
			Button btn = vh.getView(R.id.test_button);
			ProgressBar bar = vh.getView(R.id.test_progressbar);
			bar.setProgress(0);//必须加上这句做初始化 否则还是会有复用的情况
			btn.setText(item.getName());
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startListenProgress(item, position);
				}
			});
		}
		
	}

	
	class MyListViewUtil<T> extends GeneralListViewUtil{
		
		private SwipeRefreshLayout mSwipeRefreshLayout;
		private List<TestListViewBean> list;
		private BaseAdapter adapter;
		private ListView listview;

		public MyListViewUtil(Context context, ListView listview,SwipeRefreshLayout mSwipeRefreshLayout, List<TestListViewBean> list,BaseAdapter adapter,int...color) {
			super(context, listview, mSwipeRefreshLayout, list,adapter,color);

			this.listview = listview;
			this.mSwipeRefreshLayout = mSwipeRefreshLayout; 
			this.list = list;
			this.adapter = adapter;
			
			
		}

		@SuppressLint("NewApi")
		@Override
		public void onLoadMore() {
			if(listview ==null || listview instanceof vdoCustomListView ==false){
				return ;
			}
			
				//模拟请求数据 //没写网络判断。。
					AsyncTaskSimple<Void, Void> asy = new AsyncTaskSimple<Void, Void>() {
						@Override
						public Void doInBackground2(Void... params) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return null;
						}
						@Override
						public void onPostExecute2(Void result) {
							for(int i=0;i<10;i++){
								TestListViewBean bean = new TestListViewBean();
								bean.setName("onLoadMore"+i);
								bean.setUrl("http://d.hiphotos.baidu.com/image/pic/item/3c6d55fbb2fb4316bee32e8523a4462308f7d355.jpg");
								list.add(bean);
							}
							if(adapter!=null){
								adapter.notifyDataSetChanged();
							}
							((vdoCustomListView)listview).onLoadMoreComplete();
						}
						@Override
						public void onCanceled() {
						}
					};
					if(VersionUtil.getAndroidVerson()  > 11) {
						asy.executeOnExecutor(AsyncTaskSimple.getExecutorServiceInstance());   //AsyncTask默认最大只支持同时有5个AsyncTask同时执行，想要不受限制需要创建线程池。详情参见：http://blog.csdn.net/hitlion2008/article/details/7983449
					}else{
						asy.execute();
					} 
			
		}

		@Override
		public void netError() {
			MyLog.d("netError");
		}

		@SuppressLint("NewApi")
		@Override
		public void refresh() {
			
					//模拟请求数据
					AsyncTaskSimple<Void, Void> asy = new AsyncTaskSimple<Void, Void>() {
						@Override
						public Void doInBackground2(Void... params) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							return null;
						}
						@Override
						public void onPostExecute2(Void result) {
							if(mSwipeRefreshLayout!=null &&mSwipeRefreshLayout.isRefreshing()){
								mSwipeRefreshLayout.setRefreshing(false);
							}
						}
						@Override
						public void onCanceled() {
						}
					};
					if(VersionUtil.getAndroidVerson()  > 11) {
						asy.executeOnExecutor(AsyncTaskSimple.getExecutorServiceInstance());   //AsyncTask默认最大只支持同时有5个AsyncTask同时执行，想要不受限制需要创建线程池。详情参见：http://blog.csdn.net/hitlion2008/article/details/7983449
					}else{
						asy.execute();
					} 
		}

		
	}
	
	
	
	class TestListViewBean{
		private String name;
		private String url;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
		
	}



	@Override
	public void bundleInOnCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	}



	



	
}
