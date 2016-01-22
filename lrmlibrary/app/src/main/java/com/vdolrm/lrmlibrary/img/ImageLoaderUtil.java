package com.vdolrm.lrmlibrary.img;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.utils.UIUtils;

public class ImageLoaderUtil {
	
	private static ImageLoaderUtil loaderUtil;
	
	private ImageLoaderUtil(){
		
	}
	
	//private ImageLoaderUtil imageLoader;
	private static ImageLoader imageLoader;
	private static DisplayImageOptions options;
	

	/**
	 * @param path_cache 缓存路径 不用写跟目录  ex:/bnys/
	 * @return
	 */
	public  static ImageLoaderUtil getImageLoaderInstance(String path_cache){
		if(imageLoader==null){
			//imageLoader = new ImageLoaderUtil(path_cache);
			imageLoader = ImageLoader.getInstance();
			init(path_cache);
		}
		//return imageLoader;
		
		if(loaderUtil==null){
			loaderUtil = new ImageLoaderUtil();
		}
		return loaderUtil;
	}
	
	
	private static void init(String path_cache){
		//imageloader的配置 用在可翻页大图显示	
		//图片保存系统路径  
      File cacheDir = StorageUtils.getOwnCacheDirectory(UIUtils.getContext(), path_cache);	
      MyLog.d("cacheDir="+cacheDir);
      ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(UIUtils.getContext()).threadPoolSize(5)  
              .threadPriority(Thread.NORM_PRIORITY - 1).tasksProcessingOrder(QueueProcessingType.LIFO).denyCacheImageMultipleSizesInMemory()  
              .diskCache(new UnlimitedDiskCache(cacheDir)) // default
              .build();  
      //初始化配置  
      imageLoader.init(config);
     
      if(options==null){
	      options = new DisplayImageOptions.Builder()
	      //.showStubImage(R.drawable.empty_photo)
			.showImageForEmptyUri(R.drawable.ic_launcher)//drawableForEmptyUri
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//缩放类型
			.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true).cacheOnDisk(true)//drawableForLoadFail
			.bitmapConfig(Bitmap.Config.RGB_565)
					//.displayer(new FadeInBitmapDisplayer(1500,false,false,true))// 图片加载好后渐入的动画时间 ,假如不使用memory缓存，则把disk动画设为true,memory动画设为false
			.build();
	      
	     /* imageScaleType:
              EXACTLY :图像将完全按比例缩小的目标大小
              EXACTLY_STRETCHED:图片会缩放到目标大小完全
              IN_SAMPLE_INT:图像将被二次采样的整数倍
              IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小
              NONE:图片不会调整*/
      }
      
      	
      
	}
	
	/**
	 * 设置滚动时不加载*/
	public void setScrollNoLoad(ListView lv){
		if(lv==null || imageLoader ==null){
			return ;
		}
		
		//滚动时不加载
      	lv.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true));///两个分别表示拖动下拉条和滑动过程中暂停加载
	}
	
	/**
	 * 设置滚动时不加载 可以传另一个onscrolllistener*/
	public void setScrollNoLoad(ListView lv,OnScrollListener listener){
		if(lv==null || imageLoader ==null ){//|| listener==null
			return ;
		}
		//滚动时不加载
      	lv.setOnScrollListener(new PauseOnScrollListener(imageLoader, true, true,listener));///两个分别表示拖动下拉条和滑动过程中暂停加载
	}
	
	/**
	 * 设置滚动时不加载 可以传另2个onscrolllistener*/
	public void setScrollNoLoad(ListView lv,OnScrollListener listener,OnScrollListener listener2){
		if(lv==null || imageLoader ==null ){//|| listener==null || listener2==null
			return ;
		}
		//滚动时不加载
      	lv.setOnScrollListener(new MyLVListener(imageLoader, true, true,listener,listener2));///两个分别表示拖动下拉条和滑动过程中暂停加载
	}
	
	
	/**
	 * 显示图片，默认option样式的imageloader
	 * 注意：由于外部app的资源文件传到lrmlibrary内莫名无效(构造showImageForEmptyUri和showImageOnFail用)，所以改成在app内写明option传进来,默认无options参方法只供lrmlibrary内部使用
	 */
	public void loadImage(String mImageUrl,ImageView mImageView,final MyRequestCallBack callback){
		this.loadImage(mImageUrl, mImageView, callback,options);
	}
	
	/**
	 * 显示图片，可自定义option样式
	 */
	public void loadImage(String mImageUrl,ImageView mImageView,final MyRequestCallBack callback,DisplayImageOptions myoption) {
		// TODO Auto-generated method stub
		if(imageLoader==null || mImageUrl==null || mImageView ==null ){
			return;
		}
		
		ImageAware imageAware = new ImageViewAware(mImageView, false);//解决加载过的图片再次上翻后依然会重复加载的问题http://www.cnblogs.com/wuxilin/p/4333241.html
		
		imageLoader.displayImage(mImageUrl, imageAware, myoption, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				//progressBar.setVisibility(View.VISIBLE);
				
				if(callback!=null)
					callback.onStart(imageUri, view);
			}

			@Override
			public void onLoadingFailed(final String imageUri, View view, FailReason failReason) {
				String message = null;//message作为一个参考，假如要更改提示文案，则在回调中获取failReason.getType()做提示信息
				switch (failReason.getType()) {
				case IO_ERROR:
					message = "下载错误";
					break;
				case DECODING_ERROR:
					message = "图片无法显示";
					break;
				case NETWORK_DENIED:
					message = "网络有问题，无法下载";
					break;
				case OUT_OF_MEMORY:
					//message = "图片太大无法显示,请尝试下载到本地后查看";
					message = "图片太大无法显示,请尝试清理手机内存后再试";
					break;
				case UNKNOWN:
					message = "未知的错误";
					break;
					
				default :
					message = "未知的错误";
					break;
				}
				
				if(callback!=null)
					callback.onFailure(message,imageUri, view, failReason); 
				
				
			}

			

			@Override
			public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {
				//progressBar.setVisibility(View.GONE);
			
				if(callback!=null)
					callback.onSuccess(imageUri, view, loadedImage);
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				// TODO Auto-generated method stub
				if(callback!=null)
					callback.onCancelled(imageUri, view);
				super.onLoadingCancelled(imageUri, view);
			}
			
			
		});
	}
	
	
	/**
	 * 取消所有正在下载的任务*/
	public void cancelDisplay(){
		//imageLoader.cancelDisplayTask(imageAware);
		imageLoader.stop();
	}
	
	
	public interface MyRequestCallBack {
		
        public  void onStart(String imageUri, View view);

        public  void onCancelled(String imageUri, View view);

        public  void onSuccess(String imageUri, View view, Bitmap loadedImage);

        public  void onFailure(String message, String imageUri, View view, FailReason failReason);
        
       // public void onNetError();

	}

	 public class ImageLoaderCallBack implements MyRequestCallBack{

		@Override
		public void onStart(String imageUri, View view) {

		}

		@Override
		public void onCancelled(String imageUri, View view) {

		}

		@Override
		public void onSuccess(String imageUri, View view, Bitmap loadedImage) {

		}

		@Override
		public void onFailure(String message, String imageUri, View view, FailReason failReason) {

		}
	}
	
	
	/**
	 * 加入第三个onScrollListener监听*/
	class MyLVListener extends PauseOnScrollListener {
		
		private final OnScrollListener externalListener2;
		
		public MyLVListener(ImageLoader imageLoader, boolean pauseOnScroll,
				boolean pauseOnFling, OnScrollListener customListener, OnScrollListener customListener2) {
			super(imageLoader, pauseOnScroll, pauseOnFling, customListener);
			// TODO Auto-generated constructor stub
			
			externalListener2 = customListener2;
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			
			if (externalListener2 != null) {
				externalListener2.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			super.onScrollStateChanged(view, scrollState);
			
			if (externalListener2 != null) {
				externalListener2.onScrollStateChanged(view, scrollState);
			}
		}

		
	}
	
}
