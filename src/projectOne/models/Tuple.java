package projectOne.models;

import projectOne.common.StringUtils;

//Student Tuple
public class Tuple extends Object implements IConvertable{

	int ID;
	String FirstName;
	String LastName;
	int Department;
	int Program;
	int SINNumber;
	String Address;
	
	public int getID(){
		return this.ID;
	}
	
	public void setID(int id) throws Exception{
		if(id >= 0 && id <= 99999999)
			this.ID = id;
		else
			throw new Exception("Invalid input");
	}
	
	public String getFirstName(){
		return this.FirstName;
	}
	
	public void setFirstName(String firstName) throws Exception{
		if(!firstName.isEmpty() && firstName.length() > 10)
			throw new Exception("Invalid input");
		else
			this.FirstName = firstName;
	}
	
	public String getLastName(){
		return this.LastName;
	}
	
	public void setLastName(String lastName) throws Exception{
		if(!lastName.isEmpty() && lastName.length() > 10)
			throw new Exception("Invalid input");
		else
			this.LastName = lastName;
	}
	
	public String getAddress(){
		return this.Address;
	}
	
	public void setAddress(String address) throws Exception{
		if(!address.isEmpty() && address.length() > 57)
			throw new Exception("Invalid input");
		else
			this.Address = address;
	}

	public int getDepartment(){
		return this.Department;
	}
	
	public void setDepartment(int department) throws Exception{
		if(department >= 0 && department <= 999)
			this.Department = department;
		else
			throw new Exception("Invalid input");
	}

	public int getProgram(){
		return this.Program;
	}
	
	public void setProgram(int program) throws Exception{
		if(program >= 0 && program <= 999)
			this.Program = program;
		else
			throw new Exception("Invalid input");
	}
	
	public int getSINNumber(){
		return this.SINNumber;
	}
	
	public void setSINNumber(int sinNumber) throws Exception{
		if(sinNumber >= 0 && sinNumber <= 999999999)
			this.SINNumber = sinNumber;
		else
			throw new Exception("Invalid input");
	}

	public Tuple(){
	}
	
	public Tuple(String tupleString){
		this.parse(tupleString);
	}
	
	public Tuple(int id, String firstName, String lastName, int department, int program, int sinNumber, String address) throws Exception{
		setID(id);
		setFirstName(firstName);
		setLastName(lastName);
		setDepartment(department);
		setProgram(program);
		setSINNumber(sinNumber);
		setAddress(address);
	}
	
	@Override
	public void parse(String string) {
		if(string.isEmpty())
			return;
		
		try{
			this.ID = Integer.parseInt(string.substring(0, 8).trim());
			this.FirstName = string.substring(8,18).trim();
			this.LastName = string.substring(18,28).trim();
			this.Department = Integer.parseInt(string.substring(28, 31).trim());
			this.Program = Integer.parseInt(string.substring(31, 34).trim());
			this.SINNumber = Integer.parseInt(string.substring(34, 43).trim());
			this.Address = string.substring(43,100).trim();
		}
		catch (Exception ex) { }
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(StringUtils.padRight(Integer.toString(this.ID), 8));
		result.append(StringUtils.padRight(this.FirstName, 10));
		result.append(StringUtils.padRight(this.LastName, 10));
		result.append(StringUtils.padRight(Integer.toString(this.Department), 3));
		result.append(StringUtils.padRight(Integer.toString(this.Program), 3));
		result.append(StringUtils.padRight(Integer.toString(this.SINNumber), 9));
		result.append(StringUtils.padRight(this.Address, 57));
		return result.toString();
	}

}
