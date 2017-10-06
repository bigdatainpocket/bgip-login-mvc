package com.bgip.model.upload;

import java.util.List;

import com.bgip.model.user.BaseBean;

public class UploadRequest extends BaseBean{

	private static final long serialVersionUID = -1724421785291493116L;
	
	private List<FolderResponse> folderList;

	public List<FolderResponse> getFolderList() {
		return folderList;
	}

	public void setFolderList(List<FolderResponse> folderList) {
		this.folderList = folderList;
	}
	
	

}
