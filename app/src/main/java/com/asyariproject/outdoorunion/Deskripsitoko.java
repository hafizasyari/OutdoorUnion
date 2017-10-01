package com.asyariproject.outdoorunion;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Deskripsitoko extends AppCompatActivity {
    private String nama;
    private List<String> toko;
    private DataBaseHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deskripsitoko);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_backblue);
        }
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            nama = extras.getString("nama");
        }
        try {
            database= new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        toko = database.getDeskripsitoko(nama);
        TextView namatoko = (TextView) findViewById(R.id.namatoko);
        namatoko.setText(toko.get(0));
        TextView profile = (TextView) findViewById(R.id.profile);
        profile.setText(toko.get(1));
        TextView jambuka = (TextView) findViewById(R.id.jambuka);
        jambuka.setText(toko.get(2));
        TextView alamat = (TextView) findViewById(R.id.alamat);
        alamat.setText(toko.get(3));
        TextView website = (TextView) findViewById(R.id.website);
        website.setText(toko.get(4));
        ImageView gambar = (ImageView) findViewById(R.id.imagetoko);

        ImageView location = (ImageView) findViewById(R.id.loc);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+String.valueOf(toko.get(7))+","+String.valueOf(toko.get(6)));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        ImageView call = (ImageView) findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+toko.get(8)));
                startActivity(intent);
            }
        });
        try {
            // get input stream
            InputStream ims = getAssets().open(toko.get(5));
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            gambar.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent i = new Intent(Deskripsitoko.this,
                    Listtoko.class);
            startActivity(i);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//untuk membuat reset atau syncron
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(Deskripsitoko.this,
                Listtoko.class);
        startActivity(i);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//untuk membuat reset atau syncron
        startActivity(i);
        finish();
    }
}
