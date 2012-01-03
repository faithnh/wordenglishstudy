package faithnh.study.wordenglishstudy.edit;

import java.util.ArrayList;

import faithnh.study.wordenglishstudy.R;
import faithnh.study.wordenglishstudy.testaction.WordDictionary;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WordDictionaryAdapter extends ArrayAdapter {
	private ArrayList items;   
	private LayoutInflater inflater;   
	  
	public WordDictionaryAdapter(Context context, int textViewResourceId,   
				ArrayList items) {   
		super(context, textViewResourceId, items);   
		this.items = items;   
		this.inflater = (LayoutInflater) context   
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   
	}   
	  
	@Override  
	public View getView(int position, View convertView, ViewGroup parent) {   
		// �r���[���󂯎��   
		View view = convertView;   
		if (view == null) {   
			// �󂯎�����r���[��null�Ȃ�V�����r���[�𐶐�   
			view = inflater.inflate(R.layout.listmanu_layout, null);
		}   
		// �\�����ׂ��f�[�^�̎擾   
		WordDictionary item = (WordDictionary)items.get(position);   
		if (item != null) {   
			TextView listManuTitle = (TextView)view.findViewById(R.id.listManuTitle);   
			if (listManuTitle != null) {   
				listManuTitle.setText(item.getWord());
				listManuTitle.setTypeface(Typeface.DEFAULT_BOLD); 
			} 
			TextView listManuDescription = (TextView)view.findViewById(R.id.listManuDescription);   
			if (listManuDescription != null) {   
				listManuDescription.setText(item.getWord());   
			}   
	  
		}   
		return view;   
	}
	
	public ArrayList getArrayList(){
		return items;
	}

}

