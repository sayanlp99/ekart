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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                            createNotificationChannel();
                            showOtp(otp);
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

    private void showOtp(int otp) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ayush)
                .setContentTitle(String.valueOf(otp))
                .setShowWhen(true)
                .setAutoCancel(true)
                .setContentText("OTP for password change")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    int generateOtp(){
        return (1000 + (int)(Math.random() * ((9999 - 1000) + 1)));
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification";
            String description = "1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
