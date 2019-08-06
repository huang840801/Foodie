package com.guanhong.foodie.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.guanhong.foodie.Foodie;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.concurrent.Executors;

public class ImageFromLruCache {

    public void set(ImageView imageView, String imageUrl) {

        Bitmap bitmap = (Bitmap) Foodie.getLruCache().get(imageUrl);

        if (bitmap == null) {

            Log.d(Constants.TAG, "LruCache doesn't exist, start download.: " + imageUrl);

            new DownloadImageTask(imageView, imageUrl).executeOnExecutor(Executors.newCachedThreadPool());
        } else {

            Log.d(Constants.TAG, "LruCache exist, set bitmap directly.: " + imageUrl);
            imageView.setImageBitmap(bitmap);
        }

    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

    public Bitmap decodeBitmap(String url, int maxWidth) {

        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxWidth);

            InputStream is = (InputStream) new URL(url).getContent();
            bitmap = BitmapFactory.decodeStream(is, null, options);
        } catch (MalformedInputException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private class DownloadImageTask extends AsyncTask {

        private ImageView mImageView;
        private String mImageUrl;
        private Bitmap mBitmap;

        public DownloadImageTask(ImageView imageView, String imageUrl) {

            mImageView = imageView;
            mImageUrl = imageUrl;

        }

        @Override
        protected Object doInBackground(Object[] objects) {

            mBitmap = decodeBitmap(mImageUrl, 200);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (mBitmap != null) {

                Foodie.getLruCache().put(mImageUrl, mBitmap);
                if (mImageView.getTag() == mImageUrl) {
                    mImageView.setImageBitmap(mBitmap);
                }
            }
        }
    }
}
