/** File Header: This file contains the implementation of the BST creation process.
 * It also includes the implementation of various other operations that can be used to analyze the BST.
 * The BST is made up of nodes that contain a city and a population. 
 * @author Samruddhi Hande
 * @email shande@ucsd.edu */
package com.cse.ds;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** Class Header: This class implements the methods we use to create and analzye the binary search tree data structure. We have one instance variable named root, which is where the tree begins.*/
public class BinarySearchTree {

	private Node root; //root element

	// ============== BALANCED TREE CREATION ====================//
    /** Constructor: creates the BST based on a String array of cities and an int array of populations
     * @param string array of cities, int array of populations */
	public BinarySearchTree(String[] cities, int[] population) {
		// TODO: Create a balanced BST by starting at mid node and creating tree
		// recursively.
		// Use trim on cities names before adding
		if (cities == null || population == null || cities.length == 0 || population.length == 0) {
			root = null;
		} else {
			Node[] cityPop = new Node[cities.length];
			for (int i = 0; i < cities.length; i++) {
				cityPop[i] = new Node(cities[i], population[i]);
			}
			root = bstRecursive(cityPop, 0, cities.length - 1);
		}

	}

    /** Recursive Helper Method to implement constructors.
     * @param node array, int, int -> city & population nodes, left index, right index
     * @return middle node */
	private Node bstRecursive(Node[] cityPop, int leftIndex, int rightIndex) {

		int midIndex = ((leftIndex + rightIndex) / 2);
		Node midNode = cityPop[midIndex];
		if (leftIndex < rightIndex) {
			if (leftIndex < midIndex) {
				midNode.left = bstRecursive(cityPop, leftIndex, midIndex - 1);
			}
			if (midIndex < rightIndex) {
				midNode.right = bstRecursive(cityPop, midIndex + 1, rightIndex);
			}
		}
		return midNode;
	}

    /**Constructor: creates BST based on a file and the number of lines in the file passed in
     * @param string, int - file name and number of lines (file name in this case is input.txt); passed in using a Scanner */
	public BinarySearchTree(String filename, int num_lines) {
		// TODO: Create a balanced BST from the input.txt
		// Use trim on cities names before adding
		Scanner input = null;

		try {
			input = new Scanner(new File(filename));

			Node[] cityPop = new Node[num_lines];
			for (int i = num_lines - 1; i >= 0; i--) {
				String line = input.nextLine();
				int index = line.indexOf("=>");

				String city = line.substring(0, index).trim();
				int pop = Integer.parseInt(line.substring(index + 2).trim());

				cityPop[i] = new Node(city, pop);
			}
			root = bstRecursive(cityPop, 0, cityPop.length - 1);
		} catch (Exception e) {
		} finally {
			input.close();
		}
		
	}

	// ============== COMMON TREE OPERATIONS ====================//
    /** recursive helper method to add cities
     * @param new node to be added, the children node */
	private void addNodeRecursive(Node newNode, Node subRoot) {
		if (newNode.population <= subRoot.population) {
			if (subRoot.left != null) {
				addNodeRecursive(newNode, subRoot.left);
			} else {
				subRoot.left = newNode;
			}
		} else {
			if (subRoot.right != null) {
				addNodeRecursive(newNode, subRoot.right);
			} else {
				subRoot.right = newNode;
			}
		}
	}
	
    /** adds new city to BST; implements helper method addNodeRecursive
     * @param String city, int population */
	public void addCity(String city, int population) {
		// TODO: Add city node to BST
		if (city == null) {
			throw new IllegalArgumentException("need city name");
		}
		Node newCity = new Node(city, population);
		if (root == null) {
			root = newCity;
		} else {
			addNodeRecursive(newCity, root);
		}
	}

	/** recursive helper method to find depth at each node
     * @param node whose depth needs to be calculated
     * @return int depth */
	public int findDepth(Node node) {
		if (node == null) {
			return 0;
		}
		int depth = Math.max(findDepth(node.left), findDepth(node.right)) + 1;
		return depth;
	}
	
    /** returns the maximum depth of BST
     * @return max depth by calling recursive helper method */
	public int getMaxDepth() {
		// TODO: Get the max depth of BST
		return findDepth(root);
	}

