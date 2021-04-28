package com.example.androidchess52.record;

import com.example.androidchess52.pieces.Point;

import java.util.ArrayList;

public class Record {
    String name;
    ArrayList<Point[]> moves = new ArrayList<Point []>();


    public Record(String name, ArrayList<Point[]> moves){
        this.name = name;
        this.moves = moves;
    }

    public void checkString(String input){
        if(input.equals("GO BACK TO PREVIOUS SCREEN")){
            System.out.println("Hello");
        }
    }

    public ArrayList<Point[]> getMovesArrayList(){
        return moves;
    }

    public String getName(){
        return name;
    }


}
