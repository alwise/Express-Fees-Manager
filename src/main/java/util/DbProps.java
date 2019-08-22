package main.java.util;

import static main.java.models.Constants.*;

public enum DbProps {


    connection_str("jdbc:sqlite:dox.dll"),

    personTable("person"),

    feesTable("fees"),

    insert("insert into"),

    id("id"),

    name("name"),

    mobile("mobile"),

    male("male"),

    female("female"),

    gender("gender"),

    level("level"),

    photo("photo"),

    date("date"),

    amount_paid("amount_paid"),

    amount_due("amount_due"),

     YEAR_PERIOD(PERIOD.concat(YEAR));



    private String prop;

    DbProps(String prop) {
        this.prop = prop;
    }

    public String getProp(){
        return this.prop;
    }



}



