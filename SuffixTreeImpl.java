
public class SuffixTreeImpl extends SuffixTree{

	public SuffixTreeImpl(String word) {
		super(word);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean contains(String subword) {
		return this.root.numOfOccurrences(subword.toCharArray(), 0)>0;
	}


	@Override
	public int numOfOccurrences(String subword) {
		return this.root.numOfOccurrences(subword.toCharArray(), 0);
	}



}


