package com.bgip.service;


import java.util.List;

import com.bgip.model.LoginBean;
import com.bgip.model.ResponseBean;
import com.bgip.model.user.UserBean;

public interface UserService {

	
	 public ResponseBean login(LoginBean login) throws Exception ;
	
	  public LoginBean socialLogin(LoginBean login) throws Exception ;
	  
	  public ResponseBean userRegister(UserBean userBean) throws Exception;
	  
	  public ResponseBean updateUser(UserBean userBean)throws Exception;
	  
	  public UserBean findByUserName(String userName) throws Exception;
	  
	   public List<UserBean> getAllUsers() throws Exception;
	
}
