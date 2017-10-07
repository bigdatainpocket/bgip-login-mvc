/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgip.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Properties;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.bgip.exception.BgipException;
import com.bgip.model.LoginBean;
import com.bgip.model.ResponseBean;
import com.bgip.model.user.UserBean;
import com.bgip.service.UserService;
import com.bgip.utils.BgipUtils;


@Path("login")
@Produces(APPLICATION_JSON)
public class LoginController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    @Qualifier("mainControllerProperties")
    public Properties appProperties;
    
    @Autowired
    UserService userService;
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public LoginBean login(LoginBean login, @Context HttpServletResponse response) throws Exception {
    	LOGGER.info("login API call :: called");
    	try {
    		LOGGER.debug("Login Vo Encrypted Token : {} ", login.getToken());
    		userService.login(login);
    		LOGGER.info(login.getUserName() +":: successfully logged in to the account");
    		
    		String token = BgipUtils.generateAccessToken(login.getUserName(), "Admin", appProperties.getProperty("app.login.delimiter"), appProperties);
    		login.setToken(token);
    		login.setPassword(null);
    		
    	}catch(BgipException fe){
    		LOGGER.error("login post error ",fe);
    		buildErrorResponse(Response.Status.PRECONDITION_FAILED, fe.getErrorCode(), fe.getMessage());
    	}
    	return login;
    }

    
    
    
    
    
    
    @OPTIONS
    public void getOptions() {

        Response.ok().build();
    }
    
 
    
    
    
    
}
