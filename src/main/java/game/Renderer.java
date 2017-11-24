package game;


public class Renderer {
	private static final String RESET = "0m";
	private static final String RED = "31m";
	private static final String BLUE = "34m";
	private static final String GREEN = "32m";
	private static final String WHITE = "37;1m";
	private static final String BACK = "1D";
	private static final String HIDE_CURSOR = "?25l";
	private static final String SHOW_CURSOR = "?25h";

	private static String escape(String seq) {
		return String.format("\u001b[%s", seq);
	}

	public void showNextCharacter(char c) {
		System.out.printf("%s%c%s", escape(WHITE), c, escape(BACK));
		last = c;
	}

	public void start() {
		System.out.print(escape(HIDE_CURSOR));
	}
	
	public void stop() {
		System.out.println(escape(SHOW_CURSOR) + escape(RESET));
	}

	public void wrong() {
		System.out.printf("%s%c", escape(RED), last);
	}

	public void slow() {
		System.out.printf("%s%c", escape(BLUE), last);
	}

	public void correct() {
		System.out.printf("%s%c", escape(GREEN), last);
	}

	private char last;
}
