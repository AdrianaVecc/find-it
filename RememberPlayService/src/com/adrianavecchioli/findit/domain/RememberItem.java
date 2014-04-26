package com.adrianavecchioli.findit.domain;

import android.location.Location;

public class RememberItem {

	private String tag;
	private String imagePath;
	private Location location;
	private long addedDate;
	
	
	public RememberItem(String tag, String imageURI, Location location,long addedDate) {
		this.tag=tag;
		this.imagePath=imageURI;
		this.location=location;
		this.addedDate=addedDate;
	}
	public RememberItem() {
		// TODO Auto-generated constructor stub
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public void setAddedDate(long addedDate) {
		this.addedDate = addedDate;
	}
	
	public long getAddedDate() {
		return addedDate;
	}
	@Override
	public String toString() {
		return "Item [tag=" + tag + ", image=" + imagePath
				+ ", loc=" + location + ", added=" + addedDate + "]";
	}
	

}
