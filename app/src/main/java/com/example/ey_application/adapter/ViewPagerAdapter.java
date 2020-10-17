package com.example.ey_application.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ey_application.fragment.AnhAnhFragment;
import com.example.ey_application.fragment.ExampleFragment;
import com.example.ey_application.fragment.SynonymsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int  totalTabs;
    private Context context;
    private FragmentManager fm;
    public ViewPagerAdapter(Context context,@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
           /* case 1:
                AnhAnhFragment anhAnhFragment = new AnhAnhFragment();
                return anhAnhFragment;
            case 2:
                SynonymsFragment synonymsFragment = new SynonymsFragment();
                return synonymsFragment;
            case 3:
                ExampleFragment exampleFragment = new ExampleFragment();
                return exampleFragment;
            default:
                AnhVietFragment anhVietFragment = new AnhVietFragment();
                return anhVietFragment;*/
            case 1:
                SynonymsFragment synonymsFragment = new SynonymsFragment();
                return synonymsFragment;
            case 2:
                ExampleFragment exampleFragment = new ExampleFragment();
                return exampleFragment;
            default:
                AnhAnhFragment anhAnhFragment = new AnhAnhFragment();
                return anhAnhFragment;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
