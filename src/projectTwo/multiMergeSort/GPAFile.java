package projectTwo.multiMergeSort;

import projectTwo.common.*;
import projectTwo.file.*;

public class GPAFile {
	public static void SaveGpaRecord(int ID, int credits, float points, IFileManager fileHandler) {
		String GPA = Float.toString(points / (float) credits);
		//GPA = GPA + "   "; // lazy validation
		//GPA = GPA.substring(0, 3);
		GPA = StringUtils.padRight(GPA,3);
		fileHandler.writeLine(Integer.toString(ID) + GPA);
	}
}
