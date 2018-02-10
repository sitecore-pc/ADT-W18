package projectOne.file;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class FileManager {
	private String _projectPath = null;

	private FileManager(){
		URI absolutePath;
		try {
			absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
			_projectPath = absolutePath.getPath();
		} catch (URISyntaxException e) {
			_projectPath = "";
			e.printStackTrace();
		}
	}
	
	private static FileManager _current = null;
	public static FileManager getCurrent(){
		if(_current == null) _current = new FileManager();
		return _current;
	}

	public boolean appendFile(String text, String fileName){
		if(fileName.isEmpty())
			return false;
		boolean success = false;
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;
		try {
		    fw = new FileWriter(_projectPath + fileName, true);
		    bw = new BufferedWriter(fw);
		    out = new PrintWriter(bw);
		    out.println(text);
		    out.close();
		    success = true;
		} catch (IOException e) {
			success = false;
		}
		finally {
		    if(out != null)
			    out.close();
		    
		    try {
		        if(bw != null)
		            bw.close();
		    } catch (IOException e) {
		    	success = false;
		    }
		    try {
		        if(fw != null)
		            fw.close();
		    } catch (IOException e) {
		    	success = false;
		    }
		}
		
		/*PrintWriter writer = null;
		
		try{
			writer = new PrintWriter(fileName, "UTF-8");
			writer.println(text);
			success = true;
		}
		catch (Exception ex)
		{
			success = false;
		}
		finally{
			writer.close();
		}*/
		return success;
	}

	public ArrayList<String> readFile(String fileName, int maxLines)
	{
		ArrayList<String> res = new ArrayList<String>();
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(_projectPath + fileName));
			String str = null;
			for(int i = 0 ; i < maxLines; i++)
			{
				if((str = br.readLine()) == null) break;
				res.add(str);
			}
		}
		catch(Exception ex)
		{
			
		}
		finally{
			try{
				if(br != null)
					br.close();
			}
			catch (IOException e) {
		    }
		}
		return res;
	}
	
	public String readLine(String fileName)
	{
		BufferedReader br = null;
		String res = "";
		try{
			br = new BufferedReader(new FileReader(_projectPath + fileName));
			res = br.readLine();
		}
		catch(Exception ex)
		{
			
		}
		finally
		{
			try{
				if(br != null)
					br.close();
			}
			catch (IOException e) {
		    }
		}
		return res;
	}
}
