package com.fab.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fab.constants.FabConstants;
import com.fab.constants.StatusCodes;
import com.fab.model.LoginBean;
import com.fab.model.ResponseBean;
import com.fab.model.user.UserBean;
import com.fab.mongo.MongoManager;
import com.fab.resources.LoginResource;


@Component
public class LoginDAO {

	@Autowired
	MongoManager mongoManager;
	
	@Autowired
	FabConstants fabConstants;
	
	@Autowired 
	StatusCodes statusCodes;
	
	
	
	
	public ResponseBean login(LoginBean loginBean) throws Exception{
       // LOGGER.info("login API DAO call :: ");

        //Check whether the user is exist or not
        UserBean userBeanFromDB = mongoManager.getObjectByField(fabConstants.USER_COLLECTION, "userName", loginBean.getUserName(), UserBean.class);

        ResponseBean response = new ResponseBean();
        if(userBeanFromDB != null){
            if(userBeanFromDB.getPassword().equals(loginBean.getPassword())){
                response.setStatusCode(statusCodes.SUCCESS);
            }else{
           //     LOGGER.info("User Id "+loginBean.getUserName()+" password is incorrect ::");
                response.setStatusCode(statusCodes.PASSWORD_INCORRECT);
            }
        }else{
       //     LOGGER.info("User Id "+loginBean.getUserName()+" is not exist ::");
            response.setStatusCode(statusCodes.USER_DOES_NOTEXIST);
        }
        return response;
    }

	
	
	
	
	
	
	
	
	public List<UserBean> findAll(){

		try {
		//	return mongoManager.getAllObjects("user", UserBean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
