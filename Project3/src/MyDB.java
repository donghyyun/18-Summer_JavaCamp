import java.sql.*;
import java.util.ArrayList;

public class MyDB {
	Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<String[]>information = new ArrayList<String[]>();

	public MyDB() {
        try{
        	Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC 드라이버 로드
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?serverTimezone=UTC&&useSSL=false", "root", "vision2414");//URL,UID,PWD  
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
	
	public ArrayList<String[]> getdata(){
		try {
			stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery("select * from  user;");
			
			while (rs.next()) {
				String n1 = rs.getString("u_id");
				String n2 = rs.getString("u_pw");
				String n3 = rs.getString("u_name");
				String n4 = rs.getString("u_age");
				String []arr = {n1, n2, n3, n4};
				information.add(arr);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return information;
	}
	
	public String getpw(String userid) {
		try {
			stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery("select u_pw from user where u_id like'" + userid.trim() + "';");
			if (!rs.next()) {
				String pw = rs.getString("u_pw");
				return pw;	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void close(){
		try{ // 연결 해제(한정돼 있으므로)
			if(rs!=null)
				rs.close();
			if(pstmt!=null)
				pstmt.close();
			if(stmt!=null)
				stmt.close();
			if(conn!=null)
				conn.close();
			}
		catch(SQLException se2){se2.printStackTrace();}
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
	
	public String[] get(String userid) {
		String []info = new String[4];
		try {
			stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery("select * from  user where u_id like '" + userid.trim() + "';");
			
			
			if (rs.next()) {
				info[0] = rs.getString("u_id");
				info[1] = rs.getString("u_pw");
				info[2] = rs.getString("u_name");
				info[3] = rs.getString("u_age");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
	
	public void modify(String id, String userid, String userpw, String username, String userage) {
		try {
			stmt = (Statement) conn.createStatement();
			int r = stmt.executeUpdate("update user set u_id ='" + userid.trim() + "', u_pw ='" + userpw.trim() + "', u_name ='" + username.trim()
					+ "', u_age ='" + userage.trim() + "' where u_id like '" + id.trim() + "';");
			if (r == 1)
				System.out.println("update complete");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void remove(String userid) {
		try {
			stmt = (Statement) conn.createStatement();
			int r = stmt.executeUpdate("delete from user where u_id like '" + userid.trim() + "';");
			if (r == 1)
				System.out.println("delete complete");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean check(String userid, String userpw) {
		try {
			stmt = (Statement) conn.createStatement();
			rs = stmt.executeQuery("select * from user where u_id like '" + userid.trim() + "'");
			if (!rs.next())
				return false;
			else if(rs.next()){
				rs = stmt.executeQuery("select * from user where u_id like '" + userid.trim() + "'");
				System.out.println(rs.getString("u_pw"));
				if (!rs.getString("u_pw").equals(userpw))
					return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
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
}