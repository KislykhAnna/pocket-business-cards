package com.project.carrera.pocketbusinesscards;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.project.carrera.pocketbusinesscards.DataBase.DBAdapter;

public class Detail extends AppCompatActivity
{

    EditText changeName, changeSurname, changeCompany, changePosition, changeNumber, changeEmail, changeLocation;
    TextView showName, showSecondName, showCompany, showPosition, showLoc;
    Button changeBtn, callNumber, sendEmail;
    String tempTxt;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailupdated);

        // Инициализация toolbar
        initToolbar();

        // Получаем intent для заполнения данными окна "Подробно"
        Intent intent = getIntent();

        // Получаем данные по каждой строке в БД
        //region getExtras
        final String name = intent.getExtras().getString("name");
        final String secondname = intent.getExtras().getString("surname");
        final String company = intent.getExtras().getString("company");
        final String pos = intent.getExtras().getString("position");
        final String number = intent.getExtras().getString("number");
        final String email = intent.getExtras().getString("email");
        final String location = intent.getExtras().getString("location");
        final int id = intent.getExtras().getInt("id");
        //endregion

        // Инициализируем окна
        Init();

        // Устанавливаем текст в каждое из окон
        //region setText
        showName.setText(name);
        showSecondName.setText(secondname);
        showCompany.setText(company);
        showPosition.setText(pos);
        callNumber.setText(number);
        sendEmail.setText(email);
        showLoc.setText(location);
        //endregion

        // Проверяем пустые окна, если пустые, то заполняем их
        //region IF NULLABLE
        if(showName.getText().toString().length() == 0)
        {
            showName.setText("Имя не указано");
            showName.setTextColor(Color.GRAY);
        }

        if(showSecondName.getText().toString().length() == 0)
        {
            showSecondName.setText("Фамилия не указана");
            showSecondName.setTextColor(Color.GRAY);
        }

        if(showCompany.getText().toString().length() == 0)
        {
            showCompany.setText("Компания не указана");
            showCompany.setTextColor(Color.GRAY);
        }

        if(showPosition.getText().toString().length() == 0)
        {
            showPosition.setText("Должность не указана");
            showPosition.setTextColor(Color.GRAY);
        }

        if(callNumber.getText().toString().length() == 0)
        {
            callNumber.setText("Номер не указан");
            callNumber.setTextColor(Color.GRAY);
        }

        if(sendEmail.getText().toString().length() == 0)
        {
            sendEmail.setText("Email не указан");
            sendEmail.setTextColor(Color.GRAY);
        }

        if(showLoc.getText().toString().length() == 0)
        {
            showLoc.setText("Местоположение не указано");
            showLoc.setTextColor(Color.GRAY);
        }
        //endregion

        callNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((callNumber.getText().toString().length() == 0) || (callNumber.getText().toString() == "Номер не указан"));
                else
                {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", callNumber.getText().toString(), null));
                    CheckPerm(callIntent);
                }
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((sendEmail.getText().toString().length() == 0) || (sendEmail.getText().toString() == "Email не указан"));
                else {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", email, null));
                    //emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    startActivity(Intent.createChooser(emailIntent, "Отправить e-mail..."));
                }
            }
        });
    }

    // Создаем всплывающее окно
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);

        return true;
    }

    // Выбор элемента всплыващего меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Получаем id с файла menu_settings.xml
        int itemId = item.getItemId();

        switch (itemId)
        {
            case R.id.action_change:
                // Вызываем окно изменения данных
                showChangeActivity();
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
                break;
            case R.id.action_delete:
                // Получение intent для id карточки, чтобы далее удалить её
                Intent intent = getIntent();
                final int id = intent.getExtras().getInt("id");

                // Создание всплывающего окна для удаления визитной карточки
                final AlertDialog.Builder builder = new AlertDialog.Builder(Detail.this);
                builder.setMessage("Вы действительно хотите удалить визитную карточку?")
                        .setCancelable(false)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delete(id);
                                goToMain();
                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Удаление визитной карточки");
                alertDialog.show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToMain()
    {
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    // Инициализация верхнего меню
    public void initToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar((toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_action_navigation_arrow_back_inverted);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Инициализация переменных
    protected void Init()
    {
        changeBtn = (Button) findViewById(R.id.changeBtn);
        showName = (TextView) findViewById(R.id.showName);
        showSecondName = (TextView) findViewById(R.id.showSecondName);
        showCompany = (TextView) findViewById(R.id.showCompany);
        showPosition = (TextView) findViewById(R.id.showPosition);
        callNumber = (Button) findViewById(R.id.callMobileNumber);
        sendEmail = (Button) findViewById(R.id.sendEmail);
        showLoc = (TextView) findViewById(R.id.showLocation);
    }

    // Переход в окно изменения визитки
    private void showChangeActivity()
    {
        final Dialog d = new Dialog(this, R.style.AppDefault);

        d.setContentView(R.layout.activity_change);

        Intent intent = getIntent();

        //region getExtras
        final String name = intent.getExtras().getString("name");
        final String secondname = intent.getExtras().getString("surname");
        final String company = intent.getExtras().getString("company");
        final String pos = intent.getExtras().getString("position");
        final String number = intent.getExtras().getString("number");
        final String email = intent.getExtras().getString("email");
        final String location = intent.getExtras().getString("location");
        final int id = intent.getExtras().getInt("id");
        //endregion

        //region d.findView
        changeBtn = (Button) d.findViewById(R.id.changeBtn);
        changeName = (EditText) d.findViewById(R.id.changeName);
        changeSurname = (EditText) d.findViewById(R.id.changeSecondName);
        changeCompany = (EditText) d.findViewById(R.id.changeCompany);
        changePosition = (EditText) d.findViewById(R.id.changePosition);
        changeNumber = (EditText) d.findViewById(R.id.changeMobileNumber);
        changeEmail = (EditText) d.findViewById(R.id.changeEmail);
        changeLocation = (EditText) d.findViewById(R.id.changeLocation);
        //endregion

        //region setText
        changeName.setText(name);
        changeSurname.setText(secondname);
        changeCompany.setText(company);
        changePosition.setText(pos);
        changeNumber.setText(number);
        changeEmail.setText(email);
        changeLocation.setText(location);
        //endregion

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNullables() == true || checkLength() == true)
                { }
                else
                {
                    checkToUpper();
                    update(id, changeName.getText().toString(), changeSurname.getText().toString(), changeCompany.getText().toString(), changePosition.getText().toString(), changeNumber.getText().toString(), changeEmail.getText().toString(), changeLocation.getText().toString());
                    d.dismiss();
                    finish();
                }
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
            }
        });

        d.show();
    }

    // Удаление ячейки с базы данных
    private void delete(int id)
    {
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.openDB();
        long result = dbAdapter.Delete(id);
        if(result > 0)
        {
            this.finish();
        }else
        {
            Toast.makeText(getApplicationContext(), "Невозможно удалить", Toast.LENGTH_SHORT).show();
        }
        dbAdapter.closeDB();
    }

    // Проверка разрешения доступа на вызов
    private void CheckPerm(Intent intent)
    {
        startActivity(intent);
    }

    // Обновление элементов базы данных
    private void update(int id, String newName, String newSurname, String newCompany, String newPosition, String newNumber, String newEmail, String newLoc)
    {
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.openDB();
        long result = dbAdapter.Update(id, newName, newSurname, newCompany, newPosition, newNumber, newEmail, newLoc);

        if(result > 0)
        {
            changeName.setText(newName);
            changeSurname.setText(newSurname);
            changeCompany.setText(newCompany);
            changePosition.setText(newPosition);
            changeNumber.setText(newNumber);
            changeEmail.setText(newEmail);
            changeLocation.setText(newLoc);
            Toast.makeText(getApplicationContext(), "Изменения сохранены", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(getApplicationContext(), "Не удалось изменить", Toast.LENGTH_SHORT).show();
        }
        dbAdapter.closeDB();
    }

    public boolean checkNullables()
    {
        if ((changeName.getText().toString().length() == 0) || (changeSurname.getText().toString().length() == 0))
        {
            Toast.makeText(getApplicationContext(), "Имя и фамилия должны быть обязательно заполнены!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public boolean checkLength()
    {
        if ((changeName.getText().toString().length() > 25) || (changeSurname.getText().toString().length() > 25) || (changeCompany.getText().toString().length() > 25) || (changePosition.getText().toString().length() > 25) || (changeNumber.getText().toString().length() > 25) || (changeEmail.getText().toString().length() > 50) || (changeLocation.getText().toString().length() > 50))
        {
            Toast.makeText(getApplicationContext(), "Длина одной из строк превышает допустимую", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void checkToUpper()
    {
        if(changeSurname.getText().toString().length() != 0) {
            tempTxt = changeSurname.getText().toString();
            tempTxt = tempTxt.substring(0,1).toUpperCase() + tempTxt.substring(1);
            changeSurname.setText(tempTxt);
        }
        if(changeName.getText().toString().length() != 0) {
            tempTxt = changeName.getText().toString();
            tempTxt = tempTxt.substring(0,1).toUpperCase() + tempTxt.substring(1);
            changeName.setText(tempTxt);
        }
    }
}
