package com.yahoo.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class WeatherPhotoUtil {

	/**
	 * @param currentWeather
	 * @return "@drawable/cam_target"
	 */
	static public String getPhotoDrawable(String currentWeather) {
		HashMap<String, String> weatherImagePath = new HashMap<String, String>();
		String imageUri = "$sunny";
		weatherImagePath.put("Sunny", "@drawable");
		if (weatherImagePath.get(currentWeather) != null) {
			imageUri = weatherImagePath.get(currentWeather);
		}
		return imageUri;
	}
}
