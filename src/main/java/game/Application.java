package game;

class Application {
	public Application() {
		keyboard = new Keyboard();
		renderer = new Renderer();
		
		menu = new Menu(renderer, keyboard);
		leaderboards = new Leaderboards("highscore.txt");
		
		game = new Game(keyboard, renderer, leaderboards);
	}

	public void run() {
		while(true) {
			MenuAction action = menu.getAction();
			
			switch(action) {
			case PLAY:
				game.run(menu.getPlayerName());
				break;
			case VIEW_LEADERBOARDS:
				leaderboards.print(leaderboards.top(5));
				waitForQuit();
				break;
			case VIEW_LEADERBOARDS_PLAYER:
				leaderboards.print(leaderboards.top(5, menu.getPlayerName()));
				waitForQuit();
				break;
			case QUIT:
				return;
			}
		}
	}
	
	public void waitForQuit() {
		System.out.println("\nPress Q to return");
		keyboard.waitFor('q');
	}
	
	private Keyboard keyboard;
	private Renderer renderer;
	private Menu menu;
	private Leaderboards leaderboards;
	private Game game;
}