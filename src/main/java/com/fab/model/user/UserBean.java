package com.fab.model.user;

public class UserBean extends BaseBean {

	
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int phone;
	private String birthday;
	private char gender;
	private boolean termsConditions;
	
	
	//private String profile_name;
	//private String status;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public boolean isTermsConditions() {
		return termsConditions;
	}
	public void setTermsConditions(boolean termsConditions) {
		this.termsConditions = termsConditions;
	}
	 
	
	
	
	
	
	
}
