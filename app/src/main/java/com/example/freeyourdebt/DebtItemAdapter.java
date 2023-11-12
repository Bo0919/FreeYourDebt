package com.example.freeyourdebt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DebtItemAdapter extends RecyclerView.Adapter implements ItemClickListener{

    ArrayList<RecycleViewModle> arrayList;
    LayoutInflater layoutInflater;
    ItemClickListener itemClickListener;
    //Integer getItem(int id){ return arrayList.get(id);}

    public DebtItemAdapter (Context context, ArrayList<RecycleViewModle> arrayList, ManagementActivity itemClickListener1 ){
        ArrayList aList = arrayList;
       itemClickListener = itemClickListener1;
        layoutInflater = LayoutInflater.from(context);}


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycleview_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).txt.setText(arrayList.get(position).getDebtInfo());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txtDebtItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClickListener(view, getAdapterPosition());
                    }
                }
            });
        }
    }
    @Override
    public void onItemClickListener(View view, int adapterPosition) {

    }
}