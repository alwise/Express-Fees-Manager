package main.java.util;

import javafx.collections.ObservableList;
import main.java.models.Person;

import java.util.List;

public interface DbCreate {

     void createDbTables();

     /**
      * add new person to db
      * @param values list of values to set to placeholders in query
      */
     void addPerson(List<?> values);

     /**
      * update person
      * @param values list of values to set to placeholders in query
      */
     void updatePerson(List<?> values);

     /**
      * delete person from db permanently
      * @param id id of person to delete
      */
     void deletePerson(List<?> id);

     ObservableList<Person> getAllPersons();

     ObservableList<Person> getPaidForLevel(List<?> values);

     /**
      * allows  payment
      * @param values list of values to set to placeholders in query
      */
     void payFees(List<?> values);



}
