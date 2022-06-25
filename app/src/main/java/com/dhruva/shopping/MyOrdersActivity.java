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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyOrdersActivity extends AppCompatActivity {
    private RecyclerView ordersList;
    private DatabaseReference ordersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Cart List").child("User view").child(Prevalent.currentOnlineUser.getPhone()).child("Products");
        ordersList = findViewById(R.id.my_orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<MyOrders> options=
                new FirebaseRecyclerOptions.Builder<MyOrders>()
                        .setQuery(ordersRef, MyOrders.class)
                        .build();
        FirebaseRecyclerAdapter<MyOrders, MyOrdersActivity.MyOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<MyOrders, MyOrdersActivity.MyOrdersViewHolder>(options) {
                    @NonNull
                    @Override
                    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_items,parent,false);
                        return new MyOrdersViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull MyOrdersActivity.MyOrdersViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final MyOrders model) {
                        holder.orderName.setText(model.getPname());
                        holder.orderDateTime.setText(model.getPid());
                        holder.orderPrice.setText("Price: "+model.getPrice());
                        holder.orderProductQuantity.setText("Quantity: "+model.getQuantity());

                    }
                };
        ordersList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class MyOrdersViewHolder extends RecyclerView.ViewHolder{

        public TextView orderName, orderDateTime, orderPrice, orderProductQuantity;
        public MyOrdersViewHolder(View itemView) {
            super(itemView);
            orderName = itemView.findViewById(R.id.my_order_name);
            orderDateTime = itemView.findViewById(R.id.my_order_date_time);
            orderPrice = itemView.findViewById(R.id.my_order_price);
            orderProductQuantity = itemView.findViewById(R.id.product_quantity);
        }
    }
}

