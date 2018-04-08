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
        if (mark >= 2.3) return "C+";
        if (mark >= 2) return "C";
        if (mark >= 1.7) return "C-";
        if (mark >= 1.3) return "D+";
        if (mark >= 1) return "D";
        if (mark >= 0.7) return "D-";
        return "Fail";
    }
	
	public static double ToFloatMark(String letter)
    {
		// reference document provided by professor for marking scheme (DOES include C-, C+, D-, D and D+)
		// https://moodle.concordia.ca/moodle/pluginfile.php/3128958/mod_resource/content/1/GPA%20calculation.pdf
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
            case "C+":
            	return 2.3;
            case "C":
                return 2;
            case "C-":
            	return 1.7;
            case "D+":
            	return 1.3;
            case "D":
            	return 1.0;
            case "D-":
            	return 0.7;
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
