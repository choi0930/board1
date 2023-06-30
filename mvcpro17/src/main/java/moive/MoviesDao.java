package moive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoviesDao {
	List<Movie> movielist  = new ArrayList();
	public Connection conn;
	
	public MoviesDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/servletex?useUnicode=true&characterEncoding=utf8",
					"root", "1234");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public List printAllMovies() {
		List<Movie> movieslist  = new ArrayList();
		try {
			String sql = "SELECT * FROM movie ORDER BY id DESC";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String genre = rs.getString("genre");
				Movie movie = new Movie();
				movie.setId(id);
				movie.setTitle(title);
				movie.setGenre(genre);
				System.out.println(rs.getInt("id"));
				movieslist.add(movie);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return movieslist;
	} // end printAllMovies
	
	
	public Movie findByMovieId(int _movie) {
		Movie movie = new Movie();
		try {
			String sql = "SELECT id, title, genre FROM movie WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, _movie);
			ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					movie.setId(rs.getInt("id"));
					movie.setTitle(rs.getString("title"));
					movie.setGenre(rs.getString("genre"));
				}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return movie;
	}//end findByMovield
	
	public boolean deleteMovie(int _movie) {
		try {
			String sql = "DELETE FROM movie WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, _movie);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}// end deleteMovie
	
	public int save( Movie movie) {
		System.out.println(movie.getTitle());
		int _id = (int)(Math.random() * 100000)+1;
		try {
			String sql = "INSERT INTO movie (id, title, genre) VALUES (?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, _id);
			pstmt.setString(2, movie.getTitle());
			pstmt.setString(3, movie.getGenre());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return _id;
	}//end save
	
}// end MoviesDao
