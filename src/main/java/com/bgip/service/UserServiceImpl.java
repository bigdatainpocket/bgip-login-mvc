package com.bgip.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bgip.dao.UserDAO;
import com.bgip.model.LoginBean;
import com.bgip.model.ResponseBean;
import com.bgip.model.user.UserBean;

@Service
public class UserServiceImpl implements UserService{

	@Autowired UserDAO userDao;
	
	
	public ResponseBean login(LoginBean login) throws Exception {
		return userDao.login(login);
	}

	@Override
	public LoginBean socialLogin(LoginBean login) throws Exception {
		return userDao.socialLogin(login);
	}


	@Override
	public ResponseBean userRegister(UserBean userBean) throws Exception {
	
		return userDao.userRegister(userBean);
	}

	@Override
	public ResponseBean updateUser(UserBean userBean) throws Exception {
		// TODO Auto-generated method stub
		return userDao.updateUser(userBean);
	}

	@Override
	public UserBean findByUserName(String userName) throws Exception {
		
		return userDao.find(userName);
	}

	@Override
	public List<UserBean> getAllUsers() throws Exception {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}

	
	
	
}
