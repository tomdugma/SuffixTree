/**
 * This is a testing framework. Use it extensively to verify that your code is working
 * properly.
 */
public class Tester {

	private static boolean testPassed = true;
	private static int testNum = 0;
	
	/**
	 * This entry function will test all classes created in this assignment.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
	
		// Each function here should test a different class.
		
		//CharLinkedListNode
		testCharLinkedListNode();
		
		// CharLinkedList
		testCharLinkedList();

		// SuffixTreeNode
		testSuffixTreeNode();

		// ReverseSuffixTree
		testReverseSuffixTree();
		
		// SuffixTree
		testSuffixTree();


		// Notifying the user that the code have passed all tests. 
		if (testPassed) {
			System.out.println("All " + testNum + " tests passed!");
		}
	}

	/**
	 * This utility function will count the number of times it was invoked. 
	 * In addition, if a test fails the function will print the error message.  
	 * @param exp The actual test condition
	 * @param msg An error message, will be printed to the screen in case the test fails.
	 */
	private static void test(boolean exp, String msg) {
		testNum++;
		
		if (!exp) {
			testPassed = false;
			System.out.println("Test " + testNum + " failed: "  + msg);
		}
	}
	/**
	 * Checks the CharLinkedListNode class.
	 */
	private static void testCharLinkedListNode() {
		ICharLinkedListNode node1=new CharLinkedListNodeImpl('t');
		ICharLinkedListNode node2=new CharLinkedListNodeImpl('o');
		ICharLinkedListNode node3=new CharLinkedListNodeImpl('m');
		//get char, get next
		test(node1.getChar() == 't', "the node char should be t");//1
		test(node1.getNext() == null, "node next pointer should be null");//2
		node1.setNext(node2);
		node2.setNext(node3);
		test(node1.getNext() == node2, "node 1 next should be node 2");//3
		test(node2.getNext() == node3, "node 1 next should be node 3");//4
		test(node3.getNext() == null, "node 1 next should be null");//5
		
	}
	/**
	 * Checks the CharLinkedList class.
	 */
	private static void testCharLinkedList(){
		CharLinkedList list = new CharLinkedListImpl();
		CharLinkedList list2 = new CharLinkedListImpl();
		// size,getFirst,getLast,Append
		test(list.size() == 0, "The size of the list should be 0");//6
		list.add('a');
		test(list.size() == 1, "The size of the list should be 1");//7
		test(list.firstChar() == 'a', "The first char should be 'a'");//8
		list.add('b');
		list.add('c');
		test(list.size() == 3, "The size of the list should be 3");//9
		test(list.getFirst().getChar() == 'a', "the first char should be a");//10
		test(list.getLast().getChar() == 'c', "the first char should be c");//11
		test(list.getFirst().getNext().getChar()=='b',"The char should be 'b'");//12
		list2.add('d');
		list2.add('e');
		list2.add('f');
		list.append(list2);
		test(list.getLast().getChar() == 'f', "last char should be f");//13
		test(list.getFirst().getChar() == 'a', "first char should be a");//14
	}

