package com.project.carrera.pocketbusinesscards;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.carrera.pocketbusinesscards.DataBase.Card;
import com.project.carrera.pocketbusinesscards.DataBase.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by Carrera on 03.12.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder>
{
    // Класс, который собирает данные для элемента RecyclerView
    Context context;
    ArrayList<Card> cards;
    CardView cardView;

    public RecyclerAdapter(Context context, ArrayList<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, null);
        RecyclerHolder holder = new RecyclerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, int position)
    {
        holder.personTxt.setText(cards.get(position).getName() + " " + cards.get(position).getSurname());
        holder.positionTxt.setText(cards.get(position).getPosition());
        holder.companyTxt.setText(cards.get(position).getCompany());

        //region IF NULLABLE
        if (holder.personTxt.getText().toString().length() == 0)
        {
            holder.personTxt.setText("Должность не указана");
            holder.personTxt.setTextColor(Color.GRAY);
        }

        if (holder.positionTxt.getText().toString().length() == 0)
        {
            holder.positionTxt.setText("Должность не указана");
            holder.positionTxt.setTextColor(Color.GRAY);
        }

        if (holder.companyTxt.getText().toString().length() == 0)
        {
            holder.companyTxt.setText("Компания не указана");
            holder.companyTxt.setTextColor(Color.GRAY);
        }
        //endregion

        holder.setItemClickListener(new ItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                // Переход на создание
                Intent intent = new Intent(context, Detail.class);

                // Забираем нужные данные
                //region putExtra
                intent.putExtra("name", cards.get(position).getName());
                intent.putExtra("surname", cards.get(position).getSurname());
                intent.putExtra("company", cards.get(position).getCompany());
                intent.putExtra("position", cards.get(position).getPosition());
                intent.putExtra("number", cards.get(position).getNumber());
                intent.putExtra("email", cards.get(position).getEmail());
                intent.putExtra("location", cards.get(position).getLocation());
                intent.putExtra("id", cards.get(position).getId());
                //endregion

                // Запускаем окно
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return cards.size();
    }

    // Устанавливаем фильтр по карточкам
    public void setfilter(ArrayList<Card> filter) {
        cards = new ArrayList<>();
        cards.addAll(filter);
        notifyDataSetChanged();
    }
}
