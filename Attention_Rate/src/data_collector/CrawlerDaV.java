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
 * CrawlerDaV������ȡ΢����V����Ϣ��ѡ��ؼ��ʣ�����ȡ�ùؼ����µ�10*10����V
 */


public class CrawlerDaV implements PageProcessor {

	// ����һ��ץȡ��վ��������ã��������롢ץȡ��������Դ�����
	private static Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	public CrawlerDaV() throws InterruptedException {
		super();
		//ģ���½����ȡ΢����cookie
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
		
		// ��ȡ΢����ϸ�����ڵĿ�
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
		// ��һ�������ַ���
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
