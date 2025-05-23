package com.example.mobilestore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilestore.R;
import com.example.mobilestore.model.Phone;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder> {
    private List<Phone> phones;
    private OnPhoneClickListener listener;

    public interface OnPhoneClickListener {
        void onPhoneClick(Phone phone);
        void onPhoneAdjustClick(Phone phone);
    }

    public PhoneAdapter(List<Phone> phones, OnPhoneClickListener listener) {
        this.phones = phones;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_item_phone, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        Phone phone = phones.get(position);
        holder.bind(phone, listener);
    }

    @Override
    public int getItemCount() {
        return phones != null ? phones.size() : 0;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    static class PhoneViewHolder extends RecyclerView.ViewHolder {
        private final TextView brandText;
        private final TextView phoneNameText;
        private final TextView quantityText;
        Button buttonAdjust;

        PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            brandText = itemView.findViewById(R.id.tvBrand);
            phoneNameText = itemView.findViewById(R.id.tvPhoneName);
            quantityText = itemView.findViewById(R.id.tvQuantity);
            buttonAdjust = itemView.findViewById(R.id.buttonAdjust);
        }

        void bind(Phone phone, OnPhoneClickListener listener) {
            // Set basic info
            brandText.setText(phone.getBrand());
            phoneNameText.setText(phone.getPhoneName());
            quantityText.setText("Quantity: " + phone.getStockQuantity());

            // Set click listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPhoneClick(phone);
                }
            });

            buttonAdjust.setOnClickListener(v -> {
                if (listener != null) listener.onPhoneAdjustClick(phone);
            });
        }
    }

    public void removePhoneById(String phoneId) {
        for (int i = 0; i < phones.size(); i++) {
            if (phones.get(i).getId().equals(phoneId)) {
                phones.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
    }

    public void updatePhones(List<Phone> newPhones) {
        this.phones = newPhones;
        notifyDataSetChanged();
    }
}
