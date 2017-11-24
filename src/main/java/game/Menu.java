package game;


class Menu {
	Menu(Keyboard keyboard) {
		this.keyboard = keyboard;
	}
	
	public String askPlayerName() {
		return System.console().readLine("Please enter you name: ");
	}
	
	private Keyboard keyboard;
}