package data_model;

/*
 * WeiboDetail微博细节的实体类，属性有：姓名，微博文字，时间，获赞数量，转发数量，评论数量
 */

public class WeiboDetail {
	private String name;
	private String txt;
	private String time;
	int num_praise;
	int num_forward;
	int num_comment;
	
	public int getNum_praise() {
		return num_praise;
	}
	public void setNum_praise(int num_praise) {
		this.num_praise = num_praise;
	}
	public int getNum_forward() {
		return num_forward;
	}
	public void setNum_forward(int num_forward) {
		this.num_forward = num_forward;
	}
	public int getNum_comment() {
		return num_comment;
	}
	public void setNum_comment(int num_comment) {
		this.num_comment = num_comment;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
