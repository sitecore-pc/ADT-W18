package projectOne;

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
import java.util.Iterator;
import java.util.Scanner;

import projectOne.file.*;
import projectOne.models.*;

public class Main {
	static final FileManager fm = FileManager.getCurrent();
	static final String fileName = "";
	static int count=0;
	 static ArrayList<String> fileNames= new ArrayList<String>();
	public static void main(String[] args) {
		
		DivideFile("T1");
		DivideFile("T2");
		for (String temp : fileNames)
		{
			Read_Sort(temp);
		}
		
		//FileManager test
		//System.out.println("Write file result: " + fm.appendFile("Hi\r\nHow're you?", fileName));
		//ArrayList<String> lines = fm.readFile(fileName,20);
		//System.out.println("Lines: " + lines.size());
		//System.out.println("Last line: " + lines.get(lines.size()-1));
		
		
		//Tuple model test
		//Filling a new list of Tuples
		ArrayList<Tuple> students = new ArrayList<Tuple>();
		try {
			students.add(new Tuple(11111,"John", "Smith", 111,222,123456789,"3340, Maisonneuve, Montreal, QC"));
			students.add(new Tuple(22222222,"John", "Doe", 333,333,987654321,"2100, Maisonneuve, Montreal, QC"));
			students.add(new Tuple(12345678,"Jane", "Doe", 222,111,0,"4850, Cote-des-Neiges H3V1G5 Apt. 1106, Montreal, QC34567"));
			students.add(new Tuple(88888888,"Jasonsonny", "Jackson890", 8,0,999999999,"4858, Cote-des-Neiges H3V1G8 Apt. 803, Montreal, QC345678"));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		//Converting the list of Tuples to string and printing
		ArrayList<String> studentStrings = new ArrayList<String>();
		for (Iterator<Tuple> studentsIterator = students.iterator(); studentsIterator.hasNext();) {
			Tuple student = studentsIterator.next();
			String stdString = student.toString();
			studentStrings.add(stdString);
			System.out.println(stdString);
		}
		
		//Parsing back the string list to Tuple
		ArrayList<Tuple> reparsedStudents = new ArrayList<Tuple>();
		for (Iterator<String> studentsIterator = studentStrings.iterator(); studentsIterator.hasNext();) {
			String stdString = studentsIterator.next();
			reparsedStudents.add(new Tuple(stdString));
		}
		
		//Printing the parsed list again
		for (Iterator<Tuple> studentsIterator = reparsedStudents.iterator(); studentsIterator.hasNext();) {
			Tuple student = studentsIterator.next();
			System.out.println(student.toString());
		}
	}
	public static void DivideFile(String FileName)
	{
		try{
			 String inputfile =fm._projectPath+FileName+".txt";
			  double nol = 40.0; //  maximmum tuples stored in one block
			  File file = new File(inputfile);
			  Scanner scanner = new Scanner(file);
			  int count = 0;
			 
			  while (scanner.hasNextLine()) 
			  {
			   scanner.nextLine();
			   count++;
			  }
			  //System.out.println("Lines in the file: " + count); 
			 
			  double temp = (count/nol);
			  int temp1=(int)temp;
			  int nof=0;
			  if(temp1==temp)
			  {
			   nof=temp1;
			  }
			  else
			  {
			   nof=temp1+1;
			  }
			  //System.out.println("No. of files to be generated :"+nof); // Displays no. of files to be generated.
			 
			  // Split files to smaller files
			 
			  FileInputStream fstream = new FileInputStream(inputfile); DataInputStream in = new DataInputStream(fstream);
			 
			  BufferedReader br = new BufferedReader(new InputStreamReader(in)); String strLine;
			  
			  for (int j=1;j<=nof;j++)
			  {
			   FileWriter fstream1 = new FileWriter(fm._projectPath+FileName+j+".txt");     // Destination File Location
			   
			   BufferedWriter out = new BufferedWriter(fstream1); 
			   for (int i=1;i<=nol;i++)
			   {
			    strLine = br.readLine(); 
			    if (strLine!= null)
			    {
			     out.write(strLine); 
			     if(i!=nol)
			     {
			      out.newLine();
			     }
			    }
			   }
			
			   String path=fm._projectPath;
			   path=path.substring(1);
			   fileNames.add(FileName+j+".txt");
			   out.close();
			  }
			 
			  in.close();
			 }catch (Exception e)
			 {
			  System.err.println("Error: " + e.getMessage());
			 }
			 
			}
	
	public static void Read_Sort(String fileName)
	{
		
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
			//fm.appendFile(Test.getID()+ Test.getFirstName()+ Test.getLastName()+Test.getDepartment()+Test.getProgram()+Test.getSINNumber(),Test.getAddress(), fileName);
					
			
			
		}
		count++;
	}


}
