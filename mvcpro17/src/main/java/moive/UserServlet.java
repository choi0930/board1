package moive;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebServlet("/moiveUser/*")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserService service;
	UserVO userVO;
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		service = new UserService();
		userVO = new UserVO();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getPathInfo();
		String nextPage = "";
		PrintWriter out = response.getWriter();
		System.out.println(action);
		if(action.equals("/addUser")) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			UserVO vo = new UserVO(id, pwd, name, email);
			service.addUser(vo);
		
			nextPage="/moive/login.jsp";
		}else if(action.equals("login")) {
			
		}else if(action.equals("/CheckId")) {
			String id=request.getParameter("id");
			String pwd = request.getParameter("pwd");
			userVO.setId(id);
			userVO.setPwd(pwd);
			boolean check = service.checkIdPwd(userVO);
			if(check) {
				request.setAttribute("commend", "check");
				nextPage="/moive/mainpage.jsp";
			}else {
				out.print("<script>"
							+ "alert('아이디 혹은 비밀번호가 틀렸습니다');"
							+ "location.href='"
							+ request.getContextPath()
							+ "/moive/login.jsp';"
							+ "</script>");
				return;
			}
		} else if(action.equals("/ticketing")) {
			nextPage="/moiveServlet/listMoive";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
