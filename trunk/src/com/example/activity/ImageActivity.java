package com.example.activity;

import java.util.ArrayList;

import com.example.adapter.ListBigimgAdapter;
import com.example.entity.Item;
import com.example.ultilities.ConnectServer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageActivity extends Activity {
	Gallery gallery;
	ListBigimgAdapter adapter;
	ArrayList<Item> arrlistitem=new ArrayList<Item>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		
		arrlistitem=ConnectServer.instance.m_ListItem;
		//find component
		gallery=(Gallery) findViewById(R.id.image_gallery);
		//set adapter
		adapter=new ListBigimgAdapter(ImageActivity.this, R.layout.image2, arrlistitem);
		
		gallery.setAdapter(adapter);
		
//		gallery.setMinimumWidth(ListCatActivity.instance.width);
//		gallery.setMinimumHeight(ListCatActivity.instance.height);
				
//		gallery.set
		gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {					

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
}
