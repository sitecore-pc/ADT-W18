package projectTwo.common;

import projectTwo.file.*;

public class GPAFile {
	public static void SaveGpaRecord(int ID, int credits, float points, IFileManager fileHandler) {
		float gpa = points / (float) credits;
		String GPALetter = MarkUtils.ToLetterMark(gpa);

		StringBuffer sb = new StringBuffer();
		
		sb.append(StringUtils.padRight(Integer.toString(ID), 8));								//ID
		sb.append(StringUtils.padRight(GPALetter, 4));											//Letter
		sb.append(StringUtils.padRight(Double.toString(MarkUtils.ToFloatMark(GPALetter)), 4));	//Mark
		sb.append(StringUtils.padRight(Float.toString(gpa), 9));								//Real Mark
		
		fileHandler.writeLine(sb.toString());
	}
}
