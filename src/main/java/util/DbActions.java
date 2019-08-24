package main.java.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.models.Person;
import main.java.models.PersonFeesModel;
import main.java.models.SortedFessModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class DbActions extends DbUtils implements DbCreate {

    @Override
    public void createDbTables() {

        String user = "create table if not exists " +
                "user" +
                "(" +
                "password text," +
                "date datetime," +
                "primary key(password)" +
                ")";


        String personTable = "create table if not exists " +
                "person" +
                "("+
                "id integer," +
                "name text," +
                "mobile text," +
                "gender text," +
                "period text," +
                "level text," +
                "photo blob,"+
                "date datetime" +
                ", primary key(id)" +
                ")";


        String feesTable1 = "create table if not exists " +
                DbProps.feesTable1.getProp()+
                "(" +
                "id integer," +
                "yr text," +
                "period text," +
                "level text," +
                " amount decimal(10,2)," +
                " date datetime," +
                " deleted boolean" +
                ", primary key(id,date)" +
                ")";

        String feesTable2 = "create table if not exists "+DbProps.feesTable2.getProp() +" " +
                " (" +
                "id text," +
                "name text," +
                "yearPeriod text," +
                "level text," +
                "amount double(10,2)," +
                "date datetime," +
                "primary key(id,date)" +
                ")";


        String levelsTable = "create table if not exists " +
                "levels" +
                "(" +
                " level text," +
                "date datetime" +
                ",primary key(level)" +
                ")";


        String periodTable = "create table if not exists " +
                "periods" +
                "(" +
                "period text," +
                "date datetime," +
                "primary key(period)" +
                ")";
        String feesChargeTable = "create table if not exists " +
                ""+DbProps.feesCharged.getProp()+"" +
                "(" +
                "yearPeriod text," +
                "level text," +
                "amount double(10,2)," +
                "date datetime," +
                "primary key(yearPeriod,level)" +
                ")";


        String[] tables = {user,personTable,feesTable1,feesTable2,levelsTable,periodTable,feesChargeTable};

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
    public boolean createPassword(List<?> params) {
        String query = "insert into user(password,date) values(?,?)";
        executeUpdate(query,params);
        return true;
    }

    @Override
    public boolean logIn(List<?> params) {
        boolean avail = false;
        String query = "select count(password) as pass from user where password = ?";
        ResultSet rs = executeQuery(query,params);
        try{
            if (rs.next()){
                System.out.println(rs.getInt("pass"));
                avail = rs.getInt("pass") > 0;
            }
        }catch (Exception e){
            System.out.println("cannot get password due to: "+e.getLocalizedMessage());
            return false;
        }
        return avail;
    }

    @Override
    public boolean getAllUsers() {
        boolean avail = false;
        String query = "select count(password) as pass from user";
        ResultSet rs = executeQuery(query,null);
        try{
            if (rs.first()){
                avail =  rs.getInt("pass") > 0;
                System.out.println("counted is "+avail);
            }
        }catch (Exception e){
            System.out.println("cannot get password due to: "+e.getLocalizedMessage());
            return false;
        }

        return avail;
    }

    @Override
    public void addPerson(List<?> param) {
        String addPerson = "insert into person(name,mobile,gender,period,level,photo,date) values(?,?,?,?,?,?,?)";
        executeUpdate(addPerson,param);
    }

    @Override
    public void updatePerson(List<?> param) {
        String updatePerson = "update person set name =?,mobile = ?, gender =?,period = ?, level = ?, photo = ?, date =? where id = ?";
        executeUpdate(updatePerson,param);
    }

    @Override
    public void deletePerson(List<?> id) {
        String deletePerson = "delete from person where id =? ";
        executeUpdate(deletePerson, id);
    }

    @Override
    public ObservableList<Person> getAllPersons() {
        ObservableList<Person> personList = FXCollections.observableArrayList();
        String persons = "select * from person";
        ResultSet rs = executeQuery(persons,null);

            try {
               while (rs.next()){

                   personList.add( new Person(
                           rs.getString(DbProps.id.getProp()),
                           rs.getString(DbProps.name.getProp()),
                           rs.getString(DbProps.mobile.getProp()),
                           rs.getString(DbProps.gender.getProp()),
                           rs.getString(DbProps.period.getProp()),
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
    public ObservableList<SortedFessModel> getPaidForLevel(List<?> param) {
        String query = "select id,name,yearPeriod,level, sum(amount) as amt,date from "+DbProps.feesTable2.getProp()+" where yearPeriod = ? and level = ? group by id,level";
        ObservableList<SortedFessModel> sortedFessList = FXCollections.observableArrayList();
        ResultSet rs = executeQuery(query,param);

        try {
            while (rs.next()){
                        sortedFessList.add(
                                new SortedFessModel
                                (
                                rs.getString(DbProps.id.getProp()),
                                rs.getString(DbProps.name.getProp()),
                                rs.getString(DbProps.yearPeriod.getProp()),
                                rs.getString(DbProps.level.getProp()),
                                rs.getString("amt"),
                                rs.getString(DbProps.date.getProp())
                                ));
            }
        }catch (Exception e){
            System.out.println("unable to fetch data due to : "+e.getLocalizedMessage());
        }

        return sortedFessList;
    }

    @Override
    public void payFees(List<?> values) {
        String payFees = "insert into "+DbProps.feesTable1.getProp()+" (id,yr,period,level,amount,date,deleted) values(?,?,?,?,?,?,?)";
        executeUpdate(payFees,values);
    }

    @Override
    public void updateTableTwoFees(List<?> param) {
        String  updateFees = "insert into "+DbProps.feesTable2.getProp()+" (id,name,yearPeriod,level,amount,date) values(?,?,?,?,?,?)";
        executeUpdate(updateFees,param);
    }

    @Override
    public void deletePayment(List<?> param) {
        String delete = "update "+DbProps.feesTable1.getProp()+" set deleted = ? where id = ? and date = ?";
        executeUpdate(delete,param);
    }

    @Override
    public ObservableList<PersonFeesModel> personFeesData(List<?> param) {
        ObservableList<PersonFeesModel> feesModels = FXCollections.observableArrayList();
        String data = "select * from "+DbProps.feesTable1.getProp()+" where id = ?";
        ResultSet rs = executeQuery(data,param);

       try{
           while (rs.next()){
                feesModels.add(new PersonFeesModel(
                        rs.getString(DbProps.id.getProp()),
                        rs.getString(DbProps.yr.getProp()),
                        rs.getString(DbProps.period.getProp()),
                        rs.getString(DbProps.level.getProp()),
                        rs.getString(DbProps.amount.getProp()),
                        rs.getString(DbProps.date.getProp()),
                        rs.getBoolean(DbProps.delete.getProp())));


           }

       }catch (Exception e){
           System.out.println("could not fetch user fees data due to "+e.getLocalizedMessage());
           return null;
       }
        return feesModels;
    }

    @Override
    public void addFeesCharged(List<?> params) {
        String query = "insert into "+DbProps.feesCharged.getProp()+" (yearPeriod,level,amount,date) values(?,?,?,?)";
        executeUpdate(query,params);
    }

    @Override
    public ObservableList<SortedFessModel> getAllFeesCharged() {
        ObservableList<SortedFessModel> charges = FXCollections.observableArrayList();
        String query = "select * from "+DbProps.feesCharged.getProp()+" order by date desc";

        ResultSet rs = executeQuery(query,null);
        try{
            while (rs.next()){
                charges.add(new SortedFessModel
                                (null,
                                null,
                                rs.getString(DbProps.yearPeriod.getProp()),
                                rs.getString(DbProps.level.getProp()),
                                rs.getString(DbProps.amount.getProp()),
                                rs.getString(DbProps.date.getProp())
                                ));
            }
        }catch (Exception e){
            System.out.println("could not fetch fees charged due to: ".concat(e.getLocalizedMessage()));
            return null;
        }
        return charges;
    }

    @Override
    public double getSpecificFeesCharged(List<?> params) {
        double amount = 0;
        String query = "select amount from "+DbProps.feesCharged.getProp()+" where yearPeriod = ? and level = ?";
        ResultSet rs = executeQuery(query,params);
        try {
            if (rs.first()){
                amount = rs.getDouble(DbProps.amount.getProp());
            }
        }catch (Exception e){
            System.out.println("Error fetching amount due to: ".concat(e.getLocalizedMessage()));
            return 0;
        }
        return amount;
    }

    @Override
    public void deleteFeesCharged(List<?> params) {
        String query = "delete from "+DbProps.feesCharged.getProp()+" where yearPeriod = ? and level = ?";
        executeUpdate(query,params);
    }



    @Override
    public void addPeriodLevel(boolean isPeriod ,List<?> params) {
        String query = isPeriod ?
                "insert into periods(period,date) values(?,?)"
                :
                "insert into levels(level,date) values(?,?)";
        executeUpdate(query,params);
    }

    @Override
    public void deletePeriodLevel(boolean isPeriod,List<?> params) {
        String query = isPeriod ?
                "delete from periods where period = ?"
                :
                "delete from levels where level = ?";
        executeUpdate(query,params);
    }

    @Override
    public ObservableList<String> getLevels() {
        ObservableList<String> levelList = FXCollections.observableArrayList();
        String query = "select * from levels";
        ResultSet rs = executeQuery(query,null);

        try{
            while (rs.next()){
               levelList.add(rs.getString(DbProps.level.getProp()));
            }
        }catch (Exception e){
            System.out.println("cannot fetch levels due to: "+e.getLocalizedMessage());
            return null;
        }

        return levelList;
    }

    @Override
    public ObservableList<String> getPeriods() {
        ObservableList<String> periodList = FXCollections.observableArrayList();
        String query = "select * from periods";
        ResultSet rs = executeQuery(query,null);

        try{
            while (rs.next()){
                periodList.add(rs.getString(DbProps.period.getProp()));
            }
        }catch (Exception e){
            System.out.println("cannot fetch period due to: "+e.getLocalizedMessage());
            return null;
        }
        return periodList;
    }


}
