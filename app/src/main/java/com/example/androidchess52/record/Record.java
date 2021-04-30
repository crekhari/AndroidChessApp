package com.example.androidchess52.record;

import com.example.androidchess52.pieces.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Record implements Serializable {
    String name;
    ArrayList<Point[]> moves = new ArrayList<Point []>();
    public Calendar calendar;
    public Date date;


    public Record(String name, ArrayList<Point[]> moves){
        this.calendar = new GregorianCalendar();
        this.calendar.set(Calendar.MILLISECOND, 0);
        this.date = this.calendar.getTime();
        this.name = name;
        this.moves = moves;
    }

    public ArrayList<Point[]> getMovesArrayList(){
        return moves;
    }

    public String getName(){
        return name;
    }

    public Date getDate(){
        return this.date;
    }



}
