package data_model;

public class DateScore {
	private int id;
	private String date;
	private int score_positive;
	private int score_negative;
	private int num_praise_positive;
	private int num_praise_negative;
	private int num_forward_positive;
	private int num_forward_negative;
	private int num_comment_positive;
	private int num_comment_negative;
	
	public String getDate() {
		return date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	public int getScore_positive() {
		return score_positive;
	}
	public void setScore_positive(int score_positive) {
		this.score_positive = score_positive;
	}
	public int getScore_negative() {
		return score_negative;
	}
	public void setScore_negative(int score_negative) {
		this.score_negative = score_negative;
	}
	public int getNum_praise_positive() {
		return num_praise_positive;
	}
	public void setNum_praise_positive(int num_praise_positive) {
		this.num_praise_positive = num_praise_positive;
	}
	public int getNum_praise_negative() {
		return num_praise_negative;
	}
	public void setNum_praise_negative(int num_praise_negative) {
		this.num_praise_negative = num_praise_negative;
	}
	public int getNum_forward_positive() {
		return num_forward_positive;
	}
	public void setNum_forward_positive(int num_forward_positive) {
		this.num_forward_positive = num_forward_positive;
	}
	public int getNum_forward_negative() {
		return num_forward_negative;
	}
	public void setNum_forward_negative(int num_forward_negative) {
		this.num_forward_negative = num_forward_negative;
	}
	public int getNum_comment_positive() {
		return num_comment_positive;
	}
	public void setNum_comment_positive(int num_comment_positive) {
		this.num_comment_positive = num_comment_positive;
	}
	public int getNum_comment_negative() {
		return num_comment_negative;
	}
	public void setNum_comment_negative(int num_comment_negative) {
		this.num_comment_negative = num_comment_negative;
	}
}
