package faithnh.study.wordenglishstudy;


import java.io.File;
import java.util.ArrayList;

import faithnh.study.wordenglishstudy.common.Constances;
import faithnh.study.wordenglishstudy.common.ErrorMessages;
import faithnh.study.wordenglishstudy.common.ListManu;
import faithnh.study.wordenglishstudy.common.ListManuAdapter;
import faithnh.study.wordenglishstudy.testaction.WordDictionary;
import faithnh.study.wordenglishstudy.testaction.WordDictionaryManager;
import faithnh.study.wordenglishstudy.util.OriginalMessage;
import faithnh.study.wordenglishstudy.util.SequenceGenerator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.os.Process;

public class WordenglishstudyActivity extends Activity {
    
	static final int DIALOG_END_YES_NO = 0;
    
	ListView listView;
	private ArrayList list = null;
	private ListManuAdapter adapter = null; 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        File newdir = new File(Constances.WORD_DICTIONARY_DIRECTORY);
        newdir.mkdir();
        WordDictionaryManager.initialWordDictionary(Constances.SDCARD_WORD_DICTIONARY);
        listView = (ListView)findViewById(R.id.listView1);
        createListManu();
        adapter = new ListManuAdapter(this, R.layout.listmanu_layout, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
            public void onItemClick(AdapterView<?> parent, View view,
            		int position, long id) {
            	ListView listView = (ListView) parent;
            	// クリックされたアイテムを取得します 
            	ListManu item = (ListManu) listView.getItemAtPosition(position);
            	if(item.getTitle().equals(getString(R.string.test_title))){
            		onClickTestButton();
            	}else if(item.getTitle().equals(getString(R.string.register_word_title))){
            		onClickRegisterWordButton();
            	}else if(item.getTitle().equals(getString(R.string.deleteword_title))){
            		onClickDeleteWordListButton();
            	}else if(item.getTitle().equals(getString(R.string.editwordlist_title))){
            		onClickEditWordListButton();
            	}
        	}
        });

    }
    // リストメニューの作成
    private void createListManu() {   
	    this.list = new ArrayList();   
	    ListManu test = new ListManu();   
	    test.setTitle(getString(R.string.test_title));  
	    test.setDescription(getString(R.string.test_description));   
	    list.add(test);
	    ListManu register = new ListManu();   
	    register.setTitle(getString(R.string.register_word_title));  
	    register.setDescription(getString(R.string.register_word_description));   
	    list.add(register);
	    ListManu deletewordlist = new ListManu();   
	    deletewordlist.setTitle(getString(R.string.deleteword_title));  
	    deletewordlist.setDescription(getString(R.string.deleteword_description));   
	    list.add(deletewordlist);
	    ListManu editWordList = new ListManu();   
	    editWordList.setTitle(getString(R.string.editwordlist_title));  
	    editWordList.setDescription(getString(R.string.editwordlist_description));   
	    list.add(editWordList);
    }
    //テストボタンを押したときの処理
    public void onClickTestButton() {
        try {
        	
			ArrayList<WordDictionary> wordlist =
					WordDictionaryManager.getWordDictionary(
								Constances.SDCARD_WORD_DICTIONARY);
            if(wordlist.size() > 0){
            	int problemCounter = 1;
            	ArrayList<Integer> problemSequence =
            			SequenceGenerator.createRandomSequence(wordlist.size());
            	Intent intent = new Intent(
                		WordenglishstudyActivity.this,
                		WordTestActivity.class );
                
                intent.setAction(Intent.ACTION_VIEW);
				intent.putExtra(Constances.WORD_DICTIONARY, wordlist);
				intent.putExtra(Constances.PROBLEM_COUNTER, problemCounter);
				intent.putExtra(Constances.PROBLEM_SEQUENCE, problemSequence);
				startActivity( intent );
            }else{
                OriginalMessage.AlertMessage(WordenglishstudyActivity.this,
                		ErrorMessages.NOWORDS);
            }
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        
    }
    //単語登録ボタンを押したときの処理
    public void onClickRegisterWordButton() {
        try {
			Intent intent = new Intent(
            		WordenglishstudyActivity.this,
            		WordRegisterActivity.class );
            intent.setAction(Intent.ACTION_VIEW);
			startActivity( intent );
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }
    //単語帳削除についての処理
    public void onClickDeleteWordListButton() {
        try {
        	AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        	dlg.setIcon(R.drawable.ic_launcher);
        	dlg.setTitle(R.string.warning);
        	dlg.setMessage(R.string.delete_wordlistmes);
        	dlg.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
        	    
        	public void onClick(DialogInterface dialog, int whichButton) { 
        		WordDictionaryManager.deleteWordDictionary(Constances.SDCARD_WORD_DICTIONARY);
        	} 
        	});
        	dlg.setNegativeButton(R.string.no, null);
        	dlg.show();
        	
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }
    //単語登録ボタンを押したときの処理
    public void onClickEditWordListButton() {
        try {
			Intent intent = new Intent(
            		WordenglishstudyActivity.this,
            		EditWordListActivity.class );
            intent.setAction(Intent.ACTION_VIEW);
			startActivity( intent );
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	switch (event.getAction()) {
    		case KeyEvent.ACTION_DOWN:
    			switch (event.getKeyCode()) {
    				case KeyEvent.KEYCODE_BACK:
    					showDialog(DIALOG_END_YES_NO);
    				return true;
    			}
    	}
    	return super.dispatchKeyEvent(event);
    }
    //アプリ終了確認ダイアログ
    @Override
    protected Dialog onCreateDialog(int id){
    	switch(id){
    		case DIALOG_END_YES_NO:
    		return new AlertDialog.Builder(this)
			    		.setTitle(R.string.end_confirm_title)
			    		.setMessage(R.string.end_confirm_message)
			    		.setPositiveButton(R.string.yes,
			    				new DialogInterface.OnClickListener(){
			    			public void onClick(DialogInterface dialog, int whichButton){
			    				finish();
			    			}
			    		})
			    		.setNegativeButton(R.string.no,
			    				new DialogInterface.OnClickListener(){
			    			public void onClick(DialogInterface dialog, int whichButton){    				
			    			}
			    		})
			    		.create();

    	}
		return null;
    }
}



