package com.example.activity;

import java.util.ArrayList;

import com.example.adapter.Custom_Dialog;
import com.example.adapter.Custom_ListSpin;
import com.example.adapter.ListdetailAdapter;
import com.example.adapter.ListimgAdapter;
import com.example.entity.CateTitle;
import com.example.entity.Category;
import com.example.entity.Item;
import com.example.ultilities.ConnectServer;
import com.example.ultilities.SendSMS;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ListDetailActivity extends Activity implements Runnable {
	Category category;
	ListView list;
	ArrayList<Category> arrlistCat = new ArrayList<Category>();
	ArrayList<Item> arrlistitem = new ArrayList<Item>();
	ArrayList<CateTitle> arrlistspin = new ArrayList<CateTitle>();
	ArrayAdapter<CateTitle> adapterspin;
	ListdetailAdapter adapter;
	ListimgAdapter imgadapter;
	View v;
	View v1;
	Integer id = 0;
	int currentImg = 0;

	CateTitle cateTitle;
	Integer idspin;
	boolean turnSearch = false;
	String[] data;
	Button btprevious;
	Button btnext;
	Button btsearch;
	Button btmenuspinner;
	Button btmnsearch;
	EditText edSearch;

	RelativeLayout rlyoutSearch;
	RelativeLayout rlanhkhac;
	RelativeLayout rlbtpage;

	Dialog mDialog_1;
	Spinner spinner;
	TextView tvtitle1;
	static Integer y = 26;

	boolean isShow = false;

	// Custom_ListSpin listSpinadapter;
	ArrayAdapter<String> listSpinAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_image);

		Bundle bundle = new Bundle();
		bundle = getIntent().getExtras();
		category = (Category) bundle.getSerializable("category");
		id = category.getId();

		initListView();

		onScreen();

		// new UpdateView().execute();

		intitSpinner();
	}

	public void intitSpinner() {

		btmenuspinner = (Button) findViewById(R.id.aclistimg_btmenu);

		int n = ConnectServer.instance.m_ListSpinner.size();

		// Log.e("mang arrspin", ConnectServer.instance.m_ListSpinner.size() +
		// "");

		data = new String[n + 1];
		data[0] = "All";
		for (int i = 1; i < n + 1; i++) {
			data[i] = ConnectServer.instance.m_ListSpinner.get(i - 1).name;
		}

		listSpinAdapter = new ArrayAdapter<String>(ListDetailActivity.this,
				android.R.layout.simple_list_item_1, data);
		// listSpinadapter = new Custom_ListSpin(ListCatActivity.this,
		// R.layout.textspin, arrlistspin);

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				ListDetailActivity.this);
		builder.setTitle("Danh sach");
		builder.setAdapter(listSpinAdapter,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// Toast.makeText(ListCatActivity.this, items[which],
						// Toast.LENGTH_LONG).show();
						CateTitle cateTitle = new CateTitle();
						if (which != 0) {
							cateTitle = ConnectServer.instance.m_ListSpinner
									.get(which - 1);
							idspin = cateTitle.getId();
							Bundle bundle = new Bundle();
							Intent intent = new Intent(ListDetailActivity.this,
									ListCatActivity.class);
							bundle.putInt("isdpin", idspin);
							intent.putExtras(bundle);

							ListCatActivity.flag_spin_LDetail = true;

							startActivity(intent);
							finish();
						} else {
							finish();
						}
					}
				});

		// final AlertDialog alert = builder.create();

		btmenuspinner.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				// alert.show();
				builder.show();
			}
		});
	}

	private void initListView() {
		ConnectServer.instance.getActive();

		arrlistCat = ConnectServer.instance.m_ListCatOther;

		// Log.e("so luong CatOther", arrlistCat.size()+"");
		// Log.e("so luong bitmapCatOther",
		// ConnectServer.instance.m_ListbitmapCatOther.size()+"");
		// Log.e("so luong arrItem",
		// ConnectServer.instance.m_ListbitmapItem.size()+"");
		// Log.e("so luong bitmapArrItem",
		// ConnectServer.instance.m_ListbitmapItem.size()+"");

		// find component
		list = (ListView) findViewById(R.id.aclistimg_list);

		btsearch = (Button) findViewById(R.id.aclistimg_btsearch);
		btmnsearch = (Button) findViewById(R.id.aclistimg_btmnsearch);
		rlyoutSearch = (RelativeLayout) findViewById(R.id.aclistimg_layoutsearch);
		edSearch = (EditText) findViewById(R.id.aclistimg_editsearch);
		// adapter
		adapter = new ListdetailAdapter(ListDetailActivity.this,
				R.layout.list_cat, arrlistCat);

		// set adpter to list and spinner
		btmnsearch.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// TODO Auto-generated method stub
				if (turnSearch == false) {
					rlyoutSearch.setVisibility(RelativeLayout.VISIBLE);
					turnSearch = true;
				} else {
					rlyoutSearch.setVisibility(RelativeLayout.GONE);
					turnSearch = false;
				}

			}
		});
		btsearch.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				String textsearch = edSearch.getText().toString().trim();
				Bundle bundle = new Bundle();
				Intent intent = new Intent(ListDetailActivity.this,
						ListCatActivity.class);
				bundle.putString("textsearch", textsearch);
				intent.putExtras(bundle);

				ListCatActivity.flag_tsearch_LDetail = true;

				startActivity(intent);
				finish();
			}
		});

		list.setScrollingCacheEnabled(false);
		list.addHeaderView(createHeader());
		list.addFooterView(createFooter());
		list.setDivider(null);
		list.setHeaderDividersEnabled(false);
		list.setAdapter(adapter);
		if (arrlistCat != null && list != null) {
			list.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					// ConnectServer.instance.getActive();

					// ConnectServer.instance.getActive();

					category = arrlistCat.get(arg2 - 1);
					id = category.getId();
					if (ConnectServer.instance.m_Active.getStatus().trim()
							.equals("0")) {
						// Custom_Dialog.transferActivity(category,
						// ListDetailActivity.this);
						onScreen();
					} else {

						// TODO lấy cú pháp tin nhắn
						// ConnectServer.instance.getSMS();
						if (isShow == false) {
							SendSMS.send(ConnectServer.instance.m_Sms.mo + " "
									+ ConnectServer.instance.m_Active.msg,
									ConnectServer.instance.m_Sms.serviceCode,
									ListDetailActivity.this);
							isShow = true;
						} else {
							ConnectServer.instance.getActive();
							Toast.makeText(ListDetailActivity.this,
									"Xin đợi trong giây lát", 30).show();
						}

					}

					// int fposition=list.getFirstVisiblePosition();

				}
			});

		} else {
			AlertDialog.Builder buidler = new AlertDialog.Builder(this);
			buidler.create();
			buidler.setTitle("Thông báo");
			buidler.setMessage("Không có hình nào trong bộ sưu tập !!!");
			buidler.show();
			finish();
		}

	}

	private void onScreen() {
		// TODO Auto-generated method stub
		 initDialog_Loading();
		initThread();
	}

	private void initThread() {
		// TODO Auto-generated method stub
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		// TODO Auto-generated method stub

		getOtherListCat();
		ConnectServer.instance.getActive();

		// handler.sendEmptyMessage(-1);
//		new UpdateView().execute("");
	}

	private void initDialog_Loading() {

		mDialog_1 = new Dialog(ListDetailActivity.this,
				android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		mDialog_1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialog_1.setContentView(R.layout.waitting_1);
		mDialog_1.show();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			mDialog_1.dismiss();
			new UpdateView().execute("");

		};
	};

	private void getOtherListCat() {
		new Thread(new Runnable() {

			public void run() {

				ConnectServer.instance.getImage(id);
				ConnectServer.instance.getOtherCategory(id);

				handler.sendEmptyMessage(-1);
			}
		}).start();
	}

	 class UpdateView extends AsyncTask<String, Category, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initDialog_Loading();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
