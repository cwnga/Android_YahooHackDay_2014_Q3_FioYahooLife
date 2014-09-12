package com.yahoo.model;

import java.util.HashMap;

public class GoogleNewsDO {
	public String title = "";
	public String url ="";
	public String content ="";
	/**
	 * @param content
	 * @param href
	 */
	public GoogleNewsDO(HashMap input)
	{
		this.title = (String) input.get("title");
		this.url = (String) input.get("url");
		this.content = (String) input.get("content");
		
	}


}

