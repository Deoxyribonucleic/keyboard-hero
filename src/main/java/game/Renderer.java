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

	private static final String HOME = "1;1H";
	private static final String ERASE = "J";

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
		System.out.print(escape(SHOW_CURSOR) + escape(RESET));
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
	
	public void clear() {
		System.out.print(escape(HOME) + escape(ERASE));
	}
	
	public void reset() {
		System.out.print(escape(RESET));
	}
	
	public void titleEffect(String title, int x) {
		System.out.print(escape(WHITE));
		for(int i=0; i<title.length(); ++i) {
			System.out.printf("%s%c", escape(((i + x) % 2 == 0) ? RED : GREEN), title.charAt(i));
		}
	}

	private char last;
}
