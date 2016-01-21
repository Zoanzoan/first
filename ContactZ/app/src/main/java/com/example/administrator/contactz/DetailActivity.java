package com.example.administrator.contactz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import DataBase.DataBaseHandler;

public class DetailActivity extends AppCompatActivity {

    TextView txtPhoneNumber;
    FloatingActionButton btnSms, btnCall;
    Button btnBack;
    ArrayList<Contact> listContact;
    int pos;
    CheckBox checkBox;
    DataBaseHandler db;
    ImageButton imgBtn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("!!!!!!!!!1", "onBackPressed");
        setResult(Key.CODE_ZERO, null);
        // finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
        btnSms = (FloatingActionButton) findViewById(R.id.btnSms);
        btnCall = (FloatingActionButton) findViewById(R.id.btnCall);
        btnBack = (Button) findViewById(R.id.btnBack);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        imgBtn = (ImageButton) findViewById(R.id.imageButton);

        db = new DataBaseHandler(DetailActivity.this);
        //.getextra.con...
        Intent intent = getIntent();
        if (intent.getExtras().containsKey(Key.KEY_DETAIL_FROM_FAVORITE)) {
            Bundle bundle = intent.getBundleExtra(Key.KEY_DETAIL_FROM_FAVORITE);
            listContact = bundle.getParcelableArrayList(Key.KEY_LIST_DETAIL_FROM_FAVORITE);
            pos = intent.getIntExtra(Key.KEY_POS_DETAIL_FROM_FAVRITE, 0);
        } else {

            Bundle bundle = intent.getBundleExtra(Key.KEY_DETAIL_FROM_CONTACT);
            listContact = bundle.getParcelableArrayList(Key.KEY_LIST_DETAIL_FROM_CONTACT);
            pos = intent.getIntExtra(Key.KEY_POS_DETAIL_FROM_CONTACT, 0);
        }


        if (listContact.get(pos).getFavorite() == 1) {
            checkBox.setChecked(true);
        }
        btnBack.setText("<  " + listContact.get(pos).getName());
        txtPhoneNumber.setText(listContact.get(pos).getPhoneNumber());
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((listContact.get(pos).getFavorite() == 0) && (checkBox.isChecked())) {
                    db.updateCotact(listContact.get(pos), 1);
                    listContact.get(pos).setFavorite(1);
                } else if ((listContact.get(pos).getFavorite() == 1) && (!checkBox.isChecked())) {
                    db.updateCotact(listContact.get(pos), 0);
                    listContact.get(pos).setFavorite(0);
                }
                Intent intent1 = new Intent();
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList(Key.KEY_LIST_CONTACT_FROM_DETAIL, listContact);
                intent1.putExtra(Key.KEY_CONTACT_FROM_DETAIL, bundle1);

                setResult(Key.CODE_BACK_FROM_DETAIL, intent1);
                finish();
            }
        });


        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), 69);

            }
        });


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "tel:" + listContact.get(pos).getPhoneNumber();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("address", listContact.get(pos).getPhoneNumber());
                sendIntent.putExtra("sms_body", "");
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivity(sendIntent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 69)
                onSelectFromGalleryResult(data);

        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);//option
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        imgBtn.setImageBitmap(bm);
    }
}
