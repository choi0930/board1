package moive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource datafactory;
	
	public UserDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			datafactory = (DataSource) envContext.lookup("jdbc/servletex");	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public void addUser(UserVO vo) {
		String id = vo.getId();
		String pwd = vo.getPwd();
		String name = vo.getName();
		String email = vo.getEmail();
		try {
			con = datafactory.getConnection();
			String query = "insert into movieuser (id, pwd, name, email) values(?,?,?,?)";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public boolean checkIdPwd(UserVO vo) {
		boolean result = false;
		try {
			con = datafactory.getConnection();
			String id = vo.getId();
			String pwd = vo.getPwd();
			String query = "select id, pwd from movieuser where id = ? and pwd=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				result=true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
}
