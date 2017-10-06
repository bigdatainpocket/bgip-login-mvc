package com.bgip.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bgip.model.LoginBean;
import com.bgip.model.ResponseBean;
import com.bgip.model.user.UserBean;

@Transactional
public interface UserDAO {

	
	public ResponseBean login( LoginBean login) throws Exception;
	
	public ResponseBean userRegister( UserBean user) throws Exception;
	
	public ResponseBean updateUser( UserBean user) throws Exception;
	
	public UserBean find(String userName) throws Exception ;
	
	public List<UserBean> findAll() throws Exception;
	
	public ResponseBean createPassword(String email,String newPassword)throws Exception;
	
	public LoginBean socialLogin(LoginBean loginBean) throws Exception;
	
	
}
