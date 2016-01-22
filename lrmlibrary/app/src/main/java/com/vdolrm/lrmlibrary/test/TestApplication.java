package com.vdolrm.lrmlibrary.test;

import com.vdolrm.lrmlibrary.BaseApplication;
import com.vdolrm.lrmlibrary.file.MemoryUtil;
import com.vdolrm.lrmlibrary.utils.CrashHandler;
import com.vdolrm.lrmlibrary.utils.UIUtils;

public class TestApplication extends BaseApplication {

	/*@Override
	public BaseApplication getapplication() {
		// TODO Auto-generated method stub
		return new TestApplication();
	}*/

	@Override
	public String getCrashPath() {
		// TODO Auto-generated method stub
		//return UIUtils.getRootPath()+"/xxx/CrashInfos";
		return MemoryUtil.getExternalRootPath()+"/xxx/CrashInfos";
	}

	@Override
	public Class<?> getFirstActivity() {
		// TODO Auto-generated method stub
		return MainActivity.class;
	}

	@Override
	public void initOther() {
		// TODO Auto-generated method stub
		 //区别：上线的没有把崩溃信息保存到本地，并且崩溃后重启应用；
	    //    没写上线的 把崩溃信息保存到了SD卡，崩溃后完全退出应用
	   CrashHandler crashHandler = CrashHandler.getInstance(getCrashPath(),getFirstActivity());
    //    CrashHandler_shangxian crashHandler = CrashHandler_shangxian.getInstance();
      
	//    crashHandler.init(this) ;
		
		
		
		
		
	}

	@Override
	public int iconForQuickProgram() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void destroyOther() {
		// TODO Auto-generated method stub
		
	}
	
	
	 

}
