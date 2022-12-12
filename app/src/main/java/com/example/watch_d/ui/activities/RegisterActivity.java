package com.example.watch_d.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.watch_d.R;
import com.example.watch_d.pojo.firebase.ReadWriteUserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private Toolbar toolbar;
    private EditText editName,editEmail,editPassword,editConfirmPassword;
    private Button registerButton;
    private TextView alreadyHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Assign Variables
        init();
        //When user click on "Register btn" -- Validation & Register
        clickingToRegister();
        //If user have an account -- Moving to LoginActivity
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void init()
    {
        toolbar = findViewById(R.id.toolBar);
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        registerButton = findViewById(R.id.btnRegister);
        editName = findViewById(R.id.inputUsername);
        editEmail = findViewById(R.id.inputEmail);
        editConfirmPassword = findViewById(R.id.inputConformPassword);
        editPassword = findViewById(R.id.inputPassword);
        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24);
        getSupportActionBar().setTitle("");
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

    public void clickingToRegister()
    {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //filling string variables
                String name = editName.getText().toString();
                String password = editPassword.getText().toString();
                String confirmPass= editConfirmPassword.getText().toString();
                String email = editEmail.getText().toString();

                //Validation email,name,password and mobile number
                if(TextUtils.isEmpty(name))
                {
                    Toast.makeText(RegisterActivity.this,"Please enter your full name",Toast.LENGTH_SHORT).show();
                    editName.setError("Full name is required");
                    editName.requestFocus();
                }
                else if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(RegisterActivity.this,"Please enter your email",Toast.LENGTH_SHORT).show();
                    editEmail.setError("Email is required");
                    editEmail.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(RegisterActivity.this,"Please enter valid email address",Toast.LENGTH_SHORT).show();
                    editEmail.setError("Email address is not valid");
                    editEmail.requestFocus();
                }
                else if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(RegisterActivity.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                    editPassword.setError("Password is required");
                    editPassword.requestFocus();
                }
                else if (password.length() < 6)
                {
                    Toast.makeText(RegisterActivity.this,"Please enter more strong password",Toast.LENGTH_SHORT).show();
                    editPassword.setError("Password should be more than 6 digits.");
                    editPassword.requestFocus();
                }
                else if (TextUtils.isEmpty(confirmPass))
                {
                    Toast.makeText(RegisterActivity.this,"Please enter confirm your Password",Toast.LENGTH_SHORT).show();
                    editConfirmPassword.setError("required");
                    editConfirmPassword.requestFocus();
                }
                else if(!confirmPass.equals(password))
                {
                    Toast.makeText(RegisterActivity.this,"Password not matching",Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(name,email,password);
                    Toast.makeText(RegisterActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //TODO Register Firebase
    private void registerUser( String name,String email, String password) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(RegisterActivity.this,"Register successful",Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    //UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                    //firebaseUser.updateProfile(userProfileChangeRequest);
                    ReadWriteUserData writeUserData = new ReadWriteUserData(name,email,password);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Registered Users");
                    databaseReference.child(firebaseUser.getUid()).setValue(writeUserData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(RegisterActivity.this,"Registered Successfully. Please verify your email.",Toast.LENGTH_SHORT);
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this,"Register failed, please try again",Toast.LENGTH_SHORT);
                            }
                        }
                    });

                }
                else {

                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthWeakPasswordException e)
                    {
                        editPassword.setError("Password very weak.");
                        editPassword.requestFocus();
                    }
                    catch (FirebaseAuthInvalidCredentialsException e)
                    {
                        editEmail.setError("Your email is invalid or already in use. Kindly re-enter");
                        editEmail.requestFocus();
                    }
                    catch (FirebaseAuthUserCollisionException e)
                    {
                        editEmail.setError("User already exist.");
                        editEmail.requestFocus();
                    }
                    catch (Exception e)
                    {
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT);
                    }
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}