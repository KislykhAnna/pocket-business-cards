package com.project.carrera.pocketbusinesscards;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.carrera.pocketbusinesscards.DataBase.DBAdapter;

public class BusinessCardCreate extends AppCompatActivity
{
    EditText nameTxt, surnameTxt, companyTxt, positionTxt, numberTxt, emailTxt, locationTxt;
    String tempTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_card);

        //region d.findView
        nameTxt = findViewById(R.id.enterName);
        surnameTxt = findViewById(R.id.editSecondName);
        companyTxt = findViewById(R.id.editCompany);
        positionTxt = findViewById(R.id.editPosition);
        numberTxt = findViewById(R.id.editMobileNumber);
        emailTxt = findViewById(R.id.editEmail);
        locationTxt = findViewById(R.id.editLocation);
        Button addBtn = findViewById(R.id.ADDbutton);
        //endregion

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckNullables() || CheckLength())
                { }
                else
                {
                    CheckToUpper();
                    Save(nameTxt.getText().toString(), surnameTxt.getText().toString(), companyTxt.getText().toString(), positionTxt.getText().toString(), numberTxt.getText().toString(), emailTxt.getText().toString(), locationTxt.getText().toString());
                    GoToMain();
                }
            }
        });

        InitToolbar();

    }

    public void GoToMain()
    {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    public void CheckToUpper()
    {
        if(surnameTxt.getText().toString().length() != 0) {
            tempTxt = surnameTxt.getText().toString();
            tempTxt = tempTxt.substring(0,1).toUpperCase() + tempTxt.substring(1);
            surnameTxt.setText(tempTxt);
        }
        if(nameTxt.getText().toString().length() != 0) {
            tempTxt = nameTxt.getText().toString();
            tempTxt = tempTxt.substring(0,1).toUpperCase() + tempTxt.substring(1);
            nameTxt.setText(tempTxt);
        }
    }

    public boolean CheckNullables()
    {
        if ((nameTxt.getText().toString().length() == 0) || (surnameTxt.getText().toString().length() == 0))
        {
            Toast.makeText(getApplicationContext(), "Имя и фамилия должны быть обязательно заполнены!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean CheckLength()
    {
        if ((nameTxt.getText().toString().length() > 25) || (surnameTxt.getText().toString().length() > 25) || (companyTxt.getText().toString().length() > 25) || (positionTxt.getText().toString().length() > 25) || (numberTxt.getText().toString().length() > 25) || (emailTxt.getText().toString().length() > 50) || (locationTxt.getText().toString().length() > 50))
        {
            Toast.makeText(getApplicationContext(), "Длина одной из строк превышает допустимую", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    // Инициализация верхней строки с меню
    public void InitToolbar () {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar((toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Save(String name, String surname, String company, String position, String number, String email, String location) {
        DBAdapter dbAdapter = new DBAdapter(this);

        // Открываем БД
        dbAdapter.openDB();

        // Совершаем действие сохранения
        long result = dbAdapter.add(name, surname, company, position, number, email, location);

        if (result > 0) {
            nameTxt.setText("");
            positionTxt.setText("");
        } else {
            Snackbar.make(nameTxt, "Unable to Save", Snackbar.LENGTH_SHORT).show();
        }

        dbAdapter.closeDB();
    }
}
