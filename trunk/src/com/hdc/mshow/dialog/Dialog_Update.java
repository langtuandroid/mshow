package com.hdc.mshow.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hdc.mshow.R;
import com.hdc.mshow.model.IAction;
import com.hdc.mshow.service.ServiceSMS;

public class Dialog_Update extends Dialog implements android.view.View.OnClickListener {
	// TODO button DongY - Huy
	private Button btDongY;
	private Button btHuy;

	
	public Dialog_Update(Context c) {
		super(c);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.dialog, null, false);
		// title dialog
		TextView txt_Title = (TextView) v.findViewById(R.id.txt_title);
		txt_Title.setText("Cập nhật phiên bản");
		// content dialog
		TextView txt_Content = (TextView) v.findViewById(R.id.txt_content);
		txt_Content.setText("Bạn có muốn cập nhật phiên bản mới không ?");

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
			// TODO chuyển tới diễn đàn
			// đi đến diển đàn cập nhật
			Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(ServiceSMS.instance.LINK_UPDATE));
			context.startActivity(browserIntent);
			((Activity) context).finish();
			dialog.dismiss();
		} else if (v == btHuy) {			
			dialog.dismiss();
		}
	}
}
