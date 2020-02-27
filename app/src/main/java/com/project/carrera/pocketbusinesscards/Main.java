package com.project.carrera.pocketbusinesscards;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.project.carrera.pocketbusinesscards.DataBase.Card;
import com.project.carrera.pocketbusinesscards.DataBase.DBAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class Main extends AppCompatActivity
{
    private static final int LAYOUT = R.layout.activity_main;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    ArrayList<Card> cards = new ArrayList<>();
    MaterialSearchView searchView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        // Определение списка в main
        recyclerView = (RecyclerView)findViewById(R.id.mRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new RecyclerAdapter(this, cards);

        initToolbar();

        initFab();

        searchView = (MaterialSearchView)findViewById(R.id.search_view);

        retrieve();
    }

    // Инициализация верхнего меню
    public void initToolbar ()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar((toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_information);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutAppActivity();
            }
        });
        toolbar.inflateMenu(R.menu.search);

    }

    // Кнопка создания
    public void initFab()
    {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BusinessCardCreate();
                        retrieve();
                    }
                }
        );
    }

    // Создание поиска на главном экране
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.search,menu);
        final MenuItem item = menu.findItem(R.id.search);
        searchView.setMenuItem(item);

        // Устанавливаем читатель на текст в поиске
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener(){

            // Проверяем на подтвержденный текст
            @Override
            public boolean onQueryTextSubmit(String query) {

                item.collapseActionView();
                retrieve();
                return true;
            }

            // Проверяем на изменения текста
            @Override
            public boolean onQueryTextChange(String newText) {

                    final ArrayList<Card> filtermodelist = filter(cards, newText);

                    // Устанавливаем фильтр на список
                    adapter.setfilter(filtermodelist);

                    retrieve();

                return true;

            }


        });

        retrieve();

        return true;
    }

    // Фильтрация списка по поиску в очередь
    private ArrayList<Card> filter(ArrayList<Card> pl, String query)
    {
        // Устанавливаем очередь
        query = query.toLowerCase();
        final ArrayList<Card> filteredModeList = new ArrayList<>();

        // Делаем фильтр для строки ИМЕНИ
        for (Card model:pl)
        {
            final String name = model.getSurname().toLowerCase();
            if (name.startsWith(query))
            {
                filteredModeList.add(model);
            }
        }
        retrieve();
        return filteredModeList;
    }

    // Переход в окно "О программе"
    private void openAboutAppActivity()
    {
        Intent intent = new Intent(this, aboutapp.class);
        startActivity(intent);
    }

    // Переход в окно создания визитной карточки
    public void BusinessCardCreate()
    {
        Intent intent = new Intent(this, BusinessCardCreate.class);
        startActivity(intent);
        retrieve();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            retrieve();
        }
    }

    // Обновление базы данных
    private void retrieve()
    {
        cards.clear();

        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.openDB();

        Cursor cursor = dbAdapter.getAllCards();

        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String surname = cursor.getString(2);
            String company = cursor.getString(3);
            String position = cursor.getString(4);
            String number = cursor.getString(5);
            String email = cursor.getString(6);
            String location = cursor.getString(7);

            Card card = new Card(id, name, surname, company, position, number, email, location);

            cards.add(card);

        }

        if(!(cards.size() < 0))
        {
            recyclerView.setAdapter(adapter);

            Collections.sort(cards, Card.BY_SURNAME_ALPHABETICAL);
        }

        dbAdapter.closeDB();
    }

    // Постоянная работа обновления БД
    protected void onResume()
    {
        super.onResume();
        retrieve();
    }

}