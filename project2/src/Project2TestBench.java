import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
public class Project2TestBench {
	
	public enum Color {EMPTY ,RED, ORANGE, YELLOW, GREEN, BLUE, VIOLET}
	public static final int SEED = 321;
	public static final double TOLERANCE = 0.000001;
	public static Random rangen = new Random(SEED);
	public static Scanner entryScnr;
	public static Scanner scnr = new Scanner(System.in);
	private  static final int TRIALS  = 100000;	
	
	public static Code twoDubs() {
		Project2.Color[] secret = new Project2.Color[4];
		
		int pair1 = Project2.rangen.nextInt(4);
		int pair2;
		do {
			pair2 = Project2.rangen.nextInt(4);
		} while (pair1 == pair2);
		Project2.Color col1 = Project2.Color.values()[Project2.rangen.nextInt(7)];
		Project2.Color col2;
		do {
			col2 = Project2.Color.values()[Project2.rangen.nextInt(7)];
		} while (col1 == col2);
		for (int i = 0 ; i < 4 ; i++) {
			if ((i == pair1) || (i == pair2)) {
				secret[i] = col1;
			}
			else {
				secret[i] = col2;
			}
		}
		return new Code(secret);
	}
	
	
//	public static Code twoDubs() {
//		Code twoDubs = new Code ();
//		Project2.Color pair1;
//		Project2.Color pair2;
//		Integer testNumber;
//		ArrayList<Integer> testList = new ArrayList<Integer>();
//		pair1 = Project2.Color.values()[Project2.rangen.nextInt(Color.values().length)];
//		twoDubs.setSingleColor(0, pair1);
//		do {
//			pair2 = Project2.Color.values()[Project2.rangen.nextInt(Color.values().length)];
//		} while (pair1==pair2);
//		for(int i=1;i<4;i++) {
//			testList.add(i);
//		}
//		while (testList.size()>0) {
//			testNumber = testList.get(Project2.rangen.nextInt(testList.size()));
//			if (testList.size()==3) {
//				twoDubs.setSingleColor((int)testNumber, pair1);
//			}
//			else {
//				twoDubs.setSingleColor((int)testNumber, pair2);
//			}
//		testList.remove(testList.indexOf(testNumber));
//		}
//	return twoDubs;
//	}

	public static void main(String[] args) {
		//Code testCode = new Code();
		//System.out.println(Project2.allOpts());
		System.out.println(Project2.allDiff().toString());
	}
}
