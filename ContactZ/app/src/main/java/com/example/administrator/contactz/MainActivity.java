package com.example.administrator.contactz;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import DataBase.DataBaseHandler;
import adapter.PagerAdapter;

public class MainActivity extends FragmentActivity {
    PagerAdapter adapter;
    ViewPager viewPager;
    ArrayList<Contact> listContact, listFavorite;
    DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);

     /*   listFavorite = new ArrayList<Contact>();
        db = new DataBaseHandler(this);
        listContact = db.getAllContact();
        for (int i = 0; i < listContact.size(); i++) {
            if (listContact.get(i).getFavorite() == 1) {
                listFavorite.add(listContact.get(i));
                Log.e("addFavorite", i + "");
            }
        }
        db.close();
        Log.e("!!!!!!!!!!!!!!", "on SQLite");*/


       /* Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", listContact);
        FragmentContact fraContact = new FragmentContact();
        fraContact.setArguments(bundle);

        Bundle bundle1 = new Bundle();
        bundle.putParcelableArrayList(FragmentFavorite.KEY_LIST_FAVORITE, listFavorite);
        FragmentFavorite fragmentFavorite = new FragmentFavorite();
        fraContact.setArguments(bundle1);*/


       /* android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.layoutMain, fraContact);
        transaction.addToBackStack("z1");
        transaction.commit();*/

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
