package com.example.adapter;

import com.example.activity.BlankActivity;
import com.example.activity.ListCatActivity;
import com.example.activity.ListDetailActivity;
import com.example.activity.R;
import com.example.entity.Category;
import com.example.ultilities.ConnectServer;
import com.example.ultilities.FileManager;
import com.example.ultilities.SendSMS;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Custom_Dialog {
	public static Custom_Dialog cusinstance = new Custom_Dialog();
	public static Integer count;

	Context context;
	Category category;
	public static void showDialog_ActivationSMS(final Category category,
			final Context context) {

		final String isFirstTime="isFirstTime";
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.activationdialog, null);
		
//		boolean isS=isSuccess;
		
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(v);
		Button btDongY = (Button) dialog.findViewById(R.id.activation_btOk);
		btDongY.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(instance, "Đồng ý", Toast.LENGTH_LONG).show();

				 

				SendSMS.send(ConnectServer.instance.m_Sms.mo + " "
						+ ConnectServer.instance.m_Active.getMsg(),
						ConnectServer.instance.m_Sms.serviceCode, context);
				// chuyển qua activity DetailActivity
				// nếu nhắn tin thành công
				FileManager.saveFTime(context, isFirstTime, ListCatActivity.instance.ftime);
//						ConnectServer.instance.getActive();				
					dialog.dismiss();
				ListCatActivity.isShow=true;	
					
			}
		});
		Button btHuy = (Button) dialog.findViewById(R.id.activation_btCancel);
		btHuy.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ConnectServer.instance.isFirstTime = true;

				dialog.dismiss();
			}
		});
		dialog.show();
	}

	public static void transferActivity(Category category, Context context) {
		Intent mIntent = new Intent(context, ListDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("category", category);
		mIntent.putExtras(bundle);
		context.startActivity(mIntent);
	}
	
	public static void transferBlank(Context context) {
		Intent intent = new Intent(context, BlankActivity.class);
		context.startActivity(intent);
		
	}
	
}