//			for(int i=0;i<ConnectServer.instance.m_ListbitmapItem.size();i++)
//			{
//				try {
//
//					ConnectServer.threadItems[i].interrupt();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			for(int i=0;i<ConnectServer.instance.m_ListbitmapCatOther.size();i++)
//			{
//				try {
//					ConnectServer.threadIMG_Others[i].stop();
//					ConnectServer.threadIMG_Others[i].interrupt();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			return null;
		}
							
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mDialog_1.dismiss();
			updateListView();			
		}

	}

	private void updateListView() {
		// TODO Auto-generated method stub
		// mDialog_1.dismiss();
	
		this.runOnUiThread(new Runnable() {

			public void run() {

				imgadapter.clear();
				for (int i = 0; i < ConnectServer.instance.m_ListItem.size(); i++) {
					imgadapter.add(ConnectServer.instance.m_ListItem.get(i));
				}

				adapter.clear();
				for (int i = 0; i < ConnectServer.instance.m_ListCatOther
						.size(); i++) {
					adapter.add(ConnectServer.instance.m_ListCatOther.get(i));
				}

				imgadapter.notifyDataSetChanged();
				adapter.notifyDataSetChanged();

				// list.scrollTo(0, y);

				btnext.setVisibility(Button.VISIBLE);
				btprevious.setVisibility(Button.VISIBLE);
				rlanhkhac.setVisibility(RelativeLayout.VISIBLE);
				edSearch.setFocusable(true);
				edSearch.setFocusableInTouchMode(true);

				list.setSelection(0);
			}
		});
	}

	private View createHeader() {
		// TODO Auto-generated method stub

		arrlistitem = ConnectServer.instance.m_ListItem;

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.header, null);

		btprevious = (Button) v.findViewById(R.id.header_btPrevious);
		btnext = (Button) v.findViewById(R.id.header_btNext);
		final Gallery gallery = (Gallery) v.findViewById(R.id.header_gallery);
		tvtitle1 = (TextView) v.findViewById(R.id.header_title);
		rlanhkhac = (RelativeLayout) v.findViewById(R.id.header_lyoKhac);

		imgadapter = new ListimgAdapter(ListDetailActivity.this,
				R.layout.image, arrlistitem);

		gallery.setAdapter(imgadapter);
		gallery.setSpacing(30);
		gallery.setPadding(10, 0, 10, 0);
		gallery.setFadingEdgeLength(0);

		gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ListDetailActivity.this,
						ImageActivity.class);
				startActivity(intent);
			}
		});

		// gallery.seton
		// btprevious.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (currentImg == 0) {
		// btprevious.setVisibility(Button.INVISIBLE);
		// } else {
		// currentImg -= 1;
		//
		// }
		// if (currentImg < ConnectServer.instance.m_ListbitmapItem.size()) {
		// btnext.setVisibility(Button.VISIBLE);
		// }
		//
		// }
		// });
		//
		// btnext.setOnClickListener(new OnClickListener() {
		//
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// if (currentImg == (ConnectServer.instance.m_ListbitmapItem
		// .size() - 1)) {
		// btnext.setVisibility(Button.INVISIBLE);
		// } else {
		// currentImg += 1;
		//
		// }
		// if (currentImg > 0) {
		// btprevious.setVisibility(Button.VISIBLE);
		// }
		// }
		// });

		return v;
	}

	private View createFooter() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v1 = inflater.inflate(R.layout.footer, null);
		rlbtpage = (RelativeLayout) v1.findViewById(R.id.footer_layoutbtpage);
		rlbtpage.setVisibility(RelativeLayout.GONE);
		return v1;
	}

	// @Override
	// public void onBackPressed() {
	// // TODO Auto-generated method stub
	// super.onBackPressed();
	// ConnectServer.instance.getActive();
	// }
}
