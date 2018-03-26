package com.krparajuli.collegethrift.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

/**
 * Created by kal on 3/22/18.
 */

public class BackgroundImageResize extends AsyncTask<Uri, Integer, byte[]> {

    private Bitmap bitmap;

    public BackgroundImageResize(Bitmap bitmap) {
        if (bitmap != null)
            this.bitmap = bitmap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected byte[] doInBackground(Uri... uris) {
        return new byte[0];
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        super.onPostExecute(bytes);
    }
}
