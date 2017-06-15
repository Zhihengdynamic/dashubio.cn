package com.dashubio.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import com.dashubio.R;
import com.dashubio.net.AndroidHttpClient;
import com.dashubio.net.HttpClientFactory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;



/**
 * �����ṩ����ͼƬ���ص�һЩ���߷���
 */
public class ImageUtils {

	
	public static Bitmap getImageFromUrl(String url) {
		
		HttpGet httpRequest = new HttpGet(url);
		HttpResponse response = null;
		AndroidHttpClient client = HttpClientFactory.get().getHttpClient();
		try {
			response = client.execute(httpRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					// non-compressed images no need to be cached to the sd-card
					BitmapFactory.Options o = new BitmapFactory.Options();
					o.inPurgeable = true;
					Bitmap bmp = BitmapFactory.decodeStream(
							new FlushedInputStream(inputStream), null, o);
					// cache.saveToDisk(url, bmp);
					return bmp;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (ClientProtocolException e) {
			Log.w("ImageDownloader", "ClientProtocolException " + url);
		} catch (IOException e) {
			Log.w("ImageDownloader", "IOException " + url);
		} catch (Exception e) {
			Log.w("ImageDownloader",
					"other exception when download images from " + url);
		} catch (OutOfMemoryError err) {
			Log.w("ImageDownloader",
					"OutOfMemoryError when download images from " + url);
		} finally {
			httpRequest.abort();
		}
		return null;
	}

	private static final int TYPE_NORMAL = 1;
	
	
	
	static class BitmapDownloaderTask extends AsyncTask<Object, Void, Bitmap> {
		private Context context;
		private String url;
		private final WeakReference<ImageView> imageViewReference;
		private int type;
		CacheManager cache = CacheManager.getInstance();
		
		public BitmapDownloaderTask(ImageView imageView) {
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		@Override
		// Actual download method, run in the task thread
		protected Bitmap doInBackground(Object... params) {
			// params comes from the execute() call: params[0] is the url.
			context = (Context) params[0];
			url = (String) params[1];
			type = (Integer) params[2];
			Bitmap bmp = getImageFromUrl(url.toString().trim());
			
			
			if (bmp != null) {
				cache.cacheDrawable(url, bmp);
				cache.close();
				
			}
			return bmp;
		}

		@Override
		// Once the image is downloaded, associates it to the imageView
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				bitmap = null;
			}

			if (imageViewReference != null) {
				ImageView imageView = imageViewReference.get();
				BitmapDownloaderTask bitmapDownloaderTask = null;
				if (type == TYPE_NORMAL) {
					bitmapDownloaderTask = getBitmapDownloaderTask1(imageView);
				} else {
					bitmapDownloaderTask = getBitmapDownloaderTask2(imageView);
				}
				// Change bitmap only if this process is still associated with
				// it
				if (this == bitmapDownloaderTask && bitmap != null) {
					// BitmapDrawable bd = new BitmapDrawable(bitmap);
					// imageView.setBackgroundDrawable(bd);
					imageView.setImageBitmap(bitmap);

				}
			}
		}
	}

	static class DownloadedDrawable1 extends BitmapDrawable {
		private final WeakReference<BitmapDownloaderTask> bitmapDownloaderTaskReference1;

		public DownloadedDrawable1(Drawable defaultBitmap,
				BitmapDownloaderTask bitmapDownloaderTask) {
			super(((BitmapDrawable) defaultBitmap).getBitmap());
			bitmapDownloaderTaskReference1 = new WeakReference<BitmapDownloaderTask>(
					bitmapDownloaderTask);
		}

		public BitmapDownloaderTask getBitmapDownloaderTask() {
			return bitmapDownloaderTaskReference1.get();
		}
	}

	static class DownloadedDrawable2 extends AnimationDrawable {
		private final WeakReference<BitmapDownloaderTask> bitmapDownloaderTaskReference2;

		public DownloadedDrawable2(Drawable defaultAnimation,
				BitmapDownloaderTask bitmapDownloaderTask) {
			AnimationDrawable drawable = ((AnimationDrawable) defaultAnimation);
			final int frameCounter = drawable.getNumberOfFrames();
			for (int i = 0; i < frameCounter; i++) {
				super.addFrame(drawable.getFrame(i), drawable.getDuration(i));
			}
			super.setOneShot(false);
			bitmapDownloaderTaskReference2 = new WeakReference<BitmapDownloaderTask>(
					bitmapDownloaderTask);
		}

		public BitmapDownloaderTask getBitmapDownloaderTask() {
			return bitmapDownloaderTaskReference2.get();
		}
	}

	// fetch normal task
	private static BitmapDownloaderTask getBitmapDownloaderTask1(
			ImageView imageView) {
		if (imageView != null) {
			Drawable drawable = imageView.getDrawable();
			if (drawable == null || !(drawable instanceof DownloadedDrawable1)) {
				return null;
			}
			DownloadedDrawable1 downloadedDrawable = (DownloadedDrawable1) drawable;
			return downloadedDrawable.getBitmapDownloaderTask();
		}
		return null;
	}

	// fetch animation task
	private static BitmapDownloaderTask getBitmapDownloaderTask2(
			ImageView imageView) {
		if (imageView != null) {
			Drawable drawable = imageView.getBackground();
			if (drawable == null || !(drawable instanceof DownloadedDrawable2)) {
				return null;
			}
			DownloadedDrawable2 downloadedDrawable = (DownloadedDrawable2) drawable;
			return downloadedDrawable.getBitmapDownloaderTask();
		}
		return null;
	}

	/**
	 * ����ͼƬ
	 */
	public static void download(Context context, String url, ImageView imageView) {
		
		
		CacheManager cache = CacheManager.getInstance();
		
		if (cache.existsDrawable(url)) {
			imageView.setImageBitmap(cache.getDrawableFromCache(url, context));
			return;
		}

		Drawable defaultDrawable = context.getResources().getDrawable(
				R.drawable.img_load);
		if (cancelPotentialBitmapDownload(url, imageView)) {
			BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
			DownloadedDrawable1 downloadedDrawable = new DownloadedDrawable1(
					defaultDrawable, task);
			imageView.setImageDrawable(downloadedDrawable);
			task.execute(context, url, TYPE_NORMAL);
		}

	}


	/**
	 * @param urlpath
	 * @return Bitmap ����ͼƬurl��ȡͼƬ����
	 */
	public static Bitmap getBitMBitmap(String urlpath) {
		Bitmap map = null;
		try {
			URL url = new URL(urlpath);
			URLConnection conn = url.openConnection();
			conn.connect();
			InputStream in;
			in = conn.getInputStream();
			map = BitmapFactory.decodeStream(in);
			// TODO Auto-generated catch block
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public final static Bitmap returnBitMap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;

		try {
			myFileUrl = new URL(url);
			HttpURLConnection conn;

			conn = (HttpURLConnection) myFileUrl.openConnection();

			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	private static boolean cancelPotentialBitmapDownload(String url,
			ImageView imageView) {
		BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask1(imageView);

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

	private static boolean cancelPotentialImageDownload(String url,
			ImageView imageView) {
		BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask2(imageView);

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
	 * ͨ��ͼƬ������ж��Ƿ���Ҫ��ת����?
	 */
	public static Bitmap rotateImage(Bitmap bmp) {

		if (bmp == null)
			return bmp;

		int width = bmp.getWidth();
		int height = bmp.getHeight();
		float aspectRatio = ((float) height) / width;

		if (aspectRatio > 1) {
			// no need to rotate the image
			return bmp;
		}

		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		Bitmap rotatedBitmap = null;
		try {
			rotatedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height,
					matrix, false);
		} catch (OutOfMemoryError e) {
		}
		return rotatedBitmap;
	}

	/**
	 * ��ȡ��Ӧ��Ļ��С��ͼ
	 */
	public static Bitmap sacleBitmap(Context context, Bitmap bitmap) {
		// ������Ļ��С
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int screenWidth = metrics.widthPixels;
		float aspectRatio = (float) screenWidth / (float) width;
		int scaledHeight = (int) (height * aspectRatio);
		Bitmap scaledBitmap = null;
		try {
			scaledBitmap = Bitmap.createScaledBitmap(bitmap, screenWidth,
					scaledHeight, false);
		} catch (OutOfMemoryError e) {
		}
		return scaledBitmap;
	}

}

class FlushedInputStream extends FilterInputStream {
	public FlushedInputStream(InputStream inputStream) {
		super(inputStream);
	}

	@Override
	public long skip(long n) throws IOException {
		long totalBytesSkipped = 0L;
		while (totalBytesSkipped < n) {
			long bytesSkipped = in.skip(n - totalBytesSkipped);
			if (bytesSkipped == 0L) {
				int byteRead = read();
				if (byteRead < 0) {
					break; // we reached EOF
				} else {
					bytesSkipped = 1; // we read one byte
				}
			}

			totalBytesSkipped += bytesSkipped;
		}
		return totalBytesSkipped;
	}

}
