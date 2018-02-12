package projectOne.multiMergeSort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import projectOne.common.Parameters;
import projectOne.file.FileManager;
import projectOne.models.Tuple;

public class Sort {
	private static final FileManager fm = FileManager.getCurrent();
	private static final String fileName = "";
	private static int count = 0;
	private static ArrayList<String> fileNames = new ArrayList<String>();
	
	public static int[] DoSort() {
		int[] sublists = new int[2];
		
		sublists[0] = Sort.DivideFile(Parameters.dataFiles[0]);
		sublists[1] = Sort.DivideFile(Parameters.dataFiles[1]);
		
		for (String temp : fileNames) {
			Sort.Read_Sort(temp);
		}
		
		return sublists;
	}
	
	private static int DivideFile(String FileName) {
		try {
			String inputfile = FileManager._projectPath + FileName;
			double nol = Parameters.tuplesPerBlock; // maximum tuples stored in one block
			File file = new File(inputfile);
			Scanner scanner = new Scanner(file);
			int count = 0;

			while (scanner.hasNextLine()) {
				scanner.nextLine();
				count++;
			}
			// System.out.println("Lines in the file: " + count);

			double temp = (count / nol);
			int temp1 = (int) temp;
			int nof = 0;
			if (temp1 == temp) {
				nof = temp1;
			} else {
				nof = temp1 + 1;
			}
			// System.out.println("No. of files to be generated :"+nof); // Displays no. of
			// files to be generated.

			// Split files to smaller files

			FileInputStream fstream = new FileInputStream(inputfile);
			DataInputStream in = new DataInputStream(fstream);

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			for (int j = 1; j <= nof; j++) {
				FileWriter fstream1 = new FileWriter(FileManager._projectPath + FileName + j + ".txt"); // Destination File
																								// Location

				BufferedWriter out = new BufferedWriter(fstream1);
				for (int i = 1; i <= nol; i++) {
					strLine = br.readLine();
					if (strLine != null) {
						out.write(strLine);
						if (i != nol) {
							out.newLine();
						}
					}
				}

				String path = FileManager._projectPath;
				path = path.substring(1);
				fileNames.add(FileName + j + ".txt");
				out.close();
			}

			in.close();
			
			return count;
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			return -1;
		}

	}

	private static void Read_Sort(String fileName) {

		fm.readFile(fileName, 40);

		Comparator<Tuple> comparator = new Comparator<Tuple>() {

			public int compare(Tuple TupleA, Tuple TupleB) {
				// System.out.println("inside compare");
				return Integer.compare(TupleA.getID(), TupleB.getID());
			}
		};
		Collections.sort(FileManager.students, comparator);
		FileManager.clearFile(fileName);
		for (Tuple Test : FileManager.students) {
			fm.appendFile(Integer.toString(Test.getID()), fileName);
			// fm.appendFile(Test.getID()+ Test.getFirstName()+
			// Test.getLastName()+Test.getDepartment()+Test.getProgram()+Test.getSINNumber(),Test.getAddress(),
			// fileName);

		}
		count++;
	}

}
