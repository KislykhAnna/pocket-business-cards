package com.project.carrera.pocketbusinesscards;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.project.carrera.pocketbusinesscards.DataBase.ItemClickListener;

/**
 * Created by Carrera on 03.12.2017.
 */

public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    TextView personTxt, positionTxt, companyTxt;
    ItemClickListener itemClickListener;

    public RecyclerHolder(View itemView)
    {
        super(itemView);

        personTxt = itemView.findViewById(R.id.nameTxt);
        positionTxt = itemView.findViewById(R.id.positionTxt);
        companyTxt = itemView.findViewById(R.id.companyTxt);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener = ic;
    }
}
