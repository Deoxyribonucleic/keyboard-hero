package game;

import java.util.Random;
import java.util.Scanner;


public class Game {
	private final char ESCAPE = 27;
	
	private final int PENALTY_WRONG = 2;
	private final int PENALTY_SLOW = 1;
	private final int MAX_PENALTY = 6;
	
	public Game() {
		keyboard = new Keyboard();
		renderer = new Renderer();
	}

	public void run() {
		boolean keepPlaying = true;
		while (keepPlaying) {
			float survivalTime = play();
			System.out.printf("You made it for %f seconds!\n", survivalTime);
			keepPlaying = askPlayAgain();
		}
	}

	private float play() {
		renderer.start();
		keyboard.start();
		
		int penalty = 0;
		int level = 0;
		long startTime = System.nanoTime();
		
		while(penalty < MAX_PENALTY) {
			long keyStart = System.nanoTime();
			long keyTime = getKeyTime(level);
			long keyEnd = keyStart + keyTime;
			
			char nextKey = chooseRandomKey(level);
			renderer.showNextCharacter(nextKey);
			
			char pressedKey = keyboard.read(keyTime);

			if (pressedKey == ESCAPE) {
				break;
			}
			else if (keyboard.isTimeout(pressedKey)) {
				renderer.slow();
				penalty += PENALTY_SLOW;
				continue;
			}
			else if (pressedKey == nextKey) {
				renderer.correct();
				++level;
			}
			else {
				renderer.wrong();
				penalty += PENALTY_WRONG;
			}
			
			// Consume and ignore input for the remaining time for this key
			waitForNextKey(keyEnd);
		}
		
		float survivalTime = toSeconds(System.nanoTime() - startTime);

		keyboard.stop();
		renderer.stop();
		
		return survivalTime;
	}
	
	private long getKeyTime(int difficulty) {
		return seconds(1.0f / (difficulty / 10.f + 1) * 2.0f);
	}
	
	private void waitForNextKey(long when) {
		while (System.nanoTime() < when) {
			keyboard.read(when - System.nanoTime());
		}
	}

	@SuppressWarnings("resource")
	private boolean askPlayAgain() {
		System.out.print("Would you like to play again? (yes/no) ");
		return new Scanner(System.in).next().equals("yes");
	}
	
	private char chooseRandomKey(int difficulty) {
		Random random = new Random();
		return keys.charAt(random.nextInt(keys.length()));
	}
	
	private static long seconds(float s) {
		return (long)(s * 1000000000.0f);
	}
	
	private static float toSeconds(long n) {
		return n / 1000000000.0f;
	}

	private Keyboard keyboard;
	private Renderer renderer;
	
	private static String keys = "abcdefghijklmnopqrstuvwxyz"; //ABCDEFGHIJKLMNOPQRSTUVWXYZ";
}
