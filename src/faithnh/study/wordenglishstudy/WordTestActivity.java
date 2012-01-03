package faithnh.study.wordenglishstudy;

import java.util.ArrayList;
import java.util.Random;

import faithnh.study.wordenglishstudy.common.Constances;
import faithnh.study.wordenglishstudy.testaction.WordDictionary;
import faithnh.study.wordenglishstudy.util.OriginalMessage;
import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WordTestActivity extends Activity implements OnGestureListener{

	private ArrayList<WordDictionary> wordlist;
	ArrayList<Integer> problemSequence;
	int problemCounter;
	TextView problemTitle;
	TextView word;
	WebView answer;
	TextView title;
	Button answerSwitch;

	
	GestureDetector gestureDetector;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // 画面の XML を指定する
	    setContentView(R.layout.sub);
	    Intent intent = getIntent();
	    wordlist = (ArrayList<WordDictionary>)intent.getSerializableExtra(
	    		Constances.WORD_DICTIONARY);
	    problemSequence = (ArrayList<Integer>)intent.getSerializableExtra(
				Constances.PROBLEM_SEQUENCE);
	    problemCounter = (Integer)intent.getSerializableExtra(
				Constances.PROBLEM_COUNTER);
 
	    final int randIdx = problemSequence.get(problemCounter-1);
	    
	    problemTitle = (TextView)findViewById(R.id.problemTitleTextView);
	    word = (TextView)findViewById(R.id.problemWordTextView);
	    
	    answer = (WebView)findViewById(R.id.viewAnswer);
	    answerSwitch = (Button)findViewById(R.id.button1);
	   
	    OnClickListener answerButtonClick = new OnClickListener() {
            public void onClick(View v) {
                try {
                	answer.loadDataWithBaseURL("about:blank",
                			wordlist.get(randIdx).getTransrate(),
                			"text/html", "UTF-8", null);
                	
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
                
            }
        };
        problemTitle.setText((String)getString(R.string.problem_title) 
        		+ " " + (Integer)intent.getSerializableExtra(
        				Constances.PROBLEM_COUNTER));
        word.setText(wordlist.get(randIdx).getWord());
        answerSwitch.setOnClickListener(answerButtonClick);
        gestureDetector = new GestureDetector(onFlingListener);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		super.dispatchTouchEvent(event);

		// 各発生するイベントをゼスチャークラスに設定しておく。
		// (GestureDetectorにViewのonTouchEventを委譲する。)
		gestureDetector.onTouchEvent(event);
		return true;
	}

	
	@Override
	public void onGesture(GestureOverlayView overlay, MotionEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	/**
	 * フリック時のイベントリスナー。
	 */
	private final SimpleOnGestureListener onFlingListener = new SimpleOnGestureListener() {
		
		/** フリック時のイベント */
		@Override
		public boolean onFling(MotionEvent eventS, MotionEvent eventE, float velocityX, float velocityY) {
			
			if (eventE.getX() > eventS.getX() && eventE.getX() - eventS.getX() > 100) {
				onPrevPage();
			} else if(eventS.getX() - eventE.getX() > 100){
				onNextPage();
			}
			return true;
		}
	};

	public void onNextPage(){
		Intent intent;
		if(problemCounter+1 > wordlist.size()){
	    	intent = new Intent(
	    			WordTestActivity.this,
	        		WordenglishstudyActivity.class );
	    	
	        intent.setAction(Intent.ACTION_VIEW);
			startActivity( intent );
			
		}else{
	    	intent = new Intent(
	    			WordTestActivity.this,
	        		WordTestActivity.class );
	        
	        intent.setAction(Intent.ACTION_VIEW);
			intent.putExtra(Constances.WORD_DICTIONARY, wordlist);
			intent.putExtra(Constances.PROBLEM_COUNTER, problemCounter+1);
			intent.putExtra(Constances.PROBLEM_SEQUENCE, problemSequence);
			startActivity( intent );
		}
	}
	
	public void onPrevPage(){
		Intent intent;
		if(problemCounter-1 <= 0){
	    	intent = new Intent(
	    			WordTestActivity.this,
	        		WordenglishstudyActivity.class );
	    	
	        intent.setAction(Intent.ACTION_VIEW);
			startActivity( intent );
			
		}else{
	    	intent = new Intent(
	    			WordTestActivity.this,
	        		WordTestActivity.class );
	        
	        intent.setAction(Intent.ACTION_VIEW);
			intent.putExtra(Constances.WORD_DICTIONARY, wordlist);
			intent.putExtra(Constances.PROBLEM_COUNTER, problemCounter-1);
			intent.putExtra(Constances.PROBLEM_SEQUENCE, problemSequence);
			startActivity( intent );
		}
	}
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	switch (event.getAction()) {
    		case KeyEvent.ACTION_DOWN:
    			switch (event.getKeyCode()) {
    				case KeyEvent.KEYCODE_BACK:
    					Intent intent = new Intent(
    			    			WordTestActivity.this,
    			        		WordenglishstudyActivity.class );
    			    	
    			        intent.setAction(Intent.ACTION_VIEW);
    					startActivity( intent );
    				return true;
    			}
    	}
    	return super.dispatchKeyEvent(event);
    }
}
