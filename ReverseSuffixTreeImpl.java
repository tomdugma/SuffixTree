
public class ReverseSuffixTreeImpl extends ReverseSuffixTree {

	public ReverseSuffixTreeImpl(String word) {
		super(word);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String longestPalindrome() {
		String currentWord = new String(this.word); 
		String currMaxRisha = "";
		String ans = "X";
		if(currentWord.length() == 2) {
			return "X";
		}
		if(currentWord.length() == 3){
			if(currentWord.charAt(0) == currentWord.charAt(1))              //abbc
				return currentWord.substring(0, 2);
			else
				return "X";
		}
		String evenLCP = "";
		String oddLCP = "";
		for(int i = 1; i<currentWord.length()-1;i++) {
			evenLCP = LCP(reverse(currentWord.substring(0, i)), currentWord.substring(i));
			if(i==1) {
				oddLCP = evenLCP;
			}
			else {
				oddLCP = LCP(reverse(currentWord.substring(0, i-1)), currentWord.substring(i));
			}
			if(oddLCP.length() >= evenLCP.length() && oddLCP.length() >= currMaxRisha.length()) {
				if(oddLCP == "X")
					continue;
				else {
					ans = reverse(oddLCP) + "X" + oddLCP;
					currMaxRisha = oddLCP;
				}
				
			}else if(evenLCP.length() > oddLCP.length() && evenLCP.length() > currMaxRisha.length() && evenLCP != "X"){
				if(evenLCP == "X")
					continue;
				else {
					ans = reverse(evenLCP)  + evenLCP;
					currMaxRisha = evenLCP;
				}
			}
		}
		if(ans.length()==0) // if we didn't find any palindrom, return "X"
			return "X";
		return ans;
		
	}
	public String reverse(String a) {
		int j = a.length();
		char[] newWord = new char[j];
		for(int i = 0; i < a.length();i++) {
			newWord[--j] = a.charAt(i);
		}
		return new String(newWord);
	}
	
	public String LCP(String Ar, String B) {
		SuffixTreeNode leafAr = this.root.findSuffixLeaf(Ar.toCharArray(), 0);
		SuffixTreeNode leafB = this.root.findSuffixLeaf(B.toCharArray(), 0);

		SuffixTreeNode commonParent = leafAr.findLCA(leafB);
		if(commonParent.chars == null)
			return "";
		return commonParent.restoreStringAlongPath();
	}

}
