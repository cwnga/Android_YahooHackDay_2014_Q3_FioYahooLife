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
public class WeatherHashMapArrayAdapter extends ArrayAdapter<HashMap> {

	Context mContext;
	int layoutResourceId;
	ArrayList data = null;

	public WeatherHashMapArrayAdapter(Context mContext, int layoutResourceId,
			ArrayList<HashMap> arrayListWeather) {

		super(mContext, layoutResourceId, arrayListWeather);

		this.layoutResourceId = layoutResourceId;
		this.mContext = mContext;
		this.data = arrayListWeather;
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

		/*
		 * // object item based on the position NewsDO objectItem = (NewsDO)
		 * data.get(position); Log.d("dddd", objectItem.toString());
		 * 
		 * // get the TextView and then set the text (item name) and tag (item
		 * ID) // values TextView textViewItem = (TextView) convertView
		 * .findViewById(R.id.new_title); if (objectItem != null) {
		 * textViewItem.setText(objectItem.content); }
		 */
		HashMap<String, String> HashMap = (HashMap) data.get(position);

		String highTemp = HashMap.get("high");
		String lowTemp = HashMap.get("low");
		String tempRang = lowTemp + "~" + highTemp;

		TextView textViewItem = (TextView) convertView
				.findViewById(R.id.highC);
		textViewItem.setText(highTemp+"℃");
		TextView textViewItem2 = (TextView) convertView
				.findViewById(R.id.smailC);
		textViewItem2.setText(lowTemp +"℃");

		return convertView;

	}

}