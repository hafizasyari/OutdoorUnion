package com.asyariproject.outdoorunion;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class TokoAdapter extends ArrayAdapter<String> {

    private final AppCompatActivity context;
    private final String[] imagetoko;
    private final String[] namatoko;
    private final String[] alamattoko;
    private final String[] daerahtoko;

    public TokoAdapter(AppCompatActivity context, String[] imagetoko, String[] namatoko, String[] alamattoko, String[] daerahtoko) {
        super(context, R.layout.list_toko, namatoko);

        this.context = context;
        this.imagetoko = imagetoko;
        this.namatoko = namatoko;
        this.alamattoko = alamattoko;
        this.daerahtoko = daerahtoko;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_toko, null,true);

        TextView varnamatoko = (TextView) rowView.findViewById(R.id.textnamatoko);
        TextView varalamattoko = (TextView) rowView.findViewById(R.id.textalamattoko);
        TextView vardaerahtoko = (TextView) rowView.findViewById(R.id.textdaerahtoko);
        ImageView varimagetoko = (ImageView) rowView.findViewById(R.id.imagetoko);


        varnamatoko.setText(namatoko[position]);
       varalamattoko.setText(alamattoko[position]);
        vardaerahtoko.setText(daerahtoko [position]);
        try {
            // get input stream
            InputStream ims = context.getAssets().open(imagetoko[position]);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            varimagetoko.setImageDrawable(d);
        } catch (IOException ex) {
            return rowView;
        }

        return rowView;

    };
}
