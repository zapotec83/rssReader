package es.jorider.rssreader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by jorge on 22/11/14.
 */
public class DBImageUtils {

    /**
     * Get image as byteArray
     *
     * @param bitmap        Bitmap
     * @return
     */
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * Return the image from the byteArray
     *
     * @param array
     * @return
     */
    public static Bitmap getImageFromByteArray(byte[] array) {
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }
}
