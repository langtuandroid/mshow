package com.hdc.mshow.ultilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.hdc.mshow.ListAlbumOtherActivity;
import com.hdc.mshow.customize.Toast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class DownloadImage {
	public static DownloadImage instance = new DownloadImage();

	public DownloadImage() {
	}

	public Bitmap getImage(String URL) {
		Bitmap bitmap = null;
		InputStream in = null;
		try {
			in = OpenHttpConnection(URL);
			bitmap = BitmapFactory.decodeStream(in);
			//bitmap = BitmapResize(bitmap, 271, 355);
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Toast.instance.showToast(ListAlbumOtherActivity.instance, e1);
		}
		return bitmap;
	}

	public static Bitmap BitmapResize(Bitmap bitmap, float newWidth, float newHeight) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// calculate the scale - in this case = 0.4f
		float scaleWidth = (float) (newWidth / width);
		float scaleHeight = (float) (newHeight / height);

		// createa matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);

		// recreate the new Bitmap
		Bitmap resizedBitmap = null;
		try {
			resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		} catch (Exception e) {
			resizedBitmap = null;
		}

		return resizedBitmap;
	}
	
	private static InputStream OpenHttpConnection(String urlString)
			throws IOException {
		InputStream in = null;
		int response = -1;

		URL url = null;

		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		URLConnection conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP connection");

		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			// httpConn.setAllowUserInteraction(false);
			// httpConn.setInstanceFollowRedirects(true);
			// httpConn.setRequestMethod("GET");
			httpConn.setDoInput(true);
			httpConn.connect();

			response = httpConn.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();
			}
		} catch (Exception ex) {
			throw new IOException("Error connecting");
		}
		return in;
	}

}
