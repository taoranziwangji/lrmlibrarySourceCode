package com.vdolrm.lrmlibrary.bean;

/**
 * Created by Administrator on 2015/12/8.
 * 没有具体作用，用来识别是实体内部类的总基类(假如服务器返回的数据是数组形式，则全数据实体类继承BaseGsonBean，纯数据内容继承BaseInnerBean；如服务器返回数据直接是jsonobject格式，则全数据实体类直接继承BaseInnerBean)
 */
public class BaseInnerGsonBean extends BaseGsonBean {
}
