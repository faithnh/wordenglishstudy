package faithnh.study.wordenglishstudy.testaction;

import java.io.Serializable;

public class WordDictionary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String word;
	private String transrate;
	private boolean isUsed;
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getTransrate() {
		return transrate;
	}
	public void setTransrate(String transrate) {
		this.transrate = transrate;
	}
	public boolean isUsed() {
		return isUsed;
	}
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
}
