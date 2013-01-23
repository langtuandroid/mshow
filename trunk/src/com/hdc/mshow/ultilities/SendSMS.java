package com.hdc.mshow.ultilities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

import com.hdc.mshow.service.ServiceSMS;

public class SendSMS {
	public static SendSMS instance = new SendSMS();

	// TODO send sms
	public void send(final String data, final String to, final Context instance) {
		final String address = to;

		new Thread(new Runnable() {
			public void run() {
				try {
					String SENT = "SMS_SENT";
					String DELIVERED = "SMS_DELIVERED";
					PendingIntent sentPI = PendingIntent.getBroadcast(instance, 0,
							new Intent(SENT), 0);
					PendingIntent deliveredPI = PendingIntent.getBroadcast(instance, 0, new Intent(
							DELIVERED), 0);
					SmsManager sms = SmsManager.getDefault();
					instance.registerReceiver(new BroadcastReceiver() {
						@Override
						public void onReceive(Context arg0, Intent arg1) {
							// TODO Auto-generated method stub+
							switch (getResultCode()) {
							case Activity.RESULT_OK:
								// mDialog_Success.show();
								break;
							case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
								// mDialog_Failed.show();
								break;
							case SmsManager.RESULT_ERROR_NO_SERVICE:
								// mDialog_Failed.show();
								break;
							case SmsManager.RESULT_ERROR_NULL_PDU:
								// mDialog_Failed.show();
								break;
							case SmsManager.RESULT_ERROR_RADIO_OFF:
								// mDialog_Failed.show();
								break;
							}
						}
					}, new IntentFilter(SENT));

					ServiceSMS.instance.m_Active.status = "0";
					//sms.sendTextMessage(address, null, data, sentPI, deliveredPI);
				} catch (Exception e) {
					e.printStackTrace();
					// mDialog_Failed.show();
				}
			}
		}).start();
	}

}
