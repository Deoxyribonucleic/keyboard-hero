package game;

import java.util.Random;
import java.util.Scanner;


public class Game {
	private static final char ESCAPE = 27;
	
	private static final int PENALTY_WRONG = 2;
	private static final int PENALTY_SLOW = 1;
	private static final int MAX_PENALTY = 6;
	
	public Game(Keyboard keyboard, Renderer renderer, Leaderboards leaderboards) {
		this.keyboard = keyboard;
		this.renderer = renderer;
		this.leaderboards = leaderboards;
	}

	public void run(String playerName) {
		boolean keepPlaying = true;
		
		while (keepPlaying) {
			float survivalTime = play();
			
			System.out.printf("\n\nYou made it for %f seconds!\n", survivalTime);
			leaderboards.add(new Score(playerName, survivalTime));
			
			keepPlaying = askPlayAgain();
		}
	}

	private float play() {
		renderer.start();
		keyboard.start();
		
		countdown(3);
		
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
			waitFor(keyEnd);
		}
		
		float survivalTime = toSeconds(System.nanoTime() - startTime);

		keyboard.stop();
		renderer.stop();
		
		return survivalTime;
	}
	
	private long getKeyTime(int difficulty) {
		return seconds(1.0f / (difficulty / 30.f + 1) * 2.0f);
	}
	
	private void waitFor(long when) {
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
	
	private void countdown(int seconds) {
		renderer.clear();
		System.out.println("Get ready...\r");
		while(seconds-- > 0) {
			System.out.printf("%d... ", seconds + 1);
			waitFor(System.nanoTime() + seconds(1));
		}
		renderer.clear();
	}
	
	static long seconds(float s) {
		return (long)(s * 1000000000.0f);
	}
	
	public static float toSeconds(long n) {
		return n / 1000000000.0f;
	}

	private Keyboard keyboard;
	private Renderer renderer;
	private Leaderboards leaderboards;
	
	private static String keys = "abcdefghijklmnopqrstuvwxyz"; //ABCDEFGHIJKLMNOPQRSTUVWXYZ";
}
