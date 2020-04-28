
public class CharLinkedListNodeImpl implements ICharLinkedListNode {
	
	private char data;
	private CharLinkedListNodeImpl next;
	
	public CharLinkedListNodeImpl(char data) {
		this.next = null;
		this.data = data;
		
	}
	public char getChar() {
		return this.data;
	}
	
	public CharLinkedListNodeImpl getNext() {
		return next;
	}
	
	public void setNext(ICharLinkedListNode next) {
		this.next = (CharLinkedListNodeImpl)next;
		
		

	}
	
	

}
