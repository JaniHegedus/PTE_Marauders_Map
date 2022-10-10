package com.example.pte_marauders_map.login;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.namespace.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);
        TextView userRegistration = (TextView) findViewById(R.id.tvRegister);
        TextView forgotPassword = (TextView) findViewById(R.id.tvForgotPassword);

        Info.setText("No of attempts remaining: 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            startActivity(new Intent(Login.this, SecondActivity.class));
        }

        Login.setOnClickListener(view -> validate(Name.getText().toString(), Password.getText().toString()));

        userRegistration.setOnClickListener(view -> startActivity(new Intent(Login.this, RegistrationActivity.class)));

        forgotPassword.setOnClickListener(view -> startActivity(new Intent(Login.this, PasswordActivity.class)));
    }

    @SuppressLint("SetTextI18n")
    private void validate(String userName, String userPassword) {

        progressDialog.setMessage("You can subscribe to my channel until you are verified!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                progressDialog.dismiss();
                //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                checkEmailVerification();
            }else{
                Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                counter--;
                Info.setText("No of attempts remaining: " + counter);
                progressDialog.dismiss();
                if(counter == 0){
                    Login.setEnabled(false);
                }
            }
        });


    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        Boolean emailflag = firebaseUser.isEmailVerified();

        startActivity(new Intent(Login.this, SecondActivity.class));

//        if(emailflag){
//            finish();
//            startActivity(new Intent(MainActivity.this, SecondActivity.class));
//        }else{
//            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
//            firebaseAuth.signOut();
//        }
    }

}