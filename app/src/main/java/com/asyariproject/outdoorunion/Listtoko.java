package com.asyariproject.outdoorunion;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;

public class Listtoko extends AppCompatActivity {

    ListView list;

    private DataBaseHelper database;
    private ImageView tdkdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtoko);

        try {
            database= new DataBaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tdkdt = (ImageView)findViewById(R.id.tidakada);
        TokoAdapter adapter = new TokoAdapter(this, database.getGambartoko(), database.getNamatoko(), database.getAlamatlist(), database.getDaerahlist());
        list = (ListView)findViewById(R.id.list_toko);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Listtoko.this, Deskripsitoko.class);
                intent.putExtra("nama",database.getNamatoko()[position]);
                startActivity(intent);
                finish();

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_backwhite);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menudaftartoko,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        //searchView.setBackgroundResource(R.color.colorWhite);
        searchView.setQueryHint("Search");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(final String newText) {
                if(newText.equals("")){
                    TokoAdapter adapter = new TokoAdapter(Listtoko.this, database.getGambartoko(),
                            database.getNamatoko(), database.getAlamatlist(), database.getDaerahlist());
                    list = (ListView)findViewById(R.id.list_toko);
                    list.setAdapter(adapter);
                    list.setVisibility(View.GONE);
                    if(database.getNamatoko().length!=0) {
                        tdkdt.setVisibility(View.GONE);
                        list.setVisibility(View.VISIBLE);

                    } else {
                        tdkdt.setVisibility(View.VISIBLE);
                    }

                } else {
                    TokoAdapter adapter = new TokoAdapter(Listtoko.this, database.searchGambartoko(newText),
                            database.searchNamatoko(newText), database.searchAlamatlist(newText), database.searchDaerahlist(newText));
                    list = (ListView)findViewById(R.id.list_toko);
                    list.setAdapter(adapter);
                    list.setVisibility(View.GONE);
                    if (database.searchNamatoko(newText).length != 0) {
                        tdkdt.setVisibility(View.GONE);
                        list.setVisibility(View.VISIBLE);

                    } else {
                        tdkdt.setVisibility(View.VISIBLE);
                    }
                }
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Listtoko.this, Deskripsitoko.class);
                        intent.putExtra("nama",database.searchNamatoko(newText)[position]);
                        startActivity(intent);
                        finish();

                    }
                });
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         if(item.getItemId() == android.R.id.home){
             /**
              * Intent i = new Intent(Listtoko.this,
                    Menu.class);
            startActivity(i);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//untuk membuat reset atau syncron
            startActivity(i);
         */
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
