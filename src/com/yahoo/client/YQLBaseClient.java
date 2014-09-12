package com.yahoo.client;

public class YQLBaseClient {

	/**
	 * @param woeid
	 */
	public void getWeather(String woeid, CallBackListener listener) {
		YQLBaseClientThread YQLBaseClientThread = new YQLBaseClientThread();
		YQLBaseClientThread.setListener(listener);
		YQLBaseClientThread.getWeather(woeid);
	}
	public void getNewContentUrl(String newsUrl, CallBackListener listener)
	{
		YQLBaseClientThread YQLBaseClientThread = new YQLBaseClientThread();
		YQLBaseClientThread.setListener(listener);
		YQLBaseClientThread.getNewContentUrl(newsUrl);
	}
	
	
	public void getNews(CallBackListener listener) {
		YQLBaseClientThread YQLBaseClientThread = new YQLBaseClientThread();
		YQLBaseClientThread.setListener(listener);
		YQLBaseClientThread.getNews();
	}
	
	public void getGoogleNews(CallBackListener listener) {
		YQLBaseClientThread YQLBaseClientThread = new YQLBaseClientThread();
		YQLBaseClientThread.setListener(listener);
		YQLBaseClientThread.getGoogleNews();
	}
	
	
}
