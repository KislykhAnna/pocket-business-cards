package com.project.carrera.pocketbusinesscards.DataBase;

import java.util.Comparator;

/**
 * Created by Carrera on 03.12.2017.
 */

// Класс, получаюющий на обработку переменные с базы данных

public class Card
{
    private int id;
    private String name;
    private String surname;
    private String company;
    private String position;
    private String number;
    private String email;
    private String location;

    public Card(int id, String name, String surname, String company, String position, String number, String email, String location) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.company = company;
        this.position = position;
        this.number = number;
        this.email = email;
        this.location = location;
    }

    public static final Comparator<Card> BY_SURNAME_ALPHABETICAL = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            return o1.getSurname().compareTo(o2.surname);
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
