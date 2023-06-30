package sec02.ex03;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/member152/*")
public class MemberController extends HttpServlet{
MemberDAO memberDAO;
	
	public void init()throws ServletException{
		 memberDAO = new MemberDAO();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doHandle(request , response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doHandle(request , response);
	}
	private void doHandle(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getPathInfo();
		System.out.println("PathInfo:" +action);
		String nextPage = null;
		
		if(action == null || action.equals("/listMember.do")) {
			List<MemberVO> membersList = memberDAO.listMembers();
			request.setAttribute("membersList", membersList);
			nextPage="/test03/listMembers.jsp";
			
		} else if( action.equals("/addMember.do")) {
			
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			if(!memberDAO.checkId(id)) {
				MemberVO vo = new MemberVO(id, pwd, name, email);
				memberDAO.addMember(vo);
				request.setAttribute("msg", "addMember");
				nextPage="/member152/listMember.do";
			}else {
				nextPage="/test03/NewFile.jsp?commend=id";
			}
		} else if(action.equals("/memberForm.do")) {
			System.out.println(nextPage);
			nextPage = "/test03/MemberForm.jsp";
		
		} else if(action.equals("/modMemberForm.do")) {
			String id = request.getParameter("id");
			MemberVO memInfo = memberDAO.findMember(id);
			request.setAttribute("memInfo", memInfo);
			nextPage="/test03/modMemberForm.jsp";
			
		} else if(action.equals("/modMember.do")) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			MemberVO memberVO = new MemberVO(id, pwd, name, email);
			memberDAO.modMember(memberVO);
			request.setAttribute("msg", "modified");
			nextPage ="/member152/listMember.do";
			
		} else if(action.equals("/delMember.do")) {
			String id = request.getParameter("id");
			memberDAO.delMember(id);
			request.setAttribute("msg", "deleted");
			nextPage = "/member152/listMember.do";
		}
			
		
		else {
			List<MemberVO> membersList = memberDAO.listMembers();
			request.setAttribute("membersList", membersList);
			nextPage = "/test03/listMembers.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
		
	}
}
