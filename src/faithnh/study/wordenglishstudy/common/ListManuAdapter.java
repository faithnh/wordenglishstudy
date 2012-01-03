package faithnh.study.wordenglishstudy.common;

import java.util.ArrayList;

import faithnh.study.wordenglishstudy.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListManuAdapter extends ArrayAdapter {
	private ArrayList items;   
	private LayoutInflater inflater;   
	  
	public ListManuAdapter(Context context, int textViewResourceId,   
				ArrayList items) {   
		super(context, textViewResourceId, items);   
		this.items = items;   
		this.inflater = (LayoutInflater) context   
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   
	}   
	  
	@Override  
	public View getView(int position, View convertView, ViewGroup parent) {   
		// ビューを受け取る   
		View view = convertView;   
		if (view == null) {   
			// 受け取ったビューがnullなら新しくビューを生成   
			view = inflater.inflate(R.layout.listmanu_layout, null);
		}   
		// 表示すべきデータの取得   
		ListManu item = (ListManu)items.get(position);   
		if (item != null) {   
			TextView listManuTitle = (TextView)view.findViewById(R.id.listManuTitle);   
			if (listManuTitle != null) {   
				listManuTitle.setText(item.getTitle());
				listManuTitle.setTypeface(Typeface.DEFAULT_BOLD); 
			} 
			TextView listManuDescription = (TextView)view.findViewById(R.id.listManuDescription);   
			if (listManuDescription != null) {   
				listManuDescription.setText(item.getDescription());   
			}   
	  
		}   
		return view;   
	}   

}
