package com.hdc.mshow.customize;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hdc.mshow.R;

public class Footer {		
	
	public View instance;
	
	//TODO Layout paging
	LinearLayout layout_paging;
	
	public Footer(Context context) {
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) ((Activity)context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		instance = (View) inflater.inflate(R.layout.myfooter, null, false);
		
		layout_paging = (LinearLayout)instance.findViewById(R.id.layout_paging);		
	}
	
	public void setVisible_Paging(int visible){
		layout_paging.setVisibility(visible);
	}
	
}
