package com.dhruva.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VerifyPasswordOtp extends AppCompatActivity {
    EditText OtpInput;
    Button VerifyOtp;
    String generatedOtp;
    String userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_password_otp);
        generatedOtp = getIntent().getStringExtra("generated_otp");
        userPhone = getIntent().getStringExtra("phone");
        OtpInput = findViewById(R.id.otp_inp);
        VerifyOtp = findViewById(R.id.verify_user_otp);
        VerifyOtp.setOnClickListener(v -> {
            String inputOtp = OtpInput.getText().toString().trim();
            if (generatedOtp.equals(inputOtp)){
                Intent intent = new Intent(VerifyPasswordOtp.this,ChangePasswordActivity.class);
                intent.putExtra("phone", userPhone);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(VerifyPasswordOtp.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
