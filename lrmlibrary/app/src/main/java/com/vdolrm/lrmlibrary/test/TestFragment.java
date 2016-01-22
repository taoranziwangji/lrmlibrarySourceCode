package com.vdolrm.lrmlibrary.test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.vdolrm.lrmlibrary.fragment.BaseFragment;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.net.NetCheckUtil;
import com.vdolrm.lrmlibrary.widght.LoadingPage;

public class TestFragment extends BaseFragment {

    private static final String KEY_CONTENT = "BaseFragment";
    
    /** 标志位，标志已经初始化完成 */
    private boolean isPrepared = false;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    private boolean mHasLoadedOnce = false;


    public static TestFragment newInstance(String content) {
    	TestFragment fragment = new TestFragment();
    	fragment.mContent = content;
    	
    	Bundle b = new Bundle();
		b.putString(KEY_CONTENT, content);
		fragment.setArguments(b);
		MyLog.d("创建fragment "+content);
        return fragment;
    }

    private String mContent = "???";

	private TextView text;
	private LinearLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
        
        Bundle bundle = getArguments();  
        if (bundle != null){  
        	mContent = bundle.getString(KEY_CONTENT);
        }
        
        MyLog.d("$$$$BaseFragment onCreate mContent="+mContent);
    }

   
  

	@Override
	public View createLoadedView(boolean reLoading) {
	//public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		MyLog.d("^^^^^^^^^^$$$$BaseFragment onCreateView mContent="+mContent+",layout="+layout+",text="+text);
    	//if(layout==null){//由父类判断
	        text = new TextView(getActivity());
	        text.setGravity(Gravity.CENTER);
	        text.setText(mContent + "***********");
	        text.setTextSize(20 * getResources().getDisplayMetrics().density);
	        text.setPadding(20, 20, 20, 20);
	
	        layout = new LinearLayout(getActivity());
	        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	        layout.setGravity(Gravity.CENTER);
	        layout.addView(text);
	        
	       
	        
    	/*}else{
            ViewGroup parent = (ViewGroup)layout.getParent();
            if(parent != null) {
                parent.removeView(layout);
            }
    	}*/
        
    	 isPrepared = true;
    	 if(reLoading){
    		 mHasLoadedOnce = false;
    	 }
	        lazyLoad();
      

        return layout;//因为在父类中用了全局的View v来“缓存”fragment的页面，所以这里的layout写成局部变量也没问题
	}

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }

    
	@Override
    public void lazyLoad() {
		// TODO Auto-generated method stub
    	MyLog.d("lazyLoad  "+mContent+" isPrepared="+isPrepared+",mHasLoadedOnce="+mHasLoadedOnce+",isVisible="+isVisible);
    	//这里||isVisible时只缓存当前页（前一页和后一页都不缓存，不管fragmentActivity里的onScreenPageLimit为多少）；
    	//不并上的话onScreenPageLimit为多少就缓存多少页（适用于加载时无dialog的情况（progressbar没事，因为dialog是覆盖在界面上的，bar是view里面的），
    	//因为当onScreenPageLimit小于list<fragment>.size时，加载当前页时显示出来的dialog其实是后一页的dialog!）
    	//所以加载中不管是缓存还是不缓存 推荐用progressbar 而不是dialog 
		 if (isPrepared ==false|| mHasLoadedOnce ==true || isVisible ==false  ) {//|| isVisible ==false  
	            return;
	        }
		 
		
		 if(NetCheckUtil.isNetworkConnected(this.getActivity())){
		 
	        new AsyncTask<Void, Void,Boolean>() {
	 
	            @Override
	            protected void onPreExecute() {
	                super.onPreExecute();
	                
	                show(LoadingPage.STATE_LOADING);
	            }
	 
	            @Override
	            protected Boolean doInBackground(Void... params) {
	                try {
	                    Thread.sleep(1000);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	                return true;
	            }
	 
	            @Override
	            protected void onPostExecute(Boolean isSuccess) {
	            	
	            	 show(LoadingPage.STATE_SUCCEED);//STATE_SUCCESS用的界面父类没有默认实现，而是用的抽象的createLoadedView(false)方法，即本类上面的那个方法返回的界面
	            	//show(LoadingPage.STATE_EMPTY);
	                MyLog.d("********setText "+mContent);
	                text.setText(mContent);
	                mHasLoadedOnce = true;
	                    
	            }
	        }.execute();
		 }else{
			 MyLog.d("NETERROR");
			 show(LoadingPage.STATE_NETERROR);
		 }
	}




	@Override
	public View createLoadingView() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public View createEmptyView() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public View createErrorView() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public View createNetErrorView() {
		// TODO Auto-generated method stub
		return null;
	}





	
}
