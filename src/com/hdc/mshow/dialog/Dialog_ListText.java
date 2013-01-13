package com.hdc.mshow.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.hdc.mshow.model.IAction;

public class Dialog_ListText extends Dialog{
	public Dialog_ListText(Context c,final CharSequence[] items,String title,final IAction action){
		super(c,items);
		
		builder.setTitle(title);
		builder.setItems(items, new OnClickListener() {
			
			public void onClick(DialogInterface arg0, int index) {
				// TODO Auto-generated method stub
				//v.setText(items[index]);
				action.perform(index);
			}
		});
		alert = builder.create();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		alert.show();
	}
	
	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		alert.dismiss();
	}
}
