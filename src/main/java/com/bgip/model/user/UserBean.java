package com.bgip.model.user;

public class UserBean extends BaseBean {

	private static final long serialVersionUID = 1746361140054130141L;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String birthday;
	private Gender gender;
	private boolean termsConditions;
	private String privacy="public";
	private String profileImg;
	
	
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public String getPrivacy() {
		return privacy;
	}
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public boolean isTermsConditions() {
		return termsConditions;
	}
	public void setTermsConditions(boolean termsConditions) {
		this.termsConditions = termsConditions;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	 
	
	
	
	
	
	
}
