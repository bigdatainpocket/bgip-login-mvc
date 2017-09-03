/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bgip.resources;

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

import com.bgip.dao.LoginDAO;
import com.bgip.exception.FabException;
import com.bgip.model.LoginBean;
import com.bgip.utils.FabUtils;




@Path("login")
@Produces(APPLICATION_JSON)
public class LoginResource extends BaseResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginResource.class);
    @Autowired
    @Qualifier("mainControllerProperties")
    public Properties appProperties;
    
    @Autowired
    LoginDAO loginDAO;
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public LoginBean post(LoginBean login, @Context HttpServletResponse response) throws Exception {
    	LOGGER.info("login API call :: called");
    	try {
    		LOGGER.debug("Login Vo Encrypted Token : {} ", login.getToken());
    		loginDAO.login(login);
    		LOGGER.info(login.getUserName() +":: successfully logged in to the account");
    		
    		String token = FabUtils.generateAccessToken(login.getUserName(), "Admin", appProperties.getProperty("app.login.delimiter"), appProperties);
    		login.setToken(token);
    		login.setPassword(null);
    		
    	}catch(FabException fe){
    		LOGGER.error("login post error ",fe);
    		buildErrorResponse(Response.Status.PRECONDITION_FAILED, fe.getErrorCode(), fe.getMessage());
    	}
    	return login;
    }

    /**
     *
     */
    @OPTIONS
    public void getOptions() {

        Response.ok().build();
    }
    
 
    
    
    
    
}
