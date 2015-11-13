package example.android.tumblrplusplus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by chennosaurus on 11/8/15.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage; //the imageview we will add our downloaded bitmap to
    Context context; //context of mainActivity

    public DownloadImageTask(ImageView bmImage, Context context) {
        this.bmImage = bmImage;
        this.context = context;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bmp = null;
        FileOutputStream outputStream;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            String fileName = Uri.parse(urldisplay).getLastPathSegment(); //our file name is the last segment of the url
            File file = new File(context.getFilesDir(), fileName);
            bmp = BitmapFactory.decodeStream(in); //set the bmp to decode our stream
            outputStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream); //compress our bitmap to the specified file
            outputStream.close();

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bmp;
    }


    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result); //set our imageView to hold the bitmap we downloaded
    }


}

