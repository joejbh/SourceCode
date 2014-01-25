package com.joejbh.sourcecode;

import android.content.Context;
import android.widget.Toast;

public class MyDisplayCode {

	public MyDisplayCode() {
		// TODO Auto-generated constructor stub
	}
	

	public static float pixelsToSp(Context context, Float px) {
	    float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
	    return px/scaledDensity;
	}
	
	public static float spToPixels(Context context, Float px) {
	    float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
	    return px*scaledDensity;
	}
	
	
	
	public static int dpToPx(Context context, int measurementInDp) {

		float density = context.getResources().getDisplayMetrics().density;
		
		int measurementInPx;
		measurementInPx = (int) Math.ceil(measurementInDp * density);
		
		return measurementInPx;
	}
	
	
	public static int pxToDp(Context context, int measurementInPx) {

		float density = context.getResources().getDisplayMetrics().density;
		
		int measurementInDp;
		measurementInDp = (int) ( (measurementInPx - 0.5f) / density );
		
		return measurementInDp;
	}
	
	
	
	public static void doToast(Context context, String input) {
	    Toast.makeText(context, input, Toast.LENGTH_SHORT).show();
	}
	
	public static void doToast(Context context, int input) {
	    Toast.makeText(context, " " + input, Toast.LENGTH_SHORT).show();
	}
	
	public static void doToast(Context context, float input) {
	    Toast.makeText(context, " " + input, Toast.LENGTH_SHORT).show();
	}
	
	
	public static void doToastL(Context context, String input) {
	    Toast.makeText(context, input, Toast.LENGTH_LONG).show();
	}
	
	public static void doToastL(Context context, int input) {
	    Toast.makeText(context, " " + input, Toast.LENGTH_LONG).show();
	}
	
	public static void doToastL(Context context, float input) {
	    Toast.makeText(context, " " + input, Toast.LENGTH_LONG).show();
	}

	
}