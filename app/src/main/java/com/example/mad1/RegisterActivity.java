package com.example.mad1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText userNameEdt,pwdEdt,cnfPwdEdt;
    private Button registerBtn;
    private ProgressBar loadingPB;
    private TextView loginTV;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userNameEdt = findViewById(R.id.idEdtUserName);
        pwdEdt = findViewById(R.id.idEdtPwd);
        cnfPwdEdt = findViewById(R.id.idEdtCnfPwd);
        registerBtn = findViewById(R.id.idBtnRegister);
        loadingPB = findViewById(R.id.idPBloading);
        loginTV = findViewById(R.id.idTVlogin);
        mAuth = FirebaseAuth.getInstance();
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String userName = userNameEdt.getText().toString();
                String pwd = pwdEdt.getText().toString();
                String cnfPwd = pwdEdt.getText().toString();

                if(!pwd.equals(cnfPwd)){
                    Toast.makeText( RegisterActivity.this,  "Please Check Both Password", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd)){
                    Toast.makeText( RegisterActivity.this,  "Please Add your Creentials", Toast.LENGTH_SHORT).show();
                }else {

                    mAuth.createUserWithEmailAndPassword(userName , pwd) .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                               loadingPB.setVisibility(View.GONE);
                                Toast.makeText( RegisterActivity.this,  "User Registered", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this,LoginActivity.class );
                                startActivity(i);
                                finish();

                            }else{

                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText( RegisterActivity.this,  "Fail to Register User", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }
}