package com.archy_assistant_search;

import java.util.HashMap;
import java.util.Map;

//构造Item对象，实现关键字和解释的对应
public class Item {
	
	private String keywords;
	private String description;
	
	public Item() {
		
	}
	
	public Item(String keywords,String description) {
		this.keywords = keywords;
		this.description = description;
	}
	
	public Map<String,Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keywords", keywords);
		map.put("description", description);
		return map;
	}
	
	public String getKeywords() {
		return keywords;
	}
    
	public String getDescription() {
		return description;
	}
	
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
