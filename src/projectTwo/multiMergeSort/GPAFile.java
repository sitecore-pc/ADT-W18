package projectTwo.multiMergeSort;

import projectTwo.common.StringUtils;
import projectTwo.file.FileManagerV2;

public class GPAFile {
	public static void SaveGpaRecord(int ID, int credits, float points, FileManagerV2 fileHandler) {
		String GPA = Float.toString(points / (float) credits);
		//GPA = GPA + "   "; // lazy validation
		//GPA = GPA.substring(0, 3);
		GPA = StringUtils.padRight(GPA,3);
		fileHandler.writeLine(Integer.toString(ID) + GPA);
	}
}
