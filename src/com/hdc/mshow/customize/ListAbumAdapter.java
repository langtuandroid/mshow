package com.hdc.mshow.customize;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdc.mshow.R;
import com.hdc.mshow.model.Album;

public class ListAbumAdapter extends ArrayAdapter<Album> {

	private Context context;
	private int resourse;
	private ArrayList<Album> arraylist;

	public ListAbumAdapter(Context context, int textViewResourceId, ArrayList<Album> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resourse = textViewResourceId;
		this.arraylist = objects;
	}

	public void setList(ArrayList<Album> objects) {
		this.arraylist = objects;
	}

	public Album getItems(int position) {
		return arraylist.get(position);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View v = convertView;
		if (v == null) {
			LayoutInflater layout = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = layout.inflate(resourse, null);
		}

		Album item = arraylist.get(position);

		if (item != null) {
			// TODO Title
			TextView txt_title = (TextView) v.findViewById(R.id.title);
			txt_title.setText(item.getTitle());

			// TODO View
			TextView txt_View = (TextView) v.findViewById(R.id.view);
			txt_View.setText("View " + item.getHit());

			// TODO Number image in Album
			TextView txt_Number = (TextView) v.findViewById(R.id.number);
			txt_Number.setText("Số lượng " + item.getTotal_image());

			// TODO Bitmap
			ImageView img = (ImageView) v.findViewById(R.id.item_img);
			if (item.getImg() != null)
				img.setImageBitmap(item.getImg());
		}

		return v;
	}
}