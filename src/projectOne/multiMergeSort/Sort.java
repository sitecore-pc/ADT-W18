package projectOne.multiMergeSort;

import java.util.Arrays;
import projectOne.common.Parameters;
import projectOne.file.FileManagerV2;

public class Sort {
	public static int[] DoSort() {
		int[] sublists = new int[2];
		sublists[0] = Sort.ReadSortFile(Parameters.dataFiles[0]);
		sublists[1] = Sort.ReadSortFile(Parameters.dataFiles[1]);
		return sublists;
	}

	private static int ReadSortFile(String FileName) {
		int noOfSubFiles = 0;
		try {
			FileManagerV2 inputFile = new FileManagerV2(FileName);
			int noOfLines = Parameters.maxTuplesFitInMemory; // no of lines in subfiles
			long totalNoOfRows = inputFile.getTotalNumberOfRows();
			noOfSubFiles = (int) Math.ceil(totalNoOfRows / noOfLines);
			String subFileName = "";
			// splitting of file into smaller files
			for (int j = 1; j <= noOfSubFiles; j++) {
				// creation of file
				subFileName = FileName.substring(FileName.lastIndexOf('/') + 1, FileName.lastIndexOf('.'))
						+ "_" + j + ".txt";
				FileManagerV2 subFile = new FileManagerV2(subFileName);
				// read lines
				String[] lines1 = inputFile.readNextLines(noOfLines);
				// sort files
				Arrays.sort(lines1);
				// write
				for (String Test : lines1) {
					subFile.writeLine(Test);
				}
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		return noOfSubFiles;
	}
}
