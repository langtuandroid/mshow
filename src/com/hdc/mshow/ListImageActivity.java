package com.hdc.mshow;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.hdc.mshow.customize.CustomGalary;
import com.hdc.mshow.customize.CustomGalleryImageAdapter;
import com.hdc.mshow.dialog.Dialog;
import com.hdc.mshow.dialog.Dialog_Waitting;
import com.hdc.mshow.model.Album;
import com.hdc.mshow.model.Item;
import com.hdc.mshow.service.ServiceSMS;
import com.hdc.mshow.ultilities.DownloadImage;

public class ListImageActivity extends Activity {

	// TODO Galary
	CustomGalleryImageAdapter adapter;
	ArrayList<Item> arrayItem = new ArrayList<Item>();
	CustomGalary galary;

	public static ListImageActivity instance;

	public boolean isExcuting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_list_image);
		// setContentView(R.layout.item_galary);

		instance = this;

		// ImageView image = (ImageView) findViewById(R.id.imageView1);
		// image.setImageBitmap(ServiceSMS.instance.m_ListAlbum_Items.get(0).getImg());

		initGalary();

		new updateImage().execute();
	}

	// TODO init galary
	private void initGalary() {
		arrayItem = ServiceSMS.instance.m_ListAlbum_Items;
		adapter = new CustomGalleryImageAdapter(instance,
				R.layout.item_galary_listimage, arrayItem);
		galary = (CustomGalary) findViewById(R.id.galary);
		galary.setAdapter(adapter);
		galary.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

			}
		});
	}

	class updateImage extends AsyncTask<Void, Integer, Void> {
		Item item = null;
		// ArrayList<Item> aa = new ArrayList<Item>();

		Dialog w;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			isExcuting = true;

			w = new Dialog_Waitting(instance, 0);
			w.show();

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
					if (arrayItem.get(i).getImg() == null) {
						Bitmap b = null;
						try {
							b = DownloadImage.instance.getImage(arrayItem
									.get(i).getSrc());
						} catch (Exception e) {
							b = null;
						}
						arrayItem.get(i).setImg(b);
						publishProgress(i);
					}
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

			// Log.i("id", album.getId());
			// Log.i("name", album.getTitle());
			// Log.i("src", album.getSrc());

			// if (values[0] == 0)
			w.dismiss();

			// adapter.insert(item, values[0]);

			adapter.notifyDataSetChanged();

			item = null;
			// Toast.makeText(ListAlbumActivity.this, values[0] + "",
			// Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// m_ListView.addFooterView(f.instance);
			// Toast.makeText(ListAlbumActivity.this, "okie",
			// Toast.LENGTH_SHORT).show();
			w.dismiss();
			isExcuting = false;

			// footer.setVisible_Paging(View.VISIBLE);
			// footer.instance.setVisibility(View.VISIBLE);
		}
	}

}
