package moive;

public class UserService {
	UserDAO dao;
	
	public UserService(){
		dao = new UserDAO();
	}
	
	public void addUser(UserVO vo) {
		dao.addUser(vo);
	}
	
	public boolean checkIdPwd(UserVO vo) {
		return dao.checkIdPwd(vo);
	}
}
