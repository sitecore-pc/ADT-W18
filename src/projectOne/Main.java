package projectOne;

import java.util.ArrayList;
import java.util.Iterator;

import projectOne.file.*;
import projectOne.models.*;

public class Main {
	static final FileManager fm = FileManager.getCurrent();
	static final String fileName = "Sia.txt";

	public static void main(String[] args) {
		
		//FileManager test
		System.out.println("Write file result: " + fm.appendFile("Hi\r\nHow're you?", fileName));
		ArrayList<String> lines = fm.readFile(fileName,20);
		System.out.println("Lines: " + lines.size());
		System.out.println("Last line: " + lines.get(lines.size()-1));
		
		
		//Tuple model test
		//Filling a new list of Tuples
		ArrayList<Tuple> students = new ArrayList<Tuple>();
		try {
			students.add(new Tuple(11111,"John", "Smith", 111,222,123456789,"3340, Maisonneuve, Montreal, QC"));
			students.add(new Tuple(22222222,"John", "Doe", 333,333,987654321,"2100, Maisonneuve, Montreal, QC"));
			students.add(new Tuple(12345678,"Jane", "Doe", 222,111,0,"4850, Cote-des-Neiges H3V1G5 Apt. 1106, Montreal, QC34567"));
			students.add(new Tuple(88888888,"Jasonsonny", "Jackson890", 8,0,999999999,"4858, Cote-des-Neiges H3V1G8 Apt. 803, Montreal, QC345678"));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		//Converting the list of Tuples to string and printing
		ArrayList<String> studentStrings = new ArrayList<String>();
		for (Iterator<Tuple> studentsIterator = students.iterator(); studentsIterator.hasNext();) {
			Tuple student = studentsIterator.next();
			String stdString = student.toString();
			studentStrings.add(stdString);
			System.out.println(stdString);
		}
		
		//Parsing back the string list to Tuple
		ArrayList<Tuple> reparsedStudents = new ArrayList<Tuple>();
		for (Iterator<String> studentsIterator = studentStrings.iterator(); studentsIterator.hasNext();) {
			String stdString = studentsIterator.next();
			reparsedStudents.add(new Tuple(stdString));
		}
		
		//Printing the parsed list again
		for (Iterator<Tuple> studentsIterator = reparsedStudents.iterator(); studentsIterator.hasNext();) {
			Tuple student = studentsIterator.next();
			System.out.println(student.toString());
		}
	}

}
