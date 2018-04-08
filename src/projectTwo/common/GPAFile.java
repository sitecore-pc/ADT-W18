package projectTwo.common;

import projectTwo.common.*;
import projectTwo.file.*;

public class GPAFile {
	public static void SaveGpaRecord(int ID, int credits, float points, IFileManager fileHandler) {
		float gpa = points / (float) credits;
		String GPALetter = MarkUtils.ToLetterMark(gpa);
		String GPA = Float.toString(gpa);
		fileHandler.writeLine(
				StringUtils.padRight(Integer.toString(ID), 8) +
				StringUtils.padRight(GPALetter, 4) + 
				StringUtils.padRight(GPA, 3));
	}
}
