package com.example.androidchess52;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.Arrays;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.content.Intent;

import com.example.androidchess52.pieces.Point;
import com.example.androidchess52.record.Record;
import com.example.androidchess52.record.Serialize;

public class RecordController extends AppCompatActivity {

    public ArrayList<Record> recordList;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);

        try{
            recordList = Serialize.readApp(this);
        } catch (Exception e){
            e.printStackTrace();
            recordList = new ArrayList<Record>();
        }

        System.out.println(this.recordList.size());

        list = findViewById(R.id.list);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        //recordList.add(new Record("a", new ArrayList<Point[]>()));
        //recordList.add(new Record("e", new ArrayList<Point[]>()));
        //recordList.add(new Record("t", new ArrayList<Point[]>()));
        //recordList.add(new Record("z", new ArrayList<Point[]>()));



        //list.setAdapter(arrayAdapter);
        addGameNames();
        updateListView();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                Toast.makeText(RecordController.this,clickedItem,Toast.LENGTH_LONG).show();
                System.out.println(clickedItem);
                startTransition(view, clickedItem);

            }
        });
    }


    public void sortByName(View view){
        ArrayList<String> recordListString = new ArrayList<String>();
        for(Record r: recordList){
            recordListString.add(r.toString());
        }
        Collections.sort(recordListString, String.CASE_INSENSITIVE_ORDER);

        for(String s: recordListString){
            System.out.println(s);
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recordListString);
        updateListView();
    }

    public void sortByDate(View view){
        ArrayList<String> recordListString = new ArrayList<String>();

        ArrayList<Date> date=new ArrayList<>();
        for(Record r: recordList){
            date.add(r.date);
        }
        Collections.sort(recordList, Comparator.comparing(Record::getDate));
        for(Record r: recordList){
            recordListString.add(r.toString());
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recordListString);
        updateListView();
    }

    public void goBack(View view) {
        Intent i = new Intent(RecordController.this, MainActivity.class);
        startActivity(i);
    }

    private void startTransition(View view, String name) {
        Intent i = new Intent(RecordController.this, RecordGame.class);
        i.putExtra("game", name);
        startActivity(i);

    }

    public ArrayList<Record> getRecordList(){
        return recordList;
    }


    /*public void addRecording(String name, ArrayList<Point[]> moves){
        Record r = new Record(name, moves);
        recordList.add(r);
        arrayList.add(r.getName());
        updateListView();
    }*/

    public void addGameNames() {
        for (Record r: this.recordList) {
            arrayList.add(r.toString());
        }
    }

    public void updateListView(){
        list.setAdapter(arrayAdapter);
    }


}