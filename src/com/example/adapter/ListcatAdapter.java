package com.example.adapter;

import java.util.ArrayList;

import com.example.activity.ListCatActivity;
import com.example.activity.R;
import com.example.entity.Category;
import com.example.ultilities.ConnectServer;
import com.example.ultilities.DownloadImage;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListcatAdapter extends ArrayAdapter<Category> {

	Context context;
	Integer resource;
	ArrayList<Category> arrlistcat;

	public ListcatAdapter(Context context, int textViewResourceId,
			ArrayList<Category> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resource = textViewResourceId;
		this.arrlistcat = objects;
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

		Category cate = arrlistcat.get(position);
		if (cate != null) {
			ImageView imgv = (ImageView) v.findViewById(R.id.listcat_imgcat);
			TextView tvnameCate = (TextView) v.findViewById(R.id.listcat_tvCat);
			TextView tvcountView = (TextView) v
					.findViewById(R.id.listcat_tvView);
			TextView tvcountDown = (TextView) v
					.findViewById(R.id.listcat_tvCount);
			if (imgv != null) {

				// if (ConnectServer.instance.m_ListbitmapCat != null) {
				imgv.setImageBitmap(ConnectServer.instance.m_ListbitmapCat
						.get(position));
				//
				// }
				// else {
//				 imgv.setImageBitmap(DownloadImage.instance.getImage(cate
//				 .getUrl()));
				// }
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
