package com.scoutlabour.util;

import android.graphics.Bitmap;

/**
 * Created by Sahil on 17-07-2016.
 */
public interface GetBitmap {

    public void onSuccess(Bitmap bitmap);

    public void onError(Exception e);

}
