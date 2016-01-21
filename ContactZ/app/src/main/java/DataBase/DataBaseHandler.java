package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.administrator.contactz.Contact;

import java.util.ArrayList;

/**
 * Created by Administrator on 1/7/2016.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    public DataBaseHandler(Context context)
    {
        super(context,"contactData11",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE tblContact (id int primary key, name text, phoneNumber text,favorite int)";
        db.execSQL(CREATE_TABLE);


    }

    public void addNew(Contact contact)
    {
        SQLiteDatabase 	db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",contact.getId());
        contentValues.put("name",contact.getName());
        contentValues.put("phoneNumber",contact.getPhoneNumber());
        contentValues.put("favorite",contact.getFavorite());
       // contentValues.put("email");
        db.insert("tblContact", null, contentValues);
        db.close();
    }

    public ArrayList<Contact> getAllContact()
    {
        ArrayList<Contact> listContact = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM tblContact ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst())
        {
            do
            {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contact.setFavorite(cursor.getInt(3));
                listContact.add(contact);
            }while(cursor.moveToNext());
        }
        db.close();
        return listContact;
    }

    public  void updateCotact (Contact contact, int isFavorite)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE tblContact SET favorite = "+isFavorite + " WHERE id = "+contact.getId() ;
        db.execSQL(strSQL);
        Log.e("!!!!!!!!","Update");
    }
    public void deleteContact(ArrayList<Contact> listContact,int pos)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tblContact","id = " + listContact.get(pos).getId(),null);
        listContact.remove(pos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
