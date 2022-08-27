package com.example.recycleapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recycleapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabaseRef;                           //실시간 데이터베이스
    private EditText mEtEmail, mEtNickname, mEtPwd, mEtPwdcheck;      //회원가입 입력필드
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //뒤로가기 액션바 생성
        ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("회원가입");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);

            //파이어베이스 접근 설정
        firebaseAuth = FirebaseAuth.getInstance();
        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mEtPwdcheck = findViewById(R.id.et_pwdcheck);
        mBtnRegister = findViewById(R.id.btn_register);
        mEtNickname = findViewById(R.id.et_nickname);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //가입 정보 가져오기
                final String email = mEtEmail.getText().toString().trim();
                String pwd = mEtPwd.getText().toString().trim();
                String pwdcheck = mEtPwdcheck.getText().toString().trim();

                if(pwd.equals(pwdcheck)){
                    final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                    mDialog.setMessage("가입중입니다.");
                    mDialog.show();

                    //파이어베이스에 계정 등록하기
                    firebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //가입 성공했을 때
                            if(task.isSuccessful()){
                                mDialog.dismiss();
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String email = user.getEmail();
                                String uid = user.getUid();
                                String nickname = mEtNickname.getText().toString().trim();

                                //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                HashMap<Object,String> hashMap = new HashMap<>();

                                hashMap.put("uid",uid);
                                hashMap.put("email",email);
                                hashMap.put("nickname",nickname);

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue(hashMap);

                                Intent intent = new Intent(RegisterActivity.this,LogInActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(RegisterActivity.this,"회원가입에 성공하셨습니다.",Toast.LENGTH_SHORT).show();
                            } else{
                                mDialog.dismiss();
                                Toast.makeText(RegisterActivity.this,"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });

                    //비밀번호 오류시
                } else{
                    Toast.makeText(RegisterActivity.this,"비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });


    }

    public boolean onSupportNavigateUp(){
        onBackPressed(); // 뒤로가기 버튼이 눌렸을 때
        return super.onSupportNavigateUp();
    }
}