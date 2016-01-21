package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.contactz.Contact;
import com.example.administrator.contactz.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 1/7/2016.
 */
public class ContactAdapter extends BaseAdapter {
    private ArrayList<Contact> listContact;
    Context context;
    LayoutInflater inflater;

    public ContactAdapter(Context context, ArrayList<Contact> listContact) {
        this.context = context;
        this.listContact = listContact;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return listContact.size();
       // return 1;
    }

    @Override
    public Contact getItem(int position) {
        return listContact.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        View view = convertView;
        if(view == null)
        {
            view = inflater.inflate(R.layout.layout_contact_in,parent,false);
            holder = new ViewHolder();
            holder.img = (ImageView)view.findViewById(R.id.imageContact);
            holder.txt = (TextView)view.findViewById(R.id.txtContact);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)view.getTag();
        }
        Contact contact = listContact.get(position);
        holder.txt.setText(contact.getName().toString());
       // Log.e("!!!!!!!!1",contact.getName().toString());
        return view;
    }

    static class ViewHolder
    {
        ImageView img;
        TextView txt;
    }
}
