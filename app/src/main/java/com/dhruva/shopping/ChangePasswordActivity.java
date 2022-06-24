package com.dhruva.shopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText NewPassword;
    Button SetPassword;
    private ProgressDialog loadingBar;
    String userPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        loadingBar = new ProgressDialog(this);
        userPhone = getIntent().getStringExtra("phone");
        NewPassword = findViewById(R.id.new_password_inp);
        SetPassword = findViewById(R.id.set_new_password);
        SetPassword.setOnClickListener(v -> {
            String newPassword = NewPassword.getText().toString();
            if (!newPassword.isEmpty()){
                if (newPassword.length() > 8){
                    loadingBar.setTitle("Change Password");
                    loadingBar.setMessage("Please wait, while password is being changes.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("password", newPassword);
                    FirebaseDatabase.getInstance().getReference().child("Users").child(userPhone).updateChildren(userdataMap).addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ChangePasswordActivity.this, "Congratulations, your password has been changed.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(ChangePasswordActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(ChangePasswordActivity.this,"Password Length must be greater than 8",Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Toast.makeText(ChangePasswordActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
