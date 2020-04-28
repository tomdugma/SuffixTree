
public class CharLinkedListImpl extends CharLinkedList {

	@Override
	public void add(char c) {
		ICharLinkedListNode nodeToAdd = new CharLinkedListNodeImpl(c);
		if(this.first==null) {
			this.first = nodeToAdd;
			this.last = nodeToAdd;
		}else {
			this.last.setNext(nodeToAdd);
			this.last = nodeToAdd;
		}
	}

	public char firstChar() {
		if(this.first!=null) {
			return this.first.getChar();
		}
		return 0;

	}

	public int size() {
		int count = 0;
		ICharLinkedListNode link = this.first;
		while(link!=null) {
			count++;
			link = link.getNext();
		}		
		return count;
	}
	//--[__]--[__]--
	public void append(CharLinkedList toAppend) {
		if(this.first == null) {
			this.first = toAppend.first;
			this.last = toAppend.last;
		}
		else {
			this.last.setNext(toAppend.first);
			this.last = toAppend.last;
		}
	}


}
