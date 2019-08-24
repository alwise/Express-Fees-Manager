package main.java.util;

import static main.java.models.Constants.*;

public enum DbProps {


    connection_str("jdbc:sqlite:dox.dll"),

    userTable("user"),

    personTable("person"),

    feesTable1("feesDetails"),

    feesTable2("fees"),

    feesCharged("feesCharged"),

    insert("insert into"),

    password("password"),

    id("id"),

    name("name"),

    mobile("mobile"),

    male("male"),

    female("female"),

    gender("gender"),

    level("level"),

    period("period"),

    photo("photo"),

    date("date"),

    delete("deleted"),

    amount("amount"),

    yearPeriod("yearPeriod"),

    yr("yr"),

    amount_due("amount_due"),

    success("successful "),

    noPeriodSelected("Please selected a period in which you want to work from BOTTOM > RIGHT > CORNER > Periods." +
            "\n\nNB: if it's empty then add them from preferences below.");




    private String prop;

    DbProps(String prop) {
        this.prop = prop;
    }

    public String getProp(){
        return this.prop;
    }



}



