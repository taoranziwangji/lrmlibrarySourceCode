package com.vdolrm.lrmlibrary.widght;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vdolrm.lrmlibrary.log.MyLog;


/**
 * 
 * 
 * ListView下拉刷新和加载更多 假如只实现加载更多的方法则只需setOnLoadListener
 * 
 * <strong>变更说明:</strong>
 * <p>默认如果设置了OnRefreshListener接口和OnLoadMoreListener接口，<br>并且不为null，则打开这两个功能了。
 * <p>剩余两个Flag：mIsAutoLoadMore(是否自动加载更多)和
 * <br>mIsMoveToFirstItemAfterRefresh(下拉刷新后是否显示第一条Item)
 * 
 *
 */
public class vdoCustomListView extends ListView implements OnScrollListener {

	/**  显示格式化日期模板   */
	//private final static String DATE_FORMAT_STR = "yyyy年MM月dd日 HH:mm";
	
	/**  实际的padding的距离与界面上偏移距离的比例   */
	//private final static int RATIO = 3;
	
	//private final static int RELEASE_TO_REFRESH = 0;
	//private final static int PULL_TO_REFRESH = 1;
	//private final static int REFRESHING = 2;
	//private final static int DONE = 3;
	//private final static int LOADING = 4;
	
	/**  加载中   */
	private final static int ENDINT_LOADING = 1;
	/**  手动完成刷新   */
	private final static int ENDINT_MANUAL_LOAD_DONE = 2;
	/**  自动完成刷新   */
	private final static int ENDINT_AUTO_LOAD_DONE = 3;
	
	/**    0:RELEASE_TO_REFRESH;
	 * <p> 1:PULL_To_REFRESH;
	 * <p> 2:REFRESHING;
	 * <p> 3:DONE;
	 * <p> 4:LOADING */
//	private int mHeadState;
	/**    0:完成/等待刷新 ;
	 * <p> 1:加载中  */
	private int mEndState;
	
	// ================================= 功能设置Flag ================================
	
	/**  可以加载更多？   */
	private boolean mCanLoadMore = false;
	/**  可以下拉刷新？   */
//	private boolean mCanRefresh = false;
	/**  可以自动加载更多吗？（注意，先判断是否有加载更多，如果没有，这个flag也没有意义）   */
	private boolean mIsAutoLoadMore = true;
	/** 下拉刷新后是否显示第一条Item    */
//	private boolean mIsMoveToFirstItemAfterRefresh = false;
	
	public boolean isCanLoadMore() {
		return mCanLoadMore;
	}
	
	public void setCanLoadMore(boolean pCanLoadMore) {
		mCanLoadMore = pCanLoadMore;
		if(mCanLoadMore && getFooterViewsCount() == 0){
			addFooterView();
		}
		
		//20150508 lrmadd 解决第一次请求数据时list size为0，但加载更多按钮虽然不显示 但是能点的问题
//		if(mCanLoadMore==false && getFooterViewsCount()>0){
//			removeFooterView(mEndRootView);
//		}
	}
	
	
	public boolean isAutoLoadMore() {
		return mIsAutoLoadMore;
	}

	public void setAutoLoadMore(boolean pIsAutoLoadMore) {
		mIsAutoLoadMore = pIsAutoLoadMore;
	}
		
	
	
	// ============================================================================

	private LayoutInflater mInflater;

	//private LinearLayout mHeadView;
	//private TextView mTipsTextView;
//	private TextView mLastUpdatedTextView;
	//private ImageView mArrowImageView;
	//private ProgressBar mProgressBar;
	
	private View mEndRootView;
	private ProgressBar mEndLoadProgressBar;
	private TextView mEndLoadTipsTextView;

	/**  headView动画   */
	private RotateAnimation mArrowAnim;
	/**  headView反转动画   */
	private RotateAnimation mArrowReverseAnim;
 
	/** 用于保证startY的值在一个完整的touch事件中只被记录一次    */
//	private boolean mIsRecored;

	//private int mHeadViewWidth;
	//private int mHeadViewHeight;

//	private int mStartY;
//	private boolean mIsBack;
	
//	private int mFirstItemIndex;
	private int mLastItemIndex;
	private int mCount;
	private boolean mEnoughCount;//足够数量充满屏幕？ 
	
