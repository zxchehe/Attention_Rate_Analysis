package date_presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import java.awt.Font;  
import java.io.FileOutputStream;  
import java.io.IOException;

import org.apache.lucene.util.FieldCacheSanityChecker.Insanity;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;  
import org.jfree.chart.JFreeChart;  
import org.jfree.chart.axis.CategoryAxis;  
import org.jfree.chart.axis.NumberAxis;  
import org.jfree.chart.plot.CategoryPlot;  
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;  
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.Regression;
import org.jfree.ui.ApplicationFrame;

import data_collector.DBHandler;
import data_forecast.DataPoint;
import data_forecast.ForecastData;
import data_forecast.RegressionLine;
import data_model.DateScore;

/*
 * Presentation����չʾ���ƺ�Ԥ��
 */

public class Presentation extends ApplicationFrame{
	
	static String key;
	static double p_score_positive;
	static double p_score_negative;
	static double p_num_praise_positive;
	static double p_num_comment_positive;
	static double p_num_forward_positive;
	static double p_num_praise_negative;
	static double p_num_comment_negative;
	static double p_num_forward_negative;
	
	 public Presentation(String key,
		 double p_score_positive,
		 double p_score_negative,
		 double p_num_praise_positive,
		 double p_num_comment_positive,
		 double p_num_forward_positive,
		 double p_num_praise_negative,
		 double p_num_comment_negative,
		 double p_num_forward_negative)
	    {
	        super(key);
	        this.key=key;
	        this.p_score_positive=p_score_positive;
	        this.p_score_negative=p_score_negative;
	        this.p_num_praise_negative=p_num_praise_negative;
	        this.p_num_comment_negative=p_num_comment_negative;
	        this.p_num_forward_negative=p_num_forward_negative;
	        this.p_num_praise_positive=p_num_praise_positive;
	        this.p_num_comment_positive=p_num_comment_positive;
	        this.p_num_forward_positive=p_num_forward_positive;
	        this.setContentPane(createPanel()); //���캯�����Զ�����Java��panel���
	    }
	    
	    public static CategoryDataset createDataset() //������״ͼ���ݼ�
	    {
	        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
	        
	        DBHandler dbHandler=new DBHandler(key);
	        dbHandler.getConnection();
	        List<DateScore> dateScores=dbHandler.QueryAllScore();
	        Collections.reverse(dateScores);
	        	             
	        for(DateScore dateScore:dateScores)
	        {
	        	double score=0;

	 	        int score_positive = dateScore.getScore_positive();
	 			int score_negative = dateScore.getScore_negative();
	 			int num_comment_negative=dateScore.getNum_comment_negative();
	 			int num_comment_positive=dateScore.getNum_comment_positive();
	 			int num_praise_negative=dateScore.getNum_praise_negative();
	 			int num_praise_positive=dateScore.getNum_praise_positive();
	 			int num_forward_positive=dateScore.getNum_forward_positive();
	 			int num_forward_negative=dateScore.getNum_forward_negative();
	        	
	 			score=score_positive*p_score_positive*(1+
	 					num_comment_positive*p_num_comment_positive
	 					*num_praise_positive*p_num_praise_positive
	 					*num_forward_positive*p_num_forward_positive)
	 					+score_negative*p_score_negative*(1+
	 					num_comment_negative*p_num_comment_negative
	 					*num_praise_negative*p_num_praise_negative
	 					*num_forward_negative*p_num_forward_negative);
	 					
	        	dataset.setValue(score,"����",dateScore.getDate().substring(3,5));
	        }
	        
	        //�����4���ע�����ݣ�Ԥ�����������Ĺ�ע�ȵ÷�
	        List<DataPoint> dataPoints=new ArrayList<DataPoint>();
	        int i=0;
	        for(DateScore dateScore:dateScores)
	        {
	        	double score=0;

	        	int id=dateScore.getId();
	 	        int score_positive = dateScore.getScore_positive();
	 			int score_negative = dateScore.getScore_negative();
	 			int num_comment_negative=dateScore.getNum_comment_negative();
	 			int num_comment_positive=dateScore.getNum_comment_positive();
	 			int num_praise_negative=dateScore.getNum_praise_negative();
	 			int num_praise_positive=dateScore.getNum_praise_positive();
	 			int num_forward_positive=dateScore.getNum_forward_positive();
	 			int num_forward_negative=dateScore.getNum_forward_negative();
	        	
	 			score=score_positive*p_score_positive*(1+
	 					num_comment_positive*p_num_comment_positive
	 					*num_praise_positive*p_num_praise_positive
	 					*num_forward_positive*p_num_forward_positive)
	 					+score_negative*p_score_negative*(1+
	 					num_comment_negative*p_num_comment_negative
	 					*num_praise_negative*p_num_praise_negative
	 					*num_forward_negative*p_num_forward_negative);
	 					
	        	if(i>=dateScores.size()-4)
	        	{
	        		dataPoints.add(new DataPoint(i,(float)score));
	        	}
	        	i++;
	        }
	        
	        ForecastData forecastData=new ForecastData(dataPoints);
	        RegressionLine line=forecastData.getLine();
	        
	        float a1=line.getA1();
	        float a0=line.getA0();
	        
	        for(int t=1;t<=3;t++)
	        {
	        	double score=a1*(i+t)+a0;
	        	
	        	dataset.setValue(score,"Ԥ��","+"+t);
	        }
	       
	        return dataset;
	    }
	    
	    public static JFreeChart createChart(CategoryDataset dataset) //�����ݼ�����һ��ͼ��
	    {
	        JFreeChart chart=ChartFactory.createBarChart("hi", "����", 
	                "��ע��ָ��", dataset, PlotOrientation.VERTICAL, true, true, false); //����һ��JFreeChart
	        chart.setTitle(new TextTitle(key+"Ʒ�ƹ�ע������",new Font("����",Font.BOLD+Font.ITALIC,20)));//�����������ñ��⣬�滻��hi������
	        CategoryPlot plot=(CategoryPlot)chart.getPlot();//���ͼ���м䲿�֣���plot
	        CategoryAxis categoryAxis=plot.getDomainAxis();//��ú�����
	        categoryAxis.setLabelFont(new Font("΢���ź�",Font.BOLD,12));//���ú���������
	        return chart;
	    }
	    
	    public static JPanel createPanel()
	    {
	        JFreeChart chart =createChart(createDataset());
	        return new ChartPanel(chart); //��chart�������Panel�����ȥ��ChartPanel���Ѽ̳�Jpanel
	    }
	    
	    public static void main(String[] args)
	    {
	    	Presentation chart=new Presentation("pike",1,0,0,0,0,0,0,0);
	        chart.pack();//�Ժ��ʵĴ�С��ʾ
	        chart.setVisible(true);
	        
	    }
}
