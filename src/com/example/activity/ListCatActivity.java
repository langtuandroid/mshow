package com.example.activity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.Custom_Dialog;
import com.example.adapter.ListcatAdapter;
import com.example.entity.CateTitle;
import com.example.entity.Category;
import com.example.entity.Promotion;
import com.example.ultilities.ConnectServer;
import com.example.ultilities.DownloadImage;
import com.example.ultilities.FileManager;
import com.example.ultilities.SendSMS;

public class ListCatActivity extends Activity implements Runnable {
	public static ListCatActivity instance = new ListCatActivity();
	boolean isConnect = false;
	Integer flagVersion = 0;
	Thread thread;

	public static Integer width = 0;
	public static Integer height = 0;
	String brand;
	String model;

	View vfoot;
	View vspin;

	Button btprevious;
	Button bt1;
	Button bt2;
	Button bt3;
	Button btnext;
	Button btmn1;
	Button btmn2;
	static Button btmenuspinner;
	static Button btmnsearch;
	Button btsearch;

	RelativeLayout rlyoutSearch;
	// RelativeLayout rlyoutADV;
	ImageView imgADV;

	String link;
	Integer flag_focuspage = 1;
	Spinner spinner;
	CateTitle cateTitle;

	static EditText edsearch;
	String searchText = "";
	public String fileName = "userID.txt";
	public String ftime = "checktime.txt";
	// param begin
	ArrayList<Category> arrlistcat;

	ListcatAdapter adapter;
	ArrayList<Promotion> arrpromotion;

	// component
	String[] data;
	ListView list;
	ListView listspin;
	Dialog mDialog_1;
	Dialog mDialog_bg;
	Integer idspin;
	public static boolean isShow = false;
	boolean isSearch = false;
	boolean isSpinner = false;
	boolean turnSearch = false;
	static Integer count = 0;
	public static boolean flag_spin_LDetail = false;
	public static boolean flag_tsearch_LDetail = false;

	// Custom_ListSpin listSpinadapter;
	ArrayAdapter<String> listSpinAdapter;

	// public ArrayList<Bitmap> m_ListbitmapCat;

