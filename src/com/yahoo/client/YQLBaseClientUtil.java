package com.yahoo.client;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yahoo.model.GoogleNewsDO;

import android.util.Log;

public class YQLBaseClientUtil {
	static public ArrayList<HashMap> toWeatherForecastHashMap(
			JSONObject retResult) {
		ArrayList<HashMap> arrayListWeather = new ArrayList<HashMap>();

		try {
			JSONObject results = retResult.getJSONObject("results");
			// / results/channel/item/description
			if (results != null) {
				JSONObject channel = results.getJSONObject("channel");
				JSONObject item = channel.getJSONObject("item");
				JSONArray forecastArray = item.getJSONArray("forecast");

				int coutforecast = forecastArray.length();
				for (int i = 0; i < coutforecast; i++) {
					/*
					 * code : 31 date : 2 Sep 2014 day : Tue high : 78 low : 58
					 * text : Clear
					 */
					JSONObject row = forecastArray.getJSONObject(i);
					String date = row.optString("date");
					String day = row.optString("day");
					String high = row.optString("high");
					String low = row.optString("low");
					HashMap weatherHashMap = new HashMap<String, String>();
					weatherHashMap.put("date", date);// 3 sep 3014
					weatherHashMap.put("day", day);// wed
					weatherHashMap.put("high", high);// 33
					weatherHashMap.put("low", low);// 25
					arrayListWeather.add(weatherHashMap);

				}
			}
		} catch (Exception e) {
		}
		return arrayListWeather;
	}

	static public ArrayList<HashMap> toNewsHashMap(JSONObject retResult) {
		ArrayList<HashMap> arrayListNews = new ArrayList<HashMap>();

		try {
			JSONObject results = retResult.getJSONObject("results");

			JSONArray rows = results.getJSONArray("a");
			int count = rows.length();

			for (int i = 0; i < count; i++) {

				String content = rows.getJSONObject(i).optString("content");
				String href = rows.getJSONObject(i).optString("href");
				// Log.d("content", content);
				// Log.d("content"+content);
				HashMap<String, String> HashMap = new HashMap<String, String>();
				HashMap.put("content", content);// title
				/*
				 * // Extract the text between the two title elements String
				 * pattern = "(.*)-(.*).html"; String tail =
				 * href.replaceAll(pattern, "-$2.html"); Log.d("tail", tail);
				 * href = "https://tw.news.yahoo.com/" + content.replaceAll("-",
				 * " ") + tail;
				 */
				href = "https://tw.news.yahoo.com/xperia-z3%E9%A0%98%E8%BB%8D-sony%E5%BC%B7%E6%8E%A8%E5%B9%B3%E6%9D%BF-%E6%99%BA%E6%85%A7%E6%89%8B%E9%8C%B6-180612656.html";
				HashMap.put("href", href);
				Log.d("href", href);
				arrayListNews.add(HashMap);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayListNews;
	}

	/**
	 * @param retResult
	 * @return
	 */
	static public ArrayList<GoogleNewsDO> toGoogleNewsHashMap(JSONObject retResult) {
		ArrayList<GoogleNewsDO> arrayListNews = new ArrayList<GoogleNewsDO>();

		try {
			/*
			 * responseData
  results
    titleNoFormatting

			 json":{"responseData":{"results
			 */
			Log.d("resultsOrg", retResult.toString());
			JSONObject resultsOrg = retResult.getJSONObject("results");
			Log.d("resultsOrg", resultsOrg.toString());
				JSONObject json = resultsOrg.getJSONObject("json");
				Log.d("json", json.toString());
				
			JSONObject responseData = json.getJSONObject("responseData");
			Log.d("responseData", responseData.toString());
			
		    JSONArray results = responseData.getJSONArray("results");
			Log.d("results", results.toString());
			int count = results.length();

			for (int i = 0; i < count; i++) {

				String title = results.getJSONObject(i).optString("titleNoFormatting");
				String url = results.getJSONObject(i).optString("url");
				String content = results.getJSONObject(i).optString("content");
				// Log.d("content", content);
				// Log.d("content"+content);
				HashMap<String, String> GoogleNewsDOHashMap = new HashMap<String, String>();
				GoogleNewsDOHashMap.put("title", title);// title
				GoogleNewsDOHashMap.put("url", url);// title
				GoogleNewsDOHashMap.put("content", content);// title
				GoogleNewsDO GoogleNewsDO = new GoogleNewsDO(GoogleNewsDOHashMap);
				arrayListNews.add(GoogleNewsDO);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayListNews;
	}

	
	static public String toNewsString(JSONObject retResult) {
		String newsContent = "";

		try {
			JSONObject results = retResult.getJSONObject("results");

			JSONArray rows = results.getJSONArray("p");
			int count = rows.length();
			for (int i = 0; i < count; i++) {
				try {
				 rows.getJSONObject(i);
					//newsContent += tmp;
					
				} catch (Exception e) {
					String tmp = rows.getString(i);
					newsContent += tmp;
				//	break;

				}
				
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("newsContent", newsContent);
		return newsContent;

	}
}
