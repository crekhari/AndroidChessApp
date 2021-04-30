package com.example.androidchess52.record;

import com.example.androidchess52.pieces.Point;

import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;

public class Record implements Serializable {
    String name;
    ArrayList<Point[]> moves = new ArrayList<Point []>();


    public Record(String name, ArrayList<Point[]> moves){
        this.name = name;
        this.moves = moves;
    }

    public ArrayList<Point[]> getMovesArrayList(){
        return moves;
    }

    public String getName(){
        return name;
    }



}
