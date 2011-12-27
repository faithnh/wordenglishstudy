package faithnh.study.wordenglishstudy;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WordenglishstudyActivity extends Activity {
    /** Called when the activity is first created. */
	Button testButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 /*       OnClickListener mBackListener = new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( WordenglishstudyActivity.this, WordTestActivity.class );
                intent.setAction(Intent.ACTION_VIEW);
                startActivity( intent );
            }
        };
        testButton = (Button) findViewById(R.id.testButton);
        testButton.setOnClickListener(mBackListener);*/
    }

}



