package game;

import java.util.Scanner;


public class Game {
	public Game() {
		keyboard = new Keyboard();
		renderer = new Renderer();
	}

	public void run() {
		boolean keepPlaying = true;
		while (keepPlaying) {
			play();
			keepPlaying = askPlayAgain();
		}
	}

	private void play() {
		renderer.start();
		keyboard.start();

		while(true) {
			char key = keyboard.read(1.f);
			if (key == 'q') break;

			//System.out.printf("Input: %c\r\n", (char)key);
			renderer.showNextCharacter(key);

			if (key == 'T')
				renderer.slow();
			else if (key == 'j')
				renderer.correct();
			else
				renderer.wrong();
		}

		keyboard.stop();
		renderer.stop();
	}

	private boolean askPlayAgain() {
		System.out.print("Would you like to play again? (yes/no) ");
		return new Scanner(System.in).next().equals("yes");
	}

	private Keyboard keyboard;
	private Renderer renderer;
}
