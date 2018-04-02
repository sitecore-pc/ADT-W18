package projectTwo.multiMergeSort;

import java.util.Arrays;

import projectTwo.common.Parameters;
import projectTwo.file.FileManagerV2;

public class Sort {
	private static long _tuples = 0;

	public static long getTuples() {
		return _tuples;
	}

	public static int[] DoSort() {
		int[] sublists = new int[2];
		sublists[0] = Sort.ReadSortFile(Parameters.dataFiles[0],Parameters.getMaxTuplesCountT1(),Parameters.maxTupleBytesT1 );
		System.gc();
		sublists[1] = Sort.ReadSortFile(Parameters.dataFiles[1],Parameters.getMaxTuplesCountT2(), Parameters.maxTupleBytesT2);
		System.gc();
		return sublists;
	}

	private static int ReadSortFile(String FileName,long getMaxTuplesCount, int maxTupleBytes) {
		int noOfSubFiles = 0;
		try {
			FileManagerV2 inputFile = new FileManagerV2(FileName);
			int noOfLines =  (int)Math.floor((double)getMaxTuplesCount / 3.0); // no of lines in subfiles
			long totalNoOfRows = inputFile.getTotalNumberOfRows(maxTupleBytes);
			_tuples = _tuples + totalNoOfRows;
			noOfSubFiles = (int) Math.ceil((double) totalNoOfRows / (double) noOfLines);
			String subFileName = "";
			// splitting of file into smaller files
			for (int j = 1; j <= noOfSubFiles; j++) {
				// creation of file
				subFileName = FileName.substring(FileName.lastIndexOf('/') + 1, FileName.lastIndexOf('.')) + "_" + j
						+ ".txt";
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
