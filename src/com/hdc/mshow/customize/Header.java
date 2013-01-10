package com.hdc.mshow.customize;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.hdc.mshow.ListAlbumActivity;
import com.hdc.mshow.R;
import com.hdc.mshow.service.ServiceSMS;

public class Header extends LinearLayout {

	Context c;

	// TODO Control
	Button bt_Search;
	Button bt_Menu;
	Button bt_Search_Main;
	EditText txt_Search;

	// TODO Flag Layout search
	// true : enable
	// false : disable
	boolean flag = false;

	// TODO AlertDialog
	AlertDialog alert;

	public Header(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		c = context;
	}

	// TODO Init Layout
	public void initLayout() {
		LinearLayout v = (LinearLayout) ((Activity) c).findViewById(R.id.header);
		initDialog_Category();
		initAll_Control(v);
	}

	// TODO init all control
	private void initAll_Control(View v) {
		final LinearLayout layout_search = (LinearLayout) v.findViewById(R.id.layout_search);

		bt_Search = (Button) v.findViewById(R.id.bt_Search);
		bt_Menu = (Button) v.findViewById(R.id.bt_Menu);
		bt_Search_Main = (Button) v.findViewById(R.id.bt_Search_Main);
		txt_Search = (EditText) v.findViewById(R.id.txt_search);

		// SEARCH
		bt_Search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.instance.show(c, "bt search");
				if (flag) {
					layout_search.setVisibility(View.GONE);
					flag = false;
				} else {
					layout_search.setVisibility(View.VISIBLE);
					flag = true;
				}
			}
		});

		// MENU
		bt_Menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.instance.show(c, "bt menu");
				alert.show();
			}
		});

		// SEARCH MAIN
		bt_Search_Main.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.instance.show(c, "bt search main");

				if (!ListAlbumActivity.instance.isExcuting) {
					// lấy dữ liệu từ Search
					ServiceSMS.instance.getAlbum_Search("", txt_Search.getText().toString());
					ListAlbumActivity.instance.updateListView();
				} else {
					Toast.instance.show(c, "Đang lấy dữ liệu Album .... ");
				}
			}
		});
	}

	// TODO init dialog
	private void initDialog_Category() {
		// TODO Data
		int n = ServiceSMS.instance.m_ListCategory.size();
		String[] data = new String[n + 1];
		data[0] = "Tất cả";
		for (int i = 0; i < n; i++) {
			data[i + 1] = ServiceSMS.instance.m_ListCategory.get(i).getName();
		}
		// TODO Adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,
				android.R.layout.simple_list_item_1, data);
		// Builder dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(c);
		builder.setTitle("Danh mục album :");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int position) {
				// TODO Auto-generated method stub
				String catId = "";
				if (position != 0)
					catId = ServiceSMS.instance.m_ListCategory.get(position - 1).id;

				if (!ListAlbumActivity.instance.isExcuting) {
					// lấy dữ liệu từ Danh mục
					ServiceSMS.instance.getAlbum_Search(catId, "");
					ListAlbumActivity.instance.updateListView();
				} else {
					Toast.instance.show(c, "Đang lấy dữ liệu Album .... ");
				}
			}
		});
		alert = builder.create();
	}

}
