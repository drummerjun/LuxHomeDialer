package com.jun.luxhomedialer;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.jun.luxhomedialer.components.CircularDrawable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class LoadBitmapTask extends AsyncTask<Object, Void, CircularDrawable> {
    private final static String TAG = LoadBitmapTask.class.getSimpleName();
    private ContentResolver contentResolver;
    private ImageView circularImageView;

    @Override
    protected CircularDrawable doInBackground(Object... params) {
        CircularDrawable circularDrawable;
        contentResolver = (ContentResolver) params[0];
        //String sourceUri = (String)params[1];
        Uri uriImage;
        if(params[1] instanceof String) {
            Log.w(TAG, "params[1]=String");
            uriImage = Uri.parse((String)params[1]);
        } else {// if(params[1] instanceof Uri) {
            Log.w(TAG, "params[1]=else(Uri)");
            uriImage = (Uri)params[1];
        }
        circularImageView = (ImageView) params[2];

        try {
            //Uri uriImage = Uri.parse(sourceUri);
            //Bitmap sourceBitmap = MediaStore.Images.Media.getBitmap(contentResolver, uriImage);
            Bitmap sourceBitmap = getThumbnail(contentResolver, uriImage, 120);
            Bitmap destBitmap = compressRawBitmap(sourceBitmap);
            circularDrawable = new CircularDrawable(destBitmap);
            sourceBitmap.recycle();
            sourceBitmap = null;
            //destBitmap.recycle();
            return circularDrawable;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(CircularDrawable circularDrawable) {
        super.onPostExecute(circularDrawable);
        try {
            circularImageView.setImageDrawable(circularDrawable);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private Bitmap compressRawBitmap(Bitmap srcBitmap) {
        Bitmap bitmap;
        if (srcBitmap.getWidth() >= srcBitmap.getHeight()){
            bitmap = Bitmap.createBitmap(
                    srcBitmap,
                    srcBitmap.getWidth()/2 - srcBitmap.getHeight()/2,
                    0,
                    srcBitmap.getHeight(),
                    srcBitmap.getHeight()
            );
        }else{
            bitmap = Bitmap.createBitmap(
                    srcBitmap,
                    0,
                    srcBitmap.getHeight()/2 - srcBitmap.getWidth()/2,
                    srcBitmap.getWidth(),
                    srcBitmap.getWidth()
            );
        }
        return bitmap;
    }

    private static Bitmap getThumbnail(ContentResolver resolver, Uri uri, int thumbnailSize) throws FileNotFoundException, IOException{
        InputStream input = resolver.openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
        double ratio = (originalSize > thumbnailSize) ? (originalSize / thumbnailSize) : 1.0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = resolver.openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }
}
