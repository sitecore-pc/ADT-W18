package projectOne;

import java.util.ArrayList;
import projectOne.file.FileManager;

public class Main {
	static final FileManager fm = FileManager.getCurrent();
	static final String fileName = "Sia.txt";

	public static void main(String[] args) {
		System.out.println("Write file result: " + fm.appendFile("Hi\r\nHow're you?", fileName));
		
		ArrayList<String> lines = fm.readFile(fileName,20);
		System.out.println("Lines: " + lines.size());
		System.out.println("Last line: " + lines.get(lines.size()-1));
	}

}
