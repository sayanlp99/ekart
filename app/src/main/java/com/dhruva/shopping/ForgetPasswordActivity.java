package com.dhruva.shopping;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import okhttp3.internal.http.StatusLine;

public class ForgetPasswordActivity extends AppCompatActivity {
    private final String CHANNEL_ID="Notification";
    private final  int NOTIFICATION_ID=1;
    int otp;
    Button VerifyAccount;
    EditText PhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        PhoneNumber = findViewById(R.id.forget_phone_number_input);
        VerifyAccount = findViewById(R.id.verify_user_phone);
        VerifyAccount.setOnClickListener(v -> {
            String phone = PhoneNumber.getText().toString().trim();
            if (!phone.isEmpty()){
                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Users").child(phone).exists()){
                            otp = generateOtp();
                            sendOtpForVerification(otp, phone);
                            Intent intent = new Intent(ForgetPasswordActivity.this,VerifyPasswordOtp.class);
                            intent.putExtra("generated_otp", String.valueOf(otp));
                            intent.putExtra("phone", phone);
                            startActivity(intent);
                        }else{
                            Toast.makeText(ForgetPasswordActivity.this, phone + " does not exists.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else{
                Toast.makeText(ForgetPasswordActivity.this, "Enter Phone no", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendOtpForVerification(int otp, String phoneNumber) {
        httpCall("https://www.fast2sms.com/dev/bulkV2?authorization=&route=q&message=My%20Odisha%20OTP%20for%20password%20change%20is%20"+String.valueOf(otp)+"&language=english&flash=0&numbers="+phoneNumber);
    }

    public void httpCall(String url) {

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                }, error -> {
                });

        queue.add(stringRequest);
    }

    int generateOtp(){
        return (1000 + (int)(Math.random() * ((9999 - 1000) + 1)));
    }



}
