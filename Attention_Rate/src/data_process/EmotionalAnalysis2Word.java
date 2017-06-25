package data_process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/*
 * EmotionalAnalysis2Word用于判断中文词的正负性，EmotionalAnalysis返回-1表示消极，0表示中性，1表示积极。
 */

public class EmotionalAnalysis2Word {
	static public int EmotionalAnalysis(String word) throws IOException {
		// 判断是否为积极词性的词
		try {
			String filePath = "hownetD\\positive.txt";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					if (lineTxt.equals(word))
						return 1;
				}
				read.close();
			} else {
				System.out.println("找不到positive.txt");
			}
		} catch (Exception e) {
			System.out.println("读取positive.txt内容出错");
			e.printStackTrace();
		}

		// 判断是否为消极词性的词
		try {
			String filePath = "hownetD\\negative.txt";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					if (lineTxt.equals(word))
						return -1;
				}
				read.close();
			} else {
				System.out.println("找不到negative.txt");
			}
		} catch (Exception e) {
			System.out.println("读取negative.txt内容出错");
			e.printStackTrace();
		}

		return 0;
	}

	public static void main(String[] args) throws IOException {
		String word;
		while(true){
		Scanner out = new Scanner(System.in);
		System.out.println("输入一个中文词：");
		word=out.next();
		
		int res = EmotionalAnalysis2Word.EmotionalAnalysis(word);
		System.out.println("判断词性结果为： " + res);
		}
	}
}