    /** recursive helper method to find width of BST
     * @param node, column, bounds
     * @return int array with bounds of widths on each side */
	private int[] findWidth(Node node, int column, int[] bounds) {
		if (node == null) {
			return new int[] {0,0};
		}
		if (column < bounds[0]) {
			bounds[0] = column;
		} 
		if (column > bounds[1]){
			bounds[1] = column;
		}
		
		if (node.left != null) {
			findWidth(node.left, column - 1, bounds);
		}
		if (node.right != null) {
			findWidth(node.right, column + 1, bounds);
		}
		return bounds;
		
	}
	
	/** returns maximum width of BST
	 * @return integer that is the width */
	public int getMaxWidth() {
		// TODO: Get the maximum width of BST
		int[] width = findWidth(root, 0, new int[]{0,1});
		return (width[1] - width[0]);
	}

	/** returns the city with the smallest population 
	 * @return String - name of the city with smallest population */
	public String getSmallestCity() {
		// TODO: Get the smallest population city.
		if (root == null) {
			return null;
		}
		Node curRoot = root;
		while (curRoot.left != null) {
			curRoot = curRoot.left;
		}
		return curRoot.city;
	}

	/** returns the city with the largest population
	 * @return String - name of the city with the largest population */
	public String getLargestCity() {
		// TODO: Get the largest population city.
		if (root == null) {
			return null;
		}
		Node curRoot = root;
		while (curRoot.right != null) {
			curRoot = curRoot.right;
		}
		return curRoot.city;
	}

	// ============== TREE TRAVERSALS ====================//
	/** helper method to help with pre order traversal 
	 * @param list of Strings treeOrder, node */
	private void preOrderHelper(List<String> treeOrder, Node node) {
		if (node == null) {
			return;
		}
		treeOrder.add(node.city);
		preOrderHelper(treeOrder, node.left);
		preOrderHelper(treeOrder, node.right);
	}
	
	/** returns list of cities in pre order traversal
	 * @return list of Strings treePreOrder - cities in pre-order*/
	public List<String> getPreOrderTraversal() {
		// TODO: Get preorder traversal.
		List<String> treePreOrder = new ArrayList<String>();
		preOrderHelper(treePreOrder, root);
		return treePreOrder;
	}

	/** helper method to help with post order traversal 
	 * @param list of Strings treeOrder, node */
	private void postOrderHelper(List<String> treeOrder, Node node) {
		if (node == null) {
			return;
		}
		postOrderHelper(treeOrder, node.left);
		postOrderHelper(treeOrder, node.right);
		treeOrder.add(node.city);
	}
	
	/** returns list of cities in post order traversal
	 * @return list of Strings treePostOrder - cities in post-order*/
	public List<String> getPostOrderTraversal() {
		// TODO: Get postorder traversal.
		List<String> treePostOrder = new ArrayList<String>();
		postOrderHelper(treePostOrder, root);
		return treePostOrder;
	}

	/** helper method to help with in order traversal 
	 * @param list of Strings treeOrder, node */
	private void inOrderHelper(List<String> treeOrder, Node node) {
		if (node == null) {
			return;
		}
		inOrderHelper(treeOrder, node.left);
		treeOrder.add(node.city);
		inOrderHelper(treeOrder, node.right);
	}
	
	/** returns list of city names in sorted order 
	 * @return treeInOrder - list of Strings containing sorted city names*/
	public List<String> getSortedCities() {
		List<String> treeInOrder = new ArrayList<String>();
		inOrderHelper(treeInOrder, root);
		return treeInOrder;
	}

	/** helper method for getLevelWiseCities() - helps return each layer of cities in a list
	 * @param node, layers, current depth */
	private void levelHelper(Node node, List<List<String>> layers, int currentDepth) {
		if (node == null) {
			return;
		}
		layers.get(currentDepth).add(node.city);
		levelHelper(node.left, layers, currentDepth + 1);
		levelHelper(node.right, layers, currentDepth + 1);
	}
	
