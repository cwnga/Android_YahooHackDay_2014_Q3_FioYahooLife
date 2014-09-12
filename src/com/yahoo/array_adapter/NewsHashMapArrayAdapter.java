package com.yahoo.array_adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.blundell.tut.cameraoverlay.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// here's our beautiful adapter
//title
//href
public class NewsHashMapArrayAdapter extends ArrayAdapter<HashMap> {

	Context mContext;
	int layoutResourceId;
	ArrayList data = null;

	public NewsHashMapArrayAdapter(Context mContext, int layoutResourceId,
			ArrayList<HashMap> data) {

		super(mContext, layoutResourceId, data);

		this.layoutResourceId = layoutResourceId;
		this.mContext = mContext;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		/*
		 * The convertView argument is essentially a "ScrapView" as described is
		 * Lucas post
		 * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
		 * It will have a non-null value when ListView is asking you recycle the
		 * row layout. So, when convertView is not null, you should simply
		 * update its contents instead of inflating a new row layout.
		 */
		if (convertView == null) {
			// inflate the layout
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}

		// object item based on the position
		HashMap newsHashMap =  (HashMap) data.get(position);
		

		// get the TextView and then set the text (item name) and tag (item ID)
		// values
		TextView textViewItem = (TextView) convertView
				.findViewById(R.id.news_title);
		if (newsHashMap.size() > 0) {
			textViewItem.setText((String)newsHashMap.get("content"));
		}

		return convertView;

	}

}