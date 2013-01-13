package com.hdc.mshow;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hdc.mshow.customize.CustomGalary;
import com.hdc.mshow.customize.CustomGalleryImageAdapter;
import com.hdc.mshow.customize.Footer;
import com.hdc.mshow.customize.Header;
import com.hdc.mshow.customize.Header_Other;
import com.hdc.mshow.customize.ListAbumAdapter;
import com.hdc.mshow.customize.Toast;
import com.hdc.mshow.dialog.Dialog;
import com.hdc.mshow.dialog.Dialog_Waitting;
import com.hdc.mshow.model.Album;
import com.hdc.mshow.model.Item;
import com.hdc.mshow.service.ServiceSMS;
import com.hdc.mshow.ultilities.DownloadImage;

public class ListAlbumOtherActivity extends Activity {
	// TODO Instance
	public static ListAlbumOtherActivity instance;

	private static ArrayList<Album> arrayitems = new ArrayList<Album>();
	private static ListAbumAdapter adapter;
	private static ListView m_ListView;

	// TODO Footer
	Footer footer;

	// TODO Header
	Header_Other header;

	// TODO Excuting ...
	public boolean isExcuting = false;

	// TODO Galary
	CustomGalleryImageAdapter adapter_Galary;
	ArrayList<Item> arrayItem = new ArrayList<Item>();
	CustomGalary galary;
	
	Dialog w;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_list_album_other);

		instance = this;

		w = new Dialog_Waitting(instance, 0);
		w.show();

		
		// TODO init footer
		footer = new Footer(this);
		footer.instance.setVisibility(View.GONE);

		// TODO init header
		header = new Header_Other(instance);
		header.initLayout();

		initListView();

		initGalary();

		new updateImage_Galary().execute();

		new updateImage().execute();

	}

	// init ListView
	public void initListView() {
		// arrayitems = ServiceSMS.instance.m_ListAlbums;
		adapter = new ListAbumAdapter(this, R.layout.item_album, arrayitems);
		m_ListView = (ListView) findViewById(R.id.list_album_other);
		m_ListView.addHeaderView(header.instance);
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
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				// try {
				// Toast.instance.show(instance, "position " + position
				// + "\n id " + id);
				//
				// ServiceSMS.instance.getAlbum_Item(arrayitems.get((int) id)
				// .getId());
				//
				// Intent intent = new Intent(instance,
				// ListImageActivity.class);
				// startActivity(intent);
				// } catch (Exception ex) {
				// Toast.instance.showToast(instance, ex);
				// }

				try {
					Toast.instance.show(instance, "position " + position
							+ "\n id " + id);

					ServiceSMS.instance.getAlbum_Item(arrayitems.get((int) id)
							.getId());
					ServiceSMS.instance.getAlbum_Other(arrayitems.get((int) id)
							.getId());

					Intent intent = new Intent(instance,
							ListAlbumOtherActivity.class);
					startActivity(intent);
					finish();
				} catch (Exception ex) {
					Toast.instance.showToast(instance, ex);
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
		ArrayList<Album> aa = new ArrayList<Album>();

		Dialog w;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			isExcuting = true;

			// w = new Dialog_Waitting(instance, 0);
			// w.show();

			aa = ServiceSMS.instance.m_ListAlbum_Other;

			if (adapter != null && aa.size() > 0) {
				footer.instance.setVisibility(View.GONE);
				adapter.clear();
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if (aa.size() > 0) {
				for (int i = 0; i < aa.size(); i++) {
					Bitmap b = null;
					try {
						b = DownloadImage.instance.getImage(aa.get(i).getSrc());
					} catch (Exception e) {
						b = null;
					}

					album = aa.get(i);
					album.setImg(b);

					publishProgress(i);
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

			// if (values[0] == 0)
			// w.dismiss();

			adapter.insert(album, values[0]);
			adapter.notifyDataSetChanged();

			album = null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			isExcuting = false;
			footer.instance.setVisibility(View.VISIBLE);
			footer.setVisible_Paging(View.GONE);
		}
	}

	// TODO init galary
	private void initGalary() {
		arrayItem = ServiceSMS.instance.m_ListAlbum_Items;
		adapter_Galary = new CustomGalleryImageAdapter(instance,
				R.layout.item_galary_list_album_other, arrayItem);
		galary = (CustomGalary) findViewById(R.id.galary_other);
		galary.setAdapter(adapter_Galary);
		galary.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try {
					// TODO Auto-generated method stub
					Intent intent = new Intent(instance,
							ListImageActivity.class);
					startActivity(intent);
				} catch (Exception ex) {
					Toast.instance.showToast(instance, ex);
				}
			}
		});
	}

	class updateImage_Galary extends AsyncTask<Void, Integer, Void> {
		Item item = null;
		// ArrayList<Item> aa = new ArrayList<Item>();

		//Dialog w;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			isExcuting = true;


			arrayItem = ServiceSMS.instance.m_ListAlbum_Items;

			// if (adapter != null && aa.size() > 0) {
			// footer.instance.setVisibility(View.GONE);
			// adapter.clear();
			// }

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if (arrayItem.size() > 0) {
				for (int i = 0; i < arrayItem.size(); i++) {
					Bitmap b = null;
					try {
						b = DownloadImage.instance.getImage(arrayItem.get(i)
								.getSrc());
					} catch (Exception e) {
						b = null;
					}

					arrayItem.get(i).setImg(b);

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

			adapter_Galary.notifyDataSetChanged();

			item = null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			isExcuting = false;
			ServiceSMS.instance.m_ListAlbum_Items = arrayItem;
		}
	}

}
