/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.resources;

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

import com.fab.dao.LoginDAO;
import com.fab.exception.FabException;
import com.fab.model.LoginBean;
import com.fab.model.ResponseBean;
import com.fab.utils.FabUtils;




@Path("login")
@Produces(APPLICATION_JSON)
public class LoginResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginResource.class);
    @Autowired
    @Qualifier("mainControllerProperties")
    public Properties appProperties;
    
    @Autowired
    LoginDAO loginDAO;

    /**
     * HTTP POST http://localhost:8080/fabcircles/rest/login/ REQUEST BODY
     * Content-Type:application/json { "username":"hello","password":"passme"}
     * @param login
     * {"status":"OK","msg":"","username":"hello","password":"","role":"ADMIN","token":"VALIDHADHEDTOKEN"}
     * @param response
     * @return LoginBean
     * @throws Exception 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(LoginBean login, @Context HttpServletResponse response) throws Exception {
        LOGGER.info("login API call :: called");
        int httpCode = 200;
        ResponseBean resp = null;
        try {
            LOGGER.debug("Login Vo Encrypted Token : {} ", login.getToken());
            resp = loginDAO.login(login);
            if(resp.getStatusCode().equals("200")){
                LOGGER.info(login.getUserName() +":: successfully logged in to the account");
                String token = FabUtils.generateAccessToken(login.getUserName(), "Admin", appProperties.getProperty("app.login.delimiter"), appProperties);
                login.setToken(token);
                login.setPassword(null);
                return Response
                        .status(httpCode)
                        .entity(login)
                        .build();
            }else{
                httpCode = 412;
            }
        } catch (FabException ex) {
            LOGGER.error("Error during login in Login Rest Api : ", ex);
        }
        return Response
                .status(httpCode)
                .entity(resp)
                .build();
    }

    /**
     *
     */
    @OPTIONS
    public void getOptions() {

        Response.ok().build();
    }
}
