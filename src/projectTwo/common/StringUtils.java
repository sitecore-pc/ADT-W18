package projectTwo.common;

//This class has been extracted from: http://www.rgagnon.com/javadetails/java-0448.html
public class StringUtils {
	private StringUtils() {}

	// pad with " " to the right to the given length (n)
	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	// pad with " " to the left to the given length (n)
	public static String padLeft(String s, int n) {
		return String.format("%1$" + n + "s", s);
	}

	/*public static void main(String args[]) throws Exception {
		System.out.println(StringUtils.padRight("Howto", 20) + "*");
		System.out.println(StringUtils.padLeft("Howto", 20) + "*");
	}*/
}
