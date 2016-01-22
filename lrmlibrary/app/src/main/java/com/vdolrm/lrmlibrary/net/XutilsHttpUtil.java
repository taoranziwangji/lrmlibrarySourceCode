package com.vdolrm.lrmlibrary.net;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.vdolrm.lrmlibrary.bean.BaseGsonBean;
import com.vdolrm.lrmlibrary.bean.EntityBase;
import com.vdolrm.lrmlibrary.log.MyLog;
import com.vdolrm.lrmlibrary.task.AsyncTaskSimple;
import com.vdolrm.lrmlibrary.utils.UIUtils;
import com.vdolrm.lrmlibrary.utils.VersionUtil;

import java.util.List;

public class XutilsHttpUtil<T extends EntityBase> {

    private long httpCacheExpiry = 1000 * 10;//LRU缓存处理，xutils默认60秒内提交返回上次成功的结果 这里默认为10s
    //private boolean isUseDb = true;//改成使用缓存的话自动调用本类中的saveDB方法
    private HttpRequest.HttpMethod httpMethod = HttpRequest.HttpMethod.GET;
    private DbUtils db;
    private Context context;

    private static final int ERROR_RUNTIME = 0;
    private static final int ERROR_DB = 1;
    private static final int ERROR_HTTP = 2;
    private static final int ERROR_OTHER = 3;

    public XutilsHttpUtil(Context context, String dbName, int dbVersion) {
        this.context = context;
        db = Xutils_singleCase.getDbUtilsInstance(context, dbName, dbVersion);
    }

    /**
     * 当开启数据库缓存功能并以url作为查询依据时，调用此方法。适用于没有分页功能的页面、或有分页功能但支持离线加载更多的页面、或url唯一的页面
     *
     * @param url
     * @param params   //@param contentBean 要缓存的实体类.class (不需要缓存的话传null)，需继承BaseInnerGsonBean。假如服务器返回的数据是数组形式，则全数据实体类继承BaseGsonBean，纯数据内容继承BaseInnerBean；如服务器返回数据直接是jsonobject格式，则全数据实体类直接继承BaseInnerBean
     * @param cls  //用gson时的要转换成的实体类bean,不用gson时可传null
     * @param callBack
     */
    public void send(String url, RequestParams params, final Class<T> cls, final MyRequestCallBackListener<T> callBack) {
        MyLog.d("vdohttp","httputil url="+url);
        this.send_andCache(url, params, cls, callBack);
    }

