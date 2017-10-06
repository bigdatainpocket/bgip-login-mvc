package com.bgip.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.bgip.dao.UserDaoImpl;
import com.bgip.exception.BgipException;
import com.bgip.model.LoginBean;
import com.bgip.service.UserService;
import com.bgip.utils.BgipUtils;
@Path("/socialLogin")
@Produces(APPLICATION_JSON) 
public class SocialLoginController extends BaseController{
	  private static final Logger LOGGER = LoggerFactory.getLogger(SocialLoginController.class);
	
	  @Autowired
	    @Qualifier("mainControllerProperties")
	    public Properties appProperties;
	    
	  @Autowired
	    UserService userService;
	    
	    
	    @POST
	    @Consumes(APPLICATION_JSON)
	    public LoginBean socialLogin(LoginBean login, @Context HttpServletResponse httpResponse) throws Exception {
	    	LOGGER.info("login API call socialLogin mehtod called");
	    	LoginBean loginResult = null;
	    	try {
	    		loginResult = userService.socialLogin(login);
	    		LOGGER.info(login.getUserName() +":: successfully logged in to the account");
	    		
	    		String token = BgipUtils.generateAccessToken(loginResult.getUserName(), "Admin", appProperties.getProperty("app.login.delimiter"), appProperties);
	    		loginResult.setToken(token);
	    		loginResult.setPassword(null);
	    		
	    	}catch(BgipException fe){
	    		LOGGER.error("login post error ",fe);
	    		buildErrorResponse(Response.Status.PRECONDITION_FAILED, fe.getErrorCode(), fe.getMessage());
	    	}
	    	return loginResult;
	    }
	    
	    
	    
	    
	   
	
	
	
}