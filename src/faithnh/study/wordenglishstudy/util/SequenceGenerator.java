package faithnh.study.wordenglishstudy.util;

import java.util.ArrayList;
import java.util.Random;

public class SequenceGenerator {
	public static ArrayList<Integer> createRandomSequence(int size){
		ArrayList<Integer> sequence;
		ArrayList<Integer> result;
		int cnt;
		int addIdx;
		sequence = new ArrayList<Integer>();
		result = new ArrayList<Integer>();
		
		for(cnt = 0; cnt < size; cnt++){
			sequence.add(cnt);
		}
		
		while(!sequence.isEmpty()){
			Random rand = new Random();
			if(sequence.size()-1 > 0){
				addIdx = rand.nextInt(sequence.size()-1);
			}else{
				addIdx = 0;
			}
			result.add(sequence.get(addIdx));
			sequence.remove(addIdx);
		}
		
		return result;
	}
}
