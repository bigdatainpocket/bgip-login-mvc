package com.bgip.resources;


import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bgip.constants.StatusCodes;
import com.bgip.dao.UserDAO;
import com.bgip.exception.FabException;
import com.bgip.model.LoginBean;
import com.bgip.model.ResponseBean;
import com.bgip.model.user.UserBean;

@Path("user")
@Produces(APPLICATION_JSON) 
public class UserResource extends BaseResource{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    protected static final int DEFAULT_MAX_AGE = -1;
    protected static final String VARY = "Vary";
    protected static final String VARY_ACCEPT = "Accept, Accept-Encoding";

    @Autowired
    @Qualifier("mainControllerProperties")
    public Properties appProperties;
    
    @Autowired UserDAO userDAO;
    
    
    @POST
    @Path("/signUp")
    @Consumes(APPLICATION_JSON)
    public LoginBean userRegister(UserBean userBean,  @Context HttpServletResponse response) throws Exception{
    	LOGGER.info("userRegister POST call ::");
    	ResponseBean resp = null;
    	LoginBean login= new LoginBean();
    	try{
    	resp = userDAO.userRegister(userBean);
    	if(resp.getMessage().equalsIgnoreCase("Success")){
    		
    		login.setUserName(userBean.getUserName());
    		login.setFirstName(userBean.getFirstName());
    		login.setLastName(userBean.getLastName());
//    		String token = FabUtils.generateAccessToken(login.getUserName(), "Admin", appProperties.getProperty("app.login.delimiter"), appProperties);
//    		login.setToken(token);
    	}
    	}catch(FabException fe){
    		LOGGER.error("userRegister error ",fe);
    		buildErrorResponse(Response.Status.CONFLICT, fe.getErrorCode(), fe.getMessage());
    	}
    	return login;
    }
    
    
    
    
    @PUT
    @Path("/update")
    @Produces(APPLICATION_JSON)
    public ResponseBean updateUser(UserBean userBean)throws Exception{
    	ResponseBean resp = null;
    	try{
    		resp=userDAO.updateUser(userBean);
    	}catch(FabException fe){
    		LOGGER.error("updateUser error ",fe);
    		buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, fe.getErrorCode(), fe.getMessage());
    	}
    	return resp;
    }

    
    @GET
    @Path("/find")
    public UserBean findByUserName(@QueryParam("userName") String userName) throws Exception{
    	System.out.println(" user name : "+userName);
    	UserBean user = null;
    	try{
    	if(userDAO.find(userName) == null){
    		buildErrorResponse(Response.Status.CONFLICT, StatusCodes.USER_ID_NOT_EXIST, "UserId doesn't exist");
    	}else{
    		user = userDAO.find(userName);
    	}
    	}catch(FabException fe){
    		LOGGER.error("findByUserName error ",fe);
    		buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, fe.getErrorCode(), fe.getMessage());
    	}
    	return user;
    }

    
    
    
    @GET
    @Path("/getAllUsers")
    @Produces(APPLICATION_JSON)
    public List<UserBean> getAllUsers() throws Exception{
    	List<UserBean> userBean = null;
    	try{
    	userBean = (List<UserBean>) userDAO.findAll();
    	if(userBean == null){
    		buildErrorResponse(Response.Status.CONFLICT, StatusCodes.USER_ID_NOT_EXIST, "UserId doesn't exist");
    	}
    	}catch(FabException fe){
    		LOGGER.error("findByUserName error ",fe);
    		buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, fe.getErrorCode(), fe.getMessage());
    	}
    	return userBean;
    }

    
    
    
    
   
    
    
    
    
    
    
    
    protected ResponseBuilder createCacheableResponse(ResponseBuilder response, int maxage) {
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(maxage);
        cacheControl.setMustRevalidate(false);
        response.header(VARY, VARY_ACCEPT);
        return response.cacheControl(cacheControl);
    }
    
/*
    protected ResponseBuilder createCacheableResponse(ResponseBuilder response, int maxage, Request request, Object obj) {
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(1000);
        
        EntityTag etag = new EntityTag(Integer.toString(obj.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(etag);

        System.out.println(Integer.toString(obj.hashCode()));
        System.out.println(builder);
        // cached resource did change -> serve updated content
        if(builder == null){
                builder = Response.ok(obj);
                builder.tag(etag);
        }

        builder.cacheControl(cacheControl);
        
        return  builder;
    }
    */
    
    public static String toJSON(Object obj, boolean showNull) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        if (!showNull) {
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
        }
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(obj);
    } 
}
