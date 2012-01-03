package faithnh.study.wordenglishstudy.edit;

import java.util.ArrayList;

import faithnh.study.wordenglishstudy.testaction.WordDictionary;

public class EditWordListManager{
	public static void deleteWordList(ArrayList<WordDictionary> wordlist, int idx){
		wordlist.remove(idx);
	}
};
