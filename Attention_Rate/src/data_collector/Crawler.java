package data_collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.JMException;

import org.openqa.selenium.Cookie;

import data_model.WeiboDetail;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/*
 * Crawler用于爬取微博内容项，选择关键词，将爬取该关键词下的100*10条微博?
 */

public class Crawler implements PageProcessor {

	static String key;
	
	public Crawler(String key) throws InterruptedException {
		super();

		this.key=key;
		
		//部分一： 模拟登陆，获取微博的cookie
		Set<Cookie> cookies = GetCookies.getCookies();
		for (Cookie cookie : cookies) {
			site.addCookie(cookie.getName(), cookie.getValue());
		}

	}

	// 抓取网站的相关配置，包括编码，抓取间隔，重试次数等
	private static Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	@Override
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	public void process(Page page) {

		// 部分二：定义如何抽取页面信息，并保存下来
		// System.out.println(page.getHtml());

		// 爬取微博详细项所在的块
		List<String> block_txts = page.getHtml().xpath("//*[@class=\"c\"]").all();

		parseRimengWeibo(block_txts);

		// // 部分三：从页面发现后续的url地址来抓取
		// List<String> strList=new ArrayList<String>();
		// strList.add("http://m.weibo.cn/status/F2jKCzGq5");
		// page.addTargetRequests(strList);
	}

	@Override
	public Site getSite() {
		return site;
	}

	private List<WeiboDetail> parseRimengWeibo(List<String> block_txts) {
		List<WeiboDetail> weiboDetails = new ArrayList<WeiboDetail>();

		//设置提取关键信息的正则匹配模式
		Pattern pattern_comment = Pattern.compile("<span class=\"ctt\">.*</span>");
		Pattern pattern_name = Pattern.compile("<a class=\"nk\" href=\".[^\"]*\">.[^<]*</a>");
		Pattern pattern_time = Pattern.compile("<span class=\"ct\">.[^<]*</span>");
		Pattern pattern_num_praise = Pattern.compile("<a href=\".[^\"]*\">赞\\[[0-9]*\\]</a>");
		Pattern pattern_num_forward = Pattern.compile("<a href=\".[^\"]*\">转发\\[[0-9]*\\]</a>");
		Pattern pattern_num_comment = Pattern.compile("<a href=\".[^\"]*\" class=\"cc\">评论\\[[0-9]*\\]</a>");

		// 从详细块提取信息
		for (int i = 0; i < block_txts.size(); i++) {
//			 System.out.println(block_txts.get(i)+"\n\n");

			//整个微博细节项的内容
			String block_txt = block_txts.get(i);

			Matcher matcher_comment = pattern_comment.matcher(block_txt);
			Matcher matcher_name = pattern_name.matcher(block_txt);
			Matcher matcher_time = pattern_time.matcher(block_txt);
			Matcher matcher_num_praise = pattern_num_praise.matcher(block_txt);		
			Matcher matcher_num_forward = pattern_num_forward.matcher(block_txt);		
			Matcher matcher_num_comment = pattern_num_comment.matcher(block_txt);		
			
			if (matcher_comment.find() && matcher_name.find() && matcher_time.find()) {
				// 从块中提取出信息，包括姓名，微博文字，时间，获赞数量，转发数量，评论数
				String name, txt, time, num_praise_str = null,num_forward_str=null,num_comment_str=null;
				int num_praise=0,num_forward=0,num_comment = 0;
				name = matcher_name.group();
				txt = matcher_comment.group();
				time = matcher_time.group();
				
				if(matcher_num_praise.find())
				{	
					num_praise_str=matcher_num_praise.group();
					num_praise=processInfo_praise(num_praise_str);
				}
				
				if(matcher_num_forward.find())
				{	
					num_forward_str=matcher_num_forward.group();
					num_forward=processInfo_praise(num_forward_str);
				}
				
				if(matcher_num_comment.find())
				{	
					num_comment_str=matcher_num_comment.group();
					num_comment=processInfo_praise(num_comment_str);
				}
				txt = processInfo_txt(txt);
				name = processString(name);
				time = processInfo_time(time);
				

				System.out.println(i + " comment : " + txt);
				System.out.println(i + " name : " + name);
				System.out.println(i + " time : " + time);
				System.out.println(i + " num_praise : " + num_praise);
				System.out.println(i + " num_forward : " + num_forward);
				System.out.println(i + " num_comment : " + num_comment);
				System.out.println("");

				WeiboDetail weiboDetail = new WeiboDetail();
				weiboDetail.setName(name);
				weiboDetail.setTxt(txt);
				weiboDetail.setTime(time);
				weiboDetail.setNum_praise(num_praise);
				weiboDetail.setNum_forward(num_forward);
				weiboDetail.setNum_comment(num_comment);

				weiboDetails.add(weiboDetail);
			} else {
				System.out.println(i + " : no find\n");
			}
		}

		//将获取的微博细节项列表写入数据库
		DBHandler dbHandler=new DBHandler("pike");
		dbHandler.getConnection();
		for(WeiboDetail w:weiboDetails)
		{
			dbHandler.add(w);	
		}
		
		return weiboDetails;
	}

	//进一步处理微博文字内容
	private String processInfo_txt(String txt) {
		txt = processString(txt);

		Pattern pattern = Pattern.compile("<.[^>]*>");
		Matcher matcher;

		for (int i = 1; i < 20; i++) {
			
			matcher = pattern.matcher(txt);
			
			if (matcher.find()) {
				String d_Str = matcher.group();
				txt=txt.replace(d_Str,"");
			}
			else{
				break;
			}
		}

		return txt;
	}
	
	//进一步处理得到时间
	private String processInfo_time(String time)
	{
		time = processString(time);
		int d_i=time.indexOf("&nbsp");
		
		if(d_i>0)
		time=time.substring(0,d_i);
		
		return time;
	}
	
	//进一步处理得到获赞数量
	private int processInfo_praise(String praise)
	{
		int num_praise = 0;
		int begin_str = praise.indexOf('[');
		int end_str = praise.indexOf(']');
			
		praise = praise.substring(begin_str + 1, end_str);
		
		num_praise= Integer.parseInt(praise);
		
		return num_praise;
	}

	//进一步处理字符串
	private String processString(String s) {
		// 进一步处理字符串
		int begin_str = s.indexOf('>');
		int end_str = s.lastIndexOf('<');
		s = s.substring(begin_str + 1, end_str);

		return s;
	}

	public static void main(String[] args) throws InterruptedException, JMException {
		
		//创建爬虫
		Spider spider = Spider.create(new Crawler("匹克"));
		//加入某个关键词下的100页微博页面，共有100*10条微博
		for(int i=1;i<=100;i++)
		{
			spider.addUrl("https://weibo.cn/search/mblog?hideSearchFrame=&keyword="+key+"&page="+i);
		}
		//开始爬取
		spider.start();
	}
}
