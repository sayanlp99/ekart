package com.dhruva.shopping;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dhruva.shopping.Model.Cart;
import com.dhruva.shopping.Prevalent.CartViewHolder;
import com.dhruva.shopping.Prevalent.Prevalent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CartActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount, txtMsg1;
    private EditText datePicker;
    private int overTotalPrice=0;
    private RadioGroup shippingPriorityGroup;
    private String deliveryOption;
    final Calendar myCalendar= Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NextProcessBtn = (Button)findViewById(R.id.next_btn);
        txtTotalAmount = (TextView)findViewById(R.id.total_price);
        txtMsg1 = (TextView)findViewById(R.id.msg1);
        shippingPriorityGroup = (RadioGroup)findViewById(R.id.shipping_priority_group);
        datePicker=(EditText) findViewById(R.id.priority_shipment_date_picker);
        shippingPriorityGroup.clearCheck();
        shippingPriorityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                switch(checkedId){
                    case R.id.shipping_priority_option2:
                        deliveryOption = "2";
                        break;
                    case R.id.shipping_priority_option3:
                        deliveryOption = "3";
                        break;
                    case R.id.shipping_priority_option4:
                        deliveryOption = "4";
                        break;
                    case R.id.shipping_priority_option5:
                        deliveryOption = "5";
                        break;
                }
                datePicker.getText().clear();
            }
        });
        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!shippingPriorityGroup.isSelected()){
                    switch (deliveryOption){
                        case "1":
                            overTotalPrice = overTotalPrice + 80;
                            break;
                        case "2":
                            overTotalPrice = overTotalPrice + 50;
                            break;
                        case "3":
                            overTotalPrice = overTotalPrice + 30;
                            break;
                        case "4":
                            overTotalPrice = overTotalPrice + 20;
                            break;
                        case "5":
                            overTotalPrice = overTotalPrice + 10;
                            break;
                    }
                    txtTotalAmount.setText("Total Price = Rs."+String.valueOf(overTotalPrice));
                    Intent intent = new Intent(CartActivity.this,ConfirmFinalOrderActivity.class);
                    intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                    intent.putExtra("Shipping priority", deliveryOption);
                    intent.putExtra("Shipping Date", datePicker.getText());
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(CartActivity.this,"Select Delivery Options",Toast.LENGTH_SHORT).show();
                }

            }
        });
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);

                updateLabel();
            }
        };
        long now = myCalendar.getTimeInMillis();
        DatePickerDialog datePickerDialog = new DatePickerDialog(CartActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(now + (86400000));
        datePickerDialog.getDatePicker().setMaxDate(now + (86400000*5));
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shippingPriorityGroup.clearCheck();
                deliveryOption = "1";
                datePickerDialog.show();
            }
        });
    }

    private void updateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        datePicker.setText(dateFormat.format(myCalendar.getTime()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckOrderState();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("User view")
                                .child(Prevalent.currentOnlineUser.getPhone()).child("Products"),Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {
                holder.txtProductQuantity.setText("Quantity = "+model.getQuantity());
                holder.txtProductPrice.setText("Price = "+model.getPrice()+" Rs.");
                holder.txtProductName.setText(model.getPname());
                int oneTyprProductTPrice = ((Integer.valueOf(model.getPrice())))* Integer.valueOf(model.getQuantity());
                overTotalPrice = overTotalPrice + oneTyprProductTPrice;

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options: ");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i==0){
                                    Intent intent = new Intent(CartActivity.this,ProductDetailsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }
                                if (i==1){
                                    cartListRef.child("User view")
                                            .child(Prevalent.currentOnlineUser.getPhone())
                                            .child("Products")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(CartActivity.this,"Item removed Successfully.",Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(CartActivity.this,HomeActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    private void CheckOrderState()
    {
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String shippingState = dataSnapshot.child("state").getValue().toString();
                    String userName = dataSnapshot.child("name").getValue().toString();
                    if (shippingState.equals("Shipped")){
                        txtTotalAmount.setText("TDear "+userName+"\n order is shipped successfully.");
                        recyclerView.setVisibility(View.GONE);
                        txtMsg1.setVisibility(View.VISIBLE);
                        txtMsg1.setText("Congratulations, Your Final order has been shipped successfully. Soon you will received your order at your door step.");
                        NextProcessBtn.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this,"You can purchase more products, Once you received your first order",Toast.LENGTH_SHORT).show();
                    }
                    else if (shippingState.equals("Not Shipped")){
                        txtTotalAmount.setText("Shipping State = Not Shipped");
                        recyclerView.setVisibility(View.GONE);
                        txtMsg1.setVisibility(View.VISIBLE);

                        NextProcessBtn.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this,"You can purchase more products, Once you received your first order",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
