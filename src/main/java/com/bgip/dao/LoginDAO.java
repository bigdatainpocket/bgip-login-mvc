 package com.bgip.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bgip.constants.BgipConstants;
import com.bgip.constants.StatusCodes;
import com.bgip.exception.FabException;
import com.bgip.model.LoginBean;
import com.bgip.model.ResponseBean;
import com.bgip.model.user.UserBean;
import com.bgip.mongo.MongoManager;


@Component
public class LoginDAO {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginDAO.class);


	@Autowired
	MongoManager mongoManager;
	
	@Autowired
	BgipConstants BgipConstants;
	
	@Autowired 
	StatusCodes statusCodes;
	
	@Autowired	
	UserDAO userDAO;
	
	
	
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
				throw new FabException(StatusCodes.PASSWORD_INCORRECT, " invalid password, Please try again (make sure your caps lock is off).");
			}
		}else{
			LOGGER.info("User Id "+login.getUserName()+" is not exist ::");
			throw new FabException(StatusCodes.USER_ID_NOT_EXIST, "UserId doesn't exist");
		}
	}
	
	
}
