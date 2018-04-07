package projectTwo.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManagerV3 implements IFileManager {
	private static String _projectPath = null;
	private static String _projectPathEscaped = null;
	public static String getProjectFolderPath(){
		if(_projectPath == null) new FileManagerV2("dummy");//Bad practice!
		return _projectPath;
	}
	
	protected String _fileName = null;
	protected String _fullFileAddress = null;
	public String getFullFileAddress(){
		if(_fileName == null || _fileName.isEmpty()) return "";//Not happening
		if(_fullFileAddress == null || _fullFileAddress.isEmpty())
			_fullFileAddress = getProjectFolderPath() + _fileName;
		return _fullFileAddress;
	}
	public String getFullEscapedFileAddress(){
		if(_fileName == null || _fileName.isEmpty()) return "";//Not happening
		return _projectPathEscaped + _fileName;
	}
	
	private FileWriter _fileWriter = null;
	private BufferedWriter _bufferedWriter = null;
	private PrintWriter _printWriter = null;
	private PrintWriter getWriter(){
		if(_printWriter == null) initWriter();
		return _printWriter;
	}
	
	private FileReader _fileReader = null;
	private BufferedReader _bufferedReader = null;
	private BufferedReader getReader(){
		if(_bufferedReader == null) initReader();
		return _bufferedReader;
	}
	
	private static long _counter = 0;
	public static long getCounter() {
		return _counter;
	}
	private static void incrementCounter() {
		_counter++;
	}
	
	private long _fileSize;
	private void getSize(){
		try{
			File f = new File(getFullFileAddress());
			if(f.exists())
				_fileSize = f.length();
			else
				_fileSize = 0;
		}
		catch (Exception ex)
		{
			_fileSize = 0;
		}
	}
	
	private boolean initWriter() {
		String fileAddress = getFullFileAddress();
		if(fileAddress == null || fileAddress.isEmpty()) return false;
		try {
			_fileWriter = new FileWriter(fileAddress, false);
		    _bufferedWriter = new BufferedWriter(_fileWriter);
		    _printWriter = new PrintWriter(_bufferedWriter);
		    return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean initReader() {
		String fileAddress = getFullFileAddress();
		if(fileAddress == null || fileAddress.isEmpty()) return false;
		try {
			_fileReader = new FileReader(fileAddress);
		    _bufferedReader = new BufferedReader(_fileReader);
		    return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean terminateFile() {
		if(_printWriter == null) return true;
		boolean success = false; 
		try{
			_printWriter.close();
			success = true;
		}
		catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		finally {
			if(_printWriter != null)
				_printWriter.close();
		    
		    try {
		        if(_bufferedWriter != null)
		        	_bufferedWriter.close();
		        if(_bufferedReader != null)
		        	_bufferedReader.close();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    	success = false;
		    }
		    try {
		        if(_fileWriter != null)
		        	_fileWriter.close();
		        if(_fileReader != null)
		        	_fileReader.close(); // TODO: Why is this never called?
		    } catch (IOException e) {
		    	e.printStackTrace();
		    	success = false;
		    }
		}
		return success;
	}

	public int getTotalNumberOfRows(int tupleBytes){
		return (int)Math.ceil((double)_fileSize/(double)tupleBytes);
	}
	
	public FileManagerV3(String fileName) throws NullPointerException{
		if(fileName == null || fileName.isEmpty()) throw new NullPointerException("FileName is not specified");
		_fileName = fileName;
		
		try {
			//Setting the current project folder path
			URI absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
			_projectPath = absolutePath.getPath();
			_projectPathEscaped = absolutePath.toString();
		} catch (URISyntaxException e) {
			_projectPath = "";
			e.printStackTrace();
		}
		
		getSize();
	}

	@Override
	public void setFile(String fileName) throws NullPointerException{
		if(fileName == null || fileName.isEmpty()) throw new NullPointerException("FileName is not specified");
		_fileName = fileName;
		terminateFile();

		getSize();
		initWriter();
		initReader();
	}

	public String readNextLine() {
		BufferedReader br = getReader();
		if(br == null) return null;
		try{
			incrementCounter();
			return br.readLine();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public String[] readNextLines(int maxLinesQuantity) {
		if(maxLinesQuantity <= 0) return new String[0];
		
		BufferedReader br = getReader();
		if(br == null) return null;
		try{
			ArrayList<String> res = new ArrayList<String>();
			String str = null;
			for (int j = 0; j < maxLinesQuantity; j++) {
				incrementCounter();
				if ((str = br.readLine()) == null)
					break;
				res.add(str);

			}
			String[] array = new String[res.size()];
			for (int j = 0; j < res.size(); j++) {
				array[j] = res.get(j);
			}
			return array;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public boolean writeLine(String line) {
		if(line == null) return false;
		PrintWriter pr = getWriter();
		if(pr == null) return false;
		try {
			incrementCounter();
			pr.println(line);
		    pr.flush();
		    return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean writeLines(String[] lines) {
		if(lines == null || lines.length == 0) return false;
		PrintWriter pr = getWriter();
		if(pr == null) return false;
		try {
			for(int i = 0; i < lines.length; i++) {
				incrementCounter();
				pr.println(lines[i]);
			}
		    pr.flush();
		    return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteFile() {
		try
        {
			terminateFile();
			Path p = null;
			p = Paths.get(new URI(getFullEscapedFileAddress()));
            //p = Paths.get(getFullFileAddress());
			Files.deleteIfExists(p);
            return true;
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
            e.printStackTrace();
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
            e.printStackTrace();
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
            e.printStackTrace();
        } catch (URISyntaxException e) {
        	System.out.println("Invalid Syntax in URI string.");
			e.printStackTrace();
		}
         
        return false;
	}

	public boolean cleanFile() {
		deleteFile();
    	return initWriter() && initReader();
	}

	public void finalize(){
		terminateFile();
	}
}
