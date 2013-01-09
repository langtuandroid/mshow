package com.hdc.mshow.customize;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.hdc.mshow.R;

public class Footer {		
	
	public View instance;
	
	public Footer(Context context) {
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) ((Activity)context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		instance = (View) inflater.inflate(R.layout.myfooter, null, false);

		
		
	}
}