	/** returns a list of lists; each list within the list stores all the city names at a specific level of the BST 
	 * @return layers - list of levels */
	public List<List<String>> getLevelWiseCities() {
		// TODO: Get cities level wise
		int depth = this.getMaxDepth();
		List<List<String>> layers = new ArrayList<List<String>>();
		for (int i = 0; i < depth; i++) {
			layers.add(new ArrayList<String>());
		}
		levelHelper(root, layers, 0);
		return layers;
	}

	/** helper method for getLevelWiseCities() - helps return each layer of cities in a list; reverses city order if necessary
	 * @param node, layers, current depth */
	private void twistyHelper(Node node, List<List<String>> layers, int currentDepth) {
		if (node == null) {
			return;
		}
		
		if (currentDepth % 2 == 1) {
			layers.get(currentDepth).add(0, node.city);
		} else { 
			layers.get(currentDepth).add(node.city);
		}
		twistyHelper(node.left, layers, currentDepth + 1);
		twistyHelper(node.right, layers, currentDepth + 1);
	}
	
	/** returns a list of lists; each list within the list stores all the city names at a specific level of the BST 
	 * @return layers - list of levels in twisty order */
	public List<List<String>> getTwistyTraversal() {
		int depth = this.getMaxDepth();
		List<List<String>> layers = new ArrayList<List<String>>();
		for (int i = 0; i < depth; i++) {
			layers.add(new ArrayList<String>());
		}
		twistyHelper(root, layers, 0);
		return layers;
	}

	// ============== TREE VIEWS ====================//
	/** returns right view of BST 
	 * @return rightView - list of city names in right view */
	public List<String> getRightView() {
		// TODO: Get right side view of BST
		List<List<String>> layers = getLevelWiseCities();
		List<String> rightView = new ArrayList<String>();
		for (int i = 0; i < layers.size(); i++) {
			List<String> row = layers.get(i);
			rightView.add(row.get(row.size() - 1));
		}
		return rightView;
	}

	/** returns left view of BST 
	 * @return leftView - list of city names in left view */
	public List<String> getLeftView() {
		// TODO: Get left side view of BST
		List<List<String>> layers = getLevelWiseCities();
		List<String> leftView = new ArrayList<String>();
		for (int i = 0; i < layers.size(); i++) {
			leftView.add(layers.get(i).get(0));
		}
		return leftView;
	}

	/** recursive helper method for top view - checks which one is topmost
	 * @param node, topView, depths, depth, column, lower bound*/
	private void topHelper(Node node, String[] topView, int[] depths, int depth, int column, int lowB) {
		if (node == null) {
			return;
		}
		if (topView[column - lowB] == null || depths[column - lowB] > depth) {
			topView[column - lowB] = node.city;
			depths[column - lowB] = depth;
		}
		topHelper(node.left, topView, depths, depth + 1, column - 1, lowB);
		topHelper(node.right, topView, depths, depth + 1, column + 1, lowB);
	}
	
	/** returns top view of BST 
	 * @return topView - list of city names in top view */
	public List<String> getTopView() {
		// TODO: Get top view of BST
		int[] bounds = findWidth(root, 0, new int[] {0, 0});
		String[] topView = new String[bounds[1] - bounds[0] +1];
		int[] depths = new int[bounds[1] - bounds[0] +1];
		for (int i = 0; i < depths.length; i++) {
			depths[i] = Integer.MAX_VALUE;
		}
		topHelper(root, topView, depths, 1,  0, bounds[0]);
		return Arrays.asList(topView);
	}

	/** recursive helper method for top view - checks which one is bottom-most
	 * @param node, bottomView, depths, depth, column, lower bound*/
	private void bottomHelper(Node node, String[] bottomView, int[] depths, int depth, int column, int lowB) {
		if (node == null) {
			return;
		} 
		if (bottomView[column - lowB] == null || depths[column - lowB] < depth) {
			bottomView[column - lowB] = node.city;
			depths[column - lowB] = depth;
		}
		bottomHelper(node.left, bottomView, depths, depth + 1, column - 1, lowB);
		bottomHelper(node.right, bottomView, depths, depth + 1, column + 1, lowB);
	}
	
