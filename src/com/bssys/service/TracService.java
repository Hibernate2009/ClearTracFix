package com.bssys.service;

import java.util.Date;
import java.util.List;

import com.bssys.bo.DirectoryBO;


public interface TracService {
	
	public List<String> getAllShemas();
	
	public List<String> getUsers(String shema);
	
	public List<DirectoryBO> getDirectories(String shema, Integer ticket, String author, Date date);
	
	public void updateTime(String shema,Integer ticket, Long lastTime, Long newTime);
	
	public String generateScript(List<DirectoryBO> list);
}
