package projectOne.bagDifference;

import java.io.FileNotFoundException;
import projectOne.file.FileManagerV2;

public class BagDifference {
	
	private static FileManagerV2 fmT1 = null;
	private static FileManagerV2 fmT2 = null;
	private static final FileManagerV2 fmResult = new FileManagerV2("Result.txt");
	
	private static String currentTupleT1 = new String();
	private static String nextTupleT1 = new String();
	private static String currentTupleT2 = new String();
	private static int m = 0, n = 0, k = 0, mPrevious = 0, nPrevious = 0;
	private static boolean startOfProg = true;
	
	//called from main to read through the sorted bag1
	public static String comparator(String bag1, String bag2) throws FileNotFoundException
	{
		fmT1 = new FileManagerV2(bag1);//Initiate FileManager for T1
		fmT2 = new FileManagerV2(bag2);//Initiate FileManager for T2
		currentTupleT1 = fmT1.readNextLine();
		while(null!=currentTupleT1) {
			m=0;
			nextTupleT1 = fmT1.readNextLine();
			
			//This condition checks if this is the last tuple in the file T1 and next tuple is null
			if(null!=currentTupleT1 && null==nextTupleT1) {
				m++;
				n = readT2(currentTupleT1, m);
				mPrevious = m;
				writeData();
			}
			
			//Calculates m and n as currentTupleT1 and nextTupleT1 are not null
			if(null!=currentTupleT1 && null!=nextTupleT1) {
				m++;
				while(equals(currentTupleT1, nextTupleT1)) {
					m++;
					currentTupleT1 = nextTupleT1;
					nextTupleT1 = fmT1.readNextLine();
				}
				n = readT2(currentTupleT1, m);
				mPrevious = m;
				writeData();
			}			
			currentTupleT1 = nextTupleT1;
		}
//		File results = new File("Result.txt");
//		long resultSize = results.length();
		return "";
	}
	
	//called from comparator to read through sorted bag2
	private static int readT2(String currentUniqueTupleT1, int m) throws FileNotFoundException
	{
		if(n!=0 && n<mPrevious)
		{
			if(nPrevious!=0)
				currentTupleT2 = fmT2.readNextLine();
		}
		if(startOfProg)	{//to initiate the reading of file T2
			currentTupleT2 = fmT2.readNextLine();//assigns the first tuple in T2 to currentTupleT2
			startOfProg = false;
		}
		n = 0;
		if(null!=currentTupleT2) {
			boolean areTuplesIdentical = equals(currentTupleT2, currentUniqueTupleT1);
			if(areTuplesIdentical) {
				while(areTuplesIdentical) {
					n++;
					currentTupleT2 = fmT2.readNextLine();
					areTuplesIdentical = equals(currentTupleT2, currentUniqueTupleT1);
				}
				nPrevious = n;
				return n;
			}
			else {
				int t1UniqueKey = Integer.parseInt(currentUniqueTupleT1.substring(0,8));//Takes T1's current tuple and takes out its first 8 characters
				int t2UniqueKey = Integer.parseInt(currentTupleT2.substring(0,8));//Takes T1's current tuple and takes out its first 8 characters
			
				if(t2UniqueKey<t1UniqueKey)	{
					while(t2UniqueKey<=t1UniqueKey)
					{
						currentTupleT2 = fmT2.readNextLine();
						t2UniqueKey = Integer.parseInt(currentTupleT2.substring(0,8));
						if(t2UniqueKey==t1UniqueKey && equals(currentTupleT2, currentUniqueTupleT1))
						{
							while(equals(currentTupleT2, currentUniqueTupleT1))//not commented
							{
								n++;
								currentTupleT2 = fmT2.readNextLine();
							}
							nPrevious = n;
							return n;
						}
					}
					n=0;
					nPrevious=n;
					return n;
				}
				
				if(t2UniqueKey==t1UniqueKey) {//T2's unique key is equal to T1's unique key but not the whole tuple
					if(!equals(currentUniqueTupleT1, currentTupleT2)) {
						nPrevious = 0;
						return 0;
					}
				}
				
				if(t2UniqueKey>t1UniqueKey) {//T2 tuple is greater than T1
					nPrevious = 0;
					return 0;
				}
			}
			nPrevious = n;
			return n;	
		}
		nPrevious = 0;
		return 0;
	}
	
	//write data to Result file
	private static void writeData() throws FileNotFoundException
	{
		if(m<n)
			k = 0;
		else
			k = m-n;
		currentTupleT1=currentTupleT1+"--"+k;
		fmResult.writeLine(currentTupleT1);
	}
	
	//utility function to equate two strings faster
	public static boolean equals(final String s1, final String s2) {
		return s1 != null && s2 != null && s1.hashCode() == s2.hashCode()
		    && s1.equals(s2);
	}
}