package moive;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebServlet("/reservation/*")
public class ReservationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Reservation vo;
	ReservationDao dao;
	
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		vo = new Reservation();
		dao = new ReservationDao();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getPathInfo();
		String nextPage="";
		if(action.equals("/findByResid")) {
			String _resid=request.getParameter("resid");
			int resid = Integer.parseInt(_resid);
			dao.findByResid(resid);
		}
	}
}
