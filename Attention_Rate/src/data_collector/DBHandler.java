package data_collector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;

import data_model.DateScore;
import data_model.WeiboDetail;

/*
 * DBHandler用于操作数据库，方法包括连接数据库和将微博细节项写入数据库
 */

public class DBHandler {
	Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    String key;
    
    public DBHandler(String key) {
		this.key=key;
	}
    
    /**
     * 写一个连接数据库的方法
     */
    public Connection getConnection(){
        String url="jdbc:mysql://localhost:3306/weibo?useUnicode=true&characterEncoding=gbk";
        String userName="root";
        String password="19950308";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("找不到驱动！");
            e.printStackTrace();
        }
        try {
            conn=DriverManager.getConnection(url, userName, password);
            if(conn!=null){
                System.out.println("connection successful");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
                System.out.println( "connection fail");
            e.printStackTrace();
        }
        return conn;
    }
    
    // disconnect to MySQL  
    void deconnSQL() {  
        try {  
            if (conn != null)  
                conn.close();  
        } catch (Exception e) {  
            System.out.println("关闭数据库出错");  
            e.printStackTrace();  
        }  
    }  
    
    /**
     * 获取数据库的所有数据
     */
    public List<WeiboDetail> QueryAll(){
     
    	List<WeiboDetail> weiboDetails=new ArrayList<WeiboDetail>();
//        j.Connection();
    	
        String sql="select * from "+key;
        try {
            ps=conn.prepareStatement(sql);// 2.创建Satement并设置参数
            rs=ps.executeQuery(sql);  // 3.执行SQL语句
            // 4.处理结果
            while(rs.next()){
            	WeiboDetail weiboDetail=new WeiboDetail();
            	
            	String name=rs.getString("name");
            	String txt=rs.getString("txt");
            	String time=rs.getString("time");
            	int num_comment=rs.getInt("num_comment");
            	int num_forward=rs.getInt("num_forward");
            	int num_praise=rs.getInt("num_praise");
            	
            	weiboDetail.setName(name);
            	weiboDetail.setTxt(txt);
            	weiboDetail.setTime(time);
            	weiboDetail.setNum_comment(num_comment);
            	weiboDetail.setNum_forward(num_forward);
            	weiboDetail.setNum_praise(num_praise);
                
            	weiboDetails.add(weiboDetail);
            }
//            System.out.println(rs.next());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //释放资源
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		return weiboDetails;
    }
    
    /**
     * 获取数据库的处理过的所有数据
     */
    public List<DateScore> QueryAllScore(){
     
    	List<DateScore> dateScores=new ArrayList<DateScore>();
//        j.Connection();
    	
        String sql="select * from "+key+"_score";
        try {
            ps=conn.prepareStatement(sql);// 2.创建Satement并设置参数
            rs=ps.executeQuery(sql);  // 3.执行SQL语句
            // 4.处理结果
            while(rs.next()){
            	DateScore dateScore=new DateScore();
            	
            	int id=rs.getInt("id");
            	String date=rs.getString("date");          
            	int score_positive=rs.getInt("score_positive");
            	int score_negative=rs.getInt("score_negative");
            	int num_praise_positive=rs.getInt("num_praise_positive");
            	int num_praise_negative=rs.getInt("num_praise_negative");
            	int num_comment_positive=rs.getInt("num_comment_positive");
            	int num_comment_negative=rs.getInt("num_comment_negative");
            	int num_forward_positive=rs.getInt("num_forward_positive");
            	int num_forward_negative=rs.getInt("num_forward_negative");
            	  	
            	dateScore.setId(id);
            	dateScore.setDate(date);
            	dateScore.setScore_positive(score_positive);
            	dateScore.setScore_negative(score_negative);
            	dateScore.setNum_praise_positive(num_praise_positive);
            	dateScore.setNum_praise_negative(num_praise_negative);
            	dateScore.setNum_comment_positive(num_comment_positive);
            	dateScore.setNum_comment_negative(num_comment_negative);
            	dateScore.setNum_forward_positive(num_forward_positive);
            	dateScore.setNum_forward_negative(num_forward_negative);
                
            	dateScores.add(dateScore);
            }
//            System.out.println(rs.next());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //释放资源
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		return dateScores;
    }


    /*
     * 向数据库插入一个weiboDetail
     */
    public int add(WeiboDetail weiboDetail){
        int row=0;
        
        String sql="insert into "+key+"(name,txt,time,num_praise,num_forward,num_comment) values(?,?,?,?,?,?)";
        try {
//            conn=getConnection();//连接数据库
            ps=conn.prepareStatement(sql);
            ps.setString(1, weiboDetail.getName());
            ps.setString(2, weiboDetail.getTxt());
            ps.setString(3, weiboDetail.getTime());
            ps.setInt(4, weiboDetail.getNum_praise());
            ps.setInt(5, weiboDetail.getNum_forward());
            ps.setInt(6, weiboDetail.getNum_comment());
            row=ps.executeUpdate();
           
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                ps.close();
//                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        return row;
    }
    
    /*
     * 向数据库插入一个DateScore
     */
    public int add(DateScore dateScore){
        int row=0;
        
        String sql="insert into "+key+"_score(id,date,score_positive,score_negative,num_praise_positive,num_praise_negative,num_comment_positive,num_comment_negative,num_forward_positive,num_forward_negative) values(?,?,?,?,?,?,?,?,?,?)";
        try {
//            conn=getConnection();//连接数据库
            ps=conn.prepareStatement(sql);
            ps.setInt(1, dateScore.getId());
            ps.setString(2, dateScore.getDate());
            ps.setInt(3, dateScore.getScore_positive());
            ps.setInt(4, dateScore.getScore_negative());
            ps.setInt(5, dateScore.getNum_praise_positive());
            ps.setInt(6, dateScore.getNum_praise_negative());
            ps.setInt(7, dateScore.getNum_comment_positive());
            ps.setInt(8, dateScore.getNum_comment_negative());
            ps.setInt(9, dateScore.getNum_forward_positive());
            ps.setInt(10, dateScore.getNum_forward_negative());
            row=ps.executeUpdate();
           
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                ps.close();
//                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        return row;
    }
    
    
    public static void main(String[] args) {
        DBHandler dbHandler=new DBHandler("pike");
        dbHandler.getConnection();

//        //测试插入数据        
//        WeiboDetail weiboDetail=new WeiboDetail();
//        weiboDetail.setName("zxc");
//        weiboDetail.setTxt("有时充实的一天呢！！");
//        weiboDetail.setTime("7月25日");
//        weiboDetail.setNum_praise(3);
//        weiboDetail.setNum_forward(5);
//        weiboDetail.setNum_comment(7);
//        
//        dbHandler.add(weiboDetail);
        

        //测试获取数据
        List<WeiboDetail> weiboDetails=dbHandler.QueryAll();
        for(WeiboDetail weiboDetail:weiboDetails)
        {
        	System.out.println(weiboDetail.getTxt().toString());
        }
        
    }
	
}
