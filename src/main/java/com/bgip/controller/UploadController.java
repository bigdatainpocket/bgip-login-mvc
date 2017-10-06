package com.bgip.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import com.bgip.dao.UploadDAO;
import com.bgip.filter.AuthFilter;
import com.bgip.model.upload.FolderResponse;
import com.bgip.model.ResponseBean;
import com.bgip.model.upload.UploadRequest;
import com.bgip.model.upload.FilesBean;
import com.bgip.service.UploadServices;

@Path("/drive")
@Produces(APPLICATION_JSON)
public class UploadController extends BaseController {

	@Autowired
	UploadServices uploadServices;
	
	
	private AuthFilter authFilter;
	
	@POST
	@Path("/upload")
	@Consumes(APPLICATION_JSON)
	public List<FolderResponse> upload(@HeaderParam("token") String token, UploadRequest uploadFolders) throws Exception {
		List<FolderResponse> result = null;
		this.authFilter = new AuthFilter();
		 String loginUser = this.authFilter.getUserNameFromToken(token);
//		 uploadFolders.setUserName(loginUser);
		 result = uploadServices.uploadedFiles(uploadFolders.getFolderList(), loginUser);
		
		return result;
	}

	
	@POST
	@Path("/newFolder")
	@Consumes(APPLICATION_JSON)
	public FolderResponse createNewFolder(@HeaderParam("token") String token, FolderResponse emptyFolder) throws Exception {
		FolderResponse result = null;
		this.authFilter = new AuthFilter();
		 String loginUser = this.authFilter.getUserNameFromToken(token);
		 result = uploadServices.createEmptyFolder(emptyFolder, loginUser);
		return result;
	}
	
	
	@GET
	@Path("/folder/{folderId}")
	@Produces(APPLICATION_JSON)
	public List<FilesBean> getFilesByFolderId(@HeaderParam("token") String token, @PathParam("folderId") String folderId) throws Exception {
		System.out.println("folerId : "+folderId);
		List<FilesBean> result = null;
		this.authFilter = new AuthFilter();
		 String loginUser = this.authFilter.getUserNameFromToken(token);
		 result = uploadServices.getFilesByFolderId(folderId, loginUser);
		
		return result;
	}
	
//	@GET
//	@Path("/file/{fileId}")
//	@Produces(APPLICATION_JSON)
//	public FilesBean getFilebyId(@HeaderParam("token") String token, @PathParam("fileId") String fileId) throws Exception {
//		FilesBean result = null;
//		this.authFilter = new AuthFilter();
//		 String loginUser = this.authFilter.getUserNameFromToken(token);
//		 result = uploadServices.getFilbyId(fileId, loginUser);
//		
//		return result;
//	}
	
	
	
	@GET
//	@Path("/{userName}")
	@Produces(APPLICATION_JSON)
	public UploadRequest getAllFiles(@HeaderParam("token") String token ) throws Exception {
		UploadRequest result = null;
		this.authFilter = new AuthFilter();
		 String loginUser = this.authFilter.getUserNameFromToken(token);
		 result = uploadServices.getAllFiles(loginUser);
		return result;
	}
	
	
	@GET
	@Path("/folder/favourite/{folderId}")
	@Produces(APPLICATION_JSON)
	public ResponseBean makeFavouriteFolder(@HeaderParam("token") String token, @PathParam("folderId") String folderId ) throws Exception {
		ResponseBean result = null;
		this.authFilter = new AuthFilter();
		 String loginUser = this.authFilter.getUserNameFromToken(token);
		 result = uploadServices.makeFavouriteFolder(folderId, loginUser);
		return result;
	}
	
	@GET
	@Path("/file/favourite/{fileId}")
	@Produces(APPLICATION_JSON)
	public ResponseBean getFavouriteFileList(@HeaderParam("token") String token, @PathParam("fileId") String fileId ) throws Exception {
		ResponseBean result = null;
		System.out.println("files List 1: "+System.currentTimeMillis());
		this.authFilter = new AuthFilter();
		 String loginUser = this.authFilter.getUserNameFromToken(token);
		 result = uploadServices.makeFavouriteFile(fileId, loginUser);
		return result;
	}
	
	@GET
	@Path("/folder/favourite/list")
	@Produces(APPLICATION_JSON)
	public List<FolderResponse> getFavouriteFolders(@HeaderParam("token") String token ) throws Exception {
		List<FolderResponse> result = null;
		System.out.println("folder List 1: "+System.currentTimeMillis());
		this.authFilter = new AuthFilter();
		 String loginUser = this.authFilter.getUserNameFromToken(token);
		 result = uploadServices.getFavouriteFolders(loginUser);
		return result;
	}
	
	@GET
	@Path("/file/favourite/list")
	@Produces(APPLICATION_JSON)
	public List<FilesBean> getFavouriteFiles(@HeaderParam("token") String token ) throws Exception {
		List<FilesBean> result = null;
		this.authFilter = new AuthFilter();
		 String loginUser = this.authFilter.getUserNameFromToken(token);
		 result = uploadServices.getFavouriteFiles(loginUser);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
