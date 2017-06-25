package data_process;

import java.io.IOException;
import java.util.List;

/*
 * EmotionalAnalysis2Sentence用于判断句子的情感倾向
 * EmotionalAnalysis方法对句子进行分词，对每个词分析词的正负性，相加作为句子的情感倾向
 */

public class EmotionalAnalysis2Sentence {
	
	public static int []grades;
	public static List<String> words;
	
	static public int EmotionalAnalysis(String s) throws IOException
	{
		int res=0;
		
		words=WordSegmentation.getWord(s);
		
		int i=0;
		grades=new int[words.size()];
		
		for(String word:words)
		{
			int grade=EmotionalAnalysis2Word.EmotionalAnalysis(word);
			grades[i]=grade;
			i++;
			res=res+grade;
		}
		
		return res;		
	}
	
	public static void main(String[] args) throws IOException {
		String s="今天确实很幸运，有机会跟乔治希尔一起合影，虽然是抢拍，但是也很开心！唯一不好的就是今天穿了一件李宁，之前匹克的标签掉了，如果有什么影响，只好在这里道歉了！";
		int ret=EmotionalAnalysis2Sentence.EmotionalAnalysis(s);
		int i=0;
		for(String word:words)
		{
			System.out.print(word+"("+grades[i]+")|");
			i++;
		}
		System.out.println();
		System.out.println("总得分:"+ret);
	}
}
