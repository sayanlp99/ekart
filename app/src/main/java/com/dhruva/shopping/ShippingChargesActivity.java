package com.dhruva.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShippingChargesActivity extends AppCompatActivity {
    Button nextButton;
    String overTotalPrice, deliveryOption, finalDate, shippingPrice, productPrice;
    TextView TotalProductPrice, ShippingPrice, TotalPayablePrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_charges);
        overTotalPrice = getIntent().getStringExtra("Total Price");
        deliveryOption = getIntent().getStringExtra("Shipping priority");
        finalDate = getIntent().getStringExtra("Shipping Date");
        shippingPrice = getIntent().getStringExtra("Shipping Price");
        productPrice = getIntent().getStringExtra("Product Price");
        TotalProductPrice = findViewById(R.id.total_product_price);
        ShippingPrice = findViewById(R.id.shipping_price);
        TotalPayablePrice = findViewById(R.id.total_payable_amount);
        TotalPayablePrice.setText(overTotalPrice);
        ShippingPrice.setText(shippingPrice);
        TotalProductPrice.setText(productPrice);
        nextButton = findViewById(R.id.final_next_btn);
        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShippingChargesActivity.this,ConfirmFinalOrderActivity.class);
            intent.putExtra("Total Price", String.valueOf(overTotalPrice));
            intent.putExtra("Shipping priority", deliveryOption);
            intent.putExtra("Shipping Date", finalDate);
            startActivity(intent);
            finish();
        });
    }
}
