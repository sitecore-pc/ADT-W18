package projectTwo;

import java.io.*;
import java.util.ArrayList;

import projectTwo.common.Parameters;
import projectTwo.file.*;
import projectTwo.models.Tuple;
import projectTwo.multiMergeSort.*;
import projectTwo.nestedJoin.NestedJoin;
import projectTwo.sortedJoin.SortedJoin;

public class Main {

	public static void main(String[] args) throws Exception {
		/*
		 * DO NESTED JOIN
		 */		
		System.out.print("NestedJoin: Joining... ");
		long startTime = System.nanoTime();
		long numNestedTuples = NestedJoin.DoJoin(
				Parameters.dataFiles[0],
				Parameters.dataFiles[1],
				"Output-NestedJoin.txt",
				"Output-GPA-NestedJoin.txt");
		System.out.print("done.\n"); 
		double nestedJoinTime = (System.nanoTime() - startTime) / 1000000000.0 ;
		long ioCountNested = FileManagerV3.getCounter();
		long nestedFileSize =  FileManagerV3.getFileSize("Output-NestedJoin.txt");
		FileManagerV3.resetCounter();		
		
		/*
		 * DO SORT
		 */
		System.out.print("TPMMS: Sorting... ");
		startTime = System.nanoTime();
		int sublists[] = Sort.DoSort();
		System.out.print(" Bag 1: " + sublists[0] + " sublists, Bag 2: " + sublists[1] + " sublists\n");
		
		System.out.print("TPMMS: Merging... ");
		String bag1 = MultiMerge.doMerge("T1_", sublists[0]);
		System.out.print("Bag 1 complete... ");
		String bag2 = MultiMerge.doMerge("T2_", sublists[1]);
		System.out.print("Bag 2 complete.\n");
		
		/*
		 * DO SORTED JOIN
		 */
		System.out.print("Sorted Join... ");
		long numSortedTuples = 
				SortedJoin.DoJoin(bag1, 
				bag2, 
				"Output-SortedJoin.txt",
				"Output-GPA-SortedJoin.txt");		
		System.out.print("done.\n");
		double sortedJoinTime = (System.nanoTime() - startTime) / 1000000000.0;
		long ioCountSorted = FileManagerV3.getCounter();
		long sortedFileSize = FileManagerV3.getFileSize("Output-SortedJoin.txt");
		
		/*
		 * FINAL OUTPUT
		 */
		System.out.println("");
		System.out.println("------------------");
		System.out.println("PERFORMANCE REPORT");
		System.out.println("------------------");
		System.out.println("");
		System.out.println("  NESTED JOIN");
		System.out.println("---------------");
		System.out.println("Time taken: " + nestedJoinTime + "s");
		System.out.println("I/O operations: " + ioCountNested);
		System.out.println("Tuples in final output: " + numNestedTuples);
		System.out.println("File size final output: " + nestedFileSize + "bytes");
		System.out.println("");
		System.out.println("  SORTED JOIN");
		System.out.println("---------------");
		System.out.println("Time taken: " + sortedJoinTime + "s");
		System.out.println("I/O operations: " + ioCountSorted);
		System.out.println("Tuples in final output: " + numSortedTuples);
		System.out.println("File size final output: " + sortedFileSize + "bytes");
		System.out.println("");
		System.out.println("--COMPLETE--");
	}
	
	public static void TestNestedJoin(){
		String fileName1 = Parameters.dataFiles[0];
		String fileName2 = Parameters.dataFiles[1];
		
		NestedJoin.DoJoin(fileName1, fileName2, "resources/NestedJoinBag.txt", "resources/NestedJoinGPABag.txt");
	}
	
	public static void TestMemoryParams(){
		System.out.println("Total Memory: " + (Parameters.getTotalMemory()/1024) + "KBs");
		System.out.println("Max Memory: " + (Parameters.getMaxMemory()/1024) + "KBs");
		System.out.println("Available Memory: " + (Parameters.getAvailableMemory()/1024) + "KBs");
		System.out.println("Maximum fitting Tuples in memory: " + Parameters.getMaxTuplesCountT1() + " Tuples");
		System.out.println("Maximum fitting Blocks in memory: " + Parameters.getMaxBlocksCountT1() + " Blocks");
	}
	
	public static void TestByteFileReader() {
		try (
	            InputStream inputStream = new FileInputStream(FileManagerV3.getProjectFolderPath() + "resources/bag1.txt");
	            OutputStream outputStream = new FileOutputStream(FileManagerV3.getProjectFolderPath() + "resources/destination.txt");
	        ) {
	 
            byte[] buffer = new byte[101];
 
            if (inputStream.read(buffer) != -1) {
                outputStream.write(buffer);
            }
            System.out.println("Done!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	public static void TestFileManagerV3(){
		ArrayList<Tuple> students = new ArrayList<Tuple>();
		try{
			students.add(new Tuple(11111, "John", "Smith", 111, 222, 123456789, "3340, Maisonneuve, Montreal, QC"));
			students.add(new Tuple(22222222, "John", "Doe", 333, 333, 987654321, "2100, Maisonneuve, Montreal, QC"));
			students.add(new Tuple(12345678, "Jane", "Doe", 222, 111, 0,
					"4850, Cote-des-Neiges H3V1G5 Apt. 1106, Montreal, QC34567"));
			students.add(new Tuple(88888888, "Jasonsonny", "Jackson890", 8, 0, 999999999,
					"4858, Cote-des-Neiges H3V1G8 Apt. 803, Montreal, QC345678"));
		}
		catch (Exception ex)
		{
			
		}
		
		IFileManager f1 = new FileManagerV3("F1.txt");
		IFileManager f2 = new FileManagerV3("F2.txt");
		IFileManager f3 = new FileManagerV3("F3.txt");
		
		
		f1.cleanFile();
		f2.cleanFile();
		f3.cleanFile();
		
		
		System.out.println("---Testing F1---");
		f1.writeLine("Hi");
		System.out.println(f1.readNextLine());
		f1.writeLine("How are you?");
		f1.writeLine("I'm well thanks.");
		System.out.println(f1.readNextLine());
		System.out.println(f1.readNextLine());
		
		System.out.println("\n---Testing F2---");
		f2.writeLine("Second file");
		
		f2.writeLines(
				new String[]{
				students.get(0).toString(),
				students.get(1).toString(),
				students.get(2).toString(),
				students.get(3).toString()
				});//Simply an array of lines
		String[] lines = f2.readNextLines(10);
		for(int i = 0; i < lines.length; i++)
			System.out.println(lines[i]);
		
		f2.finalize();
		f2.setFile("F2.txt");
		//f2 = new FileManagerV3("F2.txt");
		lines = f2.readNextLines(10);
		for(int i = 0; i < lines.length; i++)
			System.out.println(lines[i]);
		
		f3.deleteFile();
		System.out.println("Number of lines in F2.txt: " + f2.getTotalNumberOfRows(Parameters.maxTupleBytesT1));
	}
}
