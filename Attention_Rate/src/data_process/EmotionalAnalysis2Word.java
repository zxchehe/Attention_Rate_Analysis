package data_process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/*
 * EmotionalAnalysis2Word�����ж����Ĵʵ������ԣ�EmotionalAnalysis����-1��ʾ������0��ʾ���ԣ�1��ʾ������
 */

public class EmotionalAnalysis2Word {
	static public int EmotionalAnalysis(String word) throws IOException {
		// �ж��Ƿ�Ϊ�������ԵĴ�
		try {
			String filePath = "hownetD\\positive.txt";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					if (lineTxt.equals(word))
						return 1;
				}
				read.close();
			} else {
				System.out.println("�Ҳ���positive.txt");
			}
		} catch (Exception e) {
			System.out.println("��ȡpositive.txt���ݳ���");
			e.printStackTrace();
		}

		// �ж��Ƿ�Ϊ�������ԵĴ�
		try {
			String filePath = "hownetD\\negative.txt";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(new FileInputStream(file));// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					if (lineTxt.equals(word))
						return -1;
				}
				read.close();
			} else {
				System.out.println("�Ҳ���negative.txt");
			}
		} catch (Exception e) {
			System.out.println("��ȡnegative.txt���ݳ���");
			e.printStackTrace();
		}

		return 0;
	}

	public static void main(String[] args) throws IOException {
		String word;
		while(true){
		Scanner out = new Scanner(System.in);
		System.out.println("����һ�����Ĵʣ�");
		word=out.next();
		
		int res = EmotionalAnalysis2Word.EmotionalAnalysis(word);
		System.out.println("�жϴ��Խ��Ϊ�� " + res);
		}
	}
}
