package com.bgip.model.upload;

import com.bgip.model.user.BaseBean;

public class FoldersBean extends BaseBean {

	private static final long serialVersionUID = 7102548924381281877L;

	private String folderName;
	
	private String uniqueId;

	private String parentId;

	private String link;
	
	
	private boolean favourite;


	private boolean folder;

	
	
	public boolean isFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	public boolean isFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
