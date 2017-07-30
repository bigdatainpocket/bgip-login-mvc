package com.fab.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.fab.constants.FabConstants;
import com.fab.constants.StatusCodes;
import com.fab.model.ResponseBean;
import com.fab.model.user.UserBean;
import com.fab.mongo.MongoManager;


@Component
public class UserDAO {

	@Autowired
	MongoManager mongoManager;
	
	@Autowired
	FabConstants fabConstants;
	
	@Autowired 
	StatusCodes statusCodes;
	//user Registration
    public ResponseBean userRegister(UserBean userBean) throws Exception{
        
        //Check whether the user is exist or not
        UserBean userBeanFromDB = mongoManager.getObjectByField(fabConstants.USER_COLLECTION, "userName", userBean.getUserName(), UserBean.class);
        ResponseBean response = new ResponseBean();
        if(userBeanFromDB == null){
            mongoManager.insert("user", userBean);
            response.setStatusCode(statusCodes.SUCCESS);
        }else{
            //UserId already exist
            response.setStatusCode(statusCodes.USER_ID_EXIST);
        }
        return response;
    }
	
		
	// User DELETE
	
	public ResponseBean deleteUser(UserBean userBean) throws Exception{

		//Check whether the user is exist or not
		UserBean userBeanFromDB = mongoManager.getObjectByField(fabConstants.USER_COLLECTION, "userName", userBean.getUserName(), UserBean.class);
		
		//System.out.println("from userDAO :::"+userBeanFromDB.getUserName());
		ResponseBean response = new ResponseBean();
		
		if(userBeanFromDB != null){
			
			mongoManager.delete(fabConstants.USER_COLLECTION, userBeanFromDB);
			response.setStatusCode(statusCodes.USER_DELETED_SUCCESSFULLY);
		}else{
			//UserId not exist
			response.setStatusCode(statusCodes.USER_DOES_NOTEXIST);
			System.out.println("failed");
		}
		return response;
	}
	
	
	public ResponseBean updateUser(UserBean userBean) throws Exception{

		UserBean userBeanFromDB = mongoManager.getObjectByField(fabConstants.USER_COLLECTION, "userName", userBean.getUserName(), UserBean.class);
		
		//System.out.println("from userDAO :::"+userBeanFromDB.getUserName());
		ResponseBean response = new ResponseBean();
		
		if(userBeanFromDB != null){
				userBeanFromDB.setFirstName(userBean.getFirstName());
				userBeanFromDB.setLastName(userBean.getLastName());
				userBeanFromDB.setEmail(userBean.getEmail());
				userBeanFromDB.setPhone(userBean.getPhone());
				userBeanFromDB.setBirthday(userBean.getBirthday());
				userBeanFromDB.setGender(userBean.getGender());
				
				mongoManager.insert(fabConstants.USER_COLLECTION, userBeanFromDB);
				response.setStatusCode(statusCodes.SUCCESS);
			System.out.println("update info :::"+userBeanFromDB.getFirstName());
			
		}else{
			//UserId not exist
			response.setStatusCode(statusCodes.USER_DOES_NOTEXIST);
			System.out.println("failed");
		}
		return response;
	}

	
	
	
	
	
	
	
	
	
	
}












