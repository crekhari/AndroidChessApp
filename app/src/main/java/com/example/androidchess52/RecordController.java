package com.example.androidchess52;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.EOFException;
import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.androidchess52.record.Record;

public class RecordController extends AppCompatActivity {

    public ArrayList<Record> recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        final ListView list = findViewById(R.id.list);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("GO BACK TO PREVIOUS SCREEN");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(arrayAdapter);
        try {
            this.recordList.add(new Record("GO BACK TO PREVIOUS SCREEN"));
        } catch (Exception e) {
            System.out.println("exception");
            if (e instanceof EOFException)
                this.recordList = new ArrayList<Record>();
            this.recordList.add(new Record("GO BACK TO PREVIOUS SCREEN"));
        }


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) list.getItemAtPosition(position);
                Toast.makeText(RecordController.this,clickedItem,Toast.LENGTH_LONG).show();
            }
        });
    }
}