package data_process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections.map.ListOrderedMap;

import com.gargoylesoftware.htmlunit.javascript.host.Map;

import data_collector.DBHandler;
import data_model.DateScore;
import data_model.WeiboDetail;

/*
 * DataClean是进行数据清洗的类
 * cleanData方法对数据进行清洗，整合同一天的数据，统计每一天的关注度得分，并写入数据库
 */

public class DataClean {
	
	static String key;
	
	public DataClean(String key) {
		this.key=key;
	}		
	
	public void cleanData() throws IOException {
		
		System.out.println("数据清洗中...");
		DBHandler dbHandler = new DBHandler(key);
		dbHandler.getConnection();
		List<WeiboDetail> weiboDetails = new ArrayList<WeiboDetail>();
		weiboDetails = dbHandler.QueryAll();

		List<DateScore> dateScores = new ArrayList<DateScore>();

		String curtime = "";
		boolean first = true; // 忽略第一个

		int id = 0;
		int totalscoreOfdateP = 0;
		int totalscoreOfdateN = 0;
		int num_comment_negative=0;
		int num_comment_positive=0;
		int num_praise_negative=0;
		int num_praise_positive=0;
		int num_forward_positive=0;
		int num_forward_negative=0;
		
		for (WeiboDetail weiboDetail : weiboDetails) {
			if (first) {
				first = false;
				continue;
			}

			int score = EmotionalAnalysis2Sentence.EmotionalAnalysis(weiboDetail.getTxt());
			String date = weiboDetail.getTime();
			int num_praise = weiboDetail.getNum_praise();
			int num_forward = weiboDetail.getNum_forward();
			int num_comment = weiboDetail.getNum_comment();

			boolean find;
			if (date.startsWith("05")) {
				find = true;
			} else {
				find = false;
				date="05月27日";
			}
			
			date=date.substring(0,6);

			if (!curtime.equals(date)) {
				if (id >= 1) {
					// 添加上一天的DateScore
					DateScore dateScore = new DateScore();
					dateScore.setId(id);
					dateScore.setDate(curtime);
					dateScore.setScore_positive(totalscoreOfdateP);
					dateScore.setScore_negative(totalscoreOfdateN);
					dateScore.setNum_comment_negative(num_comment_negative);
					dateScore.setNum_comment_positive(num_comment_positive);
					dateScore.setNum_praise_negative(num_praise_negative);
					dateScore.setNum_praise_positive(num_praise_positive);
					dateScore.setNum_forward_positive(num_forward_positive);
					dateScore.setNum_forward_negative(num_forward_negative);
					
					dateScores.add(dateScore);		
					
					totalscoreOfdateP = 0;
					totalscoreOfdateN = 0;
					num_comment_negative=0;
					num_comment_positive=0;
					num_praise_negative=0;
					num_praise_positive=0;
					num_forward_positive=0;
					num_forward_negative=0;
				}
				id++;
				curtime = date;

			} else {
				if(score>=0)
				{
					totalscoreOfdateP = totalscoreOfdateP + score;
					num_comment_positive=num_comment_positive+num_comment;
					num_praise_positive=num_praise_positive+num_praise;
					num_forward_positive=num_forward_positive+num_forward;
				}
				else{
					totalscoreOfdateN=totalscoreOfdateN+score;
					num_comment_negative=num_comment_negative+num_comment;
					num_praise_negative=num_praise_negative+num_praise;
					num_forward_negative=num_forward_negative+num_forward;
				}
			}

//			System.out.println(date+" "+score);
		}
		
		dbHandler.getConnection();
		System.out.println(dateScores.size());
		for(DateScore dateScore:dateScores)
		{
//			System.out.println(dateScore.getId()+" "+dateScore.getDate()+" "+dateScore.getScore_positive()+" "+dateScore.getScore_negative());			
			dbHandler.add(dateScore);
		}
	}

	public static void main(String[] args) throws IOException {
		DataClean dataClean=new DataClean("nike");
		dataClean.cleanData();
	}
}
