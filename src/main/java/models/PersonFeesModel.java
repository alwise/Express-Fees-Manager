package main.java.models;

public class PersonFeesModel {

    private String id,yr,period,level,amount,date;
    private boolean delete;


    public PersonFeesModel(String id, String yr, String period, String level, String amount, String date, boolean delete) {
        this.id = id;
        this.yr = yr;
        this.period = period;
        this.level = level;
        this.amount = amount;
        this.date = date;
        this.delete = delete;
    }

    public PersonFeesModel() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYr() {
        return yr;
    }

    public void setYr(String yr) {
        this.yr = yr;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
