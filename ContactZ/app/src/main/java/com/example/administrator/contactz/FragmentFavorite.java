package com.example.administrator.contactz;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import DataBase.DataBaseHandler;
import adapter.ContactAdapter;
import adapter.FavoriteContactAdapter;

/**
 * Created by Administrator on 1/7/2016.
 */
public class FragmentFavorite extends Fragment implements MyInterface {

    public static final String KEY_LIST_FAVORITE = "list_favorite";
    GridView gridView;
    ArrayList<Contact> listFavorite, listContact;
    FavoriteContactAdapter adapter;
    boolean check = true;
    DataBaseHandler db;


    /*public static final FragmentFavorite newInstance(ArrayList<Contact> listFarvor)
    {
        FragmentFavorite fr = new FragmentFavorite();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(listFarvor);

    }*/

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.layout_fragment_favorite, container, false);
        listFavorite = new ArrayList<Contact>();
        Log.e("!!!!!!!!1","createViewFavorite");
        db = new DataBaseHandler(getActivity());
        listContact = db.getAllContact();
        db.close();


        for (int i = 0; i < listContact.size(); i++) {
            if (listContact.get(i).getFavorite() == 1) {
                listFavorite.add(listContact.get(i));
            }
        }
        gridView = (GridView) view.findViewById(R.id.gridView);
        adapter = new FavoriteContactAdapter(getActivity(), listFavorite);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Key.KEY_LIST_DETAIL_FROM_FAVORITE,listFavorite);

                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra(Key.KEY_DETAIL_FROM_FAVORITE,bundle);
                intent.putExtra(Key.KEY_POS_DETAIL_FROM_FAVRITE,position);

                startActivityForResult(intent,Key.CODE_DETAIL_FROM_FAVORITE);

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case Key.CODE_DETAIL_FROM_FAVORITE:

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args == null) {
            Log.e("!!!!!!!!!!!!!!!!", "null bundle");
            check = false;
        }
    }

    @Override
    public void updateFavorite(ArrayList<Contact> list) {


        for (int i = 0; i < list.size(); i++) {
        if (list.get(i).getFavorite() == 1) {
        listFavorite.add(list.get(i));
        }
        }
        adapter = new FavoriteContactAdapter(getActivity(), listFavorite);
        gridView.setAdapter(adapter);
    }
}

