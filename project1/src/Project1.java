import java.util.Random;
import java.util.Scanner;
////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Title:            (Project1 Milestone 2)
//Files:            (Project1.java , Player.java , Date.java , Project1TestClass.java)
//Semester:         (course) Summer 2017
//
//Author:           (Sean Gallogly)
//Email:            (sgallogly@wisc.edu)
//CS Login:         (sean_gallogly)
//Lecturer's Name:  (Steve Earth)
//Lab Section:      (na)
//
////////////////////PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
//Partner Name:     (na)
//Partner Email:    (na)
//Partner CS Login: (na)
//Lecturer's Name:  (na)
//Lab Section:      (na)
//
//VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//_X_ Write-up states that Pair Programming is allowed for this assignment.
//_na_ We have both read the CS302 Pair Programming policy.
//_na_ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
//Students who get help from sources other than their partner must fully 
//acknowledge and credit those sources of help here.  Instructors and TAs do 
//not need to be credited here, but tutors, friends, relatives, room mates 
//strangers, etc do.
//
//Persons:          (na)
//Online Sources:   (https://stackoverflow.com/questions/12869741/
//					returning-arrays-in-java: used to understand how to return
//					an array in a class' member methods)
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

public class Project1 {
	final static int SEED 		 = 123; //seed for the dice
	public static Random rangen = new Random(SEED);
	public static Scanner scnr = new Scanner(System.in); //the kyboard scanner that scans human input
	public static char charPrompt(String question, String possibles){
		String entry;																						
		char answer;
		possibles = possibles.toUpperCase();
		do{ // this loops to make sure they started with the right letter
			do{ //this loops to make sure they entered something
				System.out.print(question);
				entry = scnr.nextLine(); 
				} while (entry.length()==0);
			answer = entry.toUpperCase().charAt(0); // grabs uppercasified first letter
		} while(possibles.indexOf(answer)==-1); //loops back to prompt if not in possibles
		return answer;
	}
	
	public static String coinFlip(char inEntry, Random inGen){
		String entry, flip;
		int rand = inGen.nextInt(2);
		if(inEntry == 'H'){
			entry = "Heads";
			flip = "Tails";
		} else{
			entry = "Tails";
			flip = "Heads";
		}
		return rand+entry+flip;
	}

