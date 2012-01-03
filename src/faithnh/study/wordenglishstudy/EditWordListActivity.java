package faithnh.study.wordenglishstudy;

import java.io.File;
import java.util.ArrayList;

import faithnh.study.wordenglishstudy.common.Constances;

import faithnh.study.wordenglishstudy.edit.EditWordListManager;
import faithnh.study.wordenglishstudy.edit.WordDictionaryAdapter;
import faithnh.study.wordenglishstudy.testaction.WordDictionary;
import faithnh.study.wordenglishstudy.testaction.WordDictionaryManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class EditWordListActivity extends Activity{
	ListView listView;
	private ArrayList list = null;
	private WordDictionaryAdapter adapter = null;
	int itemPosition;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView)findViewById(R.id.listView1);
        createWordList();
        adapter = new WordDictionaryAdapter(this, R.layout.listmanu_layout, list);
        listView.setAdapter(adapter);
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
            public void onItemClick(AdapterView<?> parent, View view,
            		int position, long id) {
            	ListView listView = (ListView) parent;
            	deleteWord(position);
        	}
        });*/
        
        // コンテキストメニューのためにリストビューを登録  
        registerForContextMenu(listView);  
    }
 
	@Override 
	public void onCreateContextMenu(  
		ContextMenu menu,  
		View v,  
		ContextMenuInfo menuInfo) {
    	AdapterContextMenuInfo adapterInfo = (AdapterContextMenuInfo) menuInfo;
    	
    	itemPosition = adapterInfo.position;

		// コンテキストメニューを作る  
		menu.setHeaderTitle(R.string.editword_menu);
		menu.add(R.string.view_transrate);
		menu.add(R.string.delete_word);
		
		super.onCreateContextMenu(menu, v, menuInfo);  
	}
	@Override 
	public boolean onContextItemSelected(MenuItem item) {  
	      
		// 選択されたヤツをテキストビューに設定する
		if(item.getTitle().equals(getString(R.string.view_transrate))){
			viewWord(itemPosition);
		}else if(item.getTitle().equals(getString(R.string.delete_word))){
			deleteWord(itemPosition);
		}
		return super.onContextItemSelected(item);
	}
	
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	switch (event.getAction()) {
    		case KeyEvent.ACTION_DOWN:
    			switch (event.getKeyCode()) {
    				case KeyEvent.KEYCODE_BACK:
    					Intent intent = new Intent(
    			    			this,
    			        		WordenglishstudyActivity.class );
    			    	
    			        intent.setAction(Intent.ACTION_VIEW);
    					startActivity( intent );
    				return true;
    			}
    	}
    	return super.dispatchKeyEvent(event);
    }
	public void createWordList(){
		try {
			list = WordDictionaryManager.getWordDictionary(Constances.SDCARD_WORD_DICTIONARY);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    	
    }
    public void deleteWord(final int position){
    	final WordDictionary item = (WordDictionary) listView.getItemAtPosition(position);
    	AlertDialog.Builder dlg = new AlertDialog.Builder(this);
    	dlg.setIcon(R.drawable.ic_launcher);
    	dlg.setTitle(R.string.warning);
    	dlg.setMessage(getString(R.string.delete_wordmes)+"\n"+item.getWord());
    	dlg.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {   
	    	public void onClick(DialogInterface dialog, int whichButton) {
	    		try {
		    		ArrayList<WordDictionary> wordlist;
		    		adapter.remove(item);
		    		wordlist = adapter.getArrayList();
					WordDictionaryManager.saveWordDictionary(Constances.SDCARD_WORD_DICTIONARY, wordlist);
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
	    		
	    	} 
    	});
    	dlg.setNegativeButton(R.string.no, null);
    	dlg.show();
    }
    public void viewWord(final int position){
    	final WordDictionary item = (WordDictionary) listView.getItemAtPosition(position);
    	String transrate = item.getTransrate();
    	transrate = transrate.replaceAll("<br.+?>", "\n");
    	AlertDialog.Builder dlg = new AlertDialog.Builder(this);
    	dlg.setIcon(R.drawable.ic_launcher);
    	dlg.setTitle(R.string.wordinfo);
    	dlg.setMessage(transrate);
    	dlg.show();
    }
}
