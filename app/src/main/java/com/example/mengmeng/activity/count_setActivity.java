package com.example.mengmeng.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class count_setActivity extends AppCompatActivity {

    private TextView new_count_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_set);

        new_count_set = ((TextView) findViewById(R.id.new_count_set));
    }

}
