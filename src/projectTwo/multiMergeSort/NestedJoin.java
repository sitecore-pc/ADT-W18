package projectTwo.multiMergeSort;

import projectTwo.file.*;

public class NestedJoin {
	public static void DoJoin(String filenameT1, String filenameT2, String outputFilename)
	{
		IFileManager T1 = new FileManagerV2(filenameT1);
		IFileManager T2 = new FileManagerV2(filenameT2);
		IFileManager output = new FileManagerV2(outputFilename);
		
		String nextT1 = T1.readNextLine();
		String nextT2 = T2.readNextLine();
		
		while(nextT1 != null)
		{
			//TODO: to be implemented
			
			
			nextT1 = T1.readNextLine();
		}
	}
}