	/**
	 * Checks the SuffixTreeNode class.
	 */
	private static void testSuffixTreeNode() {
		// test empty root
		SuffixTreeNode node = new SuffixTreeNodeImpl();
		test(node.getTotalWordLength() == 0, "node word length should be 0");//15
		test(node.getNumOfChildren() == 0, "node num of children should be 0");//16

		// test search, binary search, shiftChildren and addChild
		SuffixTreeNode child1 = new SuffixTreeNodeImpl(CharLinkedList.from("abc"), node);
		SuffixTreeNode child2 = new SuffixTreeNodeImpl(CharLinkedList.from("bcd"), node);
		SuffixTreeNode child3 = new SuffixTreeNodeImpl(CharLinkedList.from("hello"), node);
		SuffixTreeNode child4 = new SuffixTreeNodeImpl(CharLinkedList.from("mmmmm"), node);
		node.setChildren(new SuffixTreeNode[]{child1, child2, child3, child4, null, null, null, null}, 4);

		// binary search
		test(node.binarySearch('b', 0, 3) == child2, "search for 'b' should find child2");//17

		// search
		test(node.search('a') == child1, "search for 'a' should return child1");//18
		test(node.search('x') == null, "search for 'x' should fail");//19

		// add child
		SuffixTreeNode child5 = new SuffixTreeNodeImpl(CharLinkedList.from("dog"), node);
		node.addChild(child5);
		test(node.getChildren()[2] == child5, "3rd child should be child5");//20
		
		//add suffix
		SuffixTreeNode bananaRoot = new SuffixTreeNodeImpl();
		char[] word= "banana$".toCharArray();
		bananaRoot.addSuffix(word, 0);
		test(bananaRoot.numOfChildren==1, "root num of children should be 1");//21
		test(bananaRoot.children[0].totalWordLength == 1, "length should be 1");//22
		bananaRoot.addSuffix(word, 1);
		bananaRoot.addSuffix(word, 2);
		test(bananaRoot.numOfChildren==3, "root num of children should be 3");//21
		int i = 3;
		while(i<word.length) {
			bananaRoot.addSuffix(word, i);
			i++;
		}
		test(bananaRoot.numOfChildren==4, "root num of children should be 4");//21
		// compress banana
		bananaRoot.compress();
		//num of children, descendantLeaves, wordLength
		test(bananaRoot.children[1].getTotalWordLength() == 7, " total length should be 7");//22
		test(bananaRoot.children[1].chars.getLast().getChar() == '$', "The last char should be $ ");//23
		test(bananaRoot.children[0].numOfChildren == 2, "num of children suppose to be 2");//24
		test(bananaRoot.children[0].descendantLeaves == 3, "num of leafs should be 3");//25
		test(bananaRoot.descendantLeaves == 7, "num of leafs should be 7");//26
		
		SuffixTreeNode mississipiRoot = new SuffixTreeNodeImpl();
		char[] word2 = "mississippi$".toCharArray();
		int j = 0;
		while(j<word2.length) {
			mississipiRoot.addSuffix(word2, j);
			j++;
		}
		mississipiRoot.compress();
		
		//numOfOccurrences
		test(bananaRoot.numOfOccurrences("a".toCharArray(), 0) == 3, "The num of ocurrences should be  3");
		test(bananaRoot.numOfOccurrences("na".toCharArray(), 0) == 2, "The num of ocurrences should be  2");
		test(bananaRoot.numOfOccurrences("b".toCharArray(), 0) == 1, "The num of ocurrences should be  1");
		test(bananaRoot.numOfOccurrences("ban".toCharArray(), 0) == 1, "The num of ocurrences should be  1");
		test(bananaRoot.numOfOccurrences("ana".toCharArray(), 0) == 2, "The num of ocurrences should be  2");
		test(bananaRoot.numOfOccurrences("nana".toCharArray(), 0) == 1, "The num of ocurrences should be  1");
		test(mississipiRoot.numOfOccurrences("mis".toCharArray(), 0) == 1,"The num of ocurrences should be  1");//27
		test(mississipiRoot.numOfOccurrences("ipi".toCharArray(), 0) == 0,"The num of ocurrences should be  0");//28
		test(mississipiRoot.numOfOccurrences("pi".toCharArray(), 0) == 1,"The num of ocurrences should be  1");//29
		test(mississipiRoot.numOfOccurrences("ip".toCharArray(), 0) == 1,"The num of ocurrences should be  1");//30
		test(mississipiRoot.numOfOccurrences("sim".toCharArray(), 0) == 0,"The num of ocurrences should be  0");//32
		test(mississipiRoot.numOfOccurrences("ippi".toCharArray(), 0) == 1,"The num of ocurrences should be  1");//33
		test(mississipiRoot.numOfOccurrences("s".toCharArray(), 0) == 4,"The num of ocurrences should be  4");//34
		test(mississipiRoot.numOfOccurrences("i".toCharArray(), 0) == 4,"The num of ocurrences should be  4");//35
		test(mississipiRoot.numOfOccurrences("m".toCharArray(), 0) == 1,"The num of ocurrences should be  1");//36
		test(mississipiRoot.numOfOccurrences("iss".toCharArray(), 0) == 2,"The num of ocurrences should be  2");//37

		//findSuffixLeaf
		test(mississipiRoot.findSuffixLeaf("ssissip".toCharArray(), 0)!=null,"The leaf should be in the tree");//40
		test(mississipiRoot.findSuffixLeaf("ssissip".toCharArray(), 2)!=null,"The leaf should be in the tree");//41
		test(mississipiRoot.findSuffixLeaf("missis".toCharArray(), 0)!=null,"The leaf should be in the tree");//42
		
		//compress
		SuffixTreeNode letter = new SuffixTreeNodeImpl();
		char[] word3 = "a$".toCharArray();
		letter.addSuffix(word3, 0);
		letter.addSuffix(word3, 1);
		
	}
	
	private static void testSuffixTree() {
		SuffixTree mississippiTree = new SuffixTreeImpl("mississippi");
		SuffixTree bananaTree = new SuffixTreeImpl("banana");
		
		//contain
		test(bananaTree.contains("m")==false,"The subword should not be in the tree");
		test(bananaTree.contains("x")==false,"The subword should not be in the tree");
		test(bananaTree.contains("z")==false,"The subword should not be in the tree");
		test(bananaTree.contains("bana")==true,"The subword should be in the tree");
		test(bananaTree.contains("a")==true,"The subword should be in the tree");
		test(bananaTree.contains("na")==true,"The subword should be in the tree");
		test(mississippiTree.contains("m")==true,"The subword should be in the tree");
		test(mississippiTree.contains("ssi")==true,"The subword should be in the tree");
		test(mississippiTree.contains("mis")==true,"The subword should be in the tree");
		test(mississippiTree.contains("x")==false,"The subword should not be in the tree");
		test(mississippiTree.contains("az")==false,"The subword should not be in the tree");
		test(mississippiTree.contains("ik")==false,"The subword should not be in the tree");

		
	}
	private static void testReverseSuffixTree(){
		testPalindrome("mississippi", "issXssi");
		testPalindrome("abc", "X");
		testPalindrome("abbc", "bb");
		testPalindrome("aa", "aa");
		testPalindrome("aba", "aXa");
		testPalindrome("aabbccddeee", "eXe");
		testPalindrome("a", "X");
//		testPalindrome("tomdzdmot", "tomdXdmot"); // givin null pointer
		testPalindrome("ab", "X");
	}
	private static void testPalindrome(String word, String expected){
		test(new ReverseSuffixTreeImpl(word).longestPalindrome().equals(expected), "Longest palindrome should be " + expected);
	}

}