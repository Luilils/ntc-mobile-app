package com.ntc.mobileapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ntc.mobileapp.R;
import com.ntc.mobileapp.models.PaymentEntry;
import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.PaymentViewHolder> {
    private List<PaymentEntry> payments = new ArrayList<>();

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment_history, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        PaymentEntry payment = payments.get(position);
        holder.bind(payment);
    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public void updatePayments(List<PaymentEntry> newPayments) {
        this.payments = newPayments;
        notifyDataSetChanged();
    }

    static class PaymentViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateText;
        private final TextView amountText;
        private final TextView descriptionText;
        private final TextView statusText;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.paymentDateText);
            amountText = itemView.findViewById(R.id.paymentAmountText);
            descriptionText = itemView.findViewById(R.id.paymentDescriptionText);
            statusText = itemView.findViewById(R.id.paymentStatusText);
        }

        public void bind(PaymentEntry payment) {
            dateText.setText(payment.getDate());
            amountText.setText(payment.getAmount());
            descriptionText.setText(payment.getDescription());
            statusText.setText(payment.getStatus());
        }
    }
} 