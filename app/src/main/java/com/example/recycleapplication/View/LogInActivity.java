package com.example.recycleapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import com.example.recycleapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;

import com.kakao.sdk.user.UserApiClient;
import com.kakao.usermgmt.LoginButton;
import com.kakao.sdk.user.model.Account;


public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabaseRef;                           //실시간 데이터베이스
    private EditText mEtEmail,  mEtPwd;      //회원가입 입력필드
    private Button mBtnRegisterin, mBtnlogin;
    private SignInButton mBtngoogle;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private LoginButton mBtnkakao;

    //카카오 로그인
    public void login(){
        String TAGK = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(LogInActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
            }
            return null;
        });
    }

    //카카오 로그인 토큰
    public void accountLogin(){
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(LogInActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
            }
            return null;
        });
    }

    //카카오 유저 정보 가져오기
    public void getUserInfo(){
        String TAG = "getUserInfo()";
        UserApiClient.getInstance().me((user, meError) -> {
            if (meError != null) {
                Log.e(TAG, "사용자 정보 요청 실패", meError);
            } else {
                System.out.println("로그인 완료");
                Log.i(TAG, user.toString());
                {
                    Log.i(TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: "+user.getId() +
                            "\n이메일: "+user.getKakaoAccount().getEmail());
                }
                Account user1 = user.getKakaoAccount();
                System.out.println("사용자 계정" + user1);
            }
            return null;
        });
    }




    //구글 로그인 시 로그인 여부 확인
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    //구글을 통해 액티비티로 돌아오는 함수
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG,"firebaseAuthWithGoogle:"+account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e){
                Log.w(TAG,"Google sign in failed",e);
            }
            }
        }

    //구글 로그인 시 정상적으로 로그인하면 ID토큰을 가져와서 파이어베이스와 사용자 인증 정보를 교환하여 파이어베이스 인증
    private void firebaseAuthWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Log.d(TAG,"signInWithCredential:success");

                            if(user !=null){
                                Intent intent = new Intent(getApplication(),MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            updateUI(user);
                            Toast.makeText(LogInActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                        } else{
                            Log.w(TAG,"signInWithCredential:failure",task.getException());
                            Toast.makeText(LogInActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }


    private void updateUI(FirebaseUser user){

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBtnkakao = findViewById(R.id.btn_kakao);
        mBtnkakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LogInActivity.this)){
                    login();
                }
                else{
                    accountLogin();
                }
            }
        });




        //파이어베이스 접근 설정
        firebaseAuth = FirebaseAuth.getInstance();
        mEtEmail = findViewById(R.id.input_email);
        mEtPwd = findViewById(R.id.input_pwd);
        mBtnRegisterin = findViewById(R.id.btn_register_in);
        mBtnlogin = findViewById(R.id.btn_login);


        //구글 config
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);


        mBtngoogle = findViewById(R.id.btn_google);
        mBtngoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //구글 로그인 통합 페이지로 넘김
                Intent signintent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signintent, RC_SIGN_IN);

            }
        });


        //회원가입 버튼이 눌렸을때
        mBtnRegisterin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        //로그인 버튼이 눌렸을 때
        mBtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEtEmail.getText().toString().trim();
                String pwd = mEtPwd.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(email,pwd)
                        .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //로그인 성공시
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else{
                                    //로그인 실패
                                    Toast.makeText(LogInActivity.this,"아이디와 비밀번호가 잘 입력되었는지 확인해주십시오.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}