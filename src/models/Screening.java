package models;

import java.util.ArrayList;
import java.util.Date;

public class Screening {
    private ArrayList<Seat> seats = new ArrayList<Seat>();

    private Date date;

    public Screening(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
