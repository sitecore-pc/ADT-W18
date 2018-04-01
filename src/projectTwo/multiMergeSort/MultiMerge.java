package projectOne.multiMergeSort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import projectOne.common.Parameters;
import projectOne.file.FileManagerV2;

public class MultiMerge {

	public static String doMerge(String sourcePrefix, int numFiles) throws IOException, Exception {
		List<String> files = new ArrayList<String>();
		int currentIndex = 0;
		
		// calculate number of files to merge at one time
		int maxFiles = (int) Math.floor((double)Parameters.getMaxTuplesCountT1() * 0.9);
		
		// make a list of all the files to merge
		for (int i = 1; i <= numFiles; i++)
			files.add(sourcePrefix + i + ".txt");
		
		// Merging loop
		while (true) {
			int lastIndex = currentIndex + maxFiles - 1;
			if (lastIndex >= files.size())
				lastIndex = files.size() - 1;
			
			if (currentIndex == lastIndex)
				break;		
			
			// merge the maximum possible. 
			// the resulting file is saved and added to the end of the list
			files.add(partialMerge(files.subList(currentIndex, lastIndex + 1)));
			
			currentIndex = lastIndex + 1; 
		}
		
		System.gc();
		return files.get(files.size() - 1);
	}
	
	/***
	 * Merges files from sublist, returns name of file containing merged list
	 * @throws Exception 
	 */
	private static String partialMerge(List<String> sublist) throws Exception {
		String[] entryCache = new String[sublist.size()];
		FileManagerV2[] readControllers = new FileManagerV2[sublist.size()];
		String targetFilename = "mergetemp_" + System.nanoTime() + ".txt";
		FileManagerV2 target = new FileManagerV2(targetFilename);
		
		// open each file of the sublist
		int i = 0;
		for (String file : sublist) {
			readControllers[i] = new FileManagerV2(file);
			//entryCache[i] = new String();
			
			entryCache[i] = readControllers[i].readNextLine();
			if (entryCache[i] == null) throw new Exception("The sorted sub-list file " + file + " is empty.");
			
			i++;
		}

		// merge loop
		int lowest;
		while (true) {
			lowest = getIndexOfLowest(entryCache);
			
			if (lowest == -1)  // boundary condition
				break;
			
			// write lowest to target file
			target.writeLine(entryCache[lowest].toString());
			
			// get the next line from the sublist we just took from
			entryCache[lowest] = readControllers[lowest].readNextLine();
		}
		
		return targetFilename;
	}
	
	/***
	 * Returns -1 when all records are null
	 */
	private static int getIndexOfLowest(String[] array) {
		String first = "";
		int index = -1;
		
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				if (first.equals("") || first.compareTo(array[i]) > 0) {
					first = array[i];
					index = i;
				}
			}	
		}
		
		return index;
	}
	
}
