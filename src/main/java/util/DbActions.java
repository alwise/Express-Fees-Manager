package main.java.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class DbActions extends DbUtils implements DbCreate {

    @Override
    public void createDbTables() {


        String personTable = "create table if not exists " +
                "person" +
                "("+
                "id integer," +
                "name text," +
                "mobile text," +
                "gender text," +
                "level text," +
                "photo blob,"+
                "date datetime" +
                ", primary key(id))";


        String feesTable = "create table if not exists " +
                DbProps.YEAR_PERIOD.getProp()+
                "(" +
                "id integer," +
                "level text," +
                " amount decimal(10,2)," +
                " date datetime" +
                ", primary key(id,level))";
        String levelsTable = "create table if not exists " +
                "levels" +
                "(" +
                "id integer," +
                " level text," +
                "date datetime" +
                ",primary key(id))";


        String[] tables = {personTable,feesTable,levelsTable};

        dbConnect();
        try {

          Statement statement = conn.createStatement();
            for (String table : tables){
                statement.addBatch(table);
            }
            statement.executeBatch();
            System.out.println("Tables created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbDisconnect();
        }


    }

    @Override
    public void addPerson(List<?> values) {
        String addPerson = "insert into person(name,mobile,gender,level,photo,date) values(?,?,?,?,?,?)";
        executeUpdate(addPerson,values);
    }

    @Override
    public void updatePerson(List<?> values) {
        String updatePerson = "update person set name =?,mobile = ?, gender =?, level = ?, photo = ?, date =? where id = ?";
        executeUpdate(updatePerson,values);
    }

    @Override
    public void deletePerson(List<?> id) {
        String deletePerson = "delete from person where id =? ";
        executeUpdate(deletePerson, id);
    }

    @Override
    public ObservableList<Person> getAllPersons() {
        ObservableList<Person> personList = FXCollections.observableArrayList();
        String persons = "select * from person LIMIT 2";
        ResultSet rs = executeQuery(persons,null);

            try {
               while (rs.next()){

                   personList.add( new Person(
                           rs.getString(DbProps.id.getProp()),
                           rs.getString(DbProps.name.getProp()),
                           rs.getString(DbProps.mobile.getProp()),
                           rs.getString(DbProps.gender.getProp()),
                           rs.getString(DbProps.level.getProp()),
                           rs.getByte(DbProps.photo.getProp()),
                           rs.getString(DbProps.date.getProp())
                    ));

               }

            } catch (SQLException e) {
                e.printStackTrace();
        }
        return personList;
    }

    @Override
    public ObservableList<Person> getPaidForLevel(List<?> values) {

        return null;
    }

    @Override
    public void payFees(List<?> values) {
        String payFees = "insert into "+DbProps.YEAR_PERIOD.getProp()+" (id,level,amount,date) values(?,?,?,?)";
        executeUpdate(payFees,values);
    }


}
