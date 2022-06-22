package com.dhruva.shopping.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dhruva.shopping.R;

public class LocalOrdersHolder extends RecyclerView.ViewHolder {
    public TextView txtPinCode, txtCount;
    public LocalOrdersHolder(View itemView)
    {
        super(itemView);
        txtPinCode = itemView.findViewById(R.id.pincode_text);
        txtCount = itemView.findViewById(R.id.pincode_count_text);
    }
}