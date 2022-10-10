package com.example.pte_marauders_map.login;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.namespace.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {

    private EditText newPassword;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        Button update = findViewById(R.id.btnUpdatePassword);
        newPassword = findViewById(R.id.etNewPassword);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        update.setOnClickListener(view -> {

            String userPasswordNew = newPassword.getText().toString();
            firebaseUser.updatePassword(userPasswordNew).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(UpdatePassword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(UpdatePassword.this, "Password Update Failed", Toast.LENGTH_SHORT).show();
                }
            });
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}