    /**
     * 当开启数据库缓存功能并以自定义的字符串作为查询依据时，调用此方法。适用于有分页功能但不能离线加载更多(读数据库)的页面、或url不唯一的页面（类似某个页面嵌套多个fragment,url是相同的，仅某个参数不同）
     * <p>
     * 待商榷，好像上个构造方法也能实现url不唯一的页面，因为就算只有参数不同 他的Url整体也是不同的 可以实现分开来查询。还有好像也不能实现离线不让他加载，因为存数据时不管otherkey以什么区分他都存进去了，跟url没什么两样，禁止离线加载可以通过控制listview的footview来实现。
     * 待使用时实际测试，先注释掉。。
     *
     * @param url
     * @param params
     * @param cls
     * @param callBack
     */
    /*public void send(String otherKey,final String url, RequestParams params,final Class<T> cls,final MyRequestCallBack<T> callBack){
		this.send_andCache("otherKey",otherKey, url,params, cls, callBack);
	}*/
    private void send_andCache(final String url, RequestParams params, final Class<T> cls, final MyRequestCallBackListener<T> callBack) {
        if (callBack == null) {
            return;
        }

        //取数据库缓存
		/*T bean = null;
		try {
			bean = db.findFirst(Selector.from(cls).where(key,"=",otherKey));//暂时只写“=”类型的查找,后期扩展可以把这个符号作为形参传进来
		} catch (DbException e1) {
			e1.printStackTrace();
			callBack.onFailure(e1, "DbException from Cache",ERROR_DB);
		}
		if(bean!=null){
			callBack.onSuccess(bean,true);
			//return ;//不返回，只取缓存时才返回，这里是先取缓存，再取网络数据
		}*/

		/*List list = getDataFromDBFindAll(contentBean);
		if(list!=null && list.size()>0) {
			callBack.onSuccessSingleDataFromCache(list);
		}*/

        if (NetCheckUtil.isNetworkConnected(context)) {

            HttpUtils http = Xutils_singleCase.getHttpUtilsInstance();
            http.configCurrentHttpCacheExpiry(httpCacheExpiry);//LRU缓存处理，默认httpCacheExpiry秒内提交返回上次成功的结果
            //http.
            http.send(httpMethod, url, params,
                    new RequestCallBack<String>() {
                        @Override
                        public void onStart() {
                            callBack.onStart();
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {

                            callBack.onLoading(total, current, isUploading);
                        }

                        @SuppressLint("NewApi")
                        @Override
                        public void onSuccess(final ResponseInfo<String> responseInfo) {

                            try {
                                callBack.onSuccess(responseInfo.result);
                            }catch (RuntimeException e){
                                e.printStackTrace();
                            }

                            if (cls != null) {
                                AsyncTaskSimple asyncTaskSimple = new AsyncTaskSimple() {
                                    @Override
                                    public Object doInBackground2(Object[] params) {
                                        Gson gson = new Gson();//使用2.3.1版本会出现StackOverflowError错误导致崩溃，据查使用1.7版本没问题，但可能混淆的话有问题（TODO 待测）
                                        T bean = null;
                                        try {
                                            bean = gson.fromJson(responseInfo.result, cls);//不知道会ANR不


                                            //存储数据到数据库//。。废弃 改成使用缓存的话自动调用本类中的saveDB方法
                                            //if(isUseDb){
                                            //bean.setUrl(url);
                                            //db.saveOrUpdate(bean);//old
                                            //}

                                        } catch (RuntimeException e) {
                                            e.printStackTrace();
                                            callBack.onFailure(e, "RuntimeException", ERROR_RUNTIME);
                                        }
                                        return bean;
                                    }

                                    @Override
                                    public void onPostExecute2(Object bean) {
                                        if (bean != null) {
                                            try {
                                                callBack.onSuccessByGson((T) bean);
                                            }catch(RuntimeException e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCanceled() {

                                    }
                                };
                                if(VersionUtil.getAndroidVerson() <=11){
                                    asyncTaskSimple.execute();
                                }else{
                                    asyncTaskSimple.executeOnExecutor(asyncTaskSimple.getExecutorServiceInstance());
                                }

                            }
                        }

                        @Override
                        public void onFailure(HttpException error, String msg) {
                            callBack.onFailure(error, msg, ERROR_HTTP);
                        }
                    });
        } else {
            callBack.onNetError();
        }

    }


    public interface MyRequestCallBackListener<T> {

        public void onStart();

        public void onLoading(long total, long current, boolean isUploading);

        public void onSuccessByGson(T bean);//使用gson解析时使用这个方法，必须传cls.class

        public void onSuccess(String result);//把返回结果以字符串的形式返回，由调用处自行处理

        public void onFailure(Exception error, String msg, int flag);

        public void onNetError();

        //public void onDBSaveing(DbUtils db,T bean) throws DbException;

        //public void onSuccessSingleDataFromCache(List list);

    }


    public class MyRequestCallBack<T> implements MyRequestCallBackListener<T> {

        @Override
        public void onStart() {

        }

        @Override
        public void onLoading(long total, long current, boolean isUploading) {

        }

        @Override
        public void onSuccessByGson(T bean) {

        }

        @Override
        public void onSuccess(String result) {

        }

        @Override
        public void onFailure(Exception error, String msg, int flag) {
            MyLog.d("onFailure flag=" + flag + ",error=" + error.toString() + ",msg=" + msg);
            UIUtils.showToastSafe("解析失败");

        }

        @Override
        public void onNetError() {
            UIUtils.showToastSafe("网络错误");
        }

		/*@Override
		public void onSuccessSingleDataFromCache(List list) {

		}*/
    }

    public long getHttpCacheExpiry() {
        return httpCacheExpiry;
    }


    public void setHttpCacheExpiry(long httpCacheExpiry) {
        this.httpCacheExpiry = httpCacheExpiry;
    }


    public HttpRequest.HttpMethod getHttpMethod() {
        return httpMethod;
    }


    public void setHttpMethod(HttpRequest.HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

	/*public boolean isUseDbCache() {
		return isUseDb;
	}

	public void setUseDbCache(boolean useDbCache) {
		this.isUseDb = useDbCache;
	}*/


    public void saveDB(Object o) {
        if (db != null && o != null) {
            if (o instanceof List) {
                try {
                    MyLog.d("准备保存数据库：类型为list,size=" + ((List) o).size());
                    db.saveOrUpdateAll((List) o);
                } catch (DbException e) {
                    e.printStackTrace();
                    MyLog.d("准备保存数据库：保存失败");
                }
            } else if (o instanceof BaseGsonBean) {
                try {
                    MyLog.d("准备保存数据库：类型为BaseGsonBean");
                    db.saveOrUpdate((EntityBase) o);
                } catch (DbException e) {
                    e.printStackTrace();
                    MyLog.d("准备保存数据库：保存失败");
                }
            } else {
                MyLog.d("准备保存数据库：类型未知");
            }

        }
    }


    /**
     * cls need extends BaseInnerGsonBean
     */
    public List readDataFromDBFindAll(Class cls) {
        List list = null;
        if (db != null && cls != null) {
            try {
                list = db.findAll(Selector.from(cls));
                if (list != null) {
                    MyLog.d("读取数据库数据成功，size=" + list.size());
                }
                return list;
            } catch (DbException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return null;
    }


    /**
     * cls need extends BaseInnerGsonBean
     */
    public List readDataFromDBFindWhere(Class cls,String key,String value) {
        List list = null;
        if (db != null && cls != null) {
            try {
                list = db.findAll(Selector.from(cls).where(key, "=", value));
                if (list != null) {
                    MyLog.d("读取数据库数据成功，size=" + list.size());
                }
                return list;
            } catch (DbException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return null;
    }

    /**
     * cls need extends BaseInnerGsonBean
     */
    public List readDataFromDBFindWhere(Class cls,String key1,String value1,String key2,String value2) {
        List list = null;
        if (db != null && cls != null) {
            try {
                list = db.findAll(Selector.from(cls).where(key1, "=", value1).where(key2, "=", value2));
                if (list != null) {
                    MyLog.d("读取数据库数据成功，size=" + list.size());
                }
                return list;
            } catch (DbException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return null;
    }

    /**
     * cls need extends BaseInnerGsonBean
     */
    public List readDataFromDBFindWhere(Class cls,String key1,String value1,String key2,String value2,String key3,String value3) {
        List list = null;
        if (db != null && cls != null) {
            try {
                list = db.findAll(Selector.from(cls).where(key1, "=", value1).where(key2,"=",value2).where(key3,"=",value3));
                if (list != null) {
                    MyLog.d("读取数据库数据成功，size=" + list.size());
                }
                return list;
            } catch (DbException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return null;
    }


    public void cleanDataFromDBAll(Class cls){
        if(db!=null && cls!=null){
            try {
                db.deleteAll(cls);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

   /* public void cleanDataFromDBAll(List list){
        if(db!=null && list!=null){
            try {
                db.deleteAll(list);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }*/
}
