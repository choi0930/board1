package moive;

import java.io.IOException;
import java.util.List;

public class MainMenu extends AbstractMenu{
	MoviesDao moviesDao = new MoviesDao();
	ReservationDao reservationDao = new ReservationDao();
	private static final MainMenu instance = new MainMenu(null);
	private static final String MAIN_MENU_TEXT = 
					"1: 영화 예매하기\n"+
					"2: 예매 확인하기\n"+
					"3: 예매 취소하기\n"+
					"4: 관리자 메뉴로 이동\n"+
					"q: 종료 \n\n"+
					"메뉴를 선택하세요: ";
	
	public MainMenu(Menu prevMenu) {
		super(MAIN_MENU_TEXT, prevMenu);
		// TODO Auto-generated constructor stub
	}
	public static MainMenu getInstance() {
		return instance;
	}

	@Override
	public Menu next() {
		// TODO Auto-generated method stub
		switch(scanner.nextLine()) {
		case"1":
			reserve();
			return this;
		case"2":
			//checkReservation();
			return this;
		case"3":
			cancelReservation();
			return this;
		case "4":
			if(!checkAdminPassword()) {
				System.out.println(">> 비밀번호가 틀렸습니다.");
				return this;
			}
			AdminMenu adminMenu = AdminMenu.getInstance();
			adminMenu.setPrevMenu(this);
			return adminMenu;
		case "q":
			return prevMenu;
			default: return this;
		}
	}//end next

	private boolean checkAdminPassword() {
		System.out.print("관리자 비밀번호를 입력하세요: ");
		return "admin1234".equals(scanner.nextLine());
	}
	
/*	private String checkReservation() {
		System.out.print("예매발급번호를 입력하세요: ");
		String msg;
		try {
			Reservation r = reservationDao.findByResid(Integer.parseInt(scanner.nextLine()));
			if(r.getResid() != 0) { // r != null 하면 else로 안들어감 왜?
				System.out.println(">> [확인 완료]"+ " 예매발급번호: "+r.getResid()+" 좌석: "+r.getSeat()+" 영화이릅: "+r.getMoviename());
				msg=">> [확인 완료]"+ " 예매발급번호: "+r.getResid()+" 좌석: "+r.getSeat()+" 영화이릅: "+r.getMoviename();
				} else {
				System.out.println(">> 예매 내역이 없습니다.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("파일 입출력에 문제가 생겼습니다.");
			e.printStackTrace();
		}
	} //end checkReservation
*/	
	private void cancelReservation() {
		System.out.print("예매발급번호를 입력하세요: ");
		int cancel = Integer.parseInt(scanner.nextLine());
		boolean canceled = reservationDao.cancel(cancel);
		if(canceled != false) {
			System.out.printf(">> [취소 완료] %d 의 예매가 취소되었습니다.\n",
					cancel);
		} else {
			System.out.println(">> 예매 내역이 없습니다.");
		}
	}//end cancelReservation
	
	private void reserve() {
		try {
			List<Movie> movies = moviesDao.printAllMovies();
			for(int i =0; i<movies.size(); i++) {
				System.out.printf("%s\n", movies.get(i).toString());
			}
			System.out.print("예매할 영화발급번호를 입력하세요:");
			int moviedId = Integer.parseInt(scanner.nextLine());
			Movie m = moviesDao.findByMovieId(moviedId);
			System.out.println(m);
			
			List<Reservation> reservations = reservationDao.findById(m.getTitle());
			Seats seats = new Seats(reservations);
			seats.show();
			
			System.out.print("좌석을 선택하세요(예: E-9): ");
			String seatName = scanner.nextLine();
			seats.mark(seatName);
			
			Reservation r = new Reservation(seatName, m.getTitle(), m.getId());
			int _resid = reservationDao.save(r);
			System.out.println(">> 예매가 완료되었습니다.");
			System.out.printf(">> 발급번호: %d\n", _resid);
		
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println(">> 파일 입출력에 문제가 생겼습니다..");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.printf(">> 예매에 실패하였습니다: %s\n", e.getMessage());
		}
	}//end reserve
	
}//end class
