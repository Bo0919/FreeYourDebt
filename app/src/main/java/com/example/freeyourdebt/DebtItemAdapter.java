package com.example.freeyourdebt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DebtItemAdapter extends RecyclerView.Adapter<DebtItemAdapter.MyViewHolder> {
    private ItemClickListener itemClickListener;
    Context context;
    ArrayList<RecycleViewModel> arrayList;
    public DebtItemAdapter(Context context, ArrayList<RecycleViewModel> arrayList,ItemClickListener itemClickListener){
    this.context = context;
    this.arrayList = arrayList;
    this.itemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public DebtItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.debtitem,parent,false);
        return new DebtItemAdapter.MyViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtItemAdapter.MyViewHolder holder, int position) {
        if(arrayList.get(position).getDebtImg().equals("House")){
            holder.itemImg.setImageResource(R.drawable.baseline_cottage_24);
        } else if (arrayList.get(position).getDebtImg().equals("Car")) {
            holder.itemImg.setImageResource(R.drawable.baseline_car_repair_24);
        } else if (arrayList.get(position).getDebtImg().equals("Invest")){
                holder.itemImg.setImageResource(R.drawable.baseline_currency_exchange_24);
        } else if (arrayList.get(position).getDebtImg().equals("Credit card")){
            holder.itemImg.setImageResource(R.drawable.baseline_credit_card_24);
        } else {
            holder.itemImg.setImageResource(R.drawable.baseline_question_mark_24);
        }

        holder.dName.setText(arrayList.get(position).getDebtName());
    holder.dID.setText(arrayList.get(position).getDebtID());
    holder.dType.setText(arrayList.get(position).getDebtType());
    holder.dAmount.setText(arrayList.get(position).getDebtAmount());
    holder.dPayment.setText(arrayList.get(position).getDebtPayment());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

   public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImg;
        TextView dName,dID,dType,dAmount,dPayment;
       public MyViewHolder(@NonNull View itemView,ItemClickListener itemClickListener) {
           super(itemView);
           itemImg = itemView.findViewById(R.id.imgDebtItem);
           dName = itemView.findViewById(R.id.txtItemName);
           dID = itemView.findViewById(R.id.txtItemID);
           dType = itemView.findViewById(R.id.txtItemType);
           dAmount = itemView.findViewById(R.id.txtItemAmount);
           dPayment = itemView.findViewById(R.id.txtItemPayment);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(itemClickListener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            itemClickListener.onItemClickListener(pos);
                        }
                   }
               }
           });
       }
   }
}