/**
 * This code is taken from the android-developers blog.  I added comments and some slight changes.
 * http://android-developers.blogspot.com/2010/07/multithreading-for-performance.html
 */

package com.joejbh.sourcecode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;


public class ImageDownloader {

	int sampleHeight;
	int sampleWidth;

	
	// download() first checks if the download should be cancelled by calling "continuePotentialDownload(String, ImageView)"
	// Next, it creates a new BitmapDownloaderTask, which is an AsyncTask.
	// It passes the BitmapDownloaderTask to a new DownloadedDrawable in order to store it's reference and...
	// to create a solid-colored Drawable place-holder
	// Lastly, we setImageDrawable with the newly created DownloadedDrawable and begin the execution of the BitmapDownloaderTask
    public void download(String url, ImageView imageView, int sampleHeight, int sampleWidth) {
	    	
    	if (continuePotentialDownload(url, imageView)) {
    		BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
    		DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
    		imageView.setImageDrawable(downloadedDrawable);
//	    		task.execute(url, cookie);
    		task.execute(url);
    	}
    	
    	this.sampleHeight = sampleHeight;
    	this.sampleWidth= sampleWidth;
    }
    

    // Pass in the associated ImageView and store a weak reference during creation of BitmapDownloaderTask    
    class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
	    private String url;
	    private final WeakReference<ImageView> imageViewReference;

	    public BitmapDownloaderTask(ImageView imageView) {
	        imageViewReference = new WeakReference<ImageView>(imageView);
	    }

	    @Override
	    // Actual download method, run in the task thread
	    protected Bitmap doInBackground(String... urls) {
	    	
	         // urls comes from the execute() call
	         try {
				return decodeSampledBitmapFromStream(urls[0]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	    }

	    @Override
	    // Once the download is finished, check if the download had been cancelled (this check may be useless - see below)
	    // 
	    protected void onPostExecute(Bitmap bitmap) {     
	    	
	    	// According to the documentation - if the download was cancelled, it would never have reached onPostExecute
	    	// Therefore, it seems that the following if statement is useless.
	    	if (isCancelled()) {
	            bitmap = null;
	        }
	    	
	    	
	    	// Notes:
	    	// While inspecting, it appeared that there would be no way for imageViewReference to become null, but it 
	    	// could become null is the ImageView is dropped from the ListView or
	    	// if the user exits the page (maybe...)
	    	// Another possible problem with the ImageViewReference is if it were now pointing at a different ImageView... 
	    	// 
	    	// Does:
	    	// Gets the ImageView for this newly downloaded bitmap.
	    	// Gets the ImageViews's BitmapDownloaderTask
	    	// If this BitmapDownloaderTask is the same one that was associated with the ImageView...
	    	// Set the image of the ImageView with the bitmap.
	    	if (imageViewReference != null) {
	    	    ImageView imageView = imageViewReference.get();
	    	    BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
	    	    // Change bitmap only if this process is still associated with it
	    	    if (this == bitmapDownloaderTask) {
//	    	    	imageView.setImageDrawable(new ColorDrawable(Color.BLUE));
	    	    	
//	    	    	Bitmap myBitmap = BitmapFactory.decodeResource(null, R.drawable.ic_launcher);
	    	    	
//	    	    	imageView.setImageBitmap(myBitmap);
	    	        imageView.setImageBitmap(bitmap);
//		    	        imageView.setBackground(background)(imageView.getBackground());
	    	    }
	    	}
	    }
	}	    


	/**
	 * Notes:
	 * The original name of this function (cancelPotentialDownload() ) was ambiguous, because it suggested that if it returned true...
	 * that the download was cancelled.  I therefore changed the function to the current name.
	 * 
	 * Does:
	 * Takes a Url and an ImageView. 
	 * Gets the BitmapDownloaderTask associated with the ImageView.
	 * If, currently, there is no BitmapDownloaderTask associated with the imageView, return true to continue downloading.
	 * If there is a BitmapDownloaderTask, check it's url...
	 * Cancel the older BitmapDownloaderTask if:
	 * - the url of the old BitmapDownloaderTask associated with this ImageView is blank
	 * - or if the present url to be downloaded is different from the original Task.
	 * - and then return true.
	 * 
	 * If the current download url is the same, simply cancel the new attempt returning false.
	 * 
	 * @param url
	 * @param imageView
	 * @return
	 */
	private static boolean continuePotentialDownload(String url, ImageView imageView) {
	    BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
	
	    if (bitmapDownloaderTask != null) {
	        String bitmapUrl = bitmapDownloaderTask.url;
	        if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
	            bitmapDownloaderTask.cancel(true);
	        } else {
	            // The same URL is already being downloaded.
	            return false;
	        }
	    }
	    return true;
	}
	
	
	/**
	 * 
	 * If the passed ImageView exists, get the Drawable of the ImageView
	 * Check if the Drawable is a DownloadedDrawable
	 * If it is a DownloadedDrawable, return the BitmapDownloaderTask associated with that DownloadedDrawable.
	 * BitmapDownloaderTask is the AsyncTask
	 * 
	 * @param imageView
	 * @return
	 */
	private static BitmapDownloaderTask getBitmapDownloaderTask(ImageView imageView) {
	    if (imageView != null) {
	        Drawable drawable = imageView.getDrawable();
	        if (drawable instanceof DownloadedDrawable) {
	            DownloadedDrawable downloadedDrawable = (DownloadedDrawable)drawable;
	            return downloadedDrawable.getBitmapDownloaderTask();
	        }
	    }
	    return null;
	}
	
	
	/**
	 * 
	 * BitmapDownloaderTask is the AsyncTask
	 * ColorDrawable is extended in order to possess the extra functionality of:
	 * 1) Being a solid-colored Drawable place-holder for the Bitmap to be downloaded by BitmapDownloaderTask
	 * 2) To hold a reference to the BitmapDownloaderTask.
	 *
	 */
	
