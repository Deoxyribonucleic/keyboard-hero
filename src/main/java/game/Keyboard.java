package game;

import java.io.Reader;


public class Keyboard {
	public Keyboard() {
		reader = System.console().reader();
	}

	public char read(long timeout) {
		long startTime = System.nanoTime();

		while (true) {
			try {
				while (!reader.ready()) {
					if (System.nanoTime() - startTime > timeout) {
						return (char)-2;
					}
				}

				char key = (char)reader.read();
				if (key != '\r') {
					return key;
				}
			} catch (Exception e) {
				return (char)-1;
			}
		}
	}
	
	public void waitFor(char c) {
		start();
		while(read(Game.seconds(1.0f)) != c) { }
		stop();
	}
	
	public void start() {
		// Switch terminal to raw mode.
		Util.command("stty raw -echo");
	}

	public void stop() {
		// Switch terminal back to buffered (cooked) mode.
		Util.command("stty cooked echo");
	}
	
	public boolean isTimeout(char c) {
		return c == (char)-2;
	}

	private Reader reader;
}

