package data_forecast;

import java.io.DataOutput;
import java.util.List;

import com.gargoylesoftware.htmlunit.javascript.host.Map;

public class ForecastData {
	
	static List<DataPoint> dataPoints;
	static RegressionLine line;
	
	public ForecastData(List<DataPoint> dataPoints) {
		this.dataPoints=dataPoints;
		line = new RegressionLine();  
		  
        for(DataPoint dataPoint:dataPoints)
        {
        	line.addDataPoint(new DataPoint(dataPoint.x, dataPoint.y));  
        }   
  
        printSums(line);  
        printLine(line); 
	}
	
	public static RegressionLine getLine() {
		return line;
	}  
	  
	    /** 
	     * Print the computed sums. 
	     *  
	     * @param line 
	     *            the regression line 
	     */  
	    private static void printSums(RegressionLine line) {  
	        System.out.println("\n数据点个数 n = " + line.getDataPointCount());  
	        System.out.println("\nSum x  = " + line.getSumX());  
	        System.out.println("Sum y  = " + line.getSumY());  
	        System.out.println("Sum xx = " + line.getSumXX());  
	        System.out.println("Sum xy = " + line.getSumXY());  
	        System.out.println("Sum yy = " + line.getSumYY());  
	  
	    }  
	  
	    /** 
	     * Print the regression line function. 
	     *  
	     * @param line 
	     *            the regression line 
	     */  
	    private static void printLine(RegressionLine line) {  
	        System.out.println("\n回归线公式:  y = " + line.getA1() + "x + "  
	                + line.getA0());  
	        System.out.println("误差：     R^2 = " + line.getR());  
	    }  
}
