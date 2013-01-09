package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.activity.ListCatActivity;
import com.example.activity.R;
import com.example.activity.R.id;
import com.example.entity.Item;
import com.example.ultilities.ConnectServer;
import com.example.ultilities.DownloadImage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ListimgAdapter extends ArrayAdapter<Item> {
	Context context;
	Integer resource;
	ArrayList<Item> arrlistitem;

	public ListimgAdapter(Context context, int textViewResourceId,
			ArrayList<Item> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resource = textViewResourceId;
		this.arrlistitem = objects;
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
		Item item = arrlistitem.get(position);
		if (item != null) {
			ImageView imgview = (ImageView) v.findViewById(R.id.image_img);
			if(imgview!=null)
			{
				imgview.setImageBitmap(ConnectServer.instance.m_ListbitmapItem.get(position));
//				imgview.setImageBitmap(DownloadImage.instance.getImage(item.getUrlimg()));
//				if(ListCatActivity.width==240)
//				{
//					imgview.setMaxWidth(240);
//					imgview.setMinimumWidth(240);
//				}
				imgview.setScaleType(ImageView.ScaleType.FIT_XY);			
				
//				imgview.setLayoutParams(new Gallery.LayoutParams(95,70));
			}
		}
		return v;
	}
}
