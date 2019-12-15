import java.util.Random;
import java.util.Scanner;

public class Project1Test {
	public static char charPrompt(String question, String possibles){
		Scanner kybScnr = new Scanner(System.in);	
		String entry;																						
		char answer;
		possibles = possibles.toUpperCase();
		do{ // this loops to make sure they started with the right letter
			do{ //this loops to make sure they entered something
				System.out.print(question);
				entry = kybScnr.nextLine();
				} while (entry.length()==0);
			answer = entry.toUpperCase().charAt(0); // grabs uppercasified first letter
		} while(possibles.indexOf(answer)==-1); //loops back to prompt if not in possibles
		//kybScnr.close();
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
		final int SEED 		 = 123; //seed for the dice
		char charAtZero      = '?'; //a character used to delineate the character at index zero of a given string 
		int max , myNum = 0 , turnIndex = 1 , gameIndex = 1;
		boolean validMove , actualCal , done;
		String name1 , name2 , entry = "" , answer = "" , coinResult = "" , myMonth = "";
		Random coin     		 = new Random(SEED); //declare a new number generator for deciding which player goes first
		Scanner scnr = new Scanner(System.in);
		Scanner strScan;
		Player player1 , player2 , currPlayer , nextPlayer , temp; 
		Date currMove , prevMove , endGame = new Date("Dec" , 31);
		
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
		while (!done) {
			prevMove = new Date("Jan",0); //Simply for initialization purposes
			turnIndex = 1;
			charAtZero = charPrompt("call Heads or Tails to determine starting player: " , "ht");
			coinResult = coinFlip(charAtZero , coin);
			if(coinResult.charAt(0)=='0'){
				System.out.println("It came up "+coinResult.substring(1, 6)+
						"! You won the coin toss");
				currPlayer = player1;
				nextPlayer = player2;
			} else{
				System.out.println("It came up "+coinResult.substring(6, 11)+
						"! You lost the coin toss");
				currPlayer = player2;
				nextPlayer = player1;
			}
			System.out.println("\nThis is game number "+ gameIndex);
			currMove=new Date("Jan",0);
			validMove = false;
			while(!((currMove.equals(endGame)&&validMove) || (currPlayer.getStrikes()==3))) {
				validMove=false;
				actualCal=false;
				while(!actualCal){
					actualCal = true;
					if(turnIndex!=1){
						System.out.println(nextPlayer.getName()+" chose "+prevMove+" last turn");
					}
					System.out.print("Game#"+gameIndex+" Turn#"+turnIndex+": "+currPlayer.getName()+
							"("+currPlayer.getStrikes()+
							" strikes), enter a month followed by a space then a date: ");
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
							System.out.println("You did not make a second entry for the date");
							actualCal = false;
						} 
						else if(!strScan.hasNextInt()){
							System.out.println("The second entry, the date, must be a number");
							entry = strScan.next(); // a dummy var, to check if there's a 3rd entry
							actualCal = false;
						}
						else 
							myNum = strScan.nextInt();
						if (actualCal){ // block below means 2nd entry was a number AND month was legit
							max = Date.monthMax(myMonth);				
							if (myNum > max || myNum<1){
								System.out.println("Your entry date of "+myNum+" is not between 1 and "+max+
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
				currMove = new Date(myMonth, myNum);
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
						System.out.println("You must choose a date later than "+prevMove);
						currPlayer.incStrikes();
					}
				}
				if(validMove){ 
					temp = currPlayer;
					currPlayer = nextPlayer;
					nextPlayer = temp;
					prevMove = currMove;
					turnIndex++;
				}
			}
			System.out.print("Game#"+gameIndex+" is over: ");
			if(currPlayer.getStrikes()==3){
				System.out.println(currPlayer.getName()+" lost on turn "+turnIndex+
						" because of 3 strikes");
			} else {
				System.out.println(nextPlayer.getName()+" won on turn "+(turnIndex-1)+
						" by reaching Dec 31!");
			}
			nextPlayer.won();
			System.out.println("The running total of games won so far is: \n"+
					player1.getName()+"="+player1.getWins());
			System.out.println(player2.getName()+"="+player2.getWins());
			System.out.print("\nDo you wish to play again (Y/N)? ");
			answer = scnr.nextLine();
			if ((answer.length()==0) || !(answer.substring(0,1).toLowerCase().equals("n"))){
				done=false;
				gameIndex++;
				player1.reset();
				player2.reset();
	
			} 
			else{
				done = true;
			}
		}
	scnr.close();
	}
}
