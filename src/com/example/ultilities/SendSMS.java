package com.example.ultilities;

import com.example.activity.ListCatActivity;
import com.example.adapter.Custom_Dialog;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SendSMS {
	// send sms
	public static void send(final String data, final String to,
			final Context instance) {
		final String address = to.trim();

		new Thread(new Runnable() {
			public void run() {
				try {
					String SENT = "SMS_SENT";
					String DELIVERED = "SMS_DELIVERED";
					PendingIntent sentPI = PendingIntent.getBroadcast(instance,
							0, new Intent(SENT), 0);
					PendingIntent deliveredPI = PendingIntent.getBroadcast(
							instance, 0, new Intent(DELIVERED), 0);

					
					// Log.e("SkyGardenGame", data + " --->>> " + address);
					instance.registerReceiver(new BroadcastReceiver() {
						@Override
						public void onReceive(Context arg0, Intent arg1) {
							// TODO Auto-generated method stub+
							switch (getResultCode()) {
							case Activity.RESULT_OK:
								Toast.makeText(instance, "Hệ thống đang kiểm tra kích hoạt",
										Toast.LENGTH_SHORT).show();
								ConnectServer.instance.isFirstTime = false;
						
								break;
							case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
								Toast.makeText(instance, "Generic failure",
										Toast.LENGTH_SHORT).show();
								break;
							case SmsManager.RESULT_ERROR_NO_SERVICE:
								Toast.makeText(instance, "No service",
										Toast.LENGTH_SHORT).show();
								break;
							case SmsManager.RESULT_ERROR_NULL_PDU:
								Toast.makeText(instance, "Null PDU",
										Toast.LENGTH_SHORT).show();
								break;
							case SmsManager.RESULT_ERROR_RADIO_OFF:
								Toast.makeText(instance, "Radio off",
										Toast.LENGTH_SHORT).show();
								break;
							}
						}
					}, new IntentFilter(SENT));

					instance.registerReceiver(new BroadcastReceiver() {

						@Override
						public void onReceive(Context context, Intent intent) {
							// TODO Auto-generated method stub
							switch (getResultCode()) {
							case Activity.RESULT_OK:
								Toast.makeText(instance, "Hệ thống đã kiểm tra xong",
										Toast.LENGTH_SHORT).show();
								// ConnectServer.instance.hasDelivered=true;
//								ConnectServer.instance.countLuot += 1;
//								Log.e("countLuot",
//										ConnectServer.instance.countLuot + "");
								break;
							case Activity.RESULT_CANCELED:
								Toast.makeText(instance, "SMS not delivered",
										Toast.LENGTH_SHORT).show();
								break;
							}
						}
					}, new IntentFilter(DELIVERED));
					
					SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(address, null, data, sentPI,
							deliveredPI);
					// Custom_Dialog.cusinstance.count=1;
				} catch (Exception e) {
					e.printStackTrace();
					// mDialog_Failed.show();
				}
			}
		}).start();
	}

}
