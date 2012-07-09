package com.bssys.model;

import java.util.List;

import com.bssys.bo.DirectoryBO;
import com.bssys.form.DirectoryForm;

public class DirectoryModel {
	
	private List<DirectoryBO> directories;
	private DirectoryForm directoryForm;

	public DirectoryForm getDirectoryForm() {
		return directoryForm;
	}
	public void setDirectoryForm(DirectoryForm directoryForm) {
		this.directoryForm = directoryForm;
	}
	public List<DirectoryBO> getDirectories() {
		return directories;
	}
	public void setDirectories(List<DirectoryBO> directories) {
		this.directories = directories;
	}

}
