package com.bgip.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bgip.constants.BgipConstants;
import com.bgip.constants.StatusCodes;
import com.bgip.exception.BgipException;
import com.bgip.model.LoginBean;
import com.bgip.model.ResponseBean;
import com.bgip.model.user.Gender;
import com.bgip.model.user.UserBean;
import com.bgip.mongo.MongoManager;

@Component
public class UserDaoImpl  extends BaseDAO implements UserDAO {

	   private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
	@Autowired
	MongoManager mongoManager;

	@Autowired
	BgipConstants BgipConstants;

	@Autowired
	StatusCodes statusCodes;
	
	
	public ResponseBean login(LoginBean login) throws Exception{
		LOGGER.info("login API DAO call :: ");
        ResponseBean response = new ResponseBean(StatusCodes.SUCCESS_MESSAGE);

		//Check whether the user is exist or not
		UserBean userBeanFromDB = mongoManager.getObjectByField(BgipConstants.USER_COLLECTION, "userName",
				login.getUserName(), UserBean.class);
		if(userBeanFromDB != null){

			if(userBeanFromDB.getPassword().equals(login.getPassword())){
				login.setFirstName(userBeanFromDB.getFirstName());
				login.setLastName(userBeanFromDB.getLastName());
				return response;
			}else{
				LOGGER.info("User Id "+login.getUserName()+" password is incorrect ::");
				throw new BgipException(StatusCodes.PASSWORD_INCORRECT, " invalid password, Please try again (make sure your caps lock is off).");
			}
		}else{
			LOGGER.info("User Id "+login.getUserName()+" is not exist ::");
			throw new BgipException(StatusCodes.USER_ID_NOT_EXIST, "UserId doesn't exist");
		}
	}
	
	

	// Validate Email
	public void validateEmail(String email) throws Exception {
		UserBean userBeanFromDB = mongoManager.getObjectByField(com.bgip.constants.BgipConstants.USER_COLLECTION,
				"email", email, UserBean.class);

		if (userBeanFromDB != null) {
			if (userBeanFromDB.getEmail().equals(email)) {
				throw new BgipException(StatusCodes.USER_EMAIL_ID_EXIST, " This email is already registered.  ");
			}
		}
	}

	// Validate User
	public void validateUserName(String userName) throws Exception {
		UserBean userBeanFromDB = mongoManager.getObjectByField(com.bgip.constants.BgipConstants.USER_COLLECTION,
				"userName", userName, UserBean.class);
		if (userBeanFromDB != null) {
			if (userBeanFromDB.getUserName().equals(userName)) {
				throw new BgipException(StatusCodes.USER_ID_EXIST, " UserName already taken, Try Again ");
			}
		}
	}

	public ResponseBean userRegister(UserBean newUser) throws Exception {
		ResponseBean response = new ResponseBean(StatusCodes.SUCCESS_MESSAGE);
		UserBean userBeanFromDB = mongoManager.getObjectByField(com.bgip.constants.BgipConstants.USER_COLLECTION,
				"userName", newUser.getUserName(), UserBean.class);

		validateUserName(newUser.getUserName());
		validateEmail(newUser.getEmail());
		if (userBeanFromDB == null) {
			insertDB(com.bgip.constants.BgipConstants.USER_COLLECTION, newUser);
		} else {
			throw new BgipException(StatusCodes.USER_ID_EXIST, " Error In User Registration");
		}

		return response;
	}

	// user Registration
	// public ResponseBean userRegister(UserBean userBean) throws Exception {
	// ResponseBean response = new ResponseBean(StatusCodes.SUCCESS_MESSAGE);
	// UserBean userBeanFromDB = mongoManager.getObjectByField(
	// com.bgip.constants.BgipConstants.USER_COLLECTION, "userName",
	// userBean.getUserName(), UserBean.class);
	//
	// if (userBeanFromDB == null) {
	// mongoManager.insert(com.bgip.constants.BgipConstants.USER_COLLECTION,
	// userBean);
	//
	// } else {
	// // UserId already exist
	// throw new BgipException(StatusCodes.USER_ID_EXIST,
	// "UserName Already exist");
	// }
	// return response;
	// }

	// update User Name
	public ResponseBean updateUser(UserBean userBean) throws Exception {
		UserBean userBeanFromDB = mongoManager.getObjectByField(com.bgip.constants.BgipConstants.USER_COLLECTION,
				"userName", userBean.getUserName(), UserBean.class);

		ResponseBean response = new ResponseBean(StatusCodes.SUCCESS_MESSAGE);
		if (userBeanFromDB != null) {
			userBeanFromDB.setFirstName(userBean.getFirstName());
			userBeanFromDB.setLastName(userBean.getLastName());
			mongoManager.update(com.bgip.constants.BgipConstants.USER_COLLECTION, userBeanFromDB);
		} else {
			// UserId not exist
			throw new BgipException(StatusCodes.USER_ID_NOT_EXIST, "UserId doesn't exist");
		}
		return response;
	}

	// User Find
	public UserBean find(String userName) throws Exception {
		UserBean userBeanFromDB = mongoManager.getObjectByField(com.bgip.constants.BgipConstants.USER_COLLECTION,
				"userName", userName, UserBean.class);

		if (userBeanFromDB != null) {
			userBeanFromDB.setPassword(null);
			return userBeanFromDB;
		}
		return null;
	}

	// get All Friends
	public List<UserBean> findAll() throws Exception {

		List<UserBean> userBeanList = mongoManager.getAllObjects(com.bgip.constants.BgipConstants.USER_COLLECTION,
				UserBean.class);
		for (UserBean user : userBeanList) {
			user.setPassword(null);
		}

		return userBeanList;
	}
	
	
	public ResponseBean createPassword(String email,String newPassword)throws Exception{
		ResponseBean response = new ResponseBean(StatusCodes.SUCCESS_MESSAGE);
		LoginBean loginBeanFromDB = mongoManager.getObjectByField(com.bgip.constants.BgipConstants.USER_COLLECTION,
				"email", email, LoginBean.class);
		
		if( loginBeanFromDB != null){
			try{
			mongoManager.updateByField(com.bgip.constants.BgipConstants.USER_COLLECTION, "email", email, "password", newPassword);
			}catch (Exception e){
				throw new BgipException(StatusCodes.USER_ID_NOT_EXIST, " Error in createPassword method Call..");
			}
		}
		return response;
	}
	
	
	
	
	//getUser Details By Email
		public LoginBean socialLogin(LoginBean loginBean) throws Exception {
			LoginBean loginBeanFromDB = mongoManager.getObjectByField(com.bgip.constants.BgipConstants.USER_COLLECTION,
					"email", loginBean.getEmail(), LoginBean.class);
			try{
				if( loginBeanFromDB == null){
					UserBean user = new UserBean();
					user.setEmail(loginBean.getEmail());
					if( loginBean.getFirstName() != null){
						user.setFirstName(loginBean.getFirstName());
					}
					if(loginBean.getLastName() != null){
						user.setLastName(loginBean.getLastName());
					}
					user.setUserName(loginBean.getEmail());
					user.setGender(Gender.MALE);
					user.setPassword("# #");
					insertDB(com.bgip.constants.BgipConstants.USER_COLLECTION, user);
					
				}
			}catch ( Exception e){
				throw new BgipException(StatusCodes.USER_ID_EXIST, " Error In SocialLogin Registration");
			}
			
			return mongoManager.getObjectByField(com.bgip.constants.BgipConstants.USER_COLLECTION,
					"email", loginBean.getEmail(), LoginBean.class);
			
		}


}