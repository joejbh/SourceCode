package com.joejbh.sourcecode;

import android.graphics.Bitmap;

public class MyListItem {

	
	// Views
	private int resourceLayout = -1;
	private int resourceImageView = -1;
	private int resourceTextView = -1;

	// Views Contents
	private int iconResource = -1;
	private Bitmap bitmapImage = null;
	private String imageUrl = null;
	private String text = null;
	
	// Item Extras
	private String type = null;
	private String activityToStart = null;

	
	public MyListItem(
			int resourceLayout, int resourceImageView, int resourceTextView, 
			int iconResource, Bitmap bitmapImage, String imageUrl, String text, 
			String type, String activityToStart) {
		super();
		
		this.resourceLayout = resourceLayout;
		this.resourceImageView = resourceImageView;
		this.resourceTextView = resourceTextView;
		
		this.iconResource = iconResource;
		this.bitmapImage = bitmapImage;
		this.imageUrl = imageUrl;
		this.text = text;
		
		this.type = type;
		this.activityToStart = activityToStart;

		
	}
	
	public MyListItem(
			int resourceLayout, int resourceImageView, int resourceTextView, 
			int iconResource, String text, 
			String type, String activityToStart
			) {
		super();
		
		this.resourceLayout = resourceLayout;
		this.resourceImageView = resourceImageView;
		this.resourceTextView = resourceTextView;
		
		this.iconResource = iconResource;
		this.text = text;
		
		this.type = type;
		this.activityToStart = activityToStart;
		
	}
	
	public MyListItem(
			int resourceLayout, int resourceImageView, int resourceTextView, 
			Bitmap bitmapImage, String text, 
			String type, String activityToStart) {
		super();
		
		this.resourceLayout = resourceLayout;
		this.resourceImageView = resourceImageView;
		this.resourceTextView = resourceTextView;
		
		this.bitmapImage = bitmapImage;
		this.text = text;
		
		this.type = type;
		this.activityToStart = activityToStart;
		
	}
	
	public MyListItem(
			int resourceLayout, int resourceImageView, int resourceTextView, 
			String imageUrl, String text, 
			String type, String activityToStart) {
		super();
		
		this.resourceLayout = resourceLayout;
		this.resourceImageView = resourceImageView;
		this.resourceTextView = resourceTextView;
		
		this.imageUrl = imageUrl;
		this.text = text;
		
		this.type = type;
		this.activityToStart = activityToStart;
		
	}
	
	
	// Setters
	
	public void setResourceLayout(int resourceLayout) {
		this.resourceLayout = resourceLayout;
	}

	public void setResourceImageView(int resourceImageView) {
		this.resourceImageView = resourceImageView;
	}

	public void setResourceTextView(int resourceTextView) {
		this.resourceTextView = resourceTextView;
	}

	public void setIconResource(int iconResource) {
		this.iconResource = iconResource;
	}

	public void setBitmapImage(Bitmap bitmapImage) {
		this.bitmapImage = bitmapImage;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setActivityToStart(String activityToStart) {
		this.activityToStart = activityToStart;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	// Getters
	
	public int getResourceLayout() {
		return resourceLayout;
	}
	
	public int getResourceImageView() {
		return resourceImageView;
	}
	
	public int getResourceTextView() {
		return resourceTextView;
	}	

	public int getIconResource() {
		return iconResource;
	}
	
	public Bitmap getBitmapImage() {
		return bitmapImage;
	}

	public String getText() {
		return text;
	}

	public String getType() {
		return type;
	}

	public String getActivityToStart() {
		return activityToStart;
	}

	public String getImageUrl() {
		return imageUrl;
	}
}