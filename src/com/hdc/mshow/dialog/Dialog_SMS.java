package com.hdc.mshow.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.hdc.mshow.R;
import com.hdc.mshow.customize.Toast;
import com.hdc.mshow.model.Sms;
import com.hdc.mshow.service.ServiceSMS;
import com.hdc.mshow.ultilities.FileManager;
import com.hdc.mshow.ultilities.SendSMS;

public class Dialog_SMS extends Dialog implements android.view.View.OnClickListener {

	// TODO button DongY - Huy
	private Button btDongY;
	private Button btHuy;
	
	public Dialog_SMS(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.dialog, null, false);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(v);

		btDongY = (Button) v.findViewById(R.id.btdongy);
		btDongY.setOnClickListener(this);

		btHuy = (Button) v.findViewById(R.id.btHuy);
		btHuy.setOnClickListener(this);
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

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btDongY) {
			try {				
				Sms m_sms= ServiceSMS.instance.m_Sms;
				SendSMS.instance.send(m_sms.mo + " " +ServiceSMS.instance.m_Active.msg, m_sms.serviceCode, context);

				ServiceSMS.instance.isFirstTime = false;

				// TODO update view app
				ServiceSMS.instance.updateViewApp();
								
			} catch (Exception ex) {
				Toast.instance.showToast(context, ex);
			}
			dialog.dismiss();
		} else if (v == btHuy) {
			ServiceSMS.instance.isFirstTime = true;

			dialog.dismiss();
		}
	}
}
