package com.joejbh.sourcecode;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


public enum MyDisplayState {
	INSTANCE;
	
	private int screenHeight;
	private int screenWidth;
	
	public void gatherDisplayInfo(Context context){
		DisplayMetrics metrics;
		metrics = new DisplayMetrics();
		
		WindowManager wManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
		
		wManager.getDefaultDisplay().getMetrics(metrics);
		
		this.screenHeight = metrics.heightPixels;
		this.screenWidth = metrics.widthPixels;
	}
	
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
		
	}
	
	
	public int getScreenHeight() {
		return screenHeight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}
	
}
