import java.util.Iterator;

public class SuffixTreeNodeImpl extends SuffixTreeNode{

	public SuffixTreeNodeImpl() {
		super();
	}

	public SuffixTreeNodeImpl(CharLinkedList chars, SuffixTreeNode parent) {
		super(chars, parent);
	}
	/**
	 * finding a child according to a given char. 
	 * @param char
	 * @param return null if no child found.
	 */
	public SuffixTreeNode search(char c) {
		return binarySearch(c, 0, this.numOfChildren-1);
	}
	public SuffixTreeNode binarySearch(char target, int left, int right) {
		for(int i = 0;i < this.numOfChildren; i ++) {
			if(this.children[i].chars.firstChar() == target) {
				return this.children[i];
			}
		}
		return null;
	}
	
//	for(int i = 0;i < this.numOfChildren; i ++) {
//		if(this.children[i].chars.firstChar() == target) {
//			return this.children[i];
//		}
//	}
//	return null;
//	if(right >= left) {
//		int mid = (left + right) / 2;
//		char curr = this.children[mid].chars.firstChar();
//		if(this.children[mid].chars.firstChar() == target) {
//			return this.children[mid];
//		}
//		else if(curr > target || curr == '$' || curr == '#') {
//			return binarySearch(target, left, mid-1);
//		}
//		else {
//			return binarySearch(target, mid+1, right);
//		}
//	}
//	return null;
//	
	/**
	 * shift all childs in the children array one step to the right. 
	 * @param integer that indicate where to stop, from the end to until
	 * @param void, doesnt return anything
	 */
	public void shiftChildren(int until) {
		int len = this.numOfChildren; //maximum value for length of the children array
		while (until < len) {
			this.children[len] = this.children[len-1];
			len--;
		}
	}
	/**
	 * adding a child to current node
	 * @param suffixTreeNode
	 * @param void, doesnt return anything
	 */
	public void addChild(SuffixTreeNode node) {
		if(this.children[0] == null) {
			this.children[0] = node;
			this.numOfChildren++;
		}
		else if(node.chars.firstChar() == '$' || node.chars.firstChar() == '#') {
			this.children[this.numOfChildren] = node;
			this.numOfChildren++;
		}
		else {
			int i = 0;
			while(i<this.numOfChildren && node.chars.first.getChar() > this.children[i].chars.firstChar() ) {
				i++;
			}
			shiftChildren(i);
			this.children[i] = node;
			this.numOfChildren++;
		}

	}
	/**
	 * adding a suffix to current root
	 * @param char[] word, int from
	 * @param void, doesnt return anything
	 */
	public void addSuffix(char[] word, int from) {
		if(from == word.length) {
			return;
		}
		char tmp = word[from];
		SuffixTreeNode child = search(tmp);
		if(child==null) {
			child = new SuffixTreeNodeImpl(CharLinkedList.from(tmp), this);
			this.addChild(child);
		}
		this.descendantLeaves++;
		child.addSuffix(word, from+1);
	}
	
	/**
	 * compress a suffix tree, giving us a suffix trie
	 * @param void, doesnt return anything
	 */
	public void compress() {
		if(this.numOfChildren==0){ 			
			return ;
		}
		for(int i = 0; i < this.numOfChildren; i++) {
			for(int j = 0; j < this.numOfChildren; j++) {
				this.children[j].parent=this;
			}
			this.children[i].compress();
			if(this.numOfChildren==1) { 
				this.chars.append(this.children[0].chars);
				this.children[i].parent=this;
				this.setChildren(this.children[0].children, this.children[0].numOfChildren);
				this.totalWordLength = this.chars.size() + this.totalWordLength - 1;
			}
		}
	}
	
	/**
	 * calculates the number of occurrences a suffix appear in a suffix trie
	 * @param char[] word, int from
	 * @param returns an int, the number of occurrences.
	 */
	public int numOfOccurrences(char[] subword, int from) {

		if(from >= subword.length) // if we reached here, we know all chars in subword exist in the tree.
			return this.descendantLeaves;
		
		char curr = subword[from];
		SuffixTreeNode nextNode = search(curr); //returns the next child node
		if(nextNode==null) {
			return 0;
		}
		Iterator<Character> iter = nextNode.getChars().iterator();
		
		while(iter.hasNext() && from < subword.length) {
			char curr2check = iter.next();
			if(from == subword.length - 1 && curr2check == subword[from]) { // we are checking the last letter, so if its equal to curr2check, we`re done
				return nextNode.descendantLeaves;
			}
			if(curr2check == subword[from] && iter.hasNext()) {
				from++;
			}
			else if(curr2check == subword[from] && !(iter.hasNext())){
				return nextNode.numOfOccurrences(subword, from+1);
			}
			else {
				return 0;
			}
		}
		return nextNode.numOfOccurrences(subword, from+1);
	}
	
	/**
	 * finding the leaf matched to the word that given.
	 * @param char[] word, int from
	 * @param return SuffixTreeNode, that matched the suffix which has given
	 */
	public SuffixTreeNode findSuffixLeaf(char[] word, int from) {
		SuffixTreeNode nextNode = this.search(word[from]);
		if(nextNode.numOfChildren == 0){ //base case, means we reach a leaf node
			return nextNode;
		}
		Iterator<Character> iter = nextNode.getChars().iterator();

		while(iter.hasNext()){ //if curr node has more than one char
			from++;
			iter.next();
		}
		if(from == word.length) {
			return nextNode;
		}
		return nextNode.findSuffixLeaf(word, from);
	}
	/**
	 * finding the lowest common anceastor father for 2 nodes in the trie
	 * @param other node
	 * @param return the lowest common father
	 */
	public SuffixTreeNode findLCA(SuffixTreeNode node2) {
		SuffixTreeNode ans = null;
		SuffixTreeNode node1 = this;
		if(node2 == null) {
			return null;
		}
		while(true) {
			if(node1.totalWordLength > node2.totalWordLength) {
				node1 = node1.parent;
				continue;
			}
			if(node1.totalWordLength < node2.totalWordLength) {
				node2 = node2.parent;
			}else {
				if(node1==node2) {
					ans = node1;
					break;
				}else {
					node1=node1.parent;
				}	node2=node2.parent;
			}

		}//while
		return ans;
	}
}

