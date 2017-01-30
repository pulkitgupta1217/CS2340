package edu.gatech.oad.antlab.person;

import java.util.Random;

/**
 *  A simple class for person 2
 *  returns their name and a
 *  modified string
 *
 * @author Bob, Danny Zhang
 * @version 1.1.0
 * Danny Zhang
 * danny.zhang@gatech.edu
 * Robert Waters
 * 1 February 2017
 */
public class Person2 {
    /** Holds the persons real name */
    private String name;

	 /**
	 * The constructor, takes in the persons
	 * name
	 * @param pname the person's real name
	 */
	 public Person2(String pname) {
	   	name = pname;
	 }

	/**
	 * This method should take the string
	 * input and return its characters in
	 * random order.
	 * given "gtg123b" it should return
	 * something like "g3tb1g2".
	 *
	 * @param input the string to be modified
	 * @return the modified string
	 */
	private String calc(String input) {
		//Person 2 put your implementation here
	  	Random randomizer = new Random();
	  	char[] charArray = input.toCharArray();
      	for (int i = 0; i < charArray.length - 1; i++) {
        	int j = randomizer.nextInt(charArray.length - 1);
        	char temp = charArray[i];
        	charArray[i] = charArray[j];
        	charArray[j] = temp;
      	}
      	return new String(charArray);
    }

	/**
	 * Return a string rep of this object
	 * that varies with an input string
	 *
	 * @param input the varying string
	 * @return the string representing the
	 *         object
	 */
	public String toString(String input) {
		return name + calc(input);
	}

	/*public static void main(String[] args) {
		Person2 danny = new Person2("dannyzhang");
		System.out.println(danny.toString("gtg123b"));
	}*/
}
