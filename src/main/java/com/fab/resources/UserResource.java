package com.fab.resources;



import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fab.dao.UserDAO;
import com.fab.model.ResponseBean;
import com.fab.model.user.UserBean;

@Path("user")
@Produces(APPLICATION_JSON) 
public class UserResource {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);


    protected static final int DEFAULT_MAX_AGE = -1;
    protected static final String VARY = "Vary";
    protected static final String VARY_ACCEPT = "Accept, Accept-Encoding";

   /* @Autowired
    private JmsTemplate jmsTemplate;*/
    
    @Autowired UserDAO userDAO;
    
    @GET
    @Path("/")
    @Produces(APPLICATION_JSON)

    public Response get(@Context Request request) throws Exception {
    	
      
        
    	return createCacheableResponse(
                Response.ok(toJSON("Success", false)), DEFAULT_MAX_AGE)
                .build();
    }
    
    /*
    @POST
    @Path("/message")
    @Produces(APPLICATION_JSON)

    public Response sendMessageToQueue(@Context Request request, @QueryParam("message")  final String msg, @QueryParam("coId")  final String coId, @QueryParam("prId")  final Integer prId) throws Exception {
    	System.out.println("sendMessageToQueue executed");
    	
    	System.out.println("jmsTemplate is "+jmsTemplate);
    	jmsTemplate.send(new MessageCreator() {
    	      @Override
    	      public Message createMessage(Session session) throws JMSException {
    	        Message message = session.createTextMessage(msg);
    	        message.setJMSCorrelationID(coId);
    	        message.setJMSPriority(prId);
    	     //   new Desti
    	       // message.setJMSReplyTo("simonqueue");
    	        return message;
    	      }
    	    });      
        
    	return createCacheableResponse(
                Response.ok(toJSON("Successfully connected to JMS", false)), DEFAULT_MAX_AGE)
                .build();
    }
    
   */
    @POST
    @Path("/register")
    @Consumes(APPLICATION_JSON)
    public LoginBean userRegister(UserBean userBean,  @Context HttpServletResponse response) throws Exception{
    	LOGGER.info("userRegister GET call ::");
    	ResponseBean resp = null;
    	LoginBean login= new LoginBean();
    	try{
            resp = userDAO.userRegister(userBean);
            if(resp.getMessage().equalsIgnoreCase("Success")){
                
                login.setUserName(userBean.getUserName());
                login.setPassword(userBean.getPassword());
                String token = FabUtils.generateAccessToken(login.getUserName(), "Admin", appProperties.getProperty("app.login.delimiter"), appProperties);
                login.setToken(token);
                login.setPassword(null);
            }
    	}catch(FabException fe){
    		LOGGER.error("userRegister error ",fe);
    		buildErrorResponse(Response.Status.CONFLICT, fe.getErrorCode(), fe.getMessage());
    	}
    	return login;
    }
    
    
    
    @DELETE
    @Path("/delete")
    @Consumes(APPLICATION_JSON)
    	public Response delteUser(UserBean userBean) throws Exception{
    	int httpCode=200;
    	ResponseBean resp=userDAO.deleteUser(userBean);
    	System.out.println("resp ::::"+resp);
    	
    	if(resp.getStatusCode().equalsIgnoreCase("104")){
    		httpCode=412;
    	}
    	
    	return Response
    			.status(httpCode)
    			.entity(resp)
    			.build();
    }			

    @PUT
    @Path("/update")
    @Produces(APPLICATION_JSON)
    public Response updateUser(UserBean UserBean)throws Exception{
    	int httpCode=200;
    	ResponseBean resp=userDAO.updateUser(UserBean);
    	if(resp.getStatusCode().equalsIgnoreCase("104")){
    		httpCode=412;
    	}
    	return Response
    			.status(httpCode)
    			.entity(resp)
    			.build(); 	
    }
    
    
   
    
    
    @GET
    @Path("/find")
    @Produces(APPLICATION_JSON)
    public Object findall() throws Exception{
    	;
    	return (List<UserBean>) Response
    			.status(200)
    			.entity(userDAO.findall())
    			.build(); 
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
