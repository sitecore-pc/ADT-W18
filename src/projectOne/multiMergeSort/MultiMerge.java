package projectOne.multiMergeSort;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import projectOne.file.FileManager;


// TODO: Patrick add counters as needed

public class MultiMerge {

	public static void doMerge(String sourcePrefix, int numFiles) throws IOException {
		List<String> files = new ArrayList<String>();
		String target = FileManager._projectPath + sourcePrefix + "-sorted.txt";
		int currentIndex = 0;
		
		// calculate number of files to merge at one time
		int maxFiles = 1000; // TODO: patrick
		
		// make a list of all the files to merge
		for (int i = 0; i < numFiles; i++)
			files.add(FileManager._projectPath + sourcePrefix + i + ".txt");
		
		// Merging loop
		while (currentIndex < files.size()) {
			int lastIndex = currentIndex + maxFiles - 1;
			if (lastIndex >= files.size())
				lastIndex = files.size() - 1;
			
			// merge the maximum possible. 
			// the resulting file is saved and added to the end of the list
			files.add(partialMerge(files.subList(currentIndex, lastIndex)));
			
			currentIndex = lastIndex + 1;
		}
		
		// rename last file
		if (!rename(files.get(files.size() - 1), target))
			throw new IOException("Failed to create output from merge: " + target);
	}
	
	/***
	 * Merges files from sublist, returns name of file containing merged list
	 */
	private static String partialMerge(List<String> sublist) {
		
		// TODO: patrick
		
		
		
		return null;
	}
	
	private static boolean rename(String from, String to) {
		// ref: https://stackoverflow.com/questions/1158777/rename-a-file-using-java
		File file = new File(from);
		File file2 = new File(to);
		return file.renameTo(file2);
	}
	
}
