package com.example.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.example.activity.R;
import com.example.entity.Category;
import com.example.entity.Item;
import com.example.ultilities.ConnectServer;
import com.example.ultilities.DownloadImage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListdetailAdapter extends ArrayAdapter<Category> {

	Context context;
	Integer resource;
	ArrayList<Category> arrlistOtherCat;

	public ListdetailAdapter(Context context, int textViewResourceId,
			ArrayList<Category> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.resource = textViewResourceId;
		this.arrlistOtherCat = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(resource, null);
		}
		Category cate = arrlistOtherCat.get(position);
		if (cate != null) {
			ImageView imgv = (ImageView) v.findViewById(R.id.listcat_imgcat);
			TextView tvnameCate = (TextView) v.findViewById(R.id.listcat_tvCat);
			TextView tvcountView = (TextView) v
					.findViewById(R.id.listcat_tvView);
			TextView tvcountDown = (TextView) v
					.findViewById(R.id.listcat_tvCount);
			if (imgv != null) {
//				imgv.setImageBitmap(DownloadImage.instance.getImage(cate
//						.getUrl()));
				// imgv.setPadding(8, 8, 8, 8);
//				 imgv.setImageBitmap(ConnectServer.instance.m_Listbitmap.get(position));
				 imgv.setImageBitmap(ConnectServer.instance.m_ListbitmapCatOther.get(position));
				// Log.e("position of listbmcatOhter", position+"");
				// Log.e("size of CatOhter", arrlistOtherCat.size()+"");
				// Log.e("id of CatOhter", cate.getId()+"");
				// Log.e("name of CatOhter", cate.getNamecate());
			}
			if (tvnameCate != null) {
				tvnameCate.setText(cate.getNamecate());
			}
			if (tvcountView != null) {
				tvcountView.setText("View:" + " " + cate.getCountView() + "");
			}
			if (tvcountDown != null) {
				tvcountDown.setText("Số lượng:" + " " + cate.getCountDown()
						+ "");
			}
		}
		return v;
	}
}