	static class DownloadedDrawable extends ColorDrawable {
	    private final WeakReference<BitmapDownloaderTask> bitmapDownloaderTaskReference;

	    public DownloadedDrawable(BitmapDownloaderTask bitmapDownloaderTask) {
	        super(Color.LTGRAY);
	        bitmapDownloaderTaskReference =
	            new WeakReference<BitmapDownloaderTask>(bitmapDownloaderTask);
	    }

	    public BitmapDownloaderTask getBitmapDownloaderTask() {
	        return bitmapDownloaderTaskReference.get();
	    }
	}
	
	
	private Bitmap decodeSampledBitmapFromStream(String myurl) throws IOException {
		
		InputStream inputStream = null;

		try {
			URL url = new URL(myurl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			/* Handle Timeout */
			connection.setReadTimeout(10000 /* milliseconds */);
			connection.setConnectTimeout(15000 /* milliseconds */);

			connection.setDoInput(true);
			// Starts the query

			connection.connect();
			int response = connection.getResponseCode();

			Log.i("HTTP", "The response is: " + response);
			
			inputStream = connection.getInputStream();
			
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int len;
	        while ((len = inputStream.read(buffer)) > -1 ) {
	        	outputStream.write(buffer, 0, len);
	        }
	        outputStream.flush();
			
	        InputStream is1 = new ByteArrayInputStream(outputStream.toByteArray()); 
	        InputStream is2 = new ByteArrayInputStream(outputStream.toByteArray()); 
	        
	        // First decode with inJustDecodeBounds=true to check dimensions
	        final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeStream(is1, null, options);
		    

		    // Calculate inSampleSize
		    options.inSampleSize = calculateInSampleSize(options, sampleWidth, sampleHeight);
		    options.inJustDecodeBounds = false;
		    
		    return BitmapFactory.decodeStream(is2, null, options);

		    
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
    
		// Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) {
	
	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	
	    return inSampleSize;
	}
	
	static class FlushedInputStream extends FilterInputStream {
	    public FlushedInputStream(InputStream inputStream) {
	        super(inputStream);
	    }

	    @Override
	    public long skip(long n) throws IOException {
	        long totalBytesSkipped = 0L;
	        while (totalBytesSkipped < n) {
	            long bytesSkipped = in.skip(n - totalBytesSkipped);
	            if (bytesSkipped == 0L) {
	                  int bytes = read();
	                  if (bytes < 0) {
	                      break;  // we reached EOF
	                  } else {
	                      bytesSkipped = 1; // we read one byte
	                  }
	           }
	            totalBytesSkipped += bytesSkipped;
	        }
	        return totalBytesSkipped;
	    }
	}

}
