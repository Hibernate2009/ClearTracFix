package com.bssys.model;

import java.util.List;

import com.bssys.form.UserForm;

public class UserModel {

	private List<String> users;
	private UserForm userForm;

	public UserForm getUserForm() {
		return userForm;
	}

	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

}
