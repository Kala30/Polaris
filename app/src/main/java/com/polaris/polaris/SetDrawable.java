package com.polaris.polaris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class SetDrawable extends AsyncTask<Void, Void, Bitmap> {
    private ImageView view;
    private String url;
    private int scale;

    public SetDrawable(ImageView view, String url) {
        this.view = view;
        this.url = url;
        this.scale = 1;
    }

    public SetDrawable(ImageView view, String url, int scale) {
        this.view = view;
        this.url = url;
        this.scale = scale;
    }

    @Override
    public Bitmap doInBackground(Void... params) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");

            // Resize bitmap
            Bitmap bm = ((BitmapDrawable)d).getBitmap();

            is.close();
            return Bitmap.createScaledBitmap(bm, bm.getWidth()*scale, bm.getHeight()*scale, false);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            view.setImageBitmap(result);
        }
    }

}