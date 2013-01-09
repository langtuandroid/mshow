package com.example.adapter;

import java.util.ArrayList;

import com.example.activity.R;
import com.example.entity.CateTitle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Custom_ListSpin extends ArrayAdapter<CateTitle>{

	Context context;
	Integer resource;
	ArrayList< CateTitle> arrayList;
	public Custom_ListSpin(Context context, int textViewResourceId,
			ArrayList<CateTitle> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.resource=textViewResourceId;
		this.arrayList=objects;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v=convertView;
		if(v==null)
		{
			LayoutInflater inflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=inflater.inflate(resource, null);			
		}
		CateTitle cateTitle=arrayList.get(position);
		if(cateTitle!=null)
		{
			TextView textView=(TextView) v.findViewById(R.id.textspin);
			if(textView!=null)
			{
				textView.setText(cateTitle.getName());
			}
		}
		return v;
	}
}
