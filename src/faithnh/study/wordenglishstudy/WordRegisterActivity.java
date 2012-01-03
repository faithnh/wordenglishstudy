package faithnh.study.wordenglishstudy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import faithnh.study.wordenglishstudy.addword.SearchWordInformation;
import faithnh.study.wordenglishstudy.common.Constances;
import faithnh.study.wordenglishstudy.common.InfoMessage;
import faithnh.study.wordenglishstudy.testaction.WordDictionary;
import faithnh.study.wordenglishstudy.testaction.WordDictionaryManager;
import faithnh.study.wordenglishstudy.util.HttpClient;
import faithnh.study.wordenglishstudy.util.OriginalMessage;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class WordRegisterActivity extends Activity {
	EditText inputWord;
	WebView searchResultView;
	Button searchWordButton;
	Button registerWordButton;
	String transrate;
	Button playPronunciationButton;
	ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // 画面の XML を指定する
	    setContentView(R.layout.register_word);
	    searchResultView = (WebView)findViewById(R.id.searchResult);
	    inputWord = (EditText)findViewById(R.id.inputWord);
	    searchWordButton = (Button)findViewById(R.id.searchWordButton);
	    registerWordButton = (Button)findViewById(R.id.registerWordButton);
	    playPronunciationButton = (Button)findViewById(R.id.playPronunciationButton);
	  
	    OnClickListener searchResultButtonListener = new OnClickListener(){
	    	public void onClick(View v){
	    		//1.ダイアログを表示
	            progressDialog = new ProgressDialog(WordRegisterActivity.this);
	            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	            progressDialog.setMessage(getString(R.string.search_word_mes));
	            progressDialog.setCancelable(true);
	            progressDialog.show();
	            
	            //onCreateDialog()を使う場合はprogressDialog.show()をやめてshowDialog(ID_PROGRESS_DIALOG)を使う
	            //showDialog(ID_PROGRESS_DIALOG); 

	            //2.実際に行いたい処理を別スレッドで実行
	            (new Thread(runnableSearchWord)).start();
	    		//searchClickButtonEvent(v);
	    	}
	    };
        
	    OnClickListener registerWordListener = new OnClickListener() {
            public void onClick(View v) {
            	registerButtonClickEvent(v);  
            }
        };
        
	    OnClickListener playPronunciationButtonListener = new OnClickListener(){
	    	public void onClick(View v){
	    		//1.ダイアログを表示
	            progressDialog = new ProgressDialog(WordRegisterActivity.this);
	            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	            progressDialog.setMessage(getString(R.string.search_pro_mes));
	            progressDialog.setCancelable(true);
	            progressDialog.show();
	            
	            //onCreateDialog()を使う場合はprogressDialog.show()をやめてshowDialog(ID_PROGRESS_DIALOG)を使う
	            //showDialog(ID_PROGRESS_DIALOG); 

	            //2.実際に行いたい処理を別スレッドで実行
	            (new Thread(runnablePronunciation)).start();

	    	}
	    };
        searchWordButton.setOnClickListener(searchResultButtonListener);
        registerWordButton.setOnClickListener(registerWordListener);
        playPronunciationButton.setOnClickListener(playPronunciationButtonListener);
        
		if(Intent.ACTION_SEND.equals(getIntent().getAction())){  
			Pattern pattern;
			Matcher matcher;
			String sendData = getIntent().getExtras().getCharSequence(Intent.EXTRA_TEXT).toString();
    		pattern = Pattern.compile("http:.+", Pattern.DOTALL);
    		matcher = pattern.matcher(sendData);
    		sendData = matcher.replaceAll("");
    		sendData = sendData.trim().replace("\n", "").toLowerCase();
			inputWord.setText(sendData);
			searchClickButtonEvent();
		}

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
	
    public void searchClickButtonEvent(View v) {
    	searchClickButtonEvent();
    }
    public String searchResult(){
        try {
        	String word = ((SpannableStringBuilder)inputWord.getText()).toString();
        	
        	transrate = SearchWordInformation.searchTransrate(word);
        	
        	//Revise the input word
        	word = SearchWordInformation.reviseInputWord(word);
        	
        	return word;
        } catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return "";
		}
    }
    public void searchClickButtonEvent(){
        try {
        	String word = ((SpannableStringBuilder)inputWord.getText()).toString();
        	
        	transrate = SearchWordInformation.searchTransrate(word);
        	
        	//Revise the input word
        	word = SearchWordInformation.reviseInputWord(word);
        	inputWord.setText(word);
        	
        	searchResultView.loadDataWithBaseURL("about:blank", transrate, "text/html", "UTF-8", null);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }
    public void registerButtonClickEvent(View v) {
        try {
			ArrayList<WordDictionary> wordlist =
					WordDictionaryManager.getWordDictionary(
								Constances.SDCARD_WORD_DICTIONARY);
			WordDictionary tmp = new WordDictionary();
			Intent intent;
			tmp.setWord(inputWord.getText().toString());
			tmp.setTransrate(transrate);
			wordlist.add(tmp);
			WordDictionaryManager.saveWordDictionary(
					Constances.SDCARD_WORD_DICTIONARY,
					wordlist);
            OriginalMessage.AlertMessage(WordRegisterActivity.this,
            		InfoMessage.REGISTERD_WORD);
			intent = new Intent(
					WordRegisterActivity.this,
            		WordenglishstudyActivity.class );
			intent.setAction(Intent.ACTION_VIEW);
			startActivity( intent );
			
		} catch (Exception e) {
	
			e.printStackTrace();
		}
        
    }

	public void playPronunciationButtonEvent(View v) {
		playPronunciationButtonEvent();
    }

	public void playPronunciationButtonEvent() {
    	try{
    		//TODO:仮実装（発音再生）
    		String word = inputWord.getText().toString();
    		word = SearchWordInformation.reviseInputWord(word);
    		if(!word.isEmpty()){
	    		MediaPlayer m_mediaPlayer = new MediaPlayer();
	    		String fileURI = SearchWordInformation.getPronunciationURI(word);
				Uri uri = Uri.parse(fileURI);
				
		    	m_mediaPlayer.setDataSource(this, uri);
		    	m_mediaPlayer.prepare();
		    	m_mediaPlayer.start();
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	
	private Runnable runnablePronunciation = new Runnable(){
        @Override
        public void run() {
        	playPronunciationButtonEvent();
            progressDialog.dismiss();
        }
    };
    private Runnable runnableSearchWord = new Runnable(){
        @Override
        public void run() {
        	try{
        	String word = ((SpannableStringBuilder)inputWord.getText()).toString();
        	
        	transrate = SearchWordInformation.searchTransrate(word);
        	
        	//Revise the input word
        	word = SearchWordInformation.reviseInputWord(word);
        	

            Message message = new Message();
            Bundle inputBundle = new Bundle();
            inputBundle.putString("inputWord", word);
            inputBundle.putString("transrate", transrate);
            message.setData(inputBundle);
            searchWordHandler.sendMessage(message);
            
        	}catch(Exception e){
        		e.printStackTrace();
        	}finally{
                progressDialog.dismiss();
        	}

        }
        private final Handler searchWordHandler = new Handler() {
        	@Override
            public void handleMessage(Message msg) {
                //sendMessage()で渡されたBundleを取得してUIに表示
                String word = msg.getData().get("inputWord").toString();
                String transrate = msg.getData().get("transrate").toString();
                searchResultView.loadDataWithBaseURL("about:blank", transrate, "text/html", "UTF-8", null);
                inputWord.setText(word);
            }
        };   
    };	
}
