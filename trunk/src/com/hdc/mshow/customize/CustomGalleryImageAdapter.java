package com.hdc.mshow.customize;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hdc.mshow.R;
import com.hdc.mshow.model.Album;
import com.hdc.mshow.model.Item;

public class CustomGalleryImageAdapter extends BaseAdapter {
	private Context mContext;
	private ImageView image;
	private LayoutInflater mInflater;
	private int count;
	int layout;
	private ArrayList<Item> arrayItem = new ArrayList<Item>();

	// public static int[] mImageID = { R.drawable.e_1, R.drawable.e_2,
	// R.drawable.e_3,
	// R.drawable.e_4, R.drawable.e_5, R.drawable.e_6, R.drawable.e_7,
	// R.drawable.e_8,
	// R.drawable.e_9, R.drawable.e_10 };

	public CustomGalleryImageAdapter(Context context, int textViewResourceId,
			ArrayList<Item> objects) {
		// super(context, textViewResourceId, objects);
		mContext = context;
		layout = textViewResourceId;
		arrayItem = objects;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return arrayItem.size();
	}

	@Override
	public Object getItem(int position) {
		return arrayItem.get(position);
	}

	@Override
	public long getItemId(final int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View mview = convertView;

		if (mview == null) {
			mview = mInflater.inflate(layout, null);
		}

		if (arrayItem != null && arrayItem.size() > 0) {
			Item item = arrayItem.get(position);
			if (item.getImg() != null) {
				image = (ImageView) mview.findViewById(R.id.imageView1);
				image.setImageBitmap(item.getImg());
				// Toast.instance.show(mContext, position + " bitmap ");
			}
		}

		// Toast.instance.show(mContext, position + "");

		return mview;
	}
}
