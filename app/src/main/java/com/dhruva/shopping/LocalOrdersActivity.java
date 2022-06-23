package com.dhruva.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dhruva.shopping.Model.LocalOrders;
import com.dhruva.shopping.ViewHolder.LocalOrdersHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocalOrdersActivity extends AppCompatActivity {
    private DatabaseReference LocalOrdersRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_orders);
        LocalOrdersRef = FirebaseDatabase.getInstance().getReference().child("Local Orders");
        recyclerView = findViewById(R.id.local_orders_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<LocalOrders> options =
                new FirebaseRecyclerOptions.Builder<LocalOrders>()
                        .setQuery(LocalOrdersRef, LocalOrders.class)
                        .build();

        FirebaseRecyclerAdapter<LocalOrders, LocalOrdersHolder> adapter =
                new FirebaseRecyclerAdapter<LocalOrders, LocalOrdersHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull LocalOrdersHolder localOrdersHolder, int i, @NonNull LocalOrders localOrders) {
                        localOrdersHolder.txtPinCode.setText(localOrders.getPincode());
                        localOrdersHolder.txtCount.setText(localOrders.getCount());
                    }

                    @NonNull
                    @Override
                    public LocalOrdersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.local_orders_item, parent, false);
                        LocalOrdersHolder holder = new LocalOrdersHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

}
