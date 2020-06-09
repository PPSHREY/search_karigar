package com.scoutlabour.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

/**
 * Created by Sahil on 17-07-2016.
 */
public class Base64ToBitmap extends AsyncTask<String, String, Bitmap> {

    GetBitmap getBitmap;

    public Bitmap encodeToBitmap(String encodedImage) {
        encodedImage = encodedImage.substring(encodedImage.indexOf(",") + 1);
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return bitmap;
    }
    @Override
    protected Bitmap doInBackground(String... params) {

        Bitmap bit = null;
        try {
            bit = encodeToBitmap(params[0]);
        } catch (Exception e) {
            getBitmap.onError(e);
        }

        return bit;
    }

    @Override
    protected void onPostExecute(Bitmap s) {
        super.onPostExecute(s);

        try {
            if (s == null) {
                getBitmap.onError(new NullPointerException());
            } else {
                getBitmap.onSuccess(s);
            }
        } catch (Exception e) {
            getBitmap.onError(e);
        }

    }

    public void enqueue(GetBitmap getBitmap) {
        this.getBitmap = getBitmap;
    }

}