	//private OnRefreshListener mRefreshListener;
	private OnLoadMoreListener mLoadMoreListener;

	public vdoCustomListView(Context pContext, AttributeSet pAttrs) {
		super(pContext, pAttrs);
		init(pContext);
	}

	public vdoCustomListView(Context pContext) {
		super(pContext);
		init(pContext);
	}

	public vdoCustomListView(Context pContext, AttributeSet pAttrs, int pDefStyle) {
		super(pContext, pAttrs, pDefStyle);
		init(pContext);
	}

	/**
	 * 初始化操作
	 * @param pContext 
	 * @date 2013-11-20 下午4:10:46
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void init(Context pContext) {
		setCacheColorHint(pContext.getResources().getColor(android.R.color.transparent));
		mInflater = LayoutInflater.from(pContext);

	//	addHeadView();
		
		setOnScrollListener(this);

		initPullImageAnimation(0);
	}

	/**
	 * 添加下拉刷新的HeadView 
	 * @date 2013-11-11 下午9:48:26
	 * @change JohnWatson
	 * @version 1.0
	 */
	/*private void addHeadView() {
		mHeadView = (LinearLayout) mInflater.inflate(com.vdolrm.lrmlibrary.R.layout.custom_list_head, null);

		mArrowImageView = (ImageView) mHeadView
				.findViewById(com.vdolrm.lrmlibrary.R.id.head_arrowImageView);
		mArrowImageView.setMinimumWidth(70);
		mArrowImageView.setMinimumHeight(50);
		mProgressBar = (ProgressBar) mHeadView
				.findViewById(com.vdolrm.lrmlibrary.R.id.head_progressBar);
		mTipsTextView = (TextView) mHeadView.findViewById(
				com.vdolrm.lrmlibrary.R.id.head_tipsTextView);
		mLastUpdatedTextView = (TextView) mHeadView
				.findViewById(com.vdolrm.lrmlibrary.R.id.head_lastUpdatedTextView);

		measureView(mHeadView);
		mHeadViewHeight = mHeadView.getMeasuredHeight();
		mHeadViewWidth = mHeadView.getMeasuredWidth();
		
		mHeadView.setPadding(0, -1 * mHeadViewHeight, 0, 0);
		mHeadView.invalidate();

		Log.v("size", "width:" + mHeadViewWidth + " height:"
				+ mHeadViewHeight);

		addHeaderView(mHeadView, null, false);
		
		mHeadState = DONE;
	}*/
	
