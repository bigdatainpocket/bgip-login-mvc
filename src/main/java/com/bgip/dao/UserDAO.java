package com.bgip.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bgip.constants.BgipConstants;
import com.bgip.constants.StatusCodes;
import com.bgip.exception.FabException;
import com.bgip.model.ResponseBean;
import com.bgip.model.user.UserBean;
import com.bgip.mongo.MongoManager;

@Component
public class UserDAO extends BaseDAO{

	@Autowired
	MongoManager mongoManager;

	@Autowired
	BgipConstants BgipConstants;

	@Autowired
	StatusCodes statusCodes;

	
	
	
	// Validate Email
			public void validateEmail(String email) throws Exception {
				UserBean userBeanFromDB = mongoManager.getObjectByField(
						com.bgip.constants.BgipConstants.USER_COLLECTION, "email",
						email, UserBean.class);
				
				if( userBeanFromDB != null){
					if( userBeanFromDB.getEmail().equals(email)){
						throw new FabException(StatusCodes.USER_EMAIL_ID_EXIST,
								" This email is already registered.  ");
					}
				}
			}
	
	
	// Validate User
		public void validateUserName(String userName) throws Exception {
			UserBean userBeanFromDB = mongoManager.getObjectByField(
					com.bgip.constants.BgipConstants.USER_COLLECTION, "userName",
					userName, UserBean.class);
			if( userBeanFromDB != null){
				if( userBeanFromDB.getUserName().equals(userName)){
					throw new FabException(StatusCodes.USER_ID_EXIST,
							" UserName already taken, Try Again ");
				}
			}
		}
		
		public ResponseBean userRegister(UserBean newUser) throws Exception {
			ResponseBean response = new ResponseBean(StatusCodes.SUCCESS_MESSAGE);
			UserBean userBeanFromDB = mongoManager.getObjectByField(
					com.bgip.constants.BgipConstants.USER_COLLECTION, "userName",
					newUser.getUserName(), UserBean.class);
			
			validateUserName(newUser.getUserName());
			validateEmail(newUser.getEmail());
			if (userBeanFromDB == null) {
				insertDB(com.bgip.constants.BgipConstants.USER_COLLECTION, newUser);
			} else {
				throw new FabException(StatusCodes.USER_ID_EXIST," Error In User Registration");
			}
			
			return response;
		}
	
		
		
		
		
		
		
		
		
		
	// user Registration
//	public ResponseBean userRegister(UserBean userBean) throws Exception {
//		ResponseBean response = new ResponseBean(StatusCodes.SUCCESS_MESSAGE);
//		UserBean userBeanFromDB = mongoManager.getObjectByField(
//				com.bgip.constants.BgipConstants.USER_COLLECTION, "userName",
//				userBean.getUserName(), UserBean.class);
//		
//		if (userBeanFromDB == null) {
//			mongoManager.insert(com.bgip.constants.BgipConstants.USER_COLLECTION, userBean);
//
//		} else {
//			// UserId already exist
//			throw new FabException(StatusCodes.USER_ID_EXIST,
//					"UserName Already exist");
//		}
//		return response;
//	}

	// update User Name
	public ResponseBean updateUser(UserBean userBean) throws Exception {
		UserBean userBeanFromDB = mongoManager.getObjectByField(
				com.bgip.constants.BgipConstants.USER_COLLECTION, "userName",
				userBean.getUserName(), UserBean.class);

		ResponseBean response = new ResponseBean(StatusCodes.SUCCESS_MESSAGE);
		if (userBeanFromDB != null) {
			userBeanFromDB.setFirstName(userBean.getFirstName());
			userBeanFromDB.setLastName(userBean.getLastName());
			mongoManager.update(com.bgip.constants.BgipConstants.USER_COLLECTION, userBeanFromDB);
		} else {
			// UserId not exist
			throw new FabException(StatusCodes.USER_ID_NOT_EXIST,
					"UserId doesn't exist");
		}
		return response;
	}

	// User Find
	public UserBean find(String userName) throws Exception {
		UserBean userBeanFromDB = mongoManager.getObjectByField(
				com.bgip.constants.BgipConstants.USER_COLLECTION, "userName",
				userName, UserBean.class);

		if (userBeanFromDB != null) {
			userBeanFromDB.setPassword(null);
			return userBeanFromDB;
		}
		return null;
	}

	
	
	
	// get All Friends
	public List<UserBean> findAll() throws Exception {

		List<UserBean> userBeanList = mongoManager.getAllObjects(
				com.bgip.constants.BgipConstants.USER_COLLECTION, UserBean.class);
		for (UserBean user : userBeanList) {
			user.setPassword(null);
		}

		return userBeanList;
	}

}