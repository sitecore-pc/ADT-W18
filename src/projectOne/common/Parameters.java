package projectOne.common;

public class Parameters {
	
	public static String dataFiles[] = {"resources/bag1.txt", "resources/bag2.txt"};
	
	public static int tuplesPerBlock = 40;
	
	public static int maxTuplesFitInMemory = 40000;
	
	public static int maxTuplesBytes = 101;
	
	public static long getAvailableMemory(){
		return Runtime.getRuntime().freeMemory();
	}
	
	public static long getMaxMemory(){
		return Runtime.getRuntime().maxMemory();
	}
	
	public static long getTotalMemory(){
		return Runtime.getRuntime().totalMemory();
	}
	
	public static long getMaxTuplesCount(){
		return (getAvailableMemory()/maxTuplesBytes);
	}
	
	public static long getMaxBlocksCount(){
		return (getAvailableMemory()/(tuplesPerBlock * maxTuplesBytes));
	}
}
