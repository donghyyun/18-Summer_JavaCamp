import java.sql.*;
//import java.util.ArrayList;

public class DBconnect {
	Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    //ArrayList<String[]>information = new ArrayList<String[]>();

	public DBconnect() {
        try{
        	Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC 드라이버 로드
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/connect_six?serverTimezone=UTC&&useSSL=false", "root", "vision2414");//URL, Root ID, Root PW  
            }
        catch(ClassNotFoundException ce){
            ce.printStackTrace();            
        }
        catch(SQLException se){
            se.printStackTrace();    
        }
        catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public boolean check(String userid, String userpw) {
		try {
			stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery("select * from user where u_id like '" + userid.trim() + "'");
			//check database has id which received
			if (rs.next()) {
				rs = stmt.executeQuery("select u_pw from user where u_id like '" + userid.trim() + "'");
				if(rs.next()) {
					if (!rs.getString("u_pw").equals(userpw))
						return false;
				}
			}
			else {
				//if database has it, then check received pw is correct pw
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void register(String userid, String userpw, String username, String userage) {
		try {
			stmt = (Statement) conn.createStatement();
			int r = stmt.executeUpdate("insert into user(u_id,u_pw,u_name,u_age) values('" + userid.trim() + "','" + userpw.trim() + "','" + username.trim() + "','" + userage.trim() + "');");
			if (r == 1)
				System.out.println("insert complete");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean idcheck(String userid) {
		try {
			stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery("select * from user where u_id like '" + userid.trim() + "'");
			if (!rs.next())
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		return false;
	}
	
	public void close(){
		try{
			if(rs!=null)
				rs.close();
			if(pstmt!=null)
				pstmt.close();
			if(stmt!=null)
				stmt.close();
			if(conn!=null)
				conn.close();
		} catch(SQLException se2){se2.printStackTrace();}
	}
	
}
