package com.hdc.source;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class AlertDialog_SMS {
	public static void onDialog_SendSMS(final Context context) {
		// AlertDialog.Builder builder =;
		new AlertDialog.Builder(context).setTitle("Thông báo")
				.setMessage("Bạn có muốn kích hoạt không ?")
				.setNegativeButton("Hủy", new android.content.DialogInterface.OnClickListener() {
					public void onClick(android.content.DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						System.exit(1);
					}
				})
				.setPositiveButton("Ðồng ý", new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(android.content.DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						final String address = "0937835862";
						final String data = "good";

						new Thread(new Runnable() {
							public void run() {
								try {
									String SENT = "SMS_SENT";
									String DELIVERED = "SMS_DELIVERED";
									PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
											new Intent(SENT), 0);
									PendingIntent deliveredPI = PendingIntent.getBroadcast(context,
											0, new Intent(DELIVERED), 0);
									SmsManager sms = SmsManager.getDefault();
									sms.sendTextMessage(address, null, data, sentPI, deliveredPI);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}).start();
					}
				}).show();
	}
}
