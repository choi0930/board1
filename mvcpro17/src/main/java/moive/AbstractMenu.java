package moive;

import java.util.Scanner;

public abstract class AbstractMenu implements Menu {
	protected String menuText;
	protected Menu prevMenu;
	protected static final Scanner scanner = new Scanner(System.in);
	
	public AbstractMenu(String menuText, Menu prevMenu) {
		this.menuText = menuText;
		this.prevMenu = prevMenu;
	}
	
	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println("\n"+menuText); //메뉴 출력
	}


	public void setPrevMenu(Menu prevMenu) { //세터 메소드
		// TODO Auto-generated method stub
		this.prevMenu = prevMenu;
	}

}
