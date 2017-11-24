package game;


enum MenuAction {
	PLAY, VIEW_LEADERBOARDS, VIEW_LEADERBOARDS_PLAYER, QUIT
}

class Menu {
	Menu(Renderer renderer, Keyboard keyboard) {
		this.keyboard = keyboard;
		this.renderer = renderer;
	}
	
	public MenuAction getAction() {
		renderer.start();
		renderer.clear();
		if (playerName == null) {
			playerName = askPlayerName();
		}
		try {
			for(int x=0; true; ++x) {
				render(x);
				
				char input = keyboard.read(Game.seconds(0.1f));
				
				switch (input) {
				case 'p': return MenuAction.PLAY;
				case 'l': return MenuAction.VIEW_LEADERBOARDS;
				case 'm': return MenuAction.VIEW_LEADERBOARDS_PLAYER;
				case 'n': {
					playerName = askPlayerName();
					break;
				}
				case 'q': return MenuAction.QUIT;
				}
			}
		} finally {
			keyboard.stop();
			renderer.stop();
			renderer.clear();
		}
	}
	
	public String getPlayerName() {
		return playerName;
	}

	private void render(int x) {
		keyboard.stop();
		
		renderer.clear();
		renderer.titleEffect("KEYBOARD HERO", x);
		renderer.reset();
		
		System.out.printf("\nPlaying as: %s\n\n", playerName);
		
		System.out.println("Select:");
		System.out.println("* [P]lay");
		System.out.println("* [L]eaderboards");
		System.out.println("* [M]y Scores");
		System.out.println("* [N]ew Player");
		System.out.println("* [Q]uit");
		
		System.out.println("");
		
		keyboard.start();
	}
	
	private String askPlayerName() {
		renderer.stop();
		keyboard.stop();
		try {
			return System.console().readLine("Please enter you name: ");
		} finally {
			keyboard.start();
			renderer.start();
		}
	}
	
	private Renderer renderer;
	private Keyboard keyboard;
	private String playerName;
}