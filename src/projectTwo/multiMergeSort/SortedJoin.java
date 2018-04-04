package projectTwo.multiMergeSort;

import projectTwo.file.FileManagerV2;
import projectTwo.common.MarkUtils;

public class SortedJoin {
	public static void DoJoin(String filenameT1, String filenameT2, String outputFilename) {
		FileManagerV2 T1 = new FileManagerV2(filenameT1);
		FileManagerV2 T2 = new FileManagerV2(filenameT2);
		FileManagerV2 output = new FileManagerV2(outputFilename);
		
		String nextT1 = T1.readNextLine();
		String nextT2 = T2.readNextLine();
		
		int currentCredits = 0;
		float currentPoints = 0;
		
		while (nextT1 != null && nextT2 != null) {			
			int idT1 = Integer.parseInt(nextT1.substring(0, 8));
			int idT2 = Integer.parseInt(nextT2.substring(0, 8));
			
			if (idT1 < idT2) {
				if (currentCredits > 0) {
					GPAFile.SaveGpaRecord(idT1, currentCredits, currentPoints, output);
					currentCredits = 0;
					currentPoints = 0;
				}
				
				nextT1 = T1.readNextLine();
			}
			else if (idT1 > idT2) {
				nextT2 = T2.readNextLine();
			}
			else {
				String joinedString = nextT1 + nextT2.substring(8);
				output.writeLine(joinedString);
				
				float thisCredits = MarkUtils.ExtractCreditsFromTuple(nextT2);
				currentCredits += thisCredits;
				currentPoints += MarkUtils.ExtractGradeFromTuple(nextT2) * thisCredits;
				
				nextT2 = T2.readNextLine();
			}
		}
		
		T1.finalize();
		T2.finalize();
		output.finalize();
	}
}
