package com.fab.filter;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.fab.utils.FabUtils;




@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthFilter implements ContainerRequestFilter
 {

    @Autowired
    @Qualifier("mainControllerProperties")
    public Properties appProperties;
    
    @Context
    HttpServletRequest servletRequest;

    private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<Object>());;

    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("No Token or Invalid Token to access this resource", 403, new Headers<>());


	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		System.out.println("filter request method called");
        String requestUri = requestContext.getUriInfo().getPath();
        //No validation for Login API
        System.out.println("Login Filter of requestUri is :"+requestUri);
        if (requestUri.endsWith("login") || requestUri.endsWith("register")) {
            return;
        }
        //Get the token from Request
		String token  = requestContext.getHeaderString("token");
		System.out.println("token from the request");
		try{
		//Validate the token
		validateToken(token,requestContext);
		}catch(Exception e){
			e.printStackTrace();
			requestContext.abortWith(ACCESS_FORBIDDEN);
		}
		//throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		
	}

	public void validateToken(String token, ContainerRequestContext requestContext)throws Exception{

		String decryptedToken = null;
		try {
			//If token is not present 
			if(token == null){
				requestContext.abortWith(ACCESS_FORBIDDEN);
			}
			//Decrypt the token 
			decryptedToken = FabUtils.decryptString(URLDecoder.decode(token, "UTF-8"), appProperties);
			System.out.println("decryptedToken "+decryptedToken);
			//Split the token using Delimeter
			String[] tokenParts = decryptedToken.split(Pattern.quote(appProperties.getProperty("app.login.delimiter")));
			System.out.println(tokenParts.length);
			//If splits are 3 in lenght it is valid
			if(tokenParts.length ==  3){
				final String timeStmap = tokenParts[1];
				boolean tokenValid = FabUtils.isTokenValid(timeStmap);
				System.out.println("token not valid");
				if(!tokenValid){
					requestContext.abortWith(ACCESS_FORBIDDEN);
				}
			}else{
				requestContext.abortWith(ACCESS_FORBIDDEN);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
   
   }
  

