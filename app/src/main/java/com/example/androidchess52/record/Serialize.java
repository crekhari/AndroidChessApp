package com.example.androidchess52.record;

import android.content.Context;
import android.content.ContextWrapper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidchess52.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Serialize extends AppCompatActivity implements Serializable {

    public static final String storeFile = "games.dat";
    public static ArrayList<Record> games = new ArrayList<Record>();


    public static void writeApp(ArrayList<Record> games, Context context) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(context.getFilesDir(), storeFile));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(games);
        oos.close();
        fos.close();
        System.out.println("I did it?");
}

public static ArrayList<Record> readApp(Context context) throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream(new File(context.getFilesDir(), storeFile)));
    ArrayList<Record> games = (ArrayList<Record>)ois.readObject();
    return games;
}
}
