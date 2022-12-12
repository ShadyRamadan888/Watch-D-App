package com.example.watch_d.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.watch_d.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private Button login;
    private EditText editEmail,editPassword;
    private TextView textViewSignUp;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Assign Variables
        assignVariables();
        //When user click on "Login btn" -- Validation & Login
        clickingLoginBtn();

        //If user want to register
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public void assignVariables()
    {
        login=findViewById(R.id.btnlogin);
        editEmail=findViewById(R.id.inputEmail);
        editPassword=findViewById(R.id.inputPassword);
        toolbar=findViewById(R.id.toolBar);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        //Setting Toolbar
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24);
        getSupportActionBar().setTitle("");
    }

    public void clickingLoginBtn()
    {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editEmail.getText().toString();
                String pass = editPassword.getText().toString();

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(LoginActivity.this,"Please enter your email",Toast.LENGTH_SHORT).show();
                    editEmail.setError("Email is required");
                    editEmail.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(LoginActivity.this,"Email address is not valid",Toast.LENGTH_SHORT).show();
                    editEmail.setError("Valid email is required");
                    editEmail.requestFocus();
                }
                else if(TextUtils.isEmpty(pass))
                {
                    Toast.makeText(LoginActivity.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                    editEmail.setError("Password is required");
                    editEmail.requestFocus();
                }
                else {
                    //Toast.makeText(LoginActivity.this, "successful", Toast.LENGTH_SHORT).show();
                    loginUser(email,pass);
                }
            }
        });

    }




    private void loginUser(String email, String pass) {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    checkMailVerification();
                }
                else {

                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidCredentialsException e)
                    {
                        editEmail.setError("Your email is invalid or already in use. Kindly re-enter");
                        editEmail.requestFocus();
                    }
                    catch (FirebaseAuthInvalidUserException e)
                    {
                        editEmail.setError("User dose not exist. Please register again");
                        editEmail.requestFocus();
                    }
                    catch (Exception e)
                    {
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT);
                    }
                }

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkMailVerification(){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified()){
            Toast.makeText(getApplicationContext(),"Logged In",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }else{
            Toast.makeText(getApplicationContext(),"Verify your mail first",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}