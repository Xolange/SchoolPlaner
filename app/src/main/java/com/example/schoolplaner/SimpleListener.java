package com.example.schoolplaner;

import android.view.View;
import android.widget.AdapterView;

public class SimpleListener implements AdapterView.OnItemSelectedListener {

    private final View.OnClickListener listener;

    public SimpleListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        listener.onClick(view);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