	/**
	 * 添加加载更多FootView
	 * @date 2013-11-11 下午9:52:37
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void addFooterView() {
		mEndRootView = mInflater.inflate(com.vdolrm.lrmlibrary.R.layout.vdo_custom_list_footer, null);
		mEndRootView.setVisibility(View.VISIBLE);
		mEndLoadProgressBar = (ProgressBar) mEndRootView
				.findViewById(com.vdolrm.lrmlibrary.R.id.pull_to_refresh_progress);
		mEndLoadTipsTextView = (TextView) mEndRootView.findViewById(com.vdolrm.lrmlibrary.R.id.load_more);
		mEndLoadTipsTextView.setText(com.vdolrm.lrmlibrary.R.string.vdo_p2refresh_end_load_more);
		mEndRootView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mCanLoadMore){
					if(mEndState != ENDINT_LOADING){
						// 当不能下拉刷新时，FootView不正在加载时，才可以点击加载更多。
						mEndState = ENDINT_LOADING;
						onLoadMore();
					}
				}
			}
		});
		
		addFooterView(mEndRootView);
		
		if(mIsAutoLoadMore){
			mEndState = ENDINT_AUTO_LOAD_DONE;
		}else{
			mEndState = ENDINT_MANUAL_LOAD_DONE;
		}
	}
	
	
	public View getFootView(){
		return mEndRootView;
	}

	/**
	 * 实例化下拉刷新的箭头的动画效果 
	 * @param pAnimDuration 动画运行时长
	 * @date 2013-11-20 上午11:53:22
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void initPullImageAnimation(final int pAnimDuration) {
		
		int _Duration;
		
		if(pAnimDuration > 0){
			_Duration = pAnimDuration;
		}else{
			_Duration = 250;
		}
//		Interpolator _Interpolator;
//		switch (pAnimType) {
//		case 0:
//			_Interpolator = new AccelerateDecelerateInterpolator();
//			break;
//		case 1:
//			_Interpolator = new AccelerateInterpolator();
//			break;
//		case 2:
//			_Interpolator = new AnticipateInterpolator();
//			break;
//		case 3:
//			_Interpolator = new AnticipateOvershootInterpolator();
//			break;
//		case 4:
//			_Interpolator = new BounceInterpolator();
//			break;
//		case 5:
//			_Interpolator = new CycleInterpolator(1f);
//			break;
//		case 6:
//			_Interpolator = new DecelerateInterpolator();
//			break;
//		case 7:
//			_Interpolator = new OvershootInterpolator();
//			break;
//		default:
//			_Interpolator = new LinearInterpolator();
//			break;
//		}
		
		Interpolator _Interpolator = new LinearInterpolator();
		
		mArrowAnim = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mArrowAnim.setInterpolator(_Interpolator);
		mArrowAnim.setDuration(_Duration);
		mArrowAnim.setFillAfter(true);

		mArrowReverseAnim = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mArrowReverseAnim.setInterpolator(_Interpolator);
		mArrowReverseAnim.setDuration(_Duration);
		mArrowReverseAnim.setFillAfter(true);
	}

	/**
	 * 测量HeadView宽高(注意：此方法仅适用于LinearLayout，请读者自己测试验证。)
	 * @param pChild 
	 * @date 2013-11-20 下午4:12:07
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void measureView(View pChild) {
		ViewGroup.LayoutParams p = pChild.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;

		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		pChild.measure(childWidthSpec, childHeightSpec);
	}
	
	/**
	 *为了判断滑动到ListView底部没
	 */
	@Override
	public void onScroll(AbsListView pView, int pFirstVisibleItem,
			int pVisibleItemCount, int pTotalItemCount) {
	//	MyLog.d("onScroll pFirstVisibleItem="+pFirstVisibleItem+",pVisibleItemCount="+pVisibleItemCount+",pTotalItemCount="+pTotalItemCount);
		if(mLoadMoreListener!=null){
			mLoadMoreListener.onCustomListViewScroll(pFirstVisibleItem);
		}
		
	//	mFirstItemIndex = pFirstVisibleItem;
		mLastItemIndex = pFirstVisibleItem + pVisibleItemCount - 2;
		mCount = pTotalItemCount - 2;
		if (pTotalItemCount > pVisibleItemCount ) {
			mEnoughCount = true;
//			endingView.setVisibility(View.VISIBLE);
		} else {
			mEnoughCount = false;
		}
	}

	/**
	 *这个方法，可能有点乱，大家多读几遍就明白了。
	 */
	@Override
	public void onScrollStateChanged(AbsListView pView, int pScrollState) {
		//MyLog.d("onScrollStateChanged mCanLoadMore="+mCanLoadMore+",mLastItemIndex="+mLastItemIndex+",mCount="+mCount+",mEndState="+mEndState+",mIsAutoLoadMore="+mIsAutoLoadMore);
		if(mCanLoadMore){// 存在加载更多功能
			if (mLastItemIndex ==  mCount && pScrollState == SCROLL_STATE_IDLE) {
				//SCROLL_STATE_IDLE=0，滑动停止
				if (mEndState != ENDINT_LOADING) {
					if(mIsAutoLoadMore){// 自动加载更多，我们让FootView显示 “更    多”
						
							// FootView显示 : 更    多  ---> 加载中...
							mEndState = ENDINT_LOADING;
							onLoadMore();
							changeEndViewByState();
						
					}else{// 不是自动加载更多，我们让FootView显示 “点击加载”
						// FootView显示 : 点击加载  ---> 加载中...
						mEndState = ENDINT_MANUAL_LOAD_DONE;
						changeEndViewByState();
					}
				}
			}
		}else if(mEndRootView != null && mEndRootView.getVisibility() == VISIBLE){
			// 突然关闭加载更多功能之后，我们要移除FootView。
			System.out.println("this.removeFooterView(endRootView);...");
			mEndRootView.setVisibility(View.GONE);
			this.removeFooterView(mEndRootView);
		}
	}

