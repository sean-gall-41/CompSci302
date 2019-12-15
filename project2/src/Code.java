/**
 *The code class serves to define the slots within
 *each initialized code. The class includes getters
 *and setters for the field "slots" and overrides
 *the default equals() and toString() methods for 
 *objects. Furthermore, two methods are defined: 
 *the first being pegs(), which compares the calling
 *instance to the argument and declares how many
 *black and white pegs the calling code "gets". The
 *second method, pickMove() takes an arraylist of
 *possible codes and the pegs number result of the
 *calling instance to narrow down the arraylist of 
 *possible codes in order to become consistent
 *with the pegs assignment
 * 
 * Bugs: (None that I can think of.) 
 * 
 * @author (Sean Gallogly) */

import java.util.ArrayList;

public class Code {
	private Project2.Color[] slots = new Project2.Color[4];

	public Code() {
		for(int i=0;i<slots.length;i++) {
			slots[i] = Project2.Color.EMPTY;
		}
	}
	
	public Code(Project2.Color[] inArray) {
		for(int i=0;i<slots.length;i++) {
			slots[i] = inArray[i];
		}
	}
	
/**
 *The getter for the field, slots, which obtains the color
 *in the given argument index
 *
 * @param (colorIndex) (index of desired color in slots)
 * @return (returns the color in slots located at index "colorIndex")
 */
	public Project2.Color getColorInSlots(int colorIndex) {
		int i = 0;
		while (i != colorIndex) {
			i++;
		}
		return slots[i];
	}
	
/**
 *The getter for the slots field which returns the field itself
 *
 * @return (returns the slots[] field)
 */
	public Project2.Color[] getSlots() {
		return slots;
	}
	
/**
 *The getter for the field, slots, which obtains the color
 *in the given argument index
 *
 * @param (colorIndex) (index of desired color in slots)
 * @return (returns the color in slots located at index "colorIndex")
 */
	public void setSingleColor(int slotsIndex , Project2.Color inColor) {
		int i = 0;
		while (i != slotsIndex) {
			i++;
		}
		slots[i] = inColor;
	}
	
/**
 *Overriden equals() method.Compares two codes by comparing slot
 *for slot for all slots in each code
 *
 * @param (inCode) (the code to be compared to this code)
 * @return (returns true or false if this code equals the argument
 * code or not respectively)
 */
	public boolean equals(Code inCode) {
		boolean equals = true;	
		int i = 0;
		while (equals && (i<slots.length)){
			if (this.slots[i] != inCode.getColorInSlots(i)) {
				equals = false;
			}
			else {
				i++;
			}
		}
		return equals;
	}
	
	
/**
 *Overriden toString() method for the slots[] field
 *
 * @return (returns the colors in slots[] separated by a space and enclosed in brackets)
 */	
	@Override
	public String toString() {
		return "[" + slots[0] + " " + slots[1] + " " + slots[2] + " " + slots[3] + "]";
	}
	
/**
 *Takes a code as an argument and compares the calling instance to this code.
 *returns a two digit integer representing the number of pegs that are consistent
 *with the secret solution and the calling instance (e.g. if the calling instance was
 *[red red empty empty] and the solution was [empty empty empty empty] then the method 
 *would return 20.
 *
 * @param (secretSolution) (the solution code that the calling instance is to be compared to)
 * @return (returns a two digit int where the first digit represents how many black pegs are to be
 * assigned and the second digit represents the number of white pegs to be assigned )
 */
	public int pegs(Code secretSolution){
		boolean foundOne;
		int black = 0;
		int white = 0;
		for(int i=0;i<slots.length;i++){
			if(this.slots[i]==secretSolution.slots[i]){
				black++;
			}else{
				int j = 0;
				foundOne = false;
				while ((j < slots.length) && (!foundOne)) {
					if ((this.slots[i] == secretSolution.getSlots()[j]) && (j != i)) {
						white++;
						foundOne = true;
					}
					else {
						j++;
					}
				}
			}
		}
		return black*10+white;
	}
	
/**
 *returns a code after comparing every code in the argument with the calling instance and determining
 *the peg number, which it compares with the actual peg number. If the two are not equal, that code
 *in the argument arraylist gets removed from the list. Once the arraylist consists of all possibe moves 
 *consistent with the argument pegNum, it selects one of those codes at random and returns that code.
 *
 * @param (inArrayList) (the current arraylist of consistent codes with all previous peg numbers)
 * @param (pegNum) (the current peg number for this turn)
 * @return (a random code for the culled arraylist that consists of consistent codes with current
 * and all previous pegNums in a game)
 */
	public Code pickMove(ArrayList<Code> inArrayList, int pegNum){
		Code testSolution = new Code();
		boolean consistent;
		int i = 0;
		int ranNum;
		while (i < inArrayList.size()) {
			testSolution = inArrayList.get(i);
			if (this.pegs(testSolution) == pegNum) {
				consistent = true;
			}
			else {
				consistent = false;
			}
			if (consistent) { 
				i++;
			}
			else {
				inArrayList.remove(i);
			}
		}
		if (inArrayList.size() == 0) {
			return null;
		}
		else {
			ranNum = Project2.rangen.nextInt(inArrayList.size());
			return inArrayList.get(ranNum);
		}
	}
	
	
}


	
	

