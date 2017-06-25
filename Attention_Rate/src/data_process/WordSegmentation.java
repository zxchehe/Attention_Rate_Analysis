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
 * WordSegmentation�������ķִʣ�����ʵ��ʹ����IKAnalyzer��
 */

public class WordSegmentation {
	public static List<String> getWord(String text) throws IOException {  
       
		List<String> res=new ArrayList<String>();
        //�����ִʶ���  
        IKAnalyzer anal=new IKAnalyzer(true);       
        StringReader reader=new StringReader(text);  
        //�ִ�  
        org.apache.lucene.analysis.TokenStream ts=anal.tokenStream("", reader);  
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);  
        //�����ִ�����  
        while(ts.incrementToken()){  
//            System.out.print(term.toString()+"|");
            res.add(term.toString());
        }  
        reader.close();  
		return res;  
    }
	 
	 public static void main(String[] args) throws IOException{
		 String text="����java���Կ����������������ķִʹ��߰�";  
		 List<String> res=WordSegmentation.getWord(text);
		 for(String s:res)
		 {
			 System.out.print(s+"|");
		 }
	 }
}
