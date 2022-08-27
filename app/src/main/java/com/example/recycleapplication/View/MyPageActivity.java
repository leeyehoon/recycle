package com.example.recycleapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.recycleapplication.R;

public class MyPageActivity extends AppCompatActivity {

    private ImageButton mBtnsetting;
    private ImageButton mBtnEtprofile;
    private ImageButton mBtnselllist;
    private ImageButton mBtnperchselist;
    private ImageButton mBtnthumbuplist;
    private Button mBtnmypost;
    private Button mBtnmyreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        mBtnsetting = findViewById(R.id.btn_setting);
        mBtnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this,AppSettingActivity.class);
                startActivity(intent);
            }
        });

        mBtnEtprofile = findViewById(R.id.btn_etProfile);
        mBtnEtprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this,ProfileEditActivity.class);
                startActivity(intent);
            }
        });

        mBtnselllist = findViewById(R.id.btn_selllist);
        mBtnselllist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this,SelllistActivity.class);
                startActivity(intent);
            }
        });

        mBtnperchselist = findViewById(R.id.btn_perchselist);
        mBtnperchselist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this,PerchaselistActivity.class);
                startActivity(intent);
            }
        });

        mBtnthumbuplist = findViewById(R.id.btn_thumpuplist);
        mBtnthumbuplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this,ThumbuplistActivity.class);
                startActivity(intent);
            }
        });

        mBtnmypost = findViewById(R.id.btn_mypost);
        mBtnmypost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this,MyPostActivity.class);
                startActivity(intent);
            }
        });

        mBtnmyreview = findViewById(R.id.btn_myreview);
        mBtnmyreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this,MyReviewActivity.class);
                startActivity(intent);
            }
        });
    }
}