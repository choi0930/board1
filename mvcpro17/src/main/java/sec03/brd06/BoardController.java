package sec03.brd06;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
@WebServlet("/board6/*")
public class BoardController extends HttpServlet{
	private static String ARTICLE_IMAGE_REPO = "C:\\board\\article_image"; //파일을 업로드할 경로
	BoardService boardService;
	ArticleVO articleVO;
	HttpSession session;
	//페이징 답글기능 구현
	//메모장 로직 연구해서 11.서버프로그램 구현 교육에 넣기
	public void init() throws ServletException{
		boardService = new BoardService();
		articleVO = new ArticleVO();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doHandle(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doHandle(request, response);
	}
	private void doHandle(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
		String nextPage="";//스페이스바 공백X
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getPathInfo();
		System.out.println("action:"+action);
		try {
			List<ArticleVO> articlesList = new ArrayList<ArticleVO>();
			
			if (action == null) {
				String _section = request.getParameter("section");
				String _pageNum = request.getParameter("pageNum");
				int section = Integer.parseInt(((_section==null)? "1":_section));
				int pageNum = Integer.parseInt(((_pageNum==null)? "1":_pageNum));
				Map<String, Integer> pagingMap = new HashMap<String, Integer>();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				Map articlesMap=boardService.listArticles(pagingMap);
				articlesMap.put("section", section);
				articlesMap.put("pageNum", pageNum);
				request.setAttribute("articlesMap", articlesMap);
				nextPage = "/board06/listArticles.jsp";
				
				//articlesList = boardService.listArticles();
				//request.setAttribute("articlesList", articlesList);
				//nextPage = "/board06/listArticles.jsp";
				
			} else if(action.equals("/listArticles.do")) {
				String _section = request.getParameter("section");
				String _pageNum = request.getParameter("pageNum");
				int section = Integer.parseInt(((_section==null)? "1":_section));
				int pageNum = Integer.parseInt(((_pageNum==null)? "1":_pageNum));
				Map pagingMap = new HashMap();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				
				Map articlesMap=boardService.listArticles(pagingMap);
				articlesMap.put("section", section);
				articlesMap.put("pageNum", pageNum);
				request.setAttribute("articlesMap", articlesMap);
				nextPage = "/board06/listArticles.jsp";
				//articlesList = boardService.listArticles();
				//request.setAttribute("articlesList", articlesList);
				//nextPage="/board06/listArticles.jsp";
				
			} else if(action.equals("/articleForm.do")) {
				nextPage = "/board06/articleForm.jsp";
				
			} else if(action.equals("/addArticle.do")) {
				int articleNO = 0;
				//upload에서 map주소를 리턴 받는다
				Map<String, String> articleMap = upload(request, response);
				
				String title = articleMap.get("title");
				String content = articleMap.get("content");
				//이미지명.jpg | png 받아옴
				String imageFileName = articleMap.get("imageFileName");
				
				articleVO.setParentNO(0);
				articleVO.setId("Clear");
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setImageFileName(imageFileName);
				articleNO = boardService.addArticle(articleVO);
				
				 //파일이 존재할시 실행
				if(imageFileName != null && imageFileName.length() !=0) {
					//파일이 임시 저장되어있는 파일
					File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
					System.out.println(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
				
					//파일을 저장할 장소
					File desDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
					//파일 저장할 장소 파일(디렉토리) 만들기
					desDir.mkdirs();
					//파일 옮기기
					FileUtils.moveFileToDirectory(srcFile, desDir,true);
					
					PrintWriter pw = response.getWriter();
					//스크립트 location으로 listArticles.do로 넘어가기
					pw.print("<script>"
							+ "alert('새글을 추가했습니다');"
							+ "location.href='"
							+ request.getContextPath()
							+ "/board6/listArticles.do';"
							+ "</script>");
					return;
				} 
				
			} else if(action.equals("/viewArticle.do")) {
				String articleNO = request.getParameter("articleNO");
				articleVO = boardService.viewArticle(Integer.parseInt(articleNO));
				request.setAttribute("article", articleVO);
				nextPage="/board06/viewArticle.jsp";
				
			} else if(action.equals("/modArticle.do")){
				Map<String, String> articleMap = upload(request, response);
				int articleNO = Integer.parseInt(articleMap.get("articleNO"));
				articleVO.setArticleNO(articleNO);
				String title = articleMap.get("title");
				String content = articleMap.get("content");
				
				String imageFileName = articleMap.get("imageFileName");
				articleVO.setParentNO(0);
				articleVO.setId("Clear");
				articleVO.setTitle(title);
				articleVO.setContent(content);
				
				articleVO.setImageFileName(imageFileName);
				boardService.modArticle(articleVO);
				if(imageFileName != null && imageFileName.length() != 0) {
					String originalFileName = articleMap.get("originalFileName");
					File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
					File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
					File oldFile = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO+"\\"+originalFileName);
					oldFile.delete();
				}
				PrintWriter out = response.getWriter();
				out.print("<script>"+"alert('글을 수정했습니다');"+"location.href='"
						+ request.getContextPath()
						+"/board6/viewArticle.do?articleNO="
						+ articleNO+"';"+"</script>" );
				return;
			}else if(action.equals("/removeArticle.do")) {
				int articleNO = Integer.parseInt(request.getParameter("articleNO")); //request에서 articleNO을 받음 int형으로 형변환
				List<Integer> articleNOList = boardService.removeArticle(articleNO);// borardService.removeArticle 호출
				articleNOList.add(articleNO);
				for(int _articleNO: articleNOList) {
					File imgDir = new File(ARTICLE_IMAGE_REPO+"\\"+_articleNO);
					if(imgDir.exists()) {
						FileUtils.deleteDirectory(imgDir);
					}
				}
				PrintWriter out = response.getWriter();
				out.print("<script>"+"alert('글을 삭제했습니다');"+"location.href='"
						+ request.getContextPath()
						+"/board6/listArticles.do';"+"</script>");
								
				return;
			}else if(action.equals("/replyForm.do")) {
				int parentNO = Integer.parseInt(request.getParameter("parentNO"));
				session = request.getSession();
				session.setAttribute("parentNO", parentNO);
				System.out.println(parentNO);
				nextPage = "/board06/replyForm.jsp";
			} else if(action.equals("/addReply.do")) {
				session= request.getSession();
				int parentNO = (Integer) session.getAttribute("parentNO");
				session.removeAttribute("parentNO");
				Map<String, String> articleMap = upload(request, response);
				String title=articleMap.get("title");
				String content=articleMap.get("content");
				String imageFileName = articleMap.get("imageFileName");
				System.out.println(title);
				articleVO.setParentNO(parentNO);
				articleVO.setId("Fail");
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setImageFileName(imageFileName);
				int articleNO = boardService.addReply(articleVO);
				
				if(imageFileName != null && imageFileName.length() != 0) {
					File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
					File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
					
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
				}
				
				PrintWriter out = response.getWriter();
				out.print("<script>"+" alert('답글을 추가했습니다');"
						+ " location.href='"
						+ request.getContextPath()
						+ "/board6/viewArticle.do?articleNO="
						+ articleNO+"';"+"</script>");
				return;
			} 
			else {
				nextPage="/board6/listArticles.do";
			}
			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end Handle
	
	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		Map<String, String> articleMap = new HashMap<String, String>();
		String encoding = "utf-8";
		
		File currentDirPath = new File(ARTICLE_IMAGE_REPO);
		//물리적인 설정
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(currentDirPath);
		factory.setSizeThreshold(1024 * 1024);
		
		//
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			//request안 에 있는 값들이 리스트 형태로 바꿔서 리스트에 넣어줌
			//ex) 0인덱스 title : temp 1인덱스 content:tempput 2인덱스 imageFilName:Temp.jpg 
			List items = upload.parseRequest(request);
			
			for(int i=0; i<items.size(); i++) {
										//인덱스에 있는 값들은 request형 이므로 FileItem형으로 변환
				FileItem fileItem = (FileItem) items.get(i);
				
				if(fileItem.isFormField()) { //FileItem안의 데이터가 text형태면 true , 파일형식이면 false 
					System.out.println(fileItem.getFieldName()+"="+fileItem.getString(encoding));
					articleMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
										//input의 name        / 내용 출력
				}else {
					System.out.println("파라미터명: "+fileItem.getFieldName());
					System.out.println("파일명: "+fileItem.getName());
					System.out.println("파일사이즈: "+fileItem.getSize()+"bytes");
					
					if(fileItem.getSize() >0){
						int idx = fileItem.getName().lastIndexOf("\\");
						
						if( idx == -1) {
							idx = fileItem.getName().lastIndexOf("/");
						}
						
						String fileName = fileItem.getName().substring(idx+1);
						articleMap.put(fileItem.getFieldName(),fileName);
									//  imageFileName          앞의 경로를 자른 파일이름
						File uploadFile = new File(currentDirPath+"\\temp\\"+fileName);
						
						fileItem.write(uploadFile);
					} // end if
				}// end else
			}// end for
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return articleMap;
	}//end  upload
}

