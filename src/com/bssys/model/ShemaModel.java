package com.bssys.model;

import java.util.List;

import com.bssys.form.ShemaForm;

public class ShemaModel {
	
	private List<String> shemas;
	private ShemaForm shemaForm;

	public ShemaForm getShemaForm() {
		return shemaForm;
	}

	public void setShemaForm(ShemaForm shemaForm) {
		this.shemaForm = shemaForm;
	}

	public List<String> getShemas() {
		return shemas;
	}

	public void setShemas(List<String> shemas) {
		this.shemas = shemas;
	}
	

}
