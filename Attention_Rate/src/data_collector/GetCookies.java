package data_collector;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/*
 * GetCookies模拟登陆微博，获取cookie
 */

public class GetCookies {
	//该方法模拟微博登陆，返回cookie
	static public Set<Cookie> getCookies() throws InterruptedException {

		// 设置浏览器驱动的位置，很重要，不然打开的话可能是空白页
		System.setProperty("webdriver.chrome.driver", "G:\\Workplace\\Attention_Rate\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();

		// 通过配置参数禁止data;的出现

		options.addArguments(

				"--user-data-dir=C:/Users/Administrator/AppData/Local/Google/Chrome/User Data/Default");

		// 通过配置参数删除“您使用的是不受支持的命令行标记：--ignore-certificate-errors。稳定性和安全性会有所下降。”提示

		options.addArguments("--start-maximized", "allow-running-insecure-content", "--test-type");

		options.addArguments("--profile-directory=Default");

		// 实例化一个浏览器对象
		WebDriver driver = new ChromeDriver(options);

		// 网址（手机版的新浪微博，因为这个网站登录不要验证码）
		String baseUrl = "https://passport.weibo.cn/signin/login?entry=mweibo&res=wel&wm=3349&r=http%3A%2F%2Fm.weibo.cn%2F";
		// 打开浏览器

		driver.get(baseUrl);

		// 这个时候会打开一个浏览器，连接到你所get的网站

		// 因为网站不一定可以马上打开，让进程停一下，否则页面的元素会找不到。
		Thread.sleep(5000);

		// 我们要填写的地方有两个，帐号、密码，然后再点击登录。
		// 找到名为"loginName"的元素，填写帐号
		driver.findElement(By.id("loginName")).clear();
		driver.findElement(By.id("loginName")).sendKeys("821193666@qq.com");

		// 找到名为"loginPassword"的元素，填写密码
		driver.findElement(By.id("loginPassword")).clear();
		driver.findElement(By.id("loginPassword")).sendKeys("st19950308");

		// 找到登录按钮，点击
		driver.findElement(By.id("loginAction")).click();

		// 然后这个页面就会进入到登录后的界面了

		// 因为网站不一定可以马上打开，让进程停一下，否则页面还没有加载出来就进行下一步了。
		Thread.sleep(5000);

		// 获取cookies

		Set<Cookie> cookies = driver.manage().getCookies();
		String cookieStr = "";
		for (Cookie cookie : cookies) {
			cookieStr += cookie.getName() + "=" + cookie.getValue() + "; ";

		}

		System.out.println("cookieStr : " + cookieStr);

		// driver.close();

		return cookies;
	}
}
