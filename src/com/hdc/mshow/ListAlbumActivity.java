package com.hdc.mshow;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.hdc.mshow.customize.Footer;
import com.hdc.mshow.customize.ListAbumAdapter;
import com.hdc.mshow.model.Album;
import com.hdc.mshow.service.ServiceSMS;
import com.hdc.mshow.ultilities.DownloadImage;

public class ListAlbumActivity extends Activity {
	private static ArrayList<Album> arrayitems = new ArrayList<Album>();
	private static ListAbumAdapter adapter;
	private static ListView m_ListView;

	// TODO Footer
	Footer f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_list_albums);

		// TODO init footer
		f = new Footer(this);

		initListView();

		new updateImage().execute();
	}

	// init ListView
	public void initListView() {
		// arrayitems = ServiceSMS.instance.m_ListAlbums;
		adapter = new ListAbumAdapter(this, R.layout.item_album, arrayitems);
		m_ListView = (ListView) findViewById(R.id.list_album);
		m_ListView.addFooterView(f.instance);
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

			}
		});
	}

	class updateImage extends AsyncTask<Void, Integer, Void> {
		Album album = null;
		ArrayList<Album> aa = new ArrayList<Album>();

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			aa = ServiceSMS.instance.m_ListAlbums;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
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

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			adapter.insert(album, values[0]);
			adapter.notifyDataSetChanged();
			Toast.makeText(ListAlbumActivity.this, values[0] + "", Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// m_ListView.addFooterView(f.instance);
			Toast.makeText(ListAlbumActivity.this, "okie", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		adapter.clear();
	}

}
