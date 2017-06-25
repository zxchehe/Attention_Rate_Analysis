package data_collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.management.JMException;

import org.openqa.selenium.Cookie;

import data_model.WeiboDaV;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/*
 * CrawlerDaV用于爬取微博大V的信息，选择关键词，将爬取该关键词下的10*10个大V
 */


public class CrawlerDaV implements PageProcessor {

	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private static Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	public CrawlerDaV() throws InterruptedException {
		super();
		//模拟登陆，获取微博的cookie
		Set<Cookie> cookies = GetCookies.getCookies();
		for (Cookie cookie : cookies) {
			site.addCookie(cookie.getName(), cookie.getValue());
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {

		List<WeiboDaV> weiboDaVs = new ArrayList<WeiboDaV>();
		
		// 爬取微博详细项所在的块
		List<String> block_txts = page.getHtml().regex("<a href=\".[^\"]*\" class=\"nk\">.[^<]*</a>").all();

		for (int i = 0; i < block_txts.size(); i++) {
			
//			System.out.println(block_txts.get(i)+"\n\n");
			
			String s=block_txts.get(i);
			String name=processString(s);
			String url=processInfo_url(s);
			
			WeiboDaV weiboDaV=new WeiboDaV();
			weiboDaV.setName(name);
			weiboDaV .setUrl(url);
			
			weiboDaVs.add(weiboDaV);
			
			System.out.println("name : "+name);
			System.out.println("url : "+url);
			
		}
	}
	
	private String processString(String s) {
		// 进一步处理字符串
		int begin_str = s.indexOf('>');
		int end_str = s.lastIndexOf('<');
		s = s.substring(begin_str + 1, end_str);

		return s;
	}
	
	private String processInfo_url(String s) {
		int begin_str = s.indexOf('"');
		int end_str = s.indexOf('"',begin_str+1);
		
//		System.out.println("begin_str : "+begin_str);
//		System.out.println("end_str : "+end_str);
		
		s = s.substring(begin_str + 1, end_str);

		return s;
	}

	public static void main(String[] args) throws InterruptedException, JMException {
		
		Spider spider = Spider.create(new CrawlerDaV());

		for(int i=1;i<=10;i++)
		{
			spider.addUrl("https://weibo.cn/find/?cat=shishang&page="+i);
		}
		
		spider.start();
	}

	
}
