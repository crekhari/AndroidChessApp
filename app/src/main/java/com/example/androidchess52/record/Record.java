package com.example.androidchess52.record;

import com.example.androidchess52.pieces.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Record implements Serializable {
    public String name;
    public ArrayList<Point[]> moves = new ArrayList<Point []>();
    public Calendar calendar;
    public Date date;
    public int ending; //0 is resign, 1 is draw, 2 is checkmate


    public Record(String name, ArrayList<Point[]> moves, int ending){
        this.calendar = new GregorianCalendar();
        this.calendar.set(Calendar.MILLISECOND, 0);
        this.date = this.calendar.getTime();
        this.name = name;
        this.moves = moves;
        this.ending = ending;
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

    @Override
    public String toString() {
        return name + "\n" + date;
    }



}
