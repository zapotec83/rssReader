package es.jorider.rssreader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

/**
 * Created by jorge
 */
public class ImageUtils {

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
        Bitmap response = (array != null) ? BitmapFactory.decodeByteArray(array, 0, array.length) : null;
        return response;
    }

    /**
     * Resize Bitmap
     *
     * @param bitmap
     * @param sizeBigger
     * @return
     */
    public static Bitmap getResizeBitmap(Bitmap bitmap, int sizeBigger) {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * sizeBigger, bitmap.getHeight() * sizeBigger, false);

    }
}
