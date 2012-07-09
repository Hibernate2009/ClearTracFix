package com.bssys.bo;

public class DirectoryBO {
	
	private String shema;
	private Integer ticket;
	private String author;
	private String time;
	private long unixtime;
	private String hour;
	
	
	public long getUnixtime() {
		return unixtime;
	}
	public void setUnixtime(long unixtime) {
		this.unixtime = unixtime;
	}
	public String getShema() {
		return shema;
	}
	public void setShema(String shema) {
		this.shema = shema;
	}
	public Integer getTicket() {
		return ticket;
	}
	public void setTicket(Integer ticket) {
		this.ticket = ticket;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	@Override
	public String toString() {
		return "DirectoryBO [shema=" + shema + ", ticket=" + ticket + ", author=" + author + ", time=" + time + ", unixtime=" + unixtime + ", hour=" + hour
				+ "]";
	}
	
	
	
	
	

}
