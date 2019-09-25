/** This file contains the implement for the Node object. It defines its constructors.
 * @author Samruddhi Hande
 * @email shande@ucsd.edu
 */
package com.cse.ds;

/** This class constructs the Node object that we will use to build our BST. It has 4 instance variables.
 * They are city, population - these are the elements in the node.
 * There are two more - the left and right nodes which will represent the children nodes in our BST.
 */
public class Node {
    String city; //city name
    int population; //corresponding city population
    
    Node left; //left child
    Node right; //right child
    
    /** empty node constructor */
    public Node() {
		// TODO Auto-generated constructor stub
    	this.left = null;
    	this.right = null;
    	this.city = null;
    	this.population =  -1;
	}
    
    /** node constructor for node that will hold city and population
     * @param city, population - city name + corresponding population of city */
    public Node(String city, int population) {
		// TODO Auto-generated constructor stub
    	this.left = null;
    	this.right = null;
    	this.city = city.trim();
    	this.population = population;
	}
    
}