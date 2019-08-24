package main.java.util;

import javafx.collections.ObservableList;
import main.java.models.Person;
import main.java.models.PersonFeesModel;
import main.java.models.SortedFessModel;

import java.util.List;

public interface DbCreate {

     void createDbTables();

     /**
      *
      * @param params parameters for creating password
      */
     boolean createPassword(List<?> params);

     /**
      *
      * @param params parameters for login
      * @return true if user available otherwise false;
      */
     boolean logIn(List<?> params);

     /*
          return true if users exist
      */
     boolean getAllUsers();


     /**
      * add new person to db
      * @param param list of values to set to placeholders in query
      */
     void addPerson(List<?> param);

     /**
      * update person
      * @param param list of values to set to placeholders in query
      */
     void updatePerson(List<?> param);

     /**
      * delete person from db permanently
      * @param id id of person to delete
      */
     void deletePerson(List<?> id);

     /**
      *
      * @return observable list of all persons registered.
      */
     ObservableList<Person> getAllPersons();

     /***
      *
      * @param param list of yearPeriod and level
      * @return observable list of sorted fees based on level ans yearPeriod
      */
     ObservableList<SortedFessModel> getPaidForLevel(List<?> param);

     /**
      * allows  payment
      * @param param list of values to set to placeholders in query
      */
     void payFees(List<?> param);

     /**
      * update changes in second table..
      * @param param list of values to set to placeholders in query
      */
     void updateTableTwoFees(List<?> param);

     /**
      * mark payment as deleted
      * @param param list of values to set to placeholders in query
      */
     void deletePayment(List<?> param);

     /**
      *
      * @param param person id
      * @return observable list of all person payments in details
      */
      ObservableList<PersonFeesModel> personFeesData(List<?> param);


      //TODO: configurations methods

     /**
      * add new fees charges based on level and yearPeriod
      * @param params list contains yearPeriod,level amount and date
      */
     void addFeesCharged(List<?> params);

     ObservableList<SortedFessModel> getAllFeesCharged();

     double getSpecificFeesCharged(List<?> params);

     /**
      * permanently delete wrongly entered fees charged
      * @param params list of constraint such as yearPeriod and level
      */
     void deleteFeesCharged(List<?> params);

     /**
      * add new period or level to database
      * @param params list of parameters for period or level..
      * @param isPeriod: if true add to period otherwise level
      */
     void addPeriodLevel(boolean isPeriod,List<?> params);

     /**
      * delete period or level to database
      * @param params list of parameters for period or level..
      * @param isPeriod: if true add to period otherwise level
      */
     void deletePeriodLevel(boolean isPeriod,List<?> params);


     ObservableList<String> getLevels();

     ObservableList<String> getPeriods();






}
