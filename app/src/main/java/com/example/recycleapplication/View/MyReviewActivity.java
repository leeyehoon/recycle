package com.example.recycleapplication.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.recycleapplication.R;

public class MyReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);

        //뒤로가기 액션바 생성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("내가 쓴 댓글");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
    }
}