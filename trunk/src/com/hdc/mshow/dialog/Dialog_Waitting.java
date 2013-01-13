package com.hdc.mshow.dialog;

import android.content.Context;
import android.view.Window;

import com.hdc.mshow.R;
import com.hdc.mshow.customize.Toast;

public class Dialog_Waitting extends Dialog {

	public Dialog_Waitting(Context c, int theme) {
		// TODO Auto-generated constructor stub
		super(c, theme);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// if (theme == 0)
		// dialog.setContentView(R.layout.waitting_1);
		// else
		try{
			dialog.setContentView(R.layout.waitting_1);	
		}catch(Exception ex){
			Toast.instance.showToast(c, ex);
		}
		
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
