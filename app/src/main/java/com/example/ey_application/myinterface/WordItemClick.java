package com.example.ey_application.myinterface;

import android.view.View;

public interface WordItemClick {
    void onClick(View v, int position);
    void onClickItem(View v, String word);
}