	public StringWriter stringWriter = new StringWriter();
	private PrintWriter printWriter = new PrintWriter(stringWriter, true);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_list_cat);

		arrlistcat = new ArrayList<Category>();

		arrpromotion = new ArrayList<Promotion>();

		try {
			checkInternet();
			if (isConnect == true) {
				initListView();
				intitSpinner();
				if (flag_spin_LDetail == true) {
					Bundle bundle = new Bundle();
					bundle = getIntent().getExtras();
					idspin = bundle.getInt("idspin");
					searchText = "";
					onScreen();

					flag_spin_LDetail = false;
				}
				if (flag_tsearch_LDetail == true) {
					Bundle bundle = new Bundle();
					bundle = getIntent().getExtras();
					searchText = bundle.getString("textsearch");
					Log.e("co vao search", searchText);

					idspin = null;
					onScreen();

					flag_tsearch_LDetail = false;
				}

				if (count == 0) {

					onScreen();

					// count += 1;
					// Log.e("count solan", count + "");
				}
			} else {
				AlertDialog.Builder buidler = new AlertDialog.Builder(this);
				buidler.create();
				buidler.setTitle("Thông báo");
				buidler.setMessage("Bạn vui lòng kiểm tra kết nối Internet !!!");
				buidler.show();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void checkInternet() {
		// TODO Auto-generated method stub
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager.getActiveNetworkInfo() != null
				&& connectivityManager.getActiveNetworkInfo().isAvailable()
				&& connectivityManager.getActiveNetworkInfo().isConnected()) {
			isConnect = true;

		} else {

			isConnect = false;
		}
	}

	// TODO List item
	// CharSequence[] items = {"1","2","3","1","1","1","1","1","1"};

	private void initListView() {
		// TODO Auto-generated method stub]

		ConnectServer.instance.getActive();

		arrlistcat = ConnectServer.instance.m_ListCategory;
		arrpromotion = ConnectServer.instance.m_promotion;
		adapter = new ListcatAdapter(ListCatActivity.this, R.layout.list_cat,
				arrlistcat);

		// find component
		list = (ListView) findViewById(R.id.aclistcat_listcat);
		btsearch = (Button) findViewById(R.id.aclistcat_btsearch);
		edsearch = (EditText) findViewById(R.id.aclistcat_editsearch);

		btmnsearch = (Button) findViewById(R.id.aclistcat_btmnsearch);

		rlyoutSearch = (RelativeLayout) findViewById(R.id.aclistcat_layoutsearch);

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
				// edsearch.setFocusable(false);
				// edsearch.setFocusableInTouchMode(false);
				// InputMethodManager imm = (InputMethodManager)
				// getSystemService(Context.INPUT_METHOD_SERVICE);
				// imm.hideSoftInputFromWindow(edsearch.getWindowToken(), 0);
				searchText = edsearch.getText().toString().trim();

				onScreen();
				edsearch.setText("");

			}
		});

		list.addFooterView(createFooter());
		list.setFooterDividersEnabled(false);
		list.setDivider(null);
		list.setScrollingCacheEnabled(false);
		list.setAdapter(adapter);
		// set event
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// ConnectServer.instance.getActive();

				Category category = new Category();
				category = arrlistcat.get(arg2);

				String isFirstTime = "";

				Log.e("status Active",
						ConnectServer.instance.m_Active.getStatus());

				isFirstTime = FileManager
						.loadFtime(ListCatActivity.this, ftime);
				if (isFirstTime == "") {
					ConnectServer.instance.isFirstTime = true;
				} else {
					ConnectServer.instance.isFirstTime = false;
				}

				if (ConnectServer.instance.m_Active.getStatus().trim()
						.equals("0")) {

					Custom_Dialog.transferActivity(category,
							ListCatActivity.this);
				} else {
					if (isShow == false) {

						// TODO lấy cú pháp tin nhắn
						ConnectServer.instance.getSMS();
						if (ConnectServer.instance.isFirstTime == true) {
							Custom_Dialog.showDialog_ActivationSMS(category,
									ListCatActivity.this);
							// isShow = true;
						} else {
							//
							SendSMS.send(ConnectServer.instance.m_Sms.mo + " "
									+ ConnectServer.instance.m_Active.msg,
									ConnectServer.instance.m_Sms.serviceCode,
									ListCatActivity.this);
							// chuyển qua activity ListDetailACtivity
							// nếu nhắn tin thành công
							isShow = true;
						}

					} else {
						ConnectServer.instance.getActive();
						Toast.makeText(ListCatActivity.this,
								"Xin đợi trong giây lát", 30).show();
					}
				}
			}

		});

	}

	public void intitSpinner() {

		btmenuspinner = (Button) findViewById(R.id.aclistcat_btspinnermenu);

		int n = ConnectServer.instance.m_ListSpinner.size();

		// Log.e("mang arrspin", ConnectServer.instance.m_ListSpinner.size() +
		// "");

		data = new String[n + 1];
		data[0] = "All";
		for (int i = 1; i < n + 1; i++) {
			data[i] = ConnectServer.instance.m_ListSpinner.get(i - 1).name;
		}

		listSpinAdapter = new ArrayAdapter<String>(ListCatActivity.this,
				android.R.layout.simple_list_item_1, data);

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				ListCatActivity.this);
		builder.setTitle("Danh sach");
		builder.setAdapter(listSpinAdapter,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// Toast.makeText(ListCatActivity.this, items[which],
						// Toast.LENGTH_LONG).show();

						try {
							CateTitle cateTitle = new CateTitle();
							if (which != 0) {
								cateTitle = ConnectServer.instance.m_ListSpinner
										.get(which - 1);
								idspin = cateTitle.getId();
								onScreen();
							} else {
								idspin = null;
								onScreen();
							}
						} catch (Exception ex) {
							ex.printStackTrace(printWriter);
							printWriter.flush();
							stringWriter.flush();
							Toast.makeText(ListCatActivity.this,
									stringWriter.toString(),
									android.widget.Toast.LENGTH_LONG).show();
						}
					}
				});

		final AlertDialog alert = builder.create();

		btmenuspinner.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Toast.makeText(ListCatActivity.this, "show spinner",
				// Toast.LENGTH_LONG).show();

				alert.show();

				// builder.show();
			}
		});

	}

	private View createFooter() {

		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		vfoot = inflater.inflate(R.layout.footer, null);
		btprevious = (Button) vfoot.findViewById(R.id.footer_btPrevious);
		bt1 = (Button) vfoot.findViewById(R.id.footer_btp1);
		bt2 = (Button) vfoot.findViewById(R.id.footer_btp2);
		bt3 = (Button) vfoot.findViewById(R.id.footer_btp3);
		btnext = (Button) vfoot.findViewById(R.id.footer_btNext);
		btmn1 = (Button) vfoot.findViewById(R.id.footer_btcontinuesb);
		btmn2 = (Button) vfoot.findViewById(R.id.footer_btcontinuesn);
		imgADV = (ImageView) vfoot.findViewById(R.id.footer_advertise);
		// set event button
		imgADV.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!ConnectServer.instance.m_promotion.isEmpty()) {

				}
			}
		});
		btprevious.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				switch (flag_focuspage) {
				case 1:
					bt1.setBackgroundResource(R.drawable.number2);
					bt2.setBackgroundResource(R.drawable.number2);
					bt3.setBackgroundResource(R.drawable.number1);
					flag_focuspage = 3;
					bt1.setText((ConnectServer.currentpage - 3) + "");
					bt2.setText((ConnectServer.currentpage - 2) + "");
					bt3.setText((ConnectServer.currentpage - 1) + "");
					break;
				case 2:
					bt1.setBackgroundResource(R.drawable.number1);
					bt2.setBackgroundResource(R.drawable.number2);
					bt3.setBackgroundResource(R.drawable.number2);
					flag_focuspage = 1;
					break;
				case 3:
					bt1.setBackgroundResource(R.drawable.number2);
					bt2.setBackgroundResource(R.drawable.number1);
					bt3.setBackgroundResource(R.drawable.number2);
					flag_focuspage = 2;
					break;
				}
				ConnectServer.currentpage -= 1;
				if (ConnectServer.currentpage != 1) {
					btprevious.setVisibility(Button.VISIBLE);
				} else {
					btprevious.setVisibility(Button.GONE);
					btmn1.setVisibility(Button.GONE);
				}
				new UpdatePage().execute("");
			}
		});

		bt1.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				bt1.setBackgroundResource(R.drawable.number1);
				bt2.setBackgroundResource(R.drawable.number2);
				bt3.setBackgroundResource(R.drawable.number2);
				ConnectServer.currentpage = Integer.parseInt(bt1.getText()
						.toString());
				flag_focuspage = 1;
				if (ConnectServer.currentpage != 1) {
					btprevious.setVisibility(Button.VISIBLE);
				} else {

					btprevious.setVisibility(Button.GONE);
					btmn1.setVisibility(Button.GONE);
				}
				new UpdatePage().execute("");
			}
		});

		bt2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					bt1.setBackgroundResource(R.drawable.number2);
					bt2.setBackgroundResource(R.drawable.number1);
					bt3.setBackgroundResource(R.drawable.number2);
					ConnectServer.currentpage = Integer.parseInt(bt2.getText()
							.toString());
					flag_focuspage = 2;

					btprevious.setVisibility(Button.VISIBLE);
					btmn1.setVisibility(Button.VISIBLE);

					new UpdatePage().execute("");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		bt3.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				bt1.setBackgroundResource(R.drawable.number2);
				bt2.setBackgroundResource(R.drawable.number2);
				bt3.setBackgroundResource(R.drawable.number1);
				ConnectServer.currentpage = Integer.parseInt(bt3.getText()
						.toString());
				flag_focuspage = 3;

				btprevious.setVisibility(Button.VISIBLE);
				btmn1.setVisibility(Button.VISIBLE);

				new UpdatePage().execute("");
			}
		});

		btnext.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (flag_focuspage) {
				case 1:
					bt1.setBackgroundResource(R.drawable.number2);
					bt2.setBackgroundResource(R.drawable.number1);
					bt3.setBackgroundResource(R.drawable.number2);
					flag_focuspage = 2;
					break;

				case 2:
					bt1.setBackgroundResource(R.drawable.number2);
					bt2.setBackgroundResource(R.drawable.number2);
					bt3.setBackgroundResource(R.drawable.number1);
					flag_focuspage = 3;
					break;
				case 3:
					bt1.setBackgroundResource(R.drawable.number1);
					bt2.setBackgroundResource(R.drawable.number2);
					bt3.setBackgroundResource(R.drawable.number2);
					flag_focuspage = 1;
					bt1.setText((ConnectServer.currentpage + 1) + "");
					bt2.setText((ConnectServer.currentpage + 2) + "");
					bt3.setText((ConnectServer.currentpage + 3) + "");
					break;
				}
				if (ConnectServer.totalpage - ConnectServer.currentpage <= 3) {
					btnext.setVisibility(Button.GONE);
				}
				if (ConnectServer.totalpage - ConnectServer.currentpage == 2) {
					// tắt nút page 3
					bt3.setVisibility(Button.GONE);

				}
				if (ConnectServer.totalpage - ConnectServer.currentpage == 1) {
					// tắt nút page 2
					bt2.setVisibility(Button.GONE);

				}

				// set page current
				ConnectServer.currentpage += 1;
				if (ConnectServer.currentpage != 1) {
					btprevious.setVisibility(Button.VISIBLE);
					btmn1.setVisibility(Button.VISIBLE);
				}

				new UpdatePage().execute("");
			}
		});

		return vfoot;

	}

	private void onScreen() {
		// TODO Auto-generated method stub
		// if (isSearch == true || isSpinner == true) {
		initDialog_Loading();
		// }
		if (count == 0) {
			getWidth_Heigh();
			getInfoFromFile();
			checkAppID();
			updateViewApp();
		}
		initThread();
	}

	private void getInfoFromFile() {
		// TODO Auto-generated method stub
		ArrayList<String> aa = new ArrayList<String>();
		aa = FileManager.loadfileExternalStorage(ListCatActivity.this,
				R.drawable.provider);
		String[] temp;
		try {
			// provider id
			temp = aa.get(0).split("PROVIDER_ID");
			ConnectServer.PROVIDER_ID = temp[1].trim().toString();
			// link
			temp = aa.get(1).split("LINK");
			ConnectServer.LINK_UPDATE = temp[1].trim().toString();

			// ref code
			temp = aa.get(2).split("REF_CODE");

			if (temp.length == 0) {
				ConnectServer.instance.REF_CODE = "";
			} else {
				ConnectServer.instance.REF_CODE = temp[1].trim().toString();
			}
		} catch (Exception e) {
		}
	}

	private void getWidth_Heigh() {
		// TODO Auto-generated method stub
		width = getWindowManager().getDefaultDisplay().getWidth();
		height = getWindowManager().getDefaultDisplay().getHeight();
		brand = android.os.Build.BRAND;
		model = android.os.Build.MODEL;
	}

	private void initDialog_Loading() {

		mDialog_1 = new Dialog(ListCatActivity.this,
				android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
		mDialog_1.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialog_1.setContentView(R.layout.waitting_1);
		mDialog_1.show();
	}

	private void initThread() {
		// TODO Auto-generated method stub
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		// TODO Auto-generated method stub

		getListCategory();
		ConnectServer.instance.getActive();

		mhandler.sendEmptyMessage(-1);

	}

	private void checkAppID() {
		// TODO Auto-generated method stub
		if (!FileManager.fileIsExits(ListCatActivity.this, fileName)) {
			// get appID on server PHP
			String appID = ConnectServer.instance.getAppID(width, height,
					brand, model);
			Log.e("appID", appID);
			// save appID
			FileManager.saveUserID(ListCatActivity.this, appID, fileName);
			ConnectServer.instance.m_AppID = appID;
			ConnectServer.instance.isFirstTime = true;
		} else {
			int statusVersion = ConnectServer.instance.getVersion();
			if (statusVersion == 1) {
				flagVersion = 1;
			}
			// get userID in file userID.txt
			String appID = FileManager.loadUserAndPass(ListCatActivity.this,
					fileName);
			Log.e("appID", appID);
			ConnectServer.instance.m_AppID = appID;
			// ConnectServer.instance.isFirstTime = false;
		}
	}

	private void updateViewApp() {
		// TODO Auto-generated method stub
		ConnectServer.instance.updateViewApp(width, height, brand, model);
	}

	private Handler mhandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mDialog_1.dismiss();
			if (flagVersion == 0) {
				new UpdateView().execute("", "");
			} else {
				Toast.makeText(
						ListCatActivity.this,
						"Đã có phiên bản mới, mời các bạn lên trang web 'thegioigame.mobi' để tải mShow mới nhất !",
						20).show();
				new UpdateView().execute("", "");
			}
			intitSpinner();
		};
	};

	private void getListCategory() {
		// ConnectServer.instance.getSpinnerCat();
		ConnectServer.instance.getCategory(idspin, searchText,
				ListCatActivity.this);
	}

	class UpdateView extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub0
			
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			updateListView();
		}
	}

	class UpdatePage extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			initDialog_Loading();
		}

		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub

			getListCategory();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// edit.clearFocus();
			updateListView();
		}

	}

	private void updateListView() {
		// TODO Auto-generated method stub
		this.runOnUiThread(new Runnable() {

			public void run() {

				mDialog_1.dismiss();

				// if (!ConnectServer.instance.m_ListCategory.isEmpty()) {
				adapter.clear();
				for (int i = 0; i < ConnectServer.instance.m_ListCategory
						.size(); i++) {
					adapter.add(ConnectServer.instance.m_ListCategory.get(i));
					if (!ConnectServer.instance.m_promotion.isEmpty() && i == 0) {
						imgADV.setImageBitmap(DownloadImage.instance
								.getImage(arrpromotion.get(i).getSrc()));
						link = arrpromotion.get(i).getLink();
					}
				}

				adapter.notifyDataSetChanged();
				list.setSelection(0);
				// }
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_list_cat, menu);
		return true;
	}

	// @Override
	// protected void onResume() {
	//
	// super.onResume();
	// intitSpinner();
	//
	// }

	// @Override
	// protected void onStart() {
	// // TODO Auto-generated method stub
	// super.onStart();
	// ConnectServer.instance.getActive();
	// }
}
// <RelativeLayout
// android:id="@+id/aclistimg_layoutcontent"
// android:layout_width="fill_parent"
// android:layout_height="382dp"
// android:layout_below="@+id/aclistimg_layoutsearch"
// android:background="@drawable/background" >
//
// <TextView
// android:id="@+id/aclistimg_tvtitle"
// android:layout_width="fill_parent"
// android:layout_height="40dp"
// android:text="Bộ ảnh Bỏng Nguyên Con Mắt"
// android:textSize="20dp"
// android:textColor="#ffffff"
// android:gravity="center"/>
// <RelativeLayout
// android:id="@+id/aclistimg_contentimg"
// android:layout_width="fill_parent"
// android:layout_height="300dp"
// android:layout_below="@+id/aclistimg_tvtitle"
// android:background="@drawable/back_top">
//
// <Button
// android:id="@+id/aclistimg_btPrevious"
// android:layout_width="31dp"
// android:layout_height="45dp"
// android:background="@drawable/next"
// android:layout_marginTop="130dp"/>
// <ImageView
// android:id="@+id/aclistimg_bigimg"
// android:layout_width="250dp"
// android:layout_height="280dp"
// android:layout_marginLeft="35dp"
// android:layout_marginTop="30dp"/>
// </RelativeLayout>
// </RelativeLayout>
