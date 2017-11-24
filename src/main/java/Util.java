package game;

public class Util {
	public static void command(String command) {
		try {
			String[] cmd = { "/bin/sh", "-c", String.format("%s</dev/tty", command) };
			Runtime.getRuntime().exec(cmd).waitFor();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
