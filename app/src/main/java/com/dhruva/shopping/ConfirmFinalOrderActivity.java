package com.dhruva.shopping;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dhruva.shopping.Model.Users;
import com.dhruva.shopping.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private EditText nameEditText,phoneEditText,addressEditText,pinCodeEditText,cityEditText;
    private Button confirmOrderBtn;
    private String totalAmount = "";
    private String shippingPriority = "";
    private String shippingDate = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Total Price");
        shippingPriority = getIntent().getStringExtra("Shipping priority");
        shippingDate = getIntent().getStringExtra("Shipping Date");
        Toast.makeText(this, "Total Price = Rs. "+totalAmount,Toast.LENGTH_SHORT).show();
        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText =(EditText) findViewById(R.id.shippment_name);
        phoneEditText =(EditText) findViewById(R.id.shippment_phone_number);
        addressEditText =(EditText) findViewById(R.id.shippment_address);
        pinCodeEditText =(EditText) findViewById(R.id.pin_code);
        cityEditText =(EditText) findViewById(R.id.shippment_city);
        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });
    }

    private void Check() {
        if(TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Full Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Phone Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Valid Address.",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your City Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pinCodeEditText.getText().toString())){
            Toast.makeText(this,"Please Provide Your Pin Code",Toast.LENGTH_SHORT).show();
        }
        else {

            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
        final String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd. yyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference localOrdersRef= FirebaseDatabase.getInstance().getReference()
                .child("Local Orders");
        final DatabaseReference ordersRef= FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());
        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount",totalAmount);
        ordersMap.put("shippingPriority",shippingPriority);
        ordersMap.put("shippingDate",shippingDate);
        ordersMap.put("name",nameEditText.getText().toString());
        ordersMap.put("phone",phoneEditText.getText().toString());
        ordersMap.put("address",addressEditText.getText().toString());
        ordersMap.put("city",cityEditText.getText().toString());
        ordersMap.put("pincode",pinCodeEditText.getText().toString());
        ordersMap.put("date",saveCurrentDate);
        ordersMap.put("time",saveCurrentTime);
        ordersMap.put("state", "Not Shipped");
        localOrdersRef.child(pinCodeEditText.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int pinCodeCount = 0;
                if (snapshot.child("pincode").getValue(String.class) != null){
                    pinCodeCount = Integer.parseInt(Objects.requireNonNull(snapshot.child("count").getValue(String.class)));
                    System.out.println("Pin: "+pinCodeCount);
                }
                pinCodeCount = pinCodeCount + 1;
                localOrdersRef.child(pinCodeEditText.getText().toString()).child("pincode").setValue(pinCodeEditText.getText().toString());
                localOrdersRef.child(pinCodeEditText.getText().toString()).child("count").setValue(Integer.toString(pinCodeCount));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ConfirmFinalOrderActivity.this,"Failed to retrieve pin code count",Toast.LENGTH_SHORT).show();
            }
        });
        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User view")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ConfirmFinalOrderActivity.this,"Your final Order has been placed successfully.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

    }
}
