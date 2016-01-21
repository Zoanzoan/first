package com.example.administrator.contactz;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import DataBase.DataBaseHandler;
import adapter.ContactAdapter;

/**
 * Created by Administrator on 1/7/2016.
 */
public class FragmentContact extends Fragment {
    ArrayList<Contact> listContact, listFavorite;
    ListView listView;
    ContactAdapter contactAdapter;
    FloatingActionButton btnAdd;
    DataBaseHandler db;
    boolean check = true;
    MyInterface myInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_contact,container,false);
        btnAdd = (FloatingActionButton)view.findViewById(R.id.btnAdd);
      //  btnAdd.setImageResource(R.drawable.add);
        listContact = new ArrayList<Contact>();
        listFavorite = new ArrayList<Contact>();
        db = new DataBaseHandler(getActivity());
        listContact = db.getAllContact();
        db.close();
        if(listContact.isEmpty())
        {
            loadContact();
        }
        Log.e("!!!!!!!!1","createViewContact");

       /* Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_LIST_FAVORITE,listFavorite);*/

       /* if(check == true)
        {
            Bundle bundle = getArguments();
            listContact = bundle.getParcelableArrayList("data");
        }*/
     //   listContact.add(new Contact("a","b","c",true));
       // loadContact();
        listView = (ListView)view.findViewById(R.id.listView);
        contactAdapter = new ContactAdapter(getActivity(),listContact);
        listView.setAdapter(contactAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra(Key.KEY_POS_DETAIL_FROM_CONTACT, position);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Key.KEY_LIST_DETAIL_FROM_CONTACT,listContact);
                intent.putExtra(Key.KEY_DETAIL_FROM_CONTACT,bundle);

                startActivityForResult(intent,Key.CODE_DETAIL_FROM_CONTACT);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(),AddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Key.KEY_LIST_ADD_FROM_CONTACT,listContact);
                intent.putExtra(Key.KEY_ADD_FROM_CONTACT,bundle);

                startActivityForResult(intent,Key.CODE_ADD_FROM_CONTACT);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args == null)
        {
            check = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {

            case Key.CODE_ADD_FROM_CONTACT:
                if(resultCode == Key.CODE_ADD_FROM_CONTACT) {
                    Bundle bundle = new Bundle();
                    bundle = data.getBundleExtra(Key.KEY_CONTACT_FROM_ADD);
                    listContact = bundle.getParcelableArrayList(Key.KEY_LIST_CONTACT_FROM_ADD);
                    Log.e("!!!!!1->>", "" + listContact.size());
                    contactAdapter = new ContactAdapter(getActivity(), listContact);
                    listView.setAdapter(contactAdapter);
                    //  ((MainActivity)getActivity()).upd
                    //   myInterface.updateFavorite(listContact);
                }

                break;
            case Key.CODE_DETAIL_FROM_CONTACT:
                if(resultCode == Key.CODE_BACK_FROM_DETAIL) {
                    Bundle bundle1 = new Bundle();
                    bundle1 = data.getBundleExtra(Key.KEY_CONTACT_FROM_DETAIL);
                    listContact = bundle1.getParcelableArrayList(Key.KEY_LIST_CONTACT_FROM_DETAIL);
                    contactAdapter = new ContactAdapter(getActivity(), listContact);
                    listView.setAdapter(contactAdapter);
                }

                break;


        }
    }
    protected void loadContact() {


        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Contact contact = new Contact(listContact.size(), name, phoneNumber, 0);
            db.addNew(contact);
            listContact.add(contact);

        }
        phones.close();


    }
}
