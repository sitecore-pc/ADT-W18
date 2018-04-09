package projectTwo.nestedJoin;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import projectTwo.common.GPAFile;
import projectTwo.common.MarkUtils;
import projectTwo.file.*;

public class NestedJoin {
	
	static IFileManager T1;
	static String[] nextT1Arr;
	static int maxLinesQuantityT1 = 5000;
	static int i = 0;
	static int countForT2;
	
	private static Map<Integer, LinkedHashMap<String, int[]>> createT1BufferMap() {
		
		i++;
		countForT2 = 1;
		nextT1Arr = T1.readNextLines(maxLinesQuantityT1);
		if(null != nextT1Arr && nextT1Arr.length!=0) {
			Map<Integer, LinkedHashMap<String, int[]>> t1BufferMap = new HashMap<Integer, LinkedHashMap<String, int[]>>();
			
			for(int i=0;i<nextT1Arr.length;i++) {
				String tupleT1 = nextT1Arr[i];
				LinkedHashMap<String, int[]> innerMap = new LinkedHashMap<String, int[]>();
				int[] arr = {0, 0};
				innerMap.put(tupleT1, arr);
				t1BufferMap.put(Integer.parseInt(tupleT1.substring(0, 8)), innerMap);
			}
			return t1BufferMap;
		}
		return null;
	}
	
	public static long DoJoin(String filenameT1, String filenameT2, String outputFilename, String gpaFileName) {
		long totalJoinedTuples = 0;
		
		T1 = new FileManagerV3(filenameT1);
		IFileManager T2;
		IFileManager joinOutput = new FileManagerV3(outputFilename);
		IFileManager gpaOutput = new FileManagerV3(gpaFileName);
		boolean currentBufferedT2DataEmpty = true;
		int maxLinesQuantityT2 = 11005;
		
		String[] nextT2Arr;
		
		while(true) {
			Map<Integer, LinkedHashMap<String, int[]>> t1BufferMap = createT1BufferMap();
		
			if(null != t1BufferMap) {
				
				T2 = new FileManagerV3(filenameT2);
				nextT2Arr = T2.readNextLines(maxLinesQuantityT2);
				int j = 0;
			
				while(null != nextT2Arr && (nextT2Arr.length!=0)) {
			
					currentBufferedT2DataEmpty=true;
					String nextT2 = nextT2Arr[j];
					int idT2 = Integer.parseInt(nextT2.substring(0, 8));
				
					if(t1BufferMap.containsKey(idT2)) {
					
						for (Entry<String, int[]> entry : t1BufferMap.get(idT2).entrySet())	{
							String key = entry.getKey();
							String joinedString = key + nextT2.substring(8);
							joinOutput.writeLine(joinedString);
						
							float thisCredits = MarkUtils.ExtractCreditsFromTuple(nextT2);
							entry.getValue()[0] += thisCredits;
							entry.getValue()[1] += MarkUtils.ExtractGradeFromTuple(nextT2) * thisCredits;
							
							totalJoinedTuples++;
						}
					}
					
					//System.out.println("Buffer qty. of T1 ="+maxLinesQuantityT1+" & T2 ="+maxLinesQuantityT2+"...T1 round="+i+" ..T2 round="+countForT2+"...& j="+j);
					if(j==nextT2Arr.length-1) {
						nextT2Arr = T2.readNextLines(maxLinesQuantityT2);
						j=0;
						currentBufferedT2DataEmpty=false;
						countForT2++;
					}
					if(currentBufferedT2DataEmpty)
						j = j+1;
					if(null == nextT2Arr || nextT2.length()==0)
						break;

				}
				
				for (Entry<Integer, LinkedHashMap<String, int[]>> entry : t1BufferMap.entrySet())	{
					Integer key = entry.getKey();//key = idT1
					Map<String, int[]> subMap = entry.getValue();
					
					for (Entry<String, int[]> subMapEntry : subMap.entrySet()) {
						if(subMapEntry.getValue()[0] > 0)
						GPAFile.SaveGpaRecord(key, subMapEntry.getValue()[0], (float)subMapEntry.getValue()[1], gpaOutput);
					}
				}
				T2 = null;
			}
			else
				break;
		}
		
		
//		while(nextT1 != null)
//		{
//			int idT1 = Integer.parseInt(nextT1.substring(0, 8));
//			String nextT2 = T2.readNextLine();
//			while(nextT2 != null)
//			{
//				int idT2 = Integer.parseInt(nextT2.substring(0, 8));
//				
//				if(idT1 == idT2)
//				{
//					String joinedString = nextT1 + nextT2.substring(8);
//					joinOutput.writeLine(joinedString);
//					
//					float thisCredits = MarkUtils.ExtractCreditsFromTuple(nextT2);
//					currentCredits += thisCredits;
//					currentPoints += MarkUtils.ExtractGradeFromTuple(nextT2) * thisCredits;
//				}
//				
//				nextT2 = T2.readNextLine();
//			}
//			
//			if (currentCredits > 0) {
//				GPAFile.SaveGpaRecord(idT1, currentCredits, currentPoints, gpaOutput);
//				currentCredits = 0;
//				currentPoints = 0;
//			}
//			
//			//T2.setFile(filenameT2);//TODO: why does it clean?
//			T2 = new FileManagerV2(filenameT2);//TODO: Its not good. We have to dispose the previous one. (Memory Waste)
//			
//			nextT1 = T1.readNextLine();
//		}
		return totalJoinedTuples;
	}
}