package com.example.androidchess52.record;

import java.util.ArrayList;

public class Record {
    String name;
    //ArrayList<Record> allRecords = new ArrayList<Record>();

    public Record(String name){
        this.name = name;
    }

    public void checkString(String input){
        if(input.equals("GO BACK TO PREVIOUS SCREEN")){
            System.out.println("Hello");
        }
    }

    /*public ArrayList<Record> getAllRecords(){

    }*/
}
