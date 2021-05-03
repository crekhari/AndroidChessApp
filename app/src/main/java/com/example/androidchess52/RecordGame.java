package com.example.androidchess52;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidchess52.board.Board;
import com.example.androidchess52.pieces.Piece;
import com.example.androidchess52.pieces.Point;
import com.example.androidchess52.pieces.Queen;
import com.example.androidchess52.record.Record;
import com.example.androidchess52.record.Serialize;

import java.io.IOException;
import java.util.ArrayList;

public class RecordGame extends AppCompatActivity {

    public ArrayList<Point[]> moves;
    public ArrayList<Record> recordList;
    public Board game;
    public int pointer;
    public int ending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_game);

        try{
            recordList = Serialize.readApp(this);
        } catch (Exception e){
            e.printStackTrace();
            recordList = new ArrayList<Record>();
        }

        System.out.println("the size of the record list is: " + this.recordList.size());

        String name = getIntent().getExtras().getString("game");

        String[] tokens = name.split("\n");
        System.out.println(tokens[0]);

        Record record = getRecord(tokens[0]);
        this.moves = record.getMovesArrayList();
        this.ending = record.ending;
        game = new Board();
        pointer = 0;
        //makeGame();
        displayBoard(game.pieces);
    }

    public void goBack(View view) {
        Intent i = new Intent(RecordGame.this, MainActivity.class);
        startActivity(i);
    }

    public Record getRecord(String name) {
        for (Record r: this.recordList) {
            System.out.println("the record in the list is: " + r.getName().trim());
            if (r.getName().trim().equalsIgnoreCase(name.trim())) {
                System.out.println("The record is: " + r);
                return r;
            }
        }
        System.out.println("null!");
        return null;
    }

    public void nextMove(View view) {
        if (this.pointer >= this.moves.size()) {
            endOfGame();
        } else {
            makeMove(moves.get(pointer));
            System.out.println(moves.get(pointer)[0] + " " + moves.get(pointer)[1]);
            pointer++;
        }
    }

    public void endOfGame() {
        String alertMessage;
        if (this.ending == 0) {//resign
            alertMessage = game.currentPlayer + " has resigned.";
        } else if (this.ending == 1) {//draw
            alertMessage = game.currentPlayer + " has asked for a draw.";
        } else {//checkmate
            alertMessage = game.currentPlayer + " has lost.";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("End of Game");

        builder.setMessage(alertMessage);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goBackToMain();
            }
        });

        builder.show();

    }

    public void goBackToMain() {
        Intent i = new Intent(RecordGame.this, RecordController.class);
        startActivity(i);
    }

    public void makeMove(Point[] move) {
        if (game.checkPromotion(move)) {
            game.promotion(new Queen(game.currentPlayer.toLowerCase(), 0, 0), move);
            //displayBoard(game.pieces);
        } else {
            game.firstMove(move);
            game.enpassant(move);
            if (game.checkEnpassant(move)) {
                game.doEnpassant(move);
            } else {
                game.doCastle(move);
                game.makeMove(move);
            }
        }
        displayBoard(game.pieces);
    }

    public void displayBoard(ArrayList<Piece> pieces) {
        clearBoard();
        for (int i = 0; i<8; i++) {
            for (int j = 0; j<8; j++) {
                Point temp = new Point(i,j);
                for (Piece p: pieces) {
                    if (p.location.equals(temp)) {
                        ImageButton found = findButtonById(game.position(temp));
                        found.setImageResource(getPieceImage(p));
                    }
                }
            }
        }
    }

    public void clearBoard() {
        for (int i = 0; i<9; i++) {
            for (int j = 0; j<9; j++) {
                StringBuilder sb = new StringBuilder();
                sb.append(game.numberToLetter(i));
                sb.append(j);
                ImageButton found = findButtonById(sb.toString());
                try {
                    found.getDrawable();
                    found.setImageResource(0);
                } catch (Exception e) {
                    //System.out.println("there is no image here!");
                }
            }
        }
    }

    public ImageButton findButtonById(String id) {
        return (ImageButton)findViewById(getResources().getIdentifier(id, "id", this.getPackageName()));
    }

    public int getPieceImage(Piece p) {
        String name = p.getName();
        switch (name) {
            case "wR ":
                return R.drawable.whiterook;
            case "wB ":
                return R.drawable.whitebishop;
            case "wN ":
                return R.drawable.whiteknight;
            case "wQ ":
                return R.drawable.whitequeen;
            case "wK ":
                return R.drawable.whiteking;
            case "wp ":
                return R.drawable.whitepawn;
            case "bR ":
                return R.drawable.blackrook;
            case "bB ":
                return R.drawable.blackbishop;
            case "bN ":
                return R.drawable.blackknight;
            case "bQ ":
                return R.drawable.blackqueen;
            case "bK ":
                return R.drawable.blackking;
            case "bp ":
                return R.drawable.blackpawn;
        }
        return -1;
    }

}
