package com.bgip.model.upload;

import com.bgip.model.user.BaseBean;

public class FilesBean extends BaseBean{

	private static final long serialVersionUID = -6966143032293623636L;

	private String fileName;
	
	private String folderId;
	
	private int size;
	
	private boolean favourite;
	
	private String dLink;
	
	
	public boolean isFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getdLink() {
		return dLink;
	}

	public void setdLink(String dLink) {
		this.dLink = dLink;
	}

	
	
	
}
