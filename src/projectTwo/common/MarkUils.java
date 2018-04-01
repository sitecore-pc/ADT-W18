package projectOne.common;

public class MarkUils {
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
        switch (letter.toUpperCase())
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
}
