package sec03.brd06;

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

public List selectAllArticles(Map pagingMap) {
		List articlesList = new ArrayList();
		int section = (Integer)pagingMap.get("section");
		int pageNum = (Integer)pagingMap.get("pageNum");
		try {
			con = dataFactory.getConnection();
			String query = "SELECT CASE WHEN LEVEL-1 > 0 then CONCAT(CONCAT(repeat(' ',level-1),' < '), board.title) ELSE board.title END AS title, board.articleNO, board.parentNO, result.level, board.content, board.id, board.writeDate"
					+"  FROM"
					+ "   				((SELECT function_hierarchical() AS articleNO, @level AS level"
					+ "    				FROM (SELECT @start_with:=0, @articleNO:=@start_with, @level:=0) tbl JOIN t_board) result"
					+ "    				JOIN t_board board ON board.articleNO = result.articleNO ) limit ? offset ? ";
			
			//String query = "select * from t_board";
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, 10); //한번에 표시할 수
			pstmt.setInt(2,(section - 1) * 100 + (pageNum - 1) * 10); 
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
				article.setParentNO(parentNO);
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
	public int selectTotArticles() {
		try {
			con=dataFactory.getConnection();
			String query = "select count(articleNO) from t_board";
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				int a = rs.getInt(1);
				rs.close();
				pstmt.close();
				con.close();
				return a;
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exceptione.
			e.printStackTrace();
		}
		return 0;
	}

	public List selectAllArticles() {
		List articlesList = new ArrayList();
		try {
			con = dataFactory.getConnection();
			String query = "SELECT CASE WHEN LEVEL-1 > 0 then CONCAT(CONCAT(repeat(' ',level-1),' < '), board.title) ELSE board.title END AS title, board.articleNO, board.parentNO, result.level, board.content, board.id, board.writeDate"
					+"  FROM"
					+ "   				(SELECT function_hierarchical() AS articleNO, @level AS level"
					+ "    				FROM (SELECT @start_with:=0, @articleNO:=@start_with, @level:=0) tbl JOIN t_board) result"
					+ "    				JOIN t_board board ON board.articleNO = result.articleNO ";
			
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
				article.setParentNO(parentNO);
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
	
	private int getNewArticleNO() {
		try {
			
			con= dataFactory.getConnection();
			//t_board테이블에서 articleNO칼럼의 최대값 select
			String query="SELECT max(articleNO) from t_board ";
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int a = rs.getInt(1)+1;
				//다음 번호 설정
				rs.close();
				pstmt.close();
				con.close();
				return a;
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
	
	public int insertNewArticle(ArticleVO article) { //새글쓰기
			int articleNO = getNewArticleNO(); // 다음 articleNO 번호 받아옴
		try {
			System.out.println("insert");
			con = dataFactory.getConnection();
			int parentNO =article.getParentNO();
			String title = article.getTitle();
			String content = article.getContent();
			String id = article.getId();
			String imageFileName = article.getImageFileName();
			
			String query = "INSERT INTO t_board(articleNO, parentNO, title, content, imageFileName, id) values(?, ?, ?, ?, ?, ?) ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, articleNO);
			pstmt.setInt(2, parentNO);
			pstmt.setString(3, title);
			pstmt.setString(4, content);
			pstmt.setString(5, imageFileName);
			pstmt.setString(6, id);
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return articleNO;
	}

	public ArticleVO selectArticle(int articleNO) {
		ArticleVO article = new ArticleVO();
		try {
			con = dataFactory.getConnection();
			String query = "select articleNO, parentNO, title, content, ifnull(imageFileName, 'null') as imageFileName , id, writeDate "
					+ " from t_board"
					+ " where articleNO=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, articleNO);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int _articleNO = rs.getInt("articleNO");
			int parentNO = rs.getInt("parentNO");
			String title = rs.getString("title");
			String content = rs.getString("content");
			String imageFileName = URLEncoder.encode(rs.getString("imageFileName"),"UTF-8");//한글로 들어갈때 깨짐방지
			String id = rs.getString("id");
			Date writeDate = rs.getDate("writeDate");
			if(imageFileName.equals("null")) {
				imageFileName = null;
			}
			 article = new ArticleVO(_articleNO, parentNO, title, content, imageFileName, id, writeDate);
				rs.close();
				pstmt.close();
				con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return article;
	}
	
	public void updateArticle(ArticleVO article) {
		int articleNO = article.getArticleNO();
		String title = article.getTitle();
		String content = article.getContent();
		String imageFileName = article.getImageFileName();
		try {
			con=dataFactory.getConnection();
			String query = "update t_board set title=?, content=?";
			if(imageFileName != null && imageFileName.length() != 0) {
				query+=", imageFileName=?";
			}
			query+=" where articleNO=?";
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			if(imageFileName != null && imageFileName.length() != 0){
				pstmt.setString(3, imageFileName);
				pstmt.setInt(4, articleNO);
			}else {
				pstmt.setInt(3, articleNO);
			}
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void deleteArticle(int articleNO) {
		try { 
			con = dataFactory.getConnection();
			String query = "DELETE FROM t_board ";
			query+= "where parentNO = ? or articleNO = ?"; //답글과 본문글 모두 삭제
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, articleNO);
			pstmt.setInt(2, articleNO);
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public List<Integer> selectRemovedArticles(int articleNO){
		List<Integer> articleNOList = new ArrayList<Integer>();
		try {
			con= dataFactory.getConnection();
			String query = "select articleNO from t_board where articleNO IN (select articleNO from t_board where parentNO = ? )";
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, articleNO);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				articleNO = rs.getInt("articleNO");
				articleNOList.add(articleNO);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return articleNOList;
	}
	
	
}
