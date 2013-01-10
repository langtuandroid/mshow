package com.hdc.mshow;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

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
	public int flagVersion = 0;
	public boolean isConnect = true;

	public Dialog waitting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		// TODO instance
		instance = this;

		// TODO Check connect
		checkConnectInternet();

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

	// TODO check app ID
	public void checkAppID() {
		if (!FileManager.fileIsExits(instance, fileName)) {
			// TODO get appID on server PHP
			String appID = ServiceSMS.instance.getAppID(width, height);

			// TODO save appID
			FileManager.saveUserID(instance, appID, fileName);

			ServiceSMS.instance.m_AppID = appID;
			ServiceSMS.instance.isFirstTime = "end";

			// TODO update view app
			ServiceSMS.instance.updateViewApp(width, height);

		} else {
			int statusVersion = ServiceSMS.instance.getVersion();
			if (statusVersion == 1) {
				flagVersion = 1;
			}
			// TODO get userID in file userID.txt
			String appID = FileManager.loadUserAndPass(instance, fileName);
			// if (!appID.equals("")) {
			ServiceSMS.instance.m_AppID = appID;
			ServiceSMS.instance.isFirstTime = "end";
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
		width = getWindowManager().getDefaultDisplay().getWidth();
		height = getWindowManager().getDefaultDisplay().getHeight();
	}

	// TODO check connect Internet
	private void checkConnectInternet() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connMgr.getActiveNetworkInfo() != null
				&& connMgr.getActiveNetworkInfo().isAvailable()
				&& connMgr.getActiveNetworkInfo().isConnected()) {
			isConnect = true;
		} else {
			isConnect = false;
		}
	}

	// TODO showDialog if hava a new version
	private void showDialog_UpdateVersion() {
		if (flagVersion == 1) {
			// Dialog d = new Dialog_Update(instance);
			// d.show();
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

		// TODO get sms
		// ServiceSMS.instance.getSMS();

		// TODO get list albums
		ServiceSMS.instance.getAll_Albums();
		
		//TODO get category
		ServiceSMS.instance.getCategorys();

		mHandler.sendEmptyMessage(-1);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			waitting.dismiss();

			// TODO ListActivity
			Intent intent = new Intent(SplashActvity.this,
					ListAlbumActivity.class);
			startActivity(intent);
			finish();

		};
	};
}
