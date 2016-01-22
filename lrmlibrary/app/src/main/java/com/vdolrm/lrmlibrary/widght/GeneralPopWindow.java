package com.vdolrm.lrmlibrary.widght;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.vdolrm.lrmlibrary.R;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.utils.UIUtils;

/**通用的popwindow封装类*/
public abstract class GeneralPopWindow<T> {
	
	private PopupWindow popupWindow;
	
	private int popWidth ;
	private int popShowXoff;
	private int popShowYoff;
	
	private Drawable bgDrawable = null;

	public abstract View getRootView();
	
	public GeneralPopWindow(Context context){
		
		/*getDimension和getDimensionPixelOffset的功能类似，
		都是获取某个dimen的值，但是如果单位是dp或sp，则需要将其乘以density,如果是px，则不乘。
		getDimension返回float，getDimensionPixelOffset返回int.
		而getDimensionPixelSize则不管写的是dp还是sp还是px,都会乘以denstiy.*/
		
		//在MX4上 dimen里写80等同于这里直接写240 density=3.0
		//popWidth = 240;
		popWidth = context.getResources().getDimensionPixelSize(R.dimen.vdo_generalpopwindow_width);// 保证尺寸是根据屏幕像素密度来的 直接写80的话会很小
		popShowXoff = context.getResources().getDimensionPixelSize(R.dimen.vdo_generalpopwindow_xoff);// 保证尺寸是根据屏幕像素密度来的
		popShowYoff = context.getResources().getDimensionPixelSize(R.dimen.vdo_generalpopwindow_yoff);// 保证尺寸是根据屏幕像素密度来的
		
	}


	/**
	 * @param context
	 * @param popWidth_ popwindow的宽度 单位为dp
	 * @param popShowYoff_ popwindow的Y偏移 单位为dp
	 * @param popShowXoff_ popwindow的X偏移 单位为dp
	 */
	/*public GeneralPopWindow(Context context,int popWidth_,int popShowXoff_,int popShowYoff_,Drawable d) {
		
//		this.popWidth = popWidth_;
//		this.popShowYoff = popShowYoff_;
//		this.popShowXoff = popShowXoff_;
		
		this.popWidth = UIUtils.dip2px(popWidth_);
		this.popShowYoff = UIUtils.dip2px(popShowYoff_);
		this.popShowXoff = UIUtils.dip2px(popShowXoff_);
		this.bgDrawable = d;
		
		init();
	}*/
	
	
	/**改成手动初始化,在设置完宽度、偏移量、背景色以后调用*/
	public void init(){
		MyLog.d("popWindow.width="+popWidth+",xoff="+popShowXoff+",yoff="+popShowYoff);
		
		View view = getRootView();

		popupWindow = new PopupWindow(view,popWidth,LayoutParams.WRAP_CONTENT);

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		//ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.popwindow_bg));
		if(bgDrawable==null)
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
		else
			popupWindow.setBackgroundDrawable(bgDrawable);
	}

	

	// 下拉式 弹出 pop菜单 parent 右下角
	public void showAsDropDown(View parent) {
		if(popupWindow==null){
			return;
		}
		
		popupWindow.showAsDropDown(parent,popShowXoff,popShowYoff);
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 刷新状态
		popupWindow.update();
	}

	/**隐藏菜单*/
	public void dismiss() {
		if(popupWindow!=null)
			popupWindow.dismiss();
	}

	
	/**获取popwindow 可以给他加其他属性 消失监听、动画什么的*/
	public PopupWindow getPopupWindow() {
		return popupWindow;
	}

	public int getPopWidth() {
		return popWidth;
	}

	/**popwindow的宽度 单位为dp*/
	public void setPopWidth(int popWidth) {
		this.popWidth = UIUtils.dip2px(popWidth);
	}

	public int getPopShowXoff() {
		return popShowXoff;
	}

	/**popwindow的X偏移 单位为dp*/
	public void setPopShowXoff(int popShowXoff) {
		this.popShowXoff = UIUtils.dip2px(popShowXoff);
	}

	public int getPopShowYoff() {
		return popShowYoff;
	}
	/**popwindow的Y偏移 单位为dp*/
	public void setPopShowYoff(int popShowYoff) {
		this.popShowYoff = UIUtils.dip2px(popShowYoff);
	}

	public Drawable getBgDrawable() {
		return bgDrawable;
	}

	public void setBgDrawable(Drawable bgDrawable) {
		this.bgDrawable = bgDrawable;
	}
	
}
