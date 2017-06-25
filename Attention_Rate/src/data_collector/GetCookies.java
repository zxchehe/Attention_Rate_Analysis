package data_collector;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/*
 * GetCookiesģ���½΢������ȡcookie
 */

public class GetCookies {
	//�÷���ģ��΢����½������cookie
	static public Set<Cookie> getCookies() throws InterruptedException {

		// ���������������λ�ã�����Ҫ����Ȼ�򿪵Ļ������ǿհ�ҳ
		System.setProperty("webdriver.chrome.driver", "G:\\Workplace\\Attention_Rate\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();

		// ͨ�����ò�����ֹdata;�ĳ���

		options.addArguments(

				"--user-data-dir=C:/Users/Administrator/AppData/Local/Google/Chrome/User Data/Default");

		// ͨ�����ò���ɾ������ʹ�õ��ǲ���֧�ֵ������б�ǣ�--ignore-certificate-errors���ȶ��ԺͰ�ȫ�Ի������½�������ʾ

		options.addArguments("--start-maximized", "allow-running-insecure-content", "--test-type");

		options.addArguments("--profile-directory=Default");

		// ʵ����һ�����������
		WebDriver driver = new ChromeDriver(options);

		// ��ַ���ֻ��������΢������Ϊ�����վ��¼��Ҫ��֤�룩
		String baseUrl = "https://passport.weibo.cn/signin/login?entry=mweibo&res=wel&wm=3349&r=http%3A%2F%2Fm.weibo.cn%2F";
		// �������

		driver.get(baseUrl);

		// ���ʱ����һ������������ӵ�����get����վ

		// ��Ϊ��վ��һ���������ϴ򿪣��ý���ͣһ�£�����ҳ���Ԫ�ػ��Ҳ�����
		Thread.sleep(5000);

		// ����Ҫ��д�ĵط����������ʺš����룬Ȼ���ٵ����¼��
		// �ҵ���Ϊ"loginName"��Ԫ�أ���д�ʺ�
		driver.findElement(By.id("loginName")).clear();
		driver.findElement(By.id("loginName")).sendKeys("821193666@qq.com");

		// �ҵ���Ϊ"loginPassword"��Ԫ�أ���д����
		driver.findElement(By.id("loginPassword")).clear();
		driver.findElement(By.id("loginPassword")).sendKeys("st19950308");

		// �ҵ���¼��ť�����
		driver.findElement(By.id("loginAction")).click();

		// Ȼ�����ҳ��ͻ���뵽��¼��Ľ�����

		// ��Ϊ��վ��һ���������ϴ򿪣��ý���ͣһ�£�����ҳ�滹û�м��س����ͽ�����һ���ˡ�
		Thread.sleep(5000);

		// ��ȡcookies

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
