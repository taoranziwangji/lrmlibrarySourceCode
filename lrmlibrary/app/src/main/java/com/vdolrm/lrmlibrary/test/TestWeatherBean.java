package com.vdolrm.lrmlibrary.test;

import java.util.List;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Finder;
import com.lidroid.xutils.db.annotation.Table;
import com.vdolrm.lrmlibrary.bean.BaseGsonBean;
import com.vdolrm.lrmlibrary.bean.BaseInnerGsonBean;

@Table(name = "testWeatherBean")
public class TestWeatherBean extends BaseInnerGsonBean {
//{"lon":120.58531,"level":2,"address":"","cityName":"","alevel":4,"lat":31.29888}
	
	//// 静态字段不会存入数据库
	//public static final String testurl = "http://gc.ditu.aliyun.com/geocoding?a=%E8%8B%8F%E5%B7%9E%E5%B8%82";
	
	 @Column(column = "url") // 建议加上注解， 混淆后列名不受影响
	 private String url;
	
	// @Column(column = "otherKey") // 建议加上注解， 混淆后列名不受影响
	// private String otherKey;
	 
	 @Column(column = "lon") // 建议加上注解， 混淆后列名不受影响
	private double lon ;//写成 double float String 都可以 ,int不行
	 
	 @Column(column = "level") // 建议加上注解， 混淆后列名不受影响
	private String level;
	 
	 @Column(column = "address") // 建议加上注解， 混淆后列名不受影响
	private String address;
	 
	 @Column(column = "cityName") // 建议加上注解， 混淆后列名不受影响
	private String cityName;
	 
	 @Column(column = "alevel") // 建议加上注解， 混淆后列名不受影响
	private String alevel;
	 
	 @Column(column = "lat") // 建议加上注解， 混淆后列名不受影响
	private float lat;
	 
	// @Column(column = "url") // 建议加上注解， 混淆后列名不受影响
	@Finder(valueColumn = "id", targetColumn = "parentId")
	private List<TestBean> list;
	
	// @Column(column = "testcolumn")
	//private String testcolumn;
	

	
	
	@Override
	public String toString() {
		String s = "bean:lon="+lon+",level="+level+",address="+address+",cityName="+cityName+",alevel="+alevel+",lat="+lat;
		return s;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String leven) {
		this.level = leven;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAlevel() {
		return alevel;
	}
	public void setAlevel(String alevel) {
		this.alevel = alevel;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	
	public List<TestBean> getList() {
		return list;
	}
	public void setList(List<TestBean> list) {
		this.list = list;
	}


	//@Override
	public void setUrl(String url) {
		// TODO Auto-generated method stub
		this.url = url;
	}
	
	/*@Override
	public void setOtherKey(String otherKey) {
		// TODO Auto-generated method stub
		this.otherKey = otherKey;
	}*/


	@Table(name = "testBean")
	class TestBean extends BaseGsonBean{
		@Column(column = "test") // 建议加上注解， 混淆后列名不受影响
		private String test;

		public String getTest() {
			return test;
		}

		public void setTest(String test) {
			this.test = test;
		}

		//@Override
		public void setUrl(String url) {
			// TODO Auto-generated method stub
			
		}

		/*@Override
		public void setOtherKey(String otherKey) {
			// TODO Auto-generated method stub
			
		}*/

		

	
		
	}


	





	
}
