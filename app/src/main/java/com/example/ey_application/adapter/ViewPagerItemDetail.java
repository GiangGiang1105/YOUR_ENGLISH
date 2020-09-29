package com.example.ey_application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ey_application.Model.Word.DataViewPager;
import com.example.ey_application.R;
import com.example.ey_application.Volumn;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerItemDetail extends PagerAdapter {
    public interface ChangerPager{
        void onResultChangerPager(boolean bool);
    }
    private List<DataViewPager> dataViewPagerList;
    private Context context;
    private LayoutInflater inflater;
    private ChangerPager changerPager;


    public ViewPagerItemDetail(Context context) {
        this.dataViewPagerList = new ArrayList<>();
        this.context = context;
        changerPager = (ChangerPager) context;
    }
    public void setItem(List<DataViewPager> hashMap){
        this.dataViewPagerList.clear();
        this.dataViewPagerList.addAll(hashMap);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataViewPagerList.size()+1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewpager_detail_item, container, false);
        TextView txtTitle = (TextView) view.findViewById(R.id.title);
        TextView txtDetail = (TextView) view.findViewById(R.id.detail);
        Button btnNext = (Button) view.findViewById(R.id.btn_next);
        if (position == 0){
            txtTitle.setVisibility(View.INVISIBLE);
            txtDetail.setVisibility(View.INVISIBLE);
            btnNext.setVisibility(View.VISIBLE);
        }else{
            txtTitle.setVisibility(View.VISIBLE);
            txtDetail.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.INVISIBLE);
            txtTitle.setText(dataViewPagerList.get(position -1).getTitle());
            txtDetail.setText(dataViewPagerList.get(position -1 ).getDetail());
        }
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changerPager.onResultChangerPager(true);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
