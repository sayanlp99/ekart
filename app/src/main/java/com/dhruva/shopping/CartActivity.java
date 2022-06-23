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
import android.widget.LinearLayout;
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
import java.util.Objects;

public class CartActivity extends AppCompatActivity{
    private LinearLayout shippingPriorityLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount, txtMsg1;
    private EditText datePicker;
    private int overTotalPrice=0;
    private RadioGroup shippingPriorityGroup;
    private String deliveryOption, shippingPrice, onlyProductPrice;
    private String finalDate;
    final Calendar myCalendar= Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        shippingPriorityLayout = findViewById(R.id.shipping_priority_layout);
        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NextProcessBtn = findViewById(R.id.next_btn);
        txtTotalAmount = findViewById(R.id.total_price);
        txtMsg1 = findViewById(R.id.msg1);
        shippingPriorityGroup = findViewById(R.id.shipping_priority_group);
        datePicker= findViewById(R.id.priority_shipment_date_picker);
        shippingPriorityGroup.clearCheck();
        shippingPriorityGroup.setOnCheckedChangeListener((group, checkedId) -> {
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
        });
        NextProcessBtn.setOnClickListener(view -> {
            if (shippingPriorityGroup.getCheckedRadioButtonId() == R.id.shipping_priority_option2 || shippingPriorityGroup.getCheckedRadioButtonId() == R.id.shipping_priority_option3 || shippingPriorityGroup.getCheckedRadioButtonId() == R.id.shipping_priority_option4 || shippingPriorityGroup.getCheckedRadioButtonId() == R.id.shipping_priority_option5 || datePicker.getText().length() != 0){
                onlyProductPrice = String.valueOf(overTotalPrice);
                switch (deliveryOption){
                    case "1":
                        overTotalPrice = overTotalPrice + 80;
                        shippingPrice = "80";
                        break;
                    case "2":
                        overTotalPrice = overTotalPrice + 50;
                        shippingPrice = "50";
                        break;
                    case "3":
                        overTotalPrice = overTotalPrice + 30;
                        shippingPrice = "30";
                        break;
                    case "4":
                        overTotalPrice = overTotalPrice + 20;
                        shippingPrice = "20";
                        break;
                    case "5":
                        overTotalPrice = overTotalPrice + 10;
                        shippingPrice = "10";
                        break;
                }
                txtTotalAmount.setText("Total Price = Rs."+ overTotalPrice);
                Intent intent = new Intent(CartActivity.this,ShippingChargesActivity.class);
                intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                intent.putExtra("Shipping priority", deliveryOption);
                intent.putExtra("Shipping Date", finalDate);
                intent.putExtra("Shipping Price", shippingPrice);
                intent.putExtra("Product Price", onlyProductPrice);
                startActivity(intent);
            }
            else{
                Toast.makeText(CartActivity.this,"Select Delivery Options",Toast.LENGTH_SHORT).show();
            }

        });
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabel();
        };
        long now = myCalendar.getTimeInMillis();
        DatePickerDialog datePickerDialog = new DatePickerDialog(CartActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(now + (86400000));
        datePickerDialog.getDatePicker().setMaxDate(now + (86400000*5));
        datePicker.setOnClickListener(view -> {
            shippingPriorityGroup.clearCheck();
            deliveryOption = "1";
            datePickerDialog.show();
        });
    }

    private void updateLabel(){
        String myFormat="MMM dd yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        finalDate = dateFormat.format(myCalendar.getTime());
        System.out.println("Shipping Time: "+dateFormat.format(myCalendar.getTime()));
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

                holder.itemView.setOnClickListener(view -> {
                    CharSequence[] options1 = new CharSequence[]
                            {
                                    "Edit",
                                    "Remove"
                            };
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle("Cart Options: ");
                    builder.setItems(options1, (dialogInterface, i) -> {
                        if (i == 0) {
                            Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                            intent.putExtra("pid", model.getPid());
                            startActivity(intent);
                        }
                        if (i == 1) {
                            cartListRef.child("User view")
                                    .child(Prevalent.currentOnlineUser.getPhone())
                                    .child("Products")
                                    .child(model.getPid())
                                    .removeValue()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(CartActivity.this, "Item removed Successfully.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                        }
                    });
                    builder.show();
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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String shippingState = Objects.requireNonNull(dataSnapshot.child("state").getValue()).toString();
                    String userName = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                    if (shippingState.equals("Shipped")){
                        txtTotalAmount.setText("TDear "+userName+"\n order is shipped successfully.");
                        recyclerView.setVisibility(View.INVISIBLE);
                        txtMsg1.setVisibility(View.VISIBLE);
                        txtMsg1.setText("Congratulations, Your Final order has been shipped successfully. Soon you will received your order at your door step.");
                        NextProcessBtn.setVisibility(View.INVISIBLE);
                        shippingPriorityLayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(CartActivity.this,"You can purchase more products, Once you received your first order",Toast.LENGTH_SHORT).show();
                    }
                    else if (shippingState.equals("Not Shipped")){
                        txtTotalAmount.setText("Shipping State = Not Shipped");
                        recyclerView.setVisibility(View.INVISIBLE);
                        txtMsg1.setVisibility(View.VISIBLE);
                        shippingPriorityLayout.setVisibility(View.INVISIBLE);

                        NextProcessBtn.setVisibility(View.INVISIBLE);
                        Toast.makeText(CartActivity.this,"You can purchase more products, Once you received your first order",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
