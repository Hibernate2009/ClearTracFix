package com.bssys.form;

import java.io.Serializable;

public class UserForm implements Serializable{
	
	private String user;
	private String date;
	private Integer ticket;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getTicket() {
		return ticket;
	}
	public void setTicket(Integer ticket) {
		this.ticket = ticket;
	}
	
}
