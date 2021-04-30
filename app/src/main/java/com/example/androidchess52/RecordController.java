package com.example.androidchess52;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
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

    private void startTransition(View view, String name) {
        Intent i = new Intent(RecordController.this, RecordGame.class);
        Record record = getRecord(name);
        startActivity(i);

    }

    public Record getRecord(String name) {
        for (Record r: this.recordList) {
            if (r.getName().trim().equalsIgnoreCase(name)) {
                return r;
            }
        }
        return null;
    }

    public ArrayList<Record> getRecordList(){
        return recordList;
    }


    public void addRecording(String name, ArrayList<Point[]> moves){
        Record r = new Record(name, moves);
        recordList.add(r);
        arrayList.add(r.getName());
        updateListView();
    }

    public void addGameNames() {
        for (Record r: this.recordList) {
            arrayList.add(r.getName());
        }
    }

    public void updateListView(){
        list.setAdapter(arrayAdapter);
    }
}