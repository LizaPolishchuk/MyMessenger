package com.example.android.messanger;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder{

    TextView message;

    public ViewHolder(View itemView) {
        super(itemView);
        message = (TextView) itemView.findViewById(R.id.tv_item);
    }
}
