package adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import com.example.administrator.contactz.FragmentContact;
import com.example.administrator.contactz.FragmentFavorite;
import com.example.administrator.contactz.MainActivity;

/**
 * Created by Administrator on 1/8/2016.
 */
public class PagerAdapter extends FragmentPagerAdapter
{

    public PagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fr = null;

        switch (position)
        {
            case 0:

                fr = new FragmentContact();
               // fr.setArguments(bundle);
                break;
            case 1:
                fr = new FragmentFavorite();
               // Bundle bundle = new Bundle();


              //  fr.setArguments(bundle);
                break;


        }

        return fr;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
