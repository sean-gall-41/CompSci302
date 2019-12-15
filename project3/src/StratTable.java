//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:            (Project3)
// Files:           (Project3.java , Player,java , StratTable.java)
// Semester:         (course) Summer 2017
//
// Author:           (Sean Gallogly)
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
// Lab Section:      (summer 2017)
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

/**
 *This class provides the methods for the AI when the AI is the guesser or when
 *it is the chooser. It contains one two fields, both arrays, which are used
 *in the implementation of the computer guess method. The computer guess
 *works as directed and chooses a letter based off of the frequencies of 
 *occurence of the letter in the list of possible words that are consistent
 *with previous guesses/letter position assignments. <p>The code generator generates a 
 *"code" in reverse binary order that helps the computer choose a potential word.
 *The Secret solution chooser uses the culled arraylist of words that are still
 *consistent with previous input, and uses the current humanInput guess as well as the
 *codeGenerator to decide which word it should choose as its next secret solution. 
 * 
 * Bugs: (The computerGuess method alters the alGuessed arraylist so that all the entries
 * 		  are lowercase, which we do NOT want to be displayed when the computer states:
 * 		  "Letters guessed so far: ") 
 * 
 * @author (Sean Gallogly) */

import java.util.ArrayList;

public class StratTable {
	
	private final static char[] ENG_LETT = {'a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g' , 'h' , 'i' , 'j' , 
									'k' , 'l' , 'm' , 'n' , 'o' , 'p' , 'q' , 'r' , 's' , 't' ,
									'u' , 'v' , 'w' , 'x' , 'y' , 'z'};
	
	private static double[] lettStrat = new double[26]; 
	
	/**
	 *computerGuess takes a culled arraylist of consistent words up until this point as 
	 *well as an arraylist of all letters guessed up until this point and decides using
	 *these two items what its next guess should be
	 *
	 * @param (possibleWords) (This arraylist contains all words from word.txt which are
	 * consistent with the guesses and userinput so far)
	 * @param (alGuessed) (this arraylist contains all of the letters guessed up until this method is called)
	 * @return (returns the character that serves as the computer guess for this turn)
	 */
	public static char computerGuess(ArrayList<String> possibleWords , ArrayList<Character> alGuessed) {
		int i = 0;
		double sum1 = 0;
		double sum2 = 0;
		double probOfGuess;
		for (int l = 0 ; l < alGuessed.size() ; l++) {
			alGuessed.set(l, Character.toLowerCase(alGuessed.get(l)));
		}
		for (char letter = 'a' ; letter <= 'z' ; letter++) {
			if (alGuessed.contains(letter)) {
				i++;
				continue;
			}
			else {
				for (int j = 0 ; j < possibleWords.size() ; j++) {
					if (possibleWords.get(j).indexOf(letter, 0) != -1) {
						lettStrat[i] += 1.0; 
					}
				}
				i++;
			}
		}
		for (i = 0 ; i < lettStrat.length ; i++) {
			lettStrat[i] = lettStrat[i] / (double)possibleWords.size();
			sum1 += lettStrat[i];
		}
		for (i = 0 ; i < lettStrat.length ; i++) {
			lettStrat[i] = lettStrat[i] / sum1;
			sum2 += lettStrat[i];
			lettStrat[i] = sum2;
		}
		int k = 0;
		probOfGuess = Project3.rangen.nextDouble();
		
		while (probOfGuess > lettStrat[k]) {
			k++;
		}
		//NOTE:As a precaution: every time this method is called, the values in lettStrat will be zero, as we wish
		for (i = 0 ; i < lettStrat.length ; i++) {
			lettStrat[i] = 0;
		}
		return ENG_LETT[k];
	}
	
	/**
	 *This method generates the computer secret solution given the culled list of consistent words in word.txt and the 
	 *human's letter guess. The method determines a reverse binary code for each letter in the culled list, and then 
	 *proceeds to check the configuration of the user's guess and tallies the number of words of each configuration
	 *(including if the word does NOT contain the user's guess). Then, the computer finds which has the largest number of occurences,
	 *and if there are any tiebreakers of words in a particular configuration these are broken alphabetically
	 *
	 * @param (possibleWords) (The culled list of consistent words with previous guesses and chosen words as well as the 
	 * original word length)
	 * @param (humanGuess) (the upper or lower casified human guess depending on if the letter is in the current word or not)
	 * @return returns a string representing a word that is consistent with letters chosen/not chosen so far. original word
	 * length, and finally the user's guess)
	 */
	public static String secretSolutionChooser(ArrayList<String> possibleWords , char humanGuess) {
		int max = 0;
		int maxPoss = (int)Math.pow(2.0D, ((String)possibleWords.get(0)).length());
		ArrayList<ArrayList<String>> wordCodes = new ArrayList<ArrayList<String>>(maxPoss);
		for (int i = 0 ; i < maxPoss ; i++) {
			wordCodes.add(i, new ArrayList<String>());
			wordCodes.get(i).clear();
		}
		
		int[] counter = new int[maxPoss];
		for (int j = 0 ; j < possibleWords.size() ; j++) {
			possibleWords.get(j).toUpperCase();
			if (Character.isLowerCase(humanGuess)) {
				humanGuess= Character.toUpperCase(humanGuess); 
			}
			int binCode = 0;
			for (int k = 0 ; k < possibleWords.get(j).toUpperCase().length() ; k++) {
				if (possibleWords.get(j).toUpperCase().charAt(k) == humanGuess) {
					binCode += (int)Math.pow(2.0D, k);
				}
			}
			wordCodes.get(binCode).add(possibleWords.get(j).toLowerCase());
			counter[binCode] += 1;
			if ((counter[binCode] > counter[max]) || ((counter[binCode] == counter[max]) && (binCode < max))) {
				max = binCode;
			}
		}
		possibleWords.clear();
		for (int l = 0 ; l < counter[max] ; l++) {
			possibleWords.add((String)wordCodes.get(max).get(l));
		}
		return (String)possibleWords.get(0);
	}
}


