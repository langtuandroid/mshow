package com.hdc.mshow.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;

public abstract class Dialog extends android.app.Dialog {
	// TODO dialog
	Dialog dialog;
	// TODO Context
	Context context;
	
	//TODO Alert dialog
	AlertDialog alert;
	//TODO Alert builder
	AlertDialog.Builder builder;
	
	public Dialog(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
		dialog = this;
		context = c;
	}

	public Dialog(Context c, int theme) {
		super(c, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		dialog = this;
		context = c;
	}

	public Dialog(Context c, TextView v, CharSequence[] items) {
		super(c);		
		context = c;
		builder = new AlertDialog.Builder(context);		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
	}

}
