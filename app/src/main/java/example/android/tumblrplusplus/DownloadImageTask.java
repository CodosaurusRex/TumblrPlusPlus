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
    ImageView bmImage;
    Context context;

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
            String fileName = Uri.parse(urldisplay).getLastPathSegment();
            File file = new File(context.getFilesDir(), fileName);
            bmp = BitmapFactory.decodeStream(in);
            outputStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
            File f = context.getFilesDir();
            ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bmp;
    }


/*    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        showProgressDialog();
    }*/

    protected void onPostExecute(Bitmap result) {
        //pDlg.dismiss();
        bmImage.setImageBitmap(result);
    }

    public File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            Log.e("URL", url);
            file = File.createTempFile(fileName, null, context.getCacheDir());
        }
        catch (IOException e) {
                // Error while creating file
            }
            return file;
        }

}

