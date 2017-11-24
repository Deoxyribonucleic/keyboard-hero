package game;

import java.io.Reader;


public class Keyboard {
	public Keyboard() {
		reader = System.console().reader();
	}

	public char read(float timeout) {
		long nanoTimeout = (long)(timeout * 1000000000);
		long startTime = System.nanoTime();

		while (true) {
			try {
				while (!reader.ready()) {
					if (System.nanoTime() - startTime > nanoTimeout) {
						return 'T';
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

	public void start() {
		// Switch terminal to raw mode.
		Util.command("stty raw -echo");
	}

	public void stop() {
		// Switch terminal back to buffered (cooked) mode.
		Util.command("stty cooked echo");
	}

	private Reader reader;
}

