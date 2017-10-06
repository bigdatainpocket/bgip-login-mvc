package com.bgip.model.upload;

import java.util.List;

import com.bgip.model.upload.FilesBean;
import com.bgip.model.upload.FoldersBean;

public class FolderResponse extends FoldersBean{

	private static final long serialVersionUID = 6479214388144251811L;

	
	private List<FilesBean> files;


	public List<FilesBean> getFiles() {
		return files;
	}


	public void setFiles(List<FilesBean> files) {
		this.files = files;
	}
	
	
}
