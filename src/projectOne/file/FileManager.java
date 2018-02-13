package projectOne.file;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import projectOne.models.Tuple;

@Deprecated
public class FileManager {
	public static String _projectPath = null;
	public static ArrayList<Tuple> students = new ArrayList<Tuple>();
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
			for (int j = 0; j < maxLines; j++) {
				if ((str = br.readLine()) == null)
					break;
				res.add(str);

			}
			students = new ArrayList<Tuple>();
			for (int i = 0; i < res.size(); i++) {
				Tuple t = new Tuple();
				t.setID(Integer.parseInt(res.get(i).substring(0, 8)));
				t.setFirstName(res.get(i).substring(8, 17));
				t.setLastName(res.get(i).substring(18, 27));
				t.setDepartment(Integer.parseInt(res.get(i).substring(28, 31)));
				t.setProgram(Integer.parseInt(res.get(i).substring(31, 34)));
				t.setSINNumber(Integer.parseInt(res.get(i).substring(34, 43)));
				t.setAddress(res.get(i).substring(43));
				students.add(t);
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
	
	public static void clearFile(String fileName)
	{
		try {
			PrintWriter pw = new PrintWriter(_projectPath + fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

