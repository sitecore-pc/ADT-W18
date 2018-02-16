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
			double noOfLines = 40.0; // no of lines in subfiles
			long totalNoOfRows = inputFile.getTotalNumberOfRows();
			double temp = (totalNoOfRows / noOfLines);
			int temp1 = (int) temp;
			if (temp1 == temp) {
				noOfSubFiles = temp1;
			} else {
				noOfSubFiles = temp1 + 1;
			}
			String subFileName = "";
			// splitting of file into smaller files
			for (int j = 1; j <= noOfSubFiles; j++) {
				// creation of file
				subFileName = FileName.substring(0, FileName.lastIndexOf('.'))
						+ "_" + j + ".txt";
				FileManagerV2 subFile = new FileManagerV2(subFileName);
				// read lines
				String[] lines1 = inputFile.readNextLines(40);
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
