package com.joejbh.sourcecode;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyArrayAdapter extends ArrayAdapter<MyListItem> {
	
	LayoutInflater myInflater;
	Resources resource;

	public MyArrayAdapter(Activity activity, ArrayList<MyListItem> listItems) {
		// The second parameter is supposed to be the resource with which to create the views.
		// Set to 0 because we will provide the resource by another means.
		super(activity, 0, listItems);
		
		myInflater = LayoutInflater.from(activity);
		resource = activity.getResources();
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		MyListItem currentItem = getItem(position);		
		
		TextView textView;
		ImageView imageView;
		LinearLayout layout;
		
		if (convertView == null) {
			layout = (LinearLayout) myInflater.inflate(currentItem.getResourceLayout(), parent, false);
		} else {
			layout = (LinearLayout) convertView;
		}
		
		
		
		imageView = (ImageView) layout.findViewById(currentItem.getResourceImageView());
		textView = (TextView) layout.findViewById(currentItem.getResourceTextView());
		
		
		// If the ListItem is a header, disable the ability to click the item
		if (currentItem.getType().equals("Header")) {
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {}
			});
		}

		ConnectivityManager connMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    
		layout.setTag(currentItem.getActivityToStart());
		
		String imageUrl = currentItem.getImageUrl();
		if (imageUrl != null){
			if (networkInfo != null && networkInfo.isConnected()){
		    	ImageDownloader imageDownloader = new ImageDownloader();
		    	imageDownloader.download(imageUrl, imageView, imageView.getHeight(), imageView.getWidth());
		    }
		    else
		    	Toast.makeText(getContext(), "Network Connection Error", Toast.LENGTH_SHORT).show();
		}
		else if (currentItem.getBitmapImage() != null){
			imageView.setImageBitmap(currentItem.getBitmapImage());
		}
			
		else{
			imageView.setImageResource(currentItem.getIconResource());
		}
		
		textView.setText(currentItem.getText());
		
		
		
		return layout;
	}

}
