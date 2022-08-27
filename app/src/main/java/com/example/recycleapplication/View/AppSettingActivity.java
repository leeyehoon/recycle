package com.example.recycleapplication.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recycleapplication.R;

public class AppSettingActivity extends AppCompatActivity {

    private Button mBtnIdmanage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        mBtnIdmanage = findViewById(R.id.btn_idmanage);
        mBtnIdmanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppSettingActivity.this,InfoSettingActivity.class);
                startActivity(intent);
            }
        });

        //뒤로가기 액션바 생성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("설정");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
    }
}