	public static void main(String[] args) {
		Date[] testing;
		final int TRIALS     = 10000; //The number of games played by two AIs playing each other
		char charAtZero      = '?'; //a character used to delineate the character at index zero of a given string 
		int max , myNum = 0 , turnIndex = 1 , gameIndex = 1; //Max gets the maximum number of days in a month ; myNum represents the number chosen by a human player 
		boolean validMove , actualCal , done; //actualCal determines whether a date is a real date in the calendar ; done determines whether a game is finished or not
		boolean learnPhase = true , printThis , newPlayers;
		String name1 , name2 , entry = "" , answer = "" , coinResult = "" , myMonth = ""; //answer is used for determining if another game is to be played ; myMonth represents the month name input by a human player 
		Scanner strScan; //scans the human player's input and is used to parse their input
		Player player1 , player2 , currPlayer , nextPlayer , temp; //temp is used to exchange currPlayer with nextPlayer and vice versa for the next turn
		Date currMove , prevMove , endGame = new Date("Dec" , 31);
		do {
		do {
			System.out.print("Enter name for one of the players: ");
			name1 = scnr.nextLine();
		} while (name1.isEmpty());
		player1 = new Player(name1);
		do {
			System.out.print("Enter a different name for the opponent: ");
			name2 = scnr.nextLine();
		} while ((name1.equals(name2)) || ((name2.length()==0)));
		player2 = new Player(name2);
		done =false;
		newPlayers = false;
		while (!done && !newPlayers) {
			if (player1.getIsHuman() || player2.getIsHuman()) {
				printThis = true;
				learnPhase = false;
			}
			else {
				printThis = false;
			}
			if (learnPhase && (gameIndex == 1)) {
				System.out.println("Beginning AI learning phase for " + TRIALS + " games");
			}
			if (player1.getIsHuman()) {
			charAtZero = charPrompt("call Heads or Tails to determine starting player: " , "ht");
			}
			else {
				if (printThis) {
				System.out.print("call Heads or Tails to determine starting player: ");
				}
				int rand = rangen.nextInt(2);
				String AIEntry = "";
				if (rand == 0) {
					AIEntry = "Heads";
					charAtZero = AIEntry.charAt(0);
				}
				else {
					AIEntry = "Tails";
					charAtZero = AIEntry.charAt(0);
				}
				if (printThis) {
				System.out.println(AIEntry);
				}
			}
			prevMove = new Date("Jan",0); //Simply for initialization purposes
			turnIndex = 1;
			coinResult = coinFlip(charAtZero , rangen);
			if(coinResult.charAt(0)=='0'){
				if (printThis) {
					System.out.println("It came up "+coinResult.substring(1, 6)+
						"! " + player1.getName() + " won the coin toss");
				}
				currPlayer = player1;
				nextPlayer = player2;
			} else{
				if (printThis) {
					System.out.println("It came up "+coinResult.substring(6, 11)+
						"! " + player1.getName() + " lost the coin toss");
				}
				currPlayer = player2;
				nextPlayer = player1;
			}
			if (printThis) {
				System.out.println("\nThis is game number "+ gameIndex);
			}
			currMove=new Date("Jan",0);
			validMove = false;
			while(!((currMove.equals(endGame)&&validMove) || (currPlayer.getStrikes()==3))) {
				validMove=false;
				actualCal=false;
				while(!actualCal){
						actualCal = true;
						if(turnIndex!=1){
							if (printThis) {
								System.out.println(nextPlayer.getName()+" chose "+prevMove+" last turn");
							}
						}
						if (printThis) {
							System.out.print("Game#"+gameIndex+" Turn#"+turnIndex+": "+currPlayer.getName()+
								"("+currPlayer.getStrikes()+
								" strikes), enter a month followed by a space then a date: ");
						}
						if (currPlayer.getIsHuman()) {
						entry = scnr.nextLine();
							if (entry.length()==0){
								System.out.println("No entry detected");
								actualCal = false;
							}
							else{
								strScan = new Scanner(entry);
								myMonth = strScan.next();
								if (myMonth.length()>=3){
									myMonth = myMonth.substring(0, 1).toUpperCase()+
											myMonth.substring(1, 3).toLowerCase();
								}
								if(myMonth.length()<3){
									System.out.println("Entry must be at least 3 letters to determine month.");
									actualCal = false;
								} 
								else { // block below means first word had 3 letters properly cased
									if(Date.monthNumInYear(myMonth)==12){
										System.out.println("the first 3 letters \""+myMonth+"\" don't match a known month");
										actualCal = false;
									}
								}
								if(!strScan.hasNext()){
									System.out.println(currPlayer.getName() + " did not make a second entry for the date");
									actualCal = false;
								} 
								else if(!strScan.hasNextInt()){
									System.out.println("The second entry, the date, must be a number");
									entry = strScan.next(); // a dummy var, to check if there's a 3rd entry
									actualCal = false;
								}
								else { 
									myNum = strScan.nextInt();
								if (actualCal){ // block below means 2nd entry was a number AND month was legit
									max = Date.monthMax(myMonth);				
									if (myNum > max || myNum<1){
										System.out.println(currPlayer.getName() + "'s entry date of "+myNum+" is not between 1 and "+max+
												", the number of days in "+myMonth);
										actualCal = false;
									}
								}
								if(strScan.hasNext()){
									System.out.println("Only two entries needed. Ignoring additional input");
								}
								strScan.close();
							}
						}
						currMove = new Date(myMonth, myNum); //Assuming previous user input is fully correct (not including whether is valid move or not)
						validMove = true;
						if(turnIndex == 1){
							if(myMonth.equals("Jan") && myNum==1){
								validMove=true;
							} else{
								System.out.println("First player must start with Jan 1");
								currPlayer.incStrikes();
								validMove = false; 
							}
						}
						else{
							if(currMove.after(prevMove)){
								if(currMove.getName().equals(prevMove.getName())){
									validMove=true;
								} else{
									if(currMove.getNum()==prevMove.getNum()){
										validMove=true;
									} else{
										System.out.println("If choosing a month other than "+prevMove.getName()+
												", then it must have a date of "+prevMove.getNum()+
												", the same date as "+nextPlayer.getName()+" chose last turn");
										validMove=false;
										currPlayer.incStrikes();						
									}
								}
							}
							else{
								validMove = false;
								System.out.println(currPlayer.getName() + " must choose a date later than "+prevMove);
								currPlayer.incStrikes();
							}
						}
					}
					else {
						if (turnIndex == 1) {
							currMove = new Date("Jan" , 1);
							if (printThis) {
								System.out.println(currMove.toString());
							}
							validMove = true;
						}
						else {
							if (learnPhase) {
								currMove = StratTable.likelyMove(prevMove.legalMoves());
							}
							else if (currPlayer.getName().equals("AI Random")) {
								testing = prevMove.legalMoves();
								if(testing.length!=0){
								currMove = testing[rangen.nextInt(testing.length)];
								}
							}
							else {
								currMove = StratTable.bestMove(prevMove.legalMoves());
							}
							if (printThis) {
								System.out.println(currMove.toString());
							}
							validMove = true;
						}
						
					}
				}	
				if(validMove){ //finally, the move the user input is correct (will ALWAYS reach this point with AI) 
					currPlayer.recordMove(currMove);
					temp = currPlayer;
					currPlayer = nextPlayer;
					nextPlayer = temp;
					prevMove = currMove;
					turnIndex++;
				}
			}
			if (printThis) {
				System.out.print("Game#"+gameIndex+" is over: ");
			}
			if(currPlayer.getStrikes()==3){
				if (printThis) {
					System.out.println(currPlayer.getName()+" lost on turn "+turnIndex+
						" because of 3 strikes");
				}
			} 
			else {
				if (printThis) {
					System.out.println(nextPlayer.getName()+" won on turn "+(turnIndex-1)+
						" by reaching Dec 31!");
				}
			}
			StratTable.update(currPlayer.getmoves(), currPlayer.getnumMoves(), 0);
			StratTable.update(nextPlayer.getmoves(), nextPlayer.getnumMoves(), 1);
			nextPlayer.won();
			if ((gameIndex % 1000) == 0) {
				System.out.println(player1.getName() + " and " + player2.getName() + " have played each other for "
						+ gameIndex + " games");
			}
			if (gameIndex == TRIALS) {
				printThis = true;
			}
			if (printThis) {
				System.out.println("The running total of games won so far is: \n"+
						player1.getName()+"="+player1.getWins());
				System.out.println(player2.getName()+"="+player2.getWins());
				if (learnPhase) {
					System.out.println("learning phase complete\n");
					learnPhase = false;
				}
				else {
					if (player1.getIsHuman() || player2.getIsHuman()) {
					System.out.print("\nDoes " + player1.getName() + " wish to play again (Y/N)? ");
					}
				}
			}
			if (player1.getIsHuman()) {
				answer = scnr.nextLine();
				while (answer.length()==0 || !(answer.substring(0,1).toLowerCase().equals("n") || answer.substring(0,1).toLowerCase().equals("y"))) {
					System.out.println("Invalid input. Try again.");
					System.out.print("Does " + player1.getName() + " wish to play again (Y/N)? ");
					answer = scnr.nextLine();
				}
			}
			else {
				if (gameIndex < TRIALS) {
					answer = "Yes";
				}
				else {
					answer = "No";
				}
				if (player1.getIsHuman() || player2.getIsHuman()) {
					System.out.println(answer);
				}
			}
			if (answer.substring(0,1).toLowerCase().equals("n")){				
				done = true;
				gameIndex = 1;
				player1.reset();
				player2.reset();
				newPlayers = true;
			} 
			else{
				done=false;
				gameIndex++;
				player1.reset();
				player2.reset();
				newPlayers = false;
			}
		}
	} while (newPlayers || !learnPhase);	
	scnr.close();
	}
}
