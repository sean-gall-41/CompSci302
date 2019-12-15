/**
 *This serves as the test bench for new functions and methods defined in milestone 2
 *of project 1, especially for methods and functions that were tricky to code e.g.
 *the numLegal function and legalMoves method
 * 
 * Bugs: (as of now all tests return true) 
 * 
 * @author (Sean Gallogly) */
	public class Project1TestClass {
	
//All declared test dates except testDate5 are valid moves in the New Years game. TestDate5
//is an invalid date used to catch whether methods will catch invalid dates (except for
//those methods not called until human player enters a valid date
	public static void main(String[] args) {
		Date testDate1 = new Date("Jan" , 1); 
		Date testDate2 = new Date("Jan" , 30);
		Date testDate3 = new Date("Jan" , 31);
		Date testDate4 = new Date("Dec" , 30);
		Date testDate5 = new Date("Invalid" , 0);
		Date testDate6 = new Date("Feb" , 1);
		
		System.out.println("Testing started for dayNum function");
		System.out.println(testDate1.getName() + ":" + testDate1.getNum() + ", expecting 0, got " + Date.dayNum(testDate1));
		System.out.println(testDate2.getName() + ":" + testDate2.getNum() + ", expecting 29, got " + Date.dayNum(testDate2));
		System.out.println(testDate3.getName() + ":" + testDate3.getNum() + ", expecting 30, got " + Date.dayNum(testDate3));
		System.out.println(testDate4.getName() + ":" + testDate4.getNum() + ", expecting 363, got " + Date.dayNum(testDate4));
		System.out.println("Unit-testing complete.");
		
		System.out.println("\nTesting started for numLegal function");
		System.out.println(testDate1.getName() + ":" + testDate1.getNum() + ", expecting 41, got " + Date.numLegal(testDate1));
		System.out.println(testDate2.getName() + ":" + testDate2.getNum() + ", expecting 11, got " + Date.numLegal(testDate2));
		System.out.println(testDate3.getName() + ":" + testDate3.getNum() + ", expecting 6, got " + Date.numLegal(testDate3));
		System.out.println(testDate4.getName() + ":" + testDate4.getNum() + ", expecting 1, got " + Date.numLegal(testDate4));
		System.out.println("Unit-Testing complete.");
		
		System.out.println("\nTesting started for monthNumInYear method");
		System.out.println(testDate1.getName() + ":" + testDate1.getNum() + ", expecting 0, got " + Date.monthNumInYear(testDate1.getName()));
		System.out.println(testDate4.getName() + ":" + testDate4.getNum() + ", expecting 11, got " + Date.monthNumInYear(testDate4.getName()));
		System.out.println(testDate5.getName() + ":" + testDate5.getNum() + ", expecting 12, got " + Date.monthNumInYear(testDate5.getName()));
		System.out.println("Unit-Testing complete.");
		
		//NOTE: not testing with testDate5 as this method will only be called *after* a valid month name is entered
		System.out.println("\nTesting started for monthMax method");
		System.out.println(testDate1.getName() + ":" + testDate1.getNum() + ", expecting 31, got " + Date.monthMax(testDate1.getName()));
		System.out.println(testDate6.getName() + ":" + testDate6.getNum() + ", expecting 28, got " + Date.monthMax(testDate6.getName()));
		System.out.println(testDate4.getName() + ":" + testDate4.getNum() + ", expecting 31, got " + Date.monthMax(testDate4.getName()));
		System.out.println("Unit-Testing complete.");
		
		System.out.println("\nTesting started for legalMoves method");
		System.out.println(testDate4.getName() + ":" + testDate4.getNum() + ", expecting Dec 31, got " + testDate4.legalMoves()[0]);
		System.out.println(testDate1.getName() + ":" + testDate1.getNum() + ", expecting Jan 2, got " + testDate1.legalMoves()[0]);
		System.out.println(testDate1.getName() + ":" + testDate1.getNum() + ", expecting Dec 1, got " + testDate1.legalMoves()[40]);
	}
}
