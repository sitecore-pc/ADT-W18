package projectTwo.common;

public class Parameters {
	
	public static String dataFiles[] = {"resources/T1.txt", "resources/T2.txt"};
	
	public static int tuplesPerBlock = 40;
	
	public static int maxTupleBytesT1 = 101;
	public static int maxTupleBytesT2 = 28;
	
	public static long getAvailableMemory(){
		return Runtime.getRuntime().freeMemory();
	}
	
	public static long getMaxMemory(){
		return Runtime.getRuntime().maxMemory();
	}
	
	public static long getTotalMemory(){
		return Runtime.getRuntime().totalMemory();
	}
	
	public static long getMaxTuplesCountT1(){
		return (long)Math.floor((double)getAvailableMemory()/(double)maxTupleBytesT1);
	}
	
	public static long getMaxTuplesCountT2(){
		return (long)Math.floor((double)getAvailableMemory()/(double)maxTupleBytesT2);
	}
	
	public static long getMaxBlocksCountT1(){
		return (long)Math.ceil((double)getAvailableMemory()/(double)(tuplesPerBlock * maxTupleBytesT1));
	}
	
	public static long getMaxBlocksCountT2(){
		return (long)Math.ceil((double)getAvailableMemory()/(double)(tuplesPerBlock * maxTupleBytesT2));
	}
}
