package game;


public class Renderer {
	private static final String RESET = "0m";
	private static final String RED = "31m";
	private static final String YELLOW = "33m";
	private static final String GREEN = "32m";
	private static final String WHITE = "37;1m";
	private static final String BACK = "1D";

	private static String escape(String seq) {
		return String.format("\u001b[%s", seq);
	}

	public void showNextCharacter(char c) {
		System.out.printf("%s%c%s", escape(RESET), c, escape(BACK));
		last = c;
	}

	public void start() {
		Util.command("setterm --cursor off");
	}
	
	public void stop() {
		Util.command("setterm --cursor on");
		System.out.println(escape(RESET));
	}

	public void wrong() {
		System.out.printf("%s%c", escape(RED), last);
	}

	public void slow() {
		System.out.printf("%s%c", escape(YELLOW), last);
	}

	public void correct() {
		System.out.printf("%s%c", escape(GREEN), last);
	}

	private char last;
}
