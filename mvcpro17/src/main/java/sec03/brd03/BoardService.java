package sec03.brd03;

import java.util.List;

public class BoardService {

	BoardDAO boardDAO;

	public BoardService() {
		boardDAO = new BoardDAO();
	}
	
	public List<ArticleVO> listArticles(){
		List<ArticleVO> articlesList = boardDAO.selectAllArticles();
		return articlesList;
	}
	
	public int addArticle(ArticleVO article) {
		System.out.println("service");
		return boardDAO.insertNewArticle(article);
	}
}
