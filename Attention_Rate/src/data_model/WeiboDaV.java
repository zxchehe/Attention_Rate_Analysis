package data_model;

/*
 * WeiboDaV微博大V的实体类，属性包括大V姓名和大V微博页的网站
 */

public class WeiboDaV {
	private String name;
	private String url;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
