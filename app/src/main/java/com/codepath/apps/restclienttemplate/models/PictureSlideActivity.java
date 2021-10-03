package com.codepath.apps.restclienttemplate.models;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.ViewPagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PictureSlideActivity extends AppCompatActivity {

    private String[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_slide);

        Intent intent = getIntent();
        list = intent.getStringArrayExtra("url_list");
        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, list);
        viewPager.setAdapter(adapter);

    }
}