package com.example.recycleapplication.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.recycleapplication.R;

public class InfoSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_setting);

        //뒤로가기 액션바 생성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("계정/정보 관리");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
    }
}