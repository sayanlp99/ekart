package com.dhruva.shopping;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhruva.shopping.Model.MyOrders;
import com.dhruva.shopping.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MyOrdersActivity extends AppCompatActivity {
    private RecyclerView ordersList;
    private DatabaseReference ordersRef;
    TextView orderDate, orderAmount,OrderAddress, pincode, productStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        orderDate = findViewById(R.id.my_order_date);
        orderAmount = findViewById(R.id.my_order_amount);
        OrderAddress = findViewById(R.id.my_order_address);
        pincode = findViewById(R.id.my_order_pincode);
        productStatus = findViewById(R.id.my_order_state);
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());
        ordersRef.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MyOrders myorders = snapshot.getValue(MyOrders.class);
                orderDate.setText(Objects.requireNonNull(myorders).getDate());
                orderAmount.setText("Rs. " + myorders.getTotalAmount() + "/-");
                OrderAddress.setText(myorders.getAddress());
                pincode.setText(myorders.getPincode());
                productStatus.setText(myorders.getState());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}

