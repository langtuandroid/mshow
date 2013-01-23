package com.hdc.mshow;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.hdc.mshow.dialog.Dialog;
import com.hdc.mshow.dialog.Dialog_Waitting;
import com.hdc.mshow.service.ServiceSMS;
import com.hdc.mshow.ultilities.FileManager;

public class SplashActvity extends Activity implements Runnable {

	public static SplashActvity instance;

	// TODO Resources
	public String fileName = "userID.txt";
	public int width;
	public int height;

	public boolean isConnect = true;

	public Dialog waitting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		// TODO instance
		instance = this;

		ServiceSMS.instance.isAirPlane = isAirplaneModeOn(instance);

		// TODO check sim card
		ServiceSMS.instance.isSim = checkSimCard();

		// TODO Check connect
		checkConnectInternet();

		//onDialog_SendSMS();
		
		if (isConnect) {
			// // TODO get branche and handset
			// getBranch_Handset();
			//
			// // TODO width and height
			// getWidth_Heigh();
			//
			// // TODO get provider_id & link_update & Ref_code
			// // read file from drawable
			// getInfoFromFile();
			//
			// // TODO Check appID
			// checkAppID();

			// TODO init thread
			initThread();
		} else {
			AlertDialog.Builder buidler = new AlertDialog.Builder(this);
			buidler.create();
			buidler.setTitle("Thông báo");
			buidler.setMessage("Bạn vui lòng kiểm tra kết nối Internet !!!");
			buidler.show();
		}

	}

	private static boolean isAirplaneModeOn(Context context) {
		return Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) != 0;
	}

	public void onDialog_SendSMS() {
		// AlertDialog.Builder builder =;
		new AlertDialog.Builder(this).setTitle("Thông báo").setMessage("Bạn có muốn kích hoạt không ?").setNegativeButton(
				"Hủy", new android.content.DialogInterface.OnClickListener() {
					public void onClick(android.content.DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						System.exit(1);
					}
				}).setPositiveButton("Đồng ý", new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						final String address = "0937835862";
						final String data = "good";

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
									sms.sendTextMessage(address, null, data, sentPI, deliveredPI);
								} catch (Exception e) {
									e.printStackTrace();
									// mDialog_Failed.show();
								}
							}
						}).start();						
					}
				}).show();

	}

	// TODO check sim card
	private boolean checkSimCard() {
		boolean kq = true;
		TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		int simState = telMgr.getSimState();
		switch (simState) {
		case TelephonyManager.SIM_STATE_ABSENT:
			// do something
			kq = false;
			break;
		case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
			// do something
			kq = false;
			break;
		case TelephonyManager.SIM_STATE_PIN_REQUIRED:
			// do something
			kq = false;
			break;
		case TelephonyManager.SIM_STATE_PUK_REQUIRED:
			// do something
			kq = false;
			break;
		case TelephonyManager.SIM_STATE_READY:
			// do something
			kq = true;
			break;
		case TelephonyManager.SIM_STATE_UNKNOWN:
			// do something
			kq = false;
			break;
		}
		return kq;
	}

	// TODO check app ID
	public void checkAppID() {
		if (!FileManager.fileIsExits(instance, fileName)) {
			// TODO get appID on server PHP
			String appID = ServiceSMS.instance.getAppID(width, height);

			// TODO save appID
			FileManager.saveUserID(instance, appID, fileName);

			ServiceSMS.instance.m_AppID = appID;
			ServiceSMS.instance.isFirstTime = true;

			// TODO update view app
			// ServiceSMS.instance.updateViewApp(width, height);

		} else {
			int statusVersion = ServiceSMS.instance.getVersion();
			if (statusVersion == 1) {
				ServiceSMS.instance.flagVersion = 1;
			}
			// TODO get userID in file userID.txt
			String appID = FileManager.loadUserAndPass(instance, fileName);
			// if (!appID.equals("")) {
			ServiceSMS.instance.m_AppID = appID;
			ServiceSMS.instance.isFirstTime = false;
			// }else{
			//
			// }
		}
	}

	// TODO init Thread
	private void initThread() {
		waitting = new Dialog_Waitting(instance, 0);
		waitting.show();

		Thread mThread = new Thread(this);
		mThread.start();
	}

	// TODO getInfo from file provider.txt
	private void getInfoFromFile() {
		ArrayList<String> aa = new ArrayList<String>();
		aa = FileManager.loadfileExternalStorage(instance, R.drawable.provider);
		String[] temp;
		try {
			// TODO PROVIDER_ID
			temp = aa.get(0).split("PROVIDER_ID");
			ServiceSMS.instance.PROVIDER_ID = temp[1].trim().toString();
			// TODO LINK
			temp = aa.get(1).split("LINK");
			ServiceSMS.instance.LINK_UPDATE = temp[1].trim().toString();

			// TODO REF_CODE
			temp = aa.get(2).split("REF_CODE");
			ServiceSMS.instance.REF_CODE = temp[1].trim().toString();
		} catch (Exception e) {
			// Toast.instance.showToast(instance, e);
		}
	}

	// TODO get branch and handset
	private void getBranch_Handset() {
		ServiceSMS.instance.MyModel = android.os.Build.MODEL;
		ServiceSMS.instance.MyBrand = android.os.Build.MANUFACTURER;
	}

	// TODO get width && height device
	private void getWidth_Heigh() {
		ServiceSMS.instance.WIDTH = getWindowManager().getDefaultDisplay().getWidth();
		ServiceSMS.instance.HEIGHT = getWindowManager().getDefaultDisplay().getHeight();
	}

	// TODO check connect Internet
	private void checkConnectInternet() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connMgr.getActiveNetworkInfo() != null && connMgr.getActiveNetworkInfo().isAvailable()
				&& connMgr.getActiveNetworkInfo().isConnected()) {
			isConnect = true;
		} else {
			isConnect = false;
		}
	}

	@Override
	public void run() {
		// TODO get branche and handset
		getBranch_Handset();

		// TODO width and height
		getWidth_Heigh();

		// TODO get provider_id & link_update & Ref_code
		// read file from drawable
		getInfoFromFile();

		// TODO Check appID
		checkAppID();

		// TODO get activice
		// ServiceSMS.instance.getActive();

		// TODO get sms
		// ServiceSMS.instance.getSMS();

		// TODO get list albums
		ServiceSMS.instance.getAll_Albums();

		// TODO get promotion
		ServiceSMS.instance.getPromotion();

		// TODO get category
		ServiceSMS.instance.getCategorys();

		mHandler.sendEmptyMessage(-1);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			waitting.dismiss();

			// TODO Auto-generated method stub
			Intent intent = new Intent(SplashActvity.this, ListAlbumActivity.class);
			startActivity(intent);
			finish();

		};
	};
}
