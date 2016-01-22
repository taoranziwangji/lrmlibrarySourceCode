package com.vdolrm.lrmlibrary.bean;

/**由于缓存改成了由实体类作为查询依据，所以某种实体类只能作为单独的用途，尽管两个页面的数据内容一模一样也不能使用相同的Bean(不使用缓存的话无所谓)。否则可能会导致查询出错*/
public abstract class BaseGsonBean extends EntityBase{

	/**这个方法必须有，直接返回的json对象里必须重写，返回接口url，因为用url来查询缓存数据。json数组里的json对象无需重写
	 * 注意：假如url有分页的话 url就是个变量，存储的话会把每一页都存进去 。假如只想保存最新页或从缓存取的时候只取最新的（类似网易新闻 离线时不能加载更多）那么就新写一个其他的固定的字段作为查询依据*/

	//20151208new 不用url作为查询依据了。使用findAll方法会以实体类作为查询依据
	//public abstract void setUrl(String url);
	
	//public abstract void setOtherKey(String otherKey);


}
