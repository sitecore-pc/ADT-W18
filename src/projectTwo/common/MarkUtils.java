package projectTwo.common;

public class MarkUtils {
	public static String ToLetterMark(double mark)
    {
        if (mark >= 4.3) return "A+";
        if (mark >= 4) return "A";
        if (mark >= 3.7) return "A-";
        if (mark >= 3.3) return "B+";
        if (mark >= 3.0) return "B";
        if (mark >= 2.7) return "B-";
        if (mark >= 2) return "C";
        if (mark < 2) return "F";

        return "Unknown Mark";
    }
	
	public static double ToFloatMark(String letter)
    {
        switch (letter.trim().toUpperCase())
        {
            case "A+":
                return 4.3;
            case "A":
                return 4.0;
            case "A-":
                return 3.7;
            case "B+":
                return 3.3;
            case "B":
                return 3.0;
            case "B-":
                return 2.7;
            case "C":
                return 2;
            default:
                return 0;
        }
    }
	
	public static double ExtractGradeFromTuple(String tuple) {
		if(tuple == null || tuple.isEmpty() || tuple.length() < 27) 
			return 0;
		
		String grade = tuple.substring(23,27);
		grade = grade.trim();
		return ToFloatMark(grade);
	}
	
	public static int ExtractCreditsFromTuple(String tuple) {
		if(tuple == null || tuple.isEmpty() || tuple.length() < 27) 
			return 0;
		
		String credit = tuple.substring(21,23);
		credit = credit.trim();
		return Integer.parseInt(credit);
	}
	
}
