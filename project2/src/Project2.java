//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:            (Project2)
// Files:            (Project2.java , Code.java)
// Semester:         (course) Summer 2017
//
// Author:           (Sean)
// Email:            (sgallogly@wisc.edu)
// CS Login:         (sgallogly)
// Lecturer's Name:  (Steve Earth)
// Lab Section:      (Summer 2017)
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:     (Wei Lin Tsai)
// Partner Email:    (wtsai9@wisc.edu)
// Partner CS Login: (wtsai9)
// Lecturer's Name:  (Steve Earth)
// Lab Section:      (Summer 2017)
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//    _x_ Write-up states that Pair Programming is allowed for this assignment.
//    _x_ We have both read the CS302 Pair Programming policy.
//    _x_ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.
//
// Persons:          (identify each person and describe their help in detail)
// Online Sources:   (identify each URL and describe its assistance in detail)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Project2 {

	public enum Color {EMPTY ,RED, ORANGE, YELLOW, GREEN, BLUE, VIOLET}
	public static final int SEED = 321;
	public static final double TOLERANCE = 0.000001;
	public static Random rangen = new Random(SEED);
	public static Scanner entryScnr;
	public static Scanner scnr = new Scanner(System.in);
	private  static final int TRIALS  = 100000;	
	
	public static Code randColors() {//which returns a Code at random (possibly with duplicates, and possibly with empties)
		Code ranCode = new Code();
		for(int i=0;i<ranCode.getSlots().length;i++) {
			ranCode.setSingleColor(i, Color.values()[rangen.nextInt(Color.values().length)]);
		}
	return ranCode;
	}

	public static Code allDiff() {//which returns a Code with no repeats and in a randomized order.
		Code allDiff = new Code();
		for(int i=0;i<allDiff.getSlots().length;i++) {
			allDiff.setSingleColor(i, Color.values()[rangen.nextInt(Color.values().length)]);
			if (i>0) {
				while (allDiff.getColorInSlots(i)==allDiff.getColorInSlots(i-1)){
					allDiff.setSingleColor(i, Color.values()[rangen.nextInt(Color.values().length)]);
				}
			}
		}
	return allDiff;
	}
	
	public static Code allSame() {// which returns a Code in which all four slots are the same Color.
		Code allSame = new Code();
		for(int i=0;i<allSame.getSlots().length;i++) {
			allSame.setSingleColor(i, Color.values()[rangen.nextInt(Color.values().length)]);
			if (i>0) {
				while (allSame.getColorInSlots(i)!=allSame.getColorInSlots(i-1)){
					allSame.setSingleColor(i, Color.values()[rangen.nextInt(Color.values().length)]);
				}
			}
		}
	return allSame;
	}
	
	
	public static Code twoDubs() {//which returns a Code in which there are two pairs of Colors, in a randomized order
		Code twoDubs = new Code ();
		Project2.Color pair1;
		Project2.Color pair2;
		Integer testNumber;
		ArrayList<Integer> testList = new ArrayList<Integer>();
		pair1 = Project2.Color.values()[Project2.rangen.nextInt(Color.values().length)];
		twoDubs.setSingleColor(0, pair1);
		do {
			pair2 = Project2.Color.values()[Project2.rangen.nextInt(Color.values().length)];
		} while (pair1==pair2);
		for(int i=1;i<4;i++) {
			testList.add(i);
		}
		while (testList.size()>0) {
			testNumber = testList.get(Project2.rangen.nextInt(testList.size()));
			if (testList.size()==3) {
				twoDubs.setSingleColor((int)testNumber, pair1);
			}
			else {
				twoDubs.setSingleColor((int)testNumber, pair2);
			}
		testList.remove(testList.indexOf(testNumber));
		}
	return twoDubs;
	}
	
	public static ArrayList<Code> allOpts(){ //ArrayList of Code which contains every possible Code
		ArrayList<Code> allOpts = new ArrayList<Code>();
		Color[] tempColor = new Color[4];
		
			for(int i=0;i<7;i++){
				for(int j=0;j<7;j++){
					for(int k=0;k<7;k++){
						for(int l=0;l<7;l++){
							tempColor[0] = Color.values()[i];
							tempColor[1] = Color.values()[j];
							tempColor[2] = Color.values()[k];
							tempColor[3] = Color.values()[l];
							allOpts.add(new Code(tempColor));
						}
					}
				}
			}
		
	return allOpts;
	}

	public static void main(String[] args){
		String answer = "", codeMaker="", codeBreaker="";
		int turnNum = 1, totalTurn = 0, gameNum=1, pegNum = 0;
		boolean done = false, learnPhase = false, makerIsHuman=true, breakerIsHuman=true,printThis=true,invalidPegs=false;
		Code aiGuess, solution, humanGuess = new Code();
		ArrayList<Code> tempMove = new ArrayList<Code>();
		
		while(learnPhase||!done){
			tempMove = allOpts(); //get all possible move ArrayList
			if(!learnPhase){
				answer ="";
				while(codeMaker.length()==0){
					if(printThis){
						System.out.print("Enter name for codeMaker: ");
					}
					codeMaker = scnr.nextLine();
					if(codeMaker.length()<2){ //avoids substring error
						makerIsHuman = true;
					}
					else{
						makerIsHuman = !codeMaker.substring(0,2).equals("AI");						
					}
				}
				while(codeBreaker.length()==0 || codeBreaker.equals(codeMaker)){
					if(printThis){
						System.out.print("Enter a different name for codeBreaker: ");
					}
					codeBreaker = scnr.nextLine();
					if(codeBreaker.length()<2){ //avoids substring error
						breakerIsHuman = true;
					}
					else{
						breakerIsHuman = !codeBreaker.substring(0,2).equals("AI");
					}
				}
			}else{
				answer ="";
			}
			if(!breakerIsHuman&&!makerIsHuman&&gameNum!=TRIALS){ // if both player are AI, most printing will be suppressed
				printThis = false;
			}
			else {
				printThis = true;
			}
			
			solution= new Code();
			aiGuess = new Code();
			
			
			if(gameNum==TRIALS){ //if both player are AI learningPhase equal true until gameNum equal TRIALS.
				learnPhase=false;
				printThis = true;	
			}
			System.out.println("\nPlaying game #" + gameNum);
			while((turnNum<11)&&(pegNum!=40)&&(!invalidPegs)){
				if(turnNum==1){
					solution = randColors();
					aiGuess = allDiff(); //turn length was 5.03996, so choose this one.
//					aiGuess = allSame(); //turn length was 5.57973
//					aiGuess = twoDubs(); //turn length was 5.27816
					
					if(printThis){
						if(!makerIsHuman){
							System.out.println(codeMaker+" has made a code for "+codeBreaker+" to break.");	
							
						}else{
							System.out.println(codeMaker+ " should mentally make a code for "+ codeBreaker+ " to break.");					
						}
					}
					
				}
				
				if(printThis){	
					System.out.println("\nThis is game#"+gameNum+", turn #" + turnNum);
					}
				if (breakerIsHuman&&makerIsHuman) { //both player is human
					printThis = true;
					humanGuess = Provided.inputColors();
					pegNum = Provided.inputPegs();
					System.out.println(codeBreaker+" guessed "+humanGuess+", and "+codeMaker+" declares: Black = "+pegNum/10+", White = "+pegNum%10);
				}
				
				else if(breakerIsHuman&&!makerIsHuman){ //breaker is human and maker is AI
				printThis = true;
				humanGuess = Provided.inputColors();
				pegNum=humanGuess.pegs(solution);
				System.out.println(codeBreaker+" guessed "+humanGuess+", and "+codeMaker+" declares: Black = "+pegNum/10+", White = "+pegNum%10);							
				}
				else if(!breakerIsHuman&&makerIsHuman){ //breaker is AI and maker is human
					printThis = true;
					if(aiGuess !=null){  // if maker make inconsistency pegNum
						System.out.println(codeBreaker+" entered "+aiGuess);
						pegNum = Provided.inputPegs();
						System.out.println(codeBreaker+" guessed "+aiGuess+", and "+codeMaker+" declares: Black = "+pegNum/10+", White = "+pegNum%10);	
						aiGuess = aiGuess.pickMove(tempMove, pegNum);
					}
					else {
						System.out.println(codeBreaker+" cannot make guess. Inconsistent peg input. Terminating game");
						invalidPegs = true;
					}
				}
				else if(!breakerIsHuman&&!makerIsHuman){ //both player are AI
					if(gameNum==TRIALS){					
						learnPhase=false;
						printThis = true;	
					}else{
						learnPhase=true;
						printThis = false;	
					}													
					if(printThis){
					System.out.println(codeBreaker+" entered "+aiGuess);
					}
					pegNum=aiGuess.pegs(solution);
					if(printThis){
					System.out.println(codeBreaker+" guessed "+aiGuess+", and "+codeMaker+" declares: Black = "+pegNum/10+", White = "+pegNum%10);							
					}
					aiGuess = aiGuess.pickMove(tempMove, pegNum);
				}
				

				if(pegNum==40){   //if pegNum = 40, breaker broke code
					if(printThis){
					System.out.println(codeBreaker+" broke the code of "+codeMaker+" on turn #"+ turnNum);		
					}
				}else if(turnNum==10){
					if(printThis){
						System.out.println(codeBreaker+" was unable to break the code of "+codeMaker+" within the ten turn limit");
					}
					if((breakerIsHuman&&!makerIsHuman)||(!breakerIsHuman&&!makerIsHuman)){
						if(printThis){	
						System.out.println("The solution was " + solution);	
						}	
					}
				}
			turnNum++;
			}
			
			turnNum--;
			while(answer.length()==0){	
				if(learnPhase){
					answer = "Yes";
				}else{
					System.out.print("Do you wish to play again (Y/N)? ");			
					answer = scnr.nextLine();
				}
				if (answer.length()>0 && answer.substring(0,1).toLowerCase().equals("y")){
					done=false;
					invalidPegs = false;
					gameNum++;
					totalTurn=totalTurn+turnNum; // increment the totalturn count every game to determine average moves per game
					turnNum=1;
					pegNum = 0;
				}else if(answer.length()>0){
					totalTurn=totalTurn+turnNum; //Calculate average game turn length
					done = true;
					System.out.print("\nThe average game's turn length was " + (double)totalTurn/gameNum);
				
				}				
			}
		
		}// END of tournament loop
	}
}