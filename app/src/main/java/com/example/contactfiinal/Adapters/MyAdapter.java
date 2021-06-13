package com.example.contactfiinal.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactfiinal.Model.MyContacts;
import com.example.contactfiinal.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder>
{
    Context context;
    ArrayList<MyContacts> myContactsArrayList;
    public MyAdapter(Context context, ArrayList<MyContacts> myContactsArrayList)
    {
    this.context = context;
    this.myContactsArrayList = myContactsArrayList;

    }


    @Override
    public MyAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
       return new MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyAdapterViewHolder holder, int position) {
        MyContacts myContacts = myContactsArrayList.get(position);
        holder.tvName.setText(myContacts.getName());
        holder.tvNumber.setText(myContacts.getNumber());

    }

    @Override
    public int getItemCount() {
        return myContactsArrayList.size();
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName,tvNumber;
        AppCompatImageButton callButton,messageButton;

        public MyAdapterViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView)itemView.findViewById(R.id.txtName);
            tvNumber = (TextView)itemView.findViewById(R.id.txtNumber);
            callButton = itemView.findViewById(R.id.ibcall);
            messageButton = itemView.findViewById(R.id.ibmessage);

            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + myContactsArrayList.get(getAdapterPosition()).getNumber()));
                    context.startActivity(intent);
                }
            });
                messageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:" + myContactsArrayList.get(getAdapterPosition()).getNumber()));
                        context.startActivity(i);


                    }
                });
        }
    }
}
