package moive;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebServlet("/moiveServlet/*")
public class MoiveController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MoviesDao dao;
	Movie vo;
	public void init(ServletConfig config) throws ServletException {
		dao = new MoviesDao();
		vo = new Movie();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doHandle(request, response);
	}
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getPathInfo();
		String nextPage="";
		if(action.equals("/listMoive")) {
			List<Movie> moviesList = dao.printAllMovies();
			request.setAttribute("moviesList", moviesList);
			nextPage="/moive/moiveTicketing.jsp";
		}else if (action.equals("/findByMovieId")){
			String id = request.getParameter("id");
			int _id = Integer.parseInt(id);
			vo = dao.findByMovieId(_id);
			String title = vo.getTitle();
			request.setAttribute("title", title);
			nextPage="/reservation/findById";
		}
	RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
	dispatcher.forward(request, response);
	}
}
