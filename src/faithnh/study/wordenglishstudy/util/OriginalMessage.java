package faithnh.study.wordenglishstudy.util;

import android.app.Activity;
import android.widget.Toast;
public class OriginalMessage {
	public static void AlertMessage(Activity activity, String message){
		Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
	}
	
}
