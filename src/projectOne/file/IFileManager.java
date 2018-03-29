package projectOne.file;

public interface IFileManager {
	//Returns the total file rows' count
	int getTotalNumberOfRows(int tupleBytes);
	
	//Sets the pointing address and Creates the file if not exist
	void setFile(String fileName);
	
	//Reads the following line and goes to next (file stays open)
	//If its first time, it reads first line. If reaches to the end it will return ""
	String readNextLine();
	
	//Reads multiple lines instead of one line, starting from the current line (file stays open) 
	String[] readNextLines(int maxLinesQuantity);
	
	//Writes one line (only) at the end of the file (file stays open)
	boolean writeLine(String line);
	
	//Writes multiple lines (only) at the end of the file (file stays open)
	boolean writeLines(String[] lines);
		
	//Deletes the file
	boolean deleteFile();
	
	//Deletes the file and creates a new file with the same name
	boolean cleanFile();
}
