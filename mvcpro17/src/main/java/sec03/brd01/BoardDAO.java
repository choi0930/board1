package sec03.brd01;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	private DataSource dataFactory;
	private Connection con;
	private PreparedStatement pstmt;
	
	public BoardDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/servletex");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List selectAllArticles() {
		List articlesList = new ArrayList();
		try {
			con = dataFactory.getConnection();
			String query = "SELECT CASE WHEN LEVEL-1 > 0 then CONCAT(CONCAT(repeat(' ',level-1),' < '), board.title) ELSE board.title END AS title, board.articleNO, board.parentNO, result.level, board.content, board.id, board.writeDate"
					+"  FROM"
					+ "   				(SELECT function_hierarchical() AS articleNO, @level AS level"
					+ "    				FROM (SELECT @start_with:=0, @articleNO:=@start_with, @level:=0) tbl JOIN t_board) result"
					+ "    				JOIN t_board board ON board.articleNO = result.articleNO";
			
			//String query = "select * from t_board";
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int level = rs.getInt("level");
				int articleNO = rs.getInt("articleNO");
				int parentNO = rs.getInt("parentNO");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				Date writeDate = rs.getDate("writeDate");
				ArticleVO article = new ArticleVO();
				article.setLevel(level);
				article.setArticleNO(articleNO);
				article.setParentNo(parentNO);
				article.setTitle(title);
				article.setContent(content);
				article.setId(id);
				article.setWriteDate(writeDate);
				articlesList.add(article);
			}
				rs.close();
				pstmt.close();
				con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return articlesList;
	}
}