	/**
	 * 改变加载更多状态
	 * @date 2013-11-11 下午10:05:27
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void  changeEndViewByState() {
		if (mCanLoadMore) {
			//允许加载更多
			switch (mEndState) {
			case ENDINT_LOADING://刷新中
				
				// 加载中...
				if(mEndLoadTipsTextView.getText().equals(
						com.vdolrm.lrmlibrary.R.string.vdo_p2refresh_doing_end_refresh)){
					break;
				}
				mEndLoadTipsTextView.setText(com.vdolrm.lrmlibrary.R.string.vdo_p2refresh_doing_end_refresh);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.VISIBLE);
				break;
			case ENDINT_MANUAL_LOAD_DONE:// 手动刷新完成
				
				// 点击加载
				mEndLoadTipsTextView.setText(com.vdolrm.lrmlibrary.R.string.vdo_p2refresh_end_click_load_more);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.GONE);
				
				mEndRootView.setVisibility(View.VISIBLE);
				break;
			case ENDINT_AUTO_LOAD_DONE:// 自动刷新完成
				
				// 更    多
				mEndLoadTipsTextView.setText(com.vdolrm.lrmlibrary.R.string.vdo_p2refresh_end_load_more);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.GONE);
				
				mEndRootView.setVisibility(View.VISIBLE);
				break;
			default:
				// 原来的代码是为了： 当所有item的高度小于ListView本身的高度时，
				// 要隐藏掉FootView，大家自己去原作者的代码参考。
				
				if (mEnoughCount) {					
					mEndRootView.setVisibility(View.VISIBLE);
				} else {
					mEndRootView.setVisibility(View.GONE);
				}
				break;
			}
		}
	}
	
	/**
	 *原作者的，我没改动，请读者自行优化。
	 */
	public boolean onTouchEvent(MotionEvent event) {
		

		return super.onTouchEvent(event);
	}

	

	/**
	 * 下拉刷新监听接口
	 * @date 2013-11-20 下午4:50:51
	 * @change JohnWatson
	 * @version 1.0
	 */
	public interface OnRefreshListener {
		public void onRefresh();
	}
	
	/**
	 * 加载更多监听接口
	 * @date 2013-11-20 下午4:50:51
	 * @change JohnWatson
	 * @version 1.0
	 */
	public interface OnLoadMoreListener {
		public void onLoadMore();
		
		public void onCustomListViewScroll(int item);
	}
	
	

	public void setOnLoadListener(OnLoadMoreListener pLoadMoreListener) {
		if(pLoadMoreListener != null){
			mLoadMoreListener = pLoadMoreListener;
			mCanLoadMore = true;
			if(mCanLoadMore && getFooterViewsCount() == 0){
				addFooterView();
			}
		}
	}
	
	
	

	/**
	 * 正在加载更多，FootView显示 ： 加载中...
	 * @date 2013-11-20 下午4:35:51
	 * @change JohnWatson
	 * @version 1.0
	 */
	public void onLoadMore() {
		if (mLoadMoreListener != null) {
			// 加载中...
			mEndLoadTipsTextView.setText(com.vdolrm.lrmlibrary.R.string.vdo_p2refresh_doing_end_refresh);
			mEndLoadTipsTextView.setVisibility(View.VISIBLE);
			mEndLoadProgressBar.setVisibility(View.VISIBLE);
			
			mLoadMoreListener.onLoadMore();
		}
	}

	/**
	 * 加载更多完成 
	 * @date 2013-11-11 下午10:21:38
	 * @change JohnWatson
	 * @version 1.0
	 */
	public void onLoadMoreComplete() {
		if(mIsAutoLoadMore){
			mEndState = ENDINT_AUTO_LOAD_DONE;
		}else{
			mEndState = ENDINT_MANUAL_LOAD_DONE;
		}
		changeEndViewByState();
	}
	
	/**
	 * 主要更新一下刷新时间啦！
	 * @param adapter
	 * @date 2013-11-20 下午5:35:51
	 * @change JohnWatson
	 * @version 1.0
	 */
	public void setAdapter(BaseAdapter adapter) {
		
		super.setAdapter(adapter);
	}

}

