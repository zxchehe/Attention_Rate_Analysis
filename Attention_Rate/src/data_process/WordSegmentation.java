package data_process;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.google.common.base.Strings;

/*
 * WordSegmentation用于中文分词，具体实行使用了IKAnalyzer类
 */

public class WordSegmentation {
	public static List<String> getWord(String text) throws IOException {  
       
		List<String> res=new ArrayList<String>();
        //创建分词对象  
        IKAnalyzer anal=new IKAnalyzer(true);       
        StringReader reader=new StringReader(text);  
        //分词  
        org.apache.lucene.analysis.TokenStream ts=anal.tokenStream("", reader);  
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);  
        //遍历分词数据  
        while(ts.incrementToken()){  
//            System.out.print(term.toString()+"|");
            res.add(term.toString());
        }  
        reader.close();  
		return res;  
    }
	 
	 public static void main(String[] args) throws IOException{
		 String text="基于java语言开发的轻量级的中文分词工具包";  
		 List<String> res=WordSegmentation.getWord(text);
		 for(String s:res)
		 {
			 System.out.print(s+"|");
		 }
	 }
}
