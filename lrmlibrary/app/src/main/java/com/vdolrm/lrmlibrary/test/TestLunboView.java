package com.vdolrm.lrmlibrary.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.img.ImageLoaderUtil;
import com.vdolrm.lrmlibrary.img.ImageLoaderUtil.MyRequestCallBack;
import com.vdolrm.lrmlibrary.utils.UIUtils;

/**测试轮播图的适配器*/
public class TestLunboView extends DefaultSliderView {
	private String url;

	public TestLunboView(Context context,String url) {
		super(context);
		this.url = url;
	}
	

	@Override
	public void loadByPicasso(final View v, ImageView targetImageView) {
		// TODO Auto-generated method stub
		//super.loadByPicasso(v, targetImageView);
		
		 ImageLoaderUtil imageLoader = ImageLoaderUtil.getImageLoaderInstance("/bnys/");
         imageLoader.loadImage(url, targetImageView, new MyRequestCallBack() {
				
				@Override
				public void onSuccess(String imageUri, View view, Bitmap loadedImage) {
					// TODO Auto-generated method stub
					if (v.findViewById(R.id.loading_bar) != null) {
	                    v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
	                }
				}
				
				@Override
				public void onStart(String imageUri, View view) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFailure(String message, String imageUri, View view,
						FailReason failReason) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onCancelled(String imageUri, View view) {
					// TODO Auto-generated method stub
					
				}
			});
	}
	
	

}
