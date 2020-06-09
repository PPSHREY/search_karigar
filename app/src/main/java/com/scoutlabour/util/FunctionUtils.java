package com.scoutlabour.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sahil on 30-07-2016.
 */
public class FunctionUtils {

    private static FunctionUtils functionUtils;
    private static Context context;

    public FunctionUtils(Context context) {
        this.context = context;
        functionUtils = this;
    }

    public static FunctionUtils getInstance() {
        return functionUtils;
    }

    public String getCurrentDate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        return sdf.format(new Date());
    }

    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(Calendar.getInstance().getTime());
    }

    public void showToast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public String encodeToBase64(Bitmap image) {
        Bitmap immagex = Bitmap.createScaledBitmap(image, 350, 350, true);
        ;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("LOOK", imageEncoded);
        return "data:image/png;base64," + imageEncoded.replace(" ", "").replace("\n", "");
    }

    public Bitmap encodeToBitmap(String encodedImage) {
        encodedImage = encodedImage.substring(encodedImage.indexOf(",") + 1);
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return bitmap;
    }

//    public void longLog(String str) {
//        if (str.length() > 4000) {
//            Log.e("nirav", str.substring(0, 4000));
//            longLog(str.substring(4000));
//        } else
//            Log.e("nirav", str);
//    }

//    public File createImageFile(boolean clearCache) {
//        // Create an image file name
//        if (clearCache) {
//            removeCachedFiles();
//        }
//        String imageFileName = AppConstant.FILE_NAME_TEMPLATE;
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        File image = null;
//        try {
//            image = File.createTempFile(imageFileName, ".jpg", storageDir);
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        }
//        return image;
//    }
//
//    private void removeCachedFiles() {
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        if (storageDir != null && storageDir.listFiles() != null) {
//            for (File file : storageDir.listFiles()) {
//                if (file.getName().contains(AppConstant.FILE_NAME_TEMPLATE)) {
//                    file.delete();
//                }
//            }
//        }
//    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public boolean isDeviceOnline() {

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isOnline = (networkInfo != null && networkInfo.isConnected());
        if (!isOnline)
            Toast.makeText(context, " No internet Connection ", Toast.LENGTH_SHORT).show();

        return isOnline;
    }

    public String getMainPageDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
        return sdf.format(new Date(time));
    }
}
