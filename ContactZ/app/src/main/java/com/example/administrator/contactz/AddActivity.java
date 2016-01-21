package com.example.administrator.contactz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import DataBase.DataBaseHandler;

public class AddActivity extends AppCompatActivity {

    EditText etxtName, etxtPhone, etxtMail;
    String name, phoneNumber,mail;
    ArrayList<Contact> listContact;
    DataBaseHandler db;
    Button btnOk, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etxtName = (EditText)findViewById(R.id.etxtName);
        etxtPhone = (EditText)findViewById(R.id.etxtPhone);
        etxtMail = (EditText)findViewById(R.id.etxtMail);

        btnOk = (Button)findViewById(R.id.btnOk);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        db = new DataBaseHandler(this);

        final Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Key.KEY_ADD_FROM_CONTACT);
        listContact = bundle.getParcelableArrayList(Key.KEY_LIST_ADD_FROM_CONTACT);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etxtName.getText().toString();
                phoneNumber = etxtPhone.getText().toString();
                mail = etxtMail.getText().toString();
                String loi = "1";
                Log.e("!!!!!!!!!!!!!!!",""+listContact.size());
                loi = checkData();
                if(checkData().equals("1"))
                {
                    // int phone = Integer.parseInt(phoneNumber);
                    Contact contact = new Contact(listContact.size()+1,name,phoneNumber,0);
                    listContact.add(contact);
                    db.addNew(contact);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(Key.KEY_LIST_CONTACT_FROM_ADD, listContact);

                    Intent intent1 = new Intent();
                    intent1.putExtra(Key.KEY_CONTACT_FROM_ADD,bundle);
                    db.close();
                    setResult(123, intent1);
                    finish();

                }
                else
                {
                    Toast.makeText(AddActivity.this,loi,Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Key.CODE_ZERO,null);
                finish();
            }
        });

    }

    String checkData()
    {
      /*  for(int i = 0; i < etxtMail.getText().length(); i++)
        {
            //if(etxtMail.getText().charAt(i) >= 0 ) && (etxtMail.getText().charAt(i) <= 9 )
        }*/


        if(name.isEmpty())
        {
            return "Name!!!";
        }
        if(phoneNumber.isEmpty())
        {
            return "Phone number!!!";
        }
        for(int i = 0; i < phoneNumber.length(); i++)
        {
            if((phoneNumber.charAt(i) < '0') || (phoneNumber.charAt(i) > '9'))
            {
                return "Phone number!!!";
            }
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
        {
            return "Email!!!";
        }

        return "1";

    }

}
