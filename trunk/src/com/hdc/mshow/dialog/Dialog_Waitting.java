package com.hdc.mshow.dialog;

import android.content.Context;
import android.view.Window;

import com.hdc.mshow.R;

public class Dialog_Waitting extends Dialog {

	public Dialog_Waitting(Context c, int theme) {
		// TODO Auto-generated constructor stub
		super(c, theme);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// if (theme == 0)
		// dialog.setContentView(R.layout.waitting_1);
		// else
			dialog.setContentView(R.layout.waitting_1);
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
