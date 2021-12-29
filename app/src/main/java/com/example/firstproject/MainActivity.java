package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewPager viewPager = findViewById(R.id.viewpager);
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

//        // 탭에 이미지 추가(이미지를 안 넣을거면 38번 줄까지는 생략해도 됨)
//        ArrayList<Integer> image = new ArrayList<>();
//        image.add(R.drawable.photo);
//        image.add(R.drawable.person);
//        image.add(R.drawable.folder);
//
//        for (int i = 0; i < 3; i++)
//        {
//            tabLayout.getTabAt(i).setIcon(image.get(i));
//        }
    }
}