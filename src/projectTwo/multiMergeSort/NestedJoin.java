package projectTwo.multiMergeSort;

import projectTwo.common.MarkUtils;
import projectTwo.file.*;

public class NestedJoin {
	public static void DoJoin(String filenameT1, String filenameT2, String outputFilename, String gpaFileName)
	{
		IFileManager T1 = new FileManagerV2(filenameT1);
		IFileManager T2 = new FileManagerV2(filenameT2);
		IFileManager joinOutput = new FileManagerV2(outputFilename);
		IFileManager gpaOutput = new FileManagerV2(gpaFileName);
		
		String nextT1 = T1.readNextLine();

		int currentCredits = 0;
		float currentPoints = 0;
		
		while(nextT1 != null)
		{
			int idT1 = Integer.parseInt(nextT1.substring(0, 8));
			String nextT2 = T2.readNextLine();
			while(nextT2 != null)
			{
				int idT2 = Integer.parseInt(nextT2.substring(0, 8));
				
				if(idT1 == idT2)
				{
					String joinedString = nextT1 + nextT2.substring(8);
					joinOutput.writeLine(joinedString);
					
					float thisCredits = MarkUtils.ExtractCreditsFromTuple(nextT2);
					currentCredits += thisCredits;
					currentPoints += MarkUtils.ExtractGradeFromTuple(nextT2) * thisCredits;
				}
				
				nextT2 = T2.readNextLine();
			}
			
			if (currentCredits > 0) {
				GPAFile.SaveGpaRecord(idT1, currentCredits, currentPoints, gpaOutput);
				currentCredits = 0;
				currentPoints = 0;
			}
			
			//T2.setFile(filenameT2);//TODO: why does it clean?
			T2 = new FileManagerV2(filenameT2);
			
			nextT1 = T1.readNextLine();
		}
	}
}
