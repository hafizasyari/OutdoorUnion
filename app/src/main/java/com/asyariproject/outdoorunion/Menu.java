package com.asyariproject.outdoorunion;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    private TextView Daftartoko, Tentangaplikasi, Keluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Daftartoko = (TextView) findViewById(R.id.btnDaftartoko);
        Tentangaplikasi = (TextView) findViewById(R.id.btnTentangaplikasi);
        Keluar = (TextView) findViewById(R.id.btnKeluar);

        Daftartoko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftar = new Intent(Menu.this, Listtoko.class);
                startActivity(daftar);
            }
        });

        Tentangaplikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tentang = new Intent(Menu.this, Tentangaplikasi.class);
                startActivity(tentang);
            }
        });

        Keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              keluar();
            }
        });

    }

    public void keluar(){
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                         finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }
}