	/** returns bottom view of BST 
	 * @return bottomView - list of city names in bottom view */
	public List<String> getBottomView() {
		// TODO: Get bottom view of BST
		int[] bounds = findWidth(root, 0, new int[] {0, 0});
		String[] bottomView = new String[bounds[1] - bounds[0] +1];
		int[] depths = new int[bounds[1] - bounds[0] +1];
		for (int i = 0; i < depths.length; i++) {
			depths[i] = Integer.MIN_VALUE;
		}
		bottomHelper(root, bottomView, depths, 1, 0, bounds[0]);
		return Arrays.asList(bottomView);
	}

	// ============== SPECIAL TREE OPERATIONS ====================//
	/** recursive helper method to help calculate tilt of a BST - calculates subtree calculations 
	 * @return tilt */
	private int tiltHelper(Node node) {
		if (node == null) {
			return 0;
		}
		int tilt = Math.abs(tiltSum(node.left) - tiltSum(node.right));
		return tiltHelper(node.left) + tiltHelper(node.right) + tilt;
	}
	
	/** second helper method, also recursive, to calculate tilt of BST - adds up populations
	 * @param node
	 * @return population totals */
	private int tiltSum(Node node) {
		if (node == null) {
			return 0;
		}
		return tiltSum(node.left) + tiltSum(node.right) + node.population;
	}
	
	/** returns tilt of BST starting at root
	 * tilt is absolute difference between the sum of all left subtree node values and the sum of all right subtree node values
	 * @param node
	 * @return tilt */
	public int getBSTilt() {
		// TODO: Get the tilt of BST
		return tiltHelper(root);
	}

	/** recursive helper method that helps calculate the possible paths
	 * @param node, cityPath, paths
	 */
	private void pathHelper(Node node, String cityPath, List<String> paths) {
		if (node == null) {
			return;
		}
		cityPath = cityPath + "=>" + node.city;
		if (node.left == null && node.right == null) {
			paths.add(cityPath);
		} else {
			pathHelper(node.left, cityPath, paths);
			pathHelper(node.right, cityPath, paths);
		}
	}
	
	/** returns list of all paths 
	 * @return pathList - list of city names - routes that one can take from root to each city node*/
	public List<String> getAllPaths() {
		// TODO: Get all the root to leaf paths in the tree
		// HINT: iterate left first and then iterate right
		List<String> pathList = new ArrayList<String>();
		pathHelper(root, "" , pathList);
		return pathList;
	}

	/** recursive helper method to flatten BST 
	 * @param llOrder, node*/
	private void toLLHelper(List<Node> llOrder, Node node) {
		if (node == null) {
			return;
		}
		llOrder.add(node);
		toLLHelper(llOrder, node.left);
		toLLHelper(llOrder, node.right);
	}
	
	/** flattens tree to create a singly linked list */
	public void flattenToLL() {
		// TODO: Flatten BST to a Linked List like structure
		List<Node> toLL = new ArrayList<Node>();
		toLLHelper(toLL, root);
		for (int i = 0; i < toLL.size() - 1; i++) {
			toLL.get(i).left = null;
			toLL.get(i).right = toLL.get(i + 1);
		}
		root = toLL.get(0);
		toLL.get(toLL.size() - 1).left =  null;
		toLL.get(toLL.size() - 1).right = null;
	}

	// ============== TREE VISUALIZATION ====================//
	/** toString method to visualize the BST
	 * @return String - to see tree */
	@Override
	public String toString() {
		// TODO: Generate tree representation as give in writeup
		if (root == null) {
			return "|NULL\n";
		}
		return toStringHelper(root, "|");
	}
	
	/** recursive helper method to visualize tree in toString method 
	 * @param current, prefix
	 * @return String s */
	private String toStringHelper(Node current, String prefix) {
		String s = prefix + current.city + ":" + current.population + "\n";
		s += prefix + "L:\n";
		if (current.left == null) {
			s += prefix + "-----NULL\n";
		} else {
			s += toStringHelper(current.left, prefix + "-----");
		}
		s += prefix + "R:\n";
		if (current.right == null) {
			s += prefix + "-----NULL\n";
		} else {
			s += toStringHelper(current.right, prefix + "-----");
		}
		return s;
	}
}
