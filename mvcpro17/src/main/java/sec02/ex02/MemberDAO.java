package sec02.ex02;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public MemberDAO() {
		try {
			
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/servletex");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end 
	
	public List<MemberVO> listMembers(){
		List<MemberVO> membersList = new ArrayList();
		try {
			con = dataFactory.getConnection();
			String query = "SELECT * FROM t_member order by joinDate desc";
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				MemberVO vo = new MemberVO(id, pwd, name, email, joinDate);
				membersList.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return membersList;
	}
	
	public void addMember(MemberVO m) {
		try {
			con = dataFactory.getConnection();
			String id = m.getId();
			String pwd = m.getPwd();
			String name = m.getName();
			String email = m.getEmail();
			
			String query = "insert into t_member(id, pwd, name, email) values(?, ?, ?, ?)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (Exception e) {
				e.printStackTrace();
		}
		
	}//end addMember
	
	public boolean checkId(String id) {
		boolean rel = false;
		try {
			con= dataFactory.getConnection();
			String query = "select id from t_member where id = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				rel=true;
			}
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rel;
	}
	
}//Class end
