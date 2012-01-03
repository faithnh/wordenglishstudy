package faithnh.study.wordenglishstudy.testaction;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

import faithnh.study.wordenglishstudy.common.Constances;

public class WordDictionaryManager {
	/**
	 * Get WordDictionary in dictionary file.
	 * @param file name
	 * @return WordDictionary class
	 */

	public static ArrayList<WordDictionary> getWordDictionary(String file)
			throws Exception{
		FileInputStream fin = null;
		BufferedReader bfin = null;
		ArrayList<WordDictionary> result = new ArrayList<WordDictionary>();
		WordDictionary tmp;
		String line;
		String[] data;
		try{
			// input dictionary file
			fin = new FileInputStream(file);
			bfin = new BufferedReader(new InputStreamReader(fin));
			
			while((line = bfin.readLine()) != null){
				
				data = line.split("###");
				if(data.length == Constances.WORD_DATA_SIZE){
					tmp = new WordDictionary();
					tmp.setWord(data[0]);
					tmp.setTransrate(data[1]);
					tmp.setUsed(false);
					result.add(tmp);
				}
			}
			
			return result;
		}finally{
			if(bfin != null){
				bfin.close();
			}
			if(fin != null){
				fin.close();
			}
		}
	}
	
	public static void saveWordDictionary(String file, ArrayList<WordDictionary> word)
			throws Exception{
		FileOutputStream fout = null;
		BufferedWriter bfout = null;
		int idx;
		try{
			fout = new FileOutputStream(file);
			bfout = new BufferedWriter(new OutputStreamWriter(fout));
			for(idx = 0; idx < word.size(); idx++){
				bfout.write(word.get(idx).getWord() + "###" +
						word.get(idx).getTransrate() + "\n");
			}
		}finally{
			if(bfout != null){
				bfout.close();
			}
			if(fout != null){
				fout.close();
			}
			
		}
	}
	
	public static void saveWordDictionary(String file)
			throws Exception{
		FileWriter fout = null;
		try{
			fout = new FileWriter(file);
		}finally{
			if(fout != null){
				fout.close();
			}
		}
	}
	
	public static void initialWordDictionary(String file){
		try{
			File test = new File(file);
			if(!test.exists()){
				saveWordDictionary(file);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public static void deleteWordDictionary(String file){
		try{
			saveWordDictionary(file);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

