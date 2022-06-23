package com.dhruva.shopping;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class AdminCategoryActivity extends AppCompatActivity {
    private Button LogoutBtn, CheckOrdersBtn, cloth_work_btn, metal_work_btn, paper_work_btn, stone_work_btn, check_orders_btn, other_artifacts_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        LogoutBtn = findViewById(R.id.admin_logout_btn);
        LogoutBtn.setOnClickListener(view -> {
            Intent intent= new Intent(AdminCategoryActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
        CheckOrdersBtn = findViewById(R.id.check_orders_btn);


        CheckOrdersBtn.setOnClickListener(view -> {
            Intent intent= new Intent(AdminCategoryActivity.this,AdminNewOrdersActivity.class);
            startActivity(intent);
        });

        cloth_work_btn = findViewById(R.id.cloth_work_btn);
        metal_work_btn = findViewById(R.id.metal_work_btn);
        paper_work_btn = findViewById(R.id.paper_work_btn);
        stone_work_btn = findViewById(R.id.stone_work_btn);
        other_artifacts_btn = findViewById(R.id.other_artifacts_btn);

        cloth_work_btn.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
            intent.putExtra("category", "clothWork");
            startActivity(intent);
        });
        metal_work_btn.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
            intent.putExtra("category", "metalWork");
            startActivity(intent);
        });
        paper_work_btn.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
            intent.putExtra("category", "paperWork");
            startActivity(intent);
        });
        stone_work_btn.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
            intent.putExtra("category", "stoneWork");
            startActivity(intent);
        });
        other_artifacts_btn.setOnClickListener(view -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
            intent.putExtra("category", "otherArtifacts");
            startActivity(intent);
        });


//        tShirts = findViewById(R.id.t_shirts);
//        sportsTShirts = findViewById(R.id.sports_t_shirts);
//        femaleDresses = findViewById(R.id.female_dresses);
//        sweathers = findViewById(R.id.sweathers);
//
//        glasses = findViewById(R.id.glasses);
//        hatsCaps = findViewById(R.id.hats_caps);
//        walletsBagsPurses = findViewById(R.id.purses_bags_wallets);
//        shoes = findViewById(R.id.shoes);
//
//        headPhonesHandFree = findViewById(R.id.headphones_handfree);
//        Laptops = findViewById(R.id.laptop_pc);
//        watches = findViewById(R.id.watches);
//        mobilePhones = findViewById(R.id.mobilephones);


//        tShirts.setOnClickListener(view -> {
//            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
//            intent.putExtra("category", "tShirts");
//            startActivity(intent);
//        });
//        sportsTShirts.setOnClickListener(view -> {
//            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
//            intent.putExtra("category", "Sports tShirts");
//            startActivity(intent);
//        });
//
//
//        femaleDresses.setOnClickListener(view -> {
//            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
//            intent.putExtra("category", "Female Dresses");
//            startActivity(intent);
//        });
//
//
//        sweathers.setOnClickListener(view -> {
//            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
//            intent.putExtra("category", "Sweathers");
//            startActivity(intent);
//        });
//
//
//        glasses.setOnClickListener(view -> {
//            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
//            intent.putExtra("category", "Glasses");
//            startActivity(intent);
//        });
//
//
//        hatsCaps.setOnClickListener(view -> {
//            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
//            intent.putExtra("category", "Hats Caps");
//            startActivity(intent);
//        });
//
//
//
//        walletsBagsPurses.setOnClickListener(view -> {
//            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
//            intent.putExtra("category", "Wallets Bags Purses");
//            startActivity(intent);
//        });
//
//
//        shoes.setOnClickListener(view -> {
//            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
//            intent.putExtra("category", "Shoes");
//            startActivity(intent);
//        });
//
//
//
//        headPhonesHandFree.setOnClickListener(view -> {
//            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
//            intent.putExtra("category", "HeadPhones HandFree");
//            startActivity(intent);
//        });
//
//
//        Laptops.setOnClickListener(view -> {
//            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
//            intent.putExtra("category", "Laptops");
//            startActivity(intent);
//        });
//
//
//        watches.setOnClickListener(view -> {
//            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
//            intent.putExtra("category", "Watches");
//            startActivity(intent);
//        });
//
//
//        mobilePhones.setOnClickListener(view -> {
//            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
//            intent.putExtra("category", "Mobile Phones");
//            startActivity(intent);
//        });
    }
}
