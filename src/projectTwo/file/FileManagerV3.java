package projectTwo.file;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class FileManagerV3 implements IFileManager {
	private static String _projectPath = null;
	private static String _projectPathEscaped = null;
	public static String getProjectFolderPath(){
		if(_projectPath == null) new FileManagerV3("dummy");//Bad practice!
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
	
	private InputStream _inputStream = null;
	private FileReader _fileReader = null;
	private BufferedReader _bufferedReader = null;
	private BufferedReader getReader(){
		if(_bufferedReader == null) initReader();
		return _bufferedReader;
	}
	private InputStream getByteReader(){
		if(_inputStream == null) initReader();
		return _inputStream;
	}
	
	private static long _counter = 0;
	public static long getCounter() {
		return _counter;
	}
	private static void incrementCounter() {
		_counter++;
	}
	public static void resetCounter() {
		_counter = 0;
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
			_fileWriter = new FileWriter(fileAddress, true);
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
			_inputStream = new FileInputStream(fileAddress);
			_fileReader = new FileReader(fileAddress);
		    _bufferedReader = new BufferedReader(_fileReader);
		    return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean terminateFile() {
		boolean success = true;
		if(_printWriter != null){
			try{
				_printWriter.close();
			}
			catch (Exception e) {
				e.printStackTrace();
				success = false;
			}
			finally {
			    try {
			        if(_bufferedWriter != null)
			        	_bufferedWriter.close();
			    } catch (IOException e) {
			    	e.printStackTrace();
			    	success = false;
			    }
			    try {
			        if(_fileWriter != null)
			        	_fileWriter.close();
			    } catch (IOException e) {
			    	e.printStackTrace();
			    	success = false;
			    }
			}
		}

		if(_bufferedReader != null)
		{
			try{
				_bufferedReader.close();
			}
			catch (Exception e) {
				e.printStackTrace();
				success = false;
			}
			finally {
			    try {
			    	if(_fileReader != null)
			        	_fileReader.close();
			    } catch (IOException e) {
			    	e.printStackTrace();
			    	success = false;
			    }
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
			String [] array = res.toArray(new String[0]);
			
			return array;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public String[] blockReadNextLines(int maxLinesQuantity, int tupleSize, int blockSize){
        System.gc();
		if(maxLinesQuantity <= 0 || blockSize <= 0 || tupleSize <= 0) return new String[0];
		
		try {
			InputStream inputStream = getByteReader();

            int totalBytes = tupleSize * maxLinesQuantity;
            int blocks = (int)Math.floor((double)totalBytes/(double)blockSize);
            int lastBlockSize = totalBytes%blockSize;
            
			StringBuffer sb = new StringBuffer(totalBytes);
			
            byte[] buffer = new byte[blockSize];
            byte[] lastBuffer = new byte[lastBlockSize];
 
            while (blocks != 0 && inputStream.read(buffer) != -1) {
                sb.append(new String(buffer, StandardCharsets.US_ASCII));
                blocks--;
            }
            
            if(inputStream.read(lastBuffer) != -1)
            {
            	sb.append(new String(lastBuffer, StandardCharsets.US_ASCII));
            }
            
            String temp = sb.toString();
            sb = null;
            System.gc();
            return temp.split("\n");
        } catch (IOException ex) {
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
	
	
	//Call this only for limited times. Frequent call will affect performance 
	public static long getFileSize(String filename) {
		FileManagerV3 file = new FileManagerV3(filename);//Bad practice
		file.getSize();
		return file._fileSize;
	}
}
