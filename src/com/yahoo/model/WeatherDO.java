package com.yahoo.model;

import java.util.HashMap;

public class WeatherDO {
	public String content = "";
	public String href ="";
	/**
	 * @param content
	 * @param href
	 */
	public WeatherDO(HashMap input)
	{
		this.content = (String) input.get("content");
		this.href = (String) input.get("href");
	}


}

