/** File Header: This file contains tests to test the BST and its various components. The tests test methods and special cases. 
 * @author Samruddhi Hande
 * @email shande@ucsd.edu */
package com.cse.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**Class Header: This class contains the tests to check the various components of the BST. */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentTest {
	
	static BinarySearchTree bst = null; //normal binary search tree
	static BinarySearchTree bstNull = null; //null bst
	static BinarySearchTree bstAdd = null; //additional bst
	static BinarySearchTree bstEmpty = null; //empty bst
	
	//set up BSTs
	@BeforeClass
    public static void setUpBeforeClass() {
		bstNull = new BinarySearchTree(null, null);
		bst = new BinarySearchTree(null, null);
		bst.addCity("A", 5);
		bst.addCity("B", 4);
		bst.addCity("C", 8);
		bst.addCity("D", 2);
		bst.addCity("E", 7);
		String[] cities = new String[] {"A", "B", "C"};
		int[] pops = new int[] {3, 4, 6};
		bstAdd = new BinarySearchTree(cities, pops);
		String[] cities2 = new String[] {};
		int[] pops2 = new int[] {};
		bstEmpty = new BinarySearchTree(cities2, pops2);
    }
	
	//set up BSTs
	@Before
	public void beforeMethod()
	{
		bstNull = new BinarySearchTree(null, null);
		bst = new BinarySearchTree(null, null);
		bst.addCity("A", 5);
		bst.addCity("B", 4);
		bst.addCity("C", 8);
		bst.addCity("D", 2);
		bst.addCity("E", 7);
		String[] cities = new String[] {"A", "B", "C"};
		int[] pops = new int[] {3, 4, 6};
		bstAdd = new BinarySearchTree(cities, pops);
		String[] cities2 = new String[0];
		int[] pops2 = new int[0];
		bstEmpty = new BinarySearchTree(cities2, pops2);
	}
	
	//test input with file - second constructor
	@Test
	public void test1() {
		bst = new BinarySearchTree("resource/input.txt", 5);
		//System.out.println(bst.root);
		Assert.assertEquals("|Chicago city_Illinois:2695598\n" + 
				"|L:\n" + 
				"|-----Phoenix city_Arizona:1445632\n" + 
				"|-----L:\n" + 
				"|----------NULL\n" + 
				"|-----R:\n" + 
				"|----------Houston city_Texas:2099451\n" + 
				"|----------L:\n" + 
				"|---------------NULL\n" + 
				"|----------R:\n" + 
				"|---------------NULL\n" + 
				"|R:\n" + 
				"|-----Los Angeles city_California:3792621\n" + 
				"|-----L:\n" + 
				"|----------NULL\n" + 
				"|-----R:\n" + 
				"|----------New York city_New York:8175133\n" + 
				"|----------L:\n" + 
				"|---------------NULL\n" + 
				"|----------R:\n" + 
				"|---------------NULL\n", bst.toString());
		Assert.assertEquals("|NULL\n", bstNull.toString());
	}
	
	//test sorting, pre and post order traversals on normal bst
	@Test
	public void test2( ) {
		Assert.assertEquals(Arrays.asList(new String[]{"D", "B", "A", "E", "C"}),bst.getSortedCities());
		Assert.assertEquals(Arrays.asList(new String[]{"A", "B", "D", "C", "E"}),bst.getPreOrderTraversal());
		Assert.assertEquals(Arrays.asList(new String[]{"D", "B", "E", "C", "A"}),bst.getPostOrderTraversal());
	}
	
	//test depth and width functions
	@Test
	public void test2_2( ) {
		Assert.assertEquals(3, bst.getMaxDepth());
		Assert.assertEquals(3, bst.getMaxWidth());
		Assert.assertEquals(0, bstNull.getMaxDepth());
		Assert.assertEquals(0, bstNull.getMaxWidth());
	}
	
	//test getting levels of bst
	@Test
	public void test2_3( ) {
		List<List<String>> res = new ArrayList<>();
		res.add(Arrays.asList(new String[]{"A"}));
		res.add(Arrays.asList(new String[]{"B","C"}));
		res.add(Arrays.asList(new String[]{"D","E"}));

		Assert.assertEquals(res,bst.getLevelWiseCities());
	}
	
	//test getting all the paths + twisty traversal
	@Test
	public void test2_4( ) {
		Assert.assertEquals(Arrays.asList(new String[]{"=>A=>B=>D", "=>A=>C=>E"}),bst.getAllPaths());
		
		List<List<String>> res1 = new ArrayList<>();
		res1.add(Arrays.asList(new String[]{"A"}));
		res1.add(Arrays.asList(new String[]{"C","B"}));
		res1.add(Arrays.asList(new String[]{"D","E"}));
		Assert.assertEquals(res1,bst.getTwistyTraversal());
	}
	
	//test twisty traversal
	@Test
	public void test2_5( ) {
		List<List<String>> res1 = new ArrayList<>();
		res1.add(Arrays.asList(new String[]{"A"}));
		res1.add(Arrays.asList(new String[]{"C","B"}));
		res1.add(Arrays.asList(new String[]{"D","E"}));
		Assert.assertEquals(res1,bst.getTwistyTraversal());
	}
	
	//testing tilts
	@Test
	public void test3( ) {
		Assert.assertEquals(18, bst.getBSTilt());
		Assert.assertEquals(0, bstEmpty.getBSTilt());
		Assert.assertEquals(0, bstNull.getBSTilt());
	}
	
	//testing right view
	@Test
	public void test4( ) {
		Assert.assertEquals(Arrays.asList(new String[]{"A", "C", "E"}), bst.getRightView());
	}
	
	//testing left view
	@Test
	public void test5( ) {
		Assert.assertEquals(Arrays.asList(new String[]{"A", "B", "D"}), bst.getLeftView());
	}
	
	//testing top view
	@Test
	public void test6( ) {
		Assert.assertEquals(Arrays.asList(new String[]{"D", "B", "A", "C"}), bst.getTopView());
	}
	
	//testing bottom view
	@Test
	public void test7( ) {
		Assert.assertEquals(Arrays.asList(new String[]{"D", "B", "E", "C"}), bst.getBottomView());
	}

	//testing flattened bst
	@Test
	public void test9( ) {
		bst.flattenToLL();
		Assert.assertEquals("|A:5\n" + 
				"|L:\n" + 
				"|-----NULL\n" + 
				"|R:\n" + 
				"|-----B:4\n" + 
				"|-----L:\n" + 
				"|----------NULL\n" + 
				"|-----R:\n" + 
				"|----------D:2\n" + 
				"|----------L:\n" + 
				"|---------------NULL\n" + 
				"|----------R:\n" + 
				"|---------------C:8\n" + 
				"|---------------L:\n" + 
				"|--------------------NULL\n" + 
				"|---------------R:\n" + 
				"|--------------------E:7\n" + 
				"|--------------------L:\n" + 
				"|-------------------------NULL\n" + 
				"|--------------------R:\n" + 
				"|-------------------------NULL\n", bst.toString());
	}
	
	//testing return of smallest city
	@Test
	public void testSmallestCity() {
		Assert.assertEquals("D", bst.getSmallestCity());
		Assert.assertEquals(null, bstNull.getSmallestCity());
		Assert.assertEquals("A", bstAdd.getSmallestCity());
	}
	
	//testing return of largest city
	@Test
	public void testLargestCity() {
		Assert.assertEquals("C", bst.getLargestCity());
		Assert.assertEquals(null, bstNull.getLargestCity());
		Assert.assertEquals("C", bstAdd.getLargestCity());
	}

	//testing empty bst
	@Test
	public void testbstEmpty() {
		Assert.assertEquals(null, bstEmpty.getLargestCity());
		Assert.assertEquals(null, bstEmpty.getSmallestCity());
	}
	
	//testing defauly empty node constructor
	@Test
	public void testDefaultConstructor(){
	    Node node = new Node();
	    Assert.assertEquals(null, node.city);
	    Assert.assertEquals(-1, node.population);
	}
	
	//testing empty orders
	@Test
	public void EmptyOrders( ) {
		Assert.assertEquals(Arrays.asList(new String[]{}),bstEmpty.getSortedCities());
		Assert.assertEquals(Arrays.asList(new String[]{}),bstEmpty.getPreOrderTraversal());
		Assert.assertEquals(Arrays.asList(new String[]{}),bstEmpty.getPostOrderTraversal());
	}

}
