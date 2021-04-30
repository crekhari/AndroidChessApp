package com.example.androidchess52;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
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

public class PlayGame extends AppCompatActivity {

    public Board game;
    public ImageButton firstSelected;
    public ImageButton lastSelected;
    public String starting;
    public String ending;
    public Button resign, undo, ai, draw;
    public Boolean undoEnabled;
    public ArrayList<Record> recordList;


    /**
     * Initializes the buttons, recordlist, and game
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);

        game = new Board();
        resign = (Button) findViewById(R.id.resign);
        undo = (Button) findViewById(R.id.undo);
        ai = (Button) findViewById(R.id.ai);
        draw = (Button) findViewById(R.id.draw);

        try{
            recordList = Serialize.readApp(this);
        } catch (Exception e){
            e.printStackTrace();
            recordList = new ArrayList<Record>();
        }

        //game.drawBoard();
        this.undoEnabled = false;
        undo.setEnabled(undoEnabled);
        displayBoard(game.pieces);
    }


    /**
     * Undo's one move of the current player
     *
     * @param view
     */
    public void onUndoClick(View view) {
        if (undoEnabled) {
            game.undoMove();
            displayBoard(game.pieces);
            this.undoEnabled = false;
            undo.setEnabled(undoEnabled);
        } else {
            Toast.makeText(getApplicationContext(), "You can only undo the last move!", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Allows the current player to resign
     *
     * @param view
     * @throws IOException
     */
    public void onResignClick(View view) throws IOException{
        Toast.makeText(getApplicationContext(), game.currentPlayer + " has resigned. Game has ended.", Toast.LENGTH_SHORT).show();
        addGametoRecord();
    }

    /**
     * Allows the current player to ask AI to make a move for them
     *
     * @param view
     * @throws IOException
     */
    public void onAIClick(View view) throws IOException {
        Point[] move = game.aiMove();
        makeMove(move);
        this.undoEnabled = true;
        undo.setEnabled(undoEnabled);
    }


    /**
     * Allows the current player to draw
     *
     * @param view
     * @throws IOException
     */
    public void onDrawClick(View view) throws IOException {
        Toast.makeText(getApplicationContext(), game.currentPlayer + " has called a draw. Game has ended.", Toast.LENGTH_SHORT).show();
        addGametoRecord();
    }


    /**
     * Takes care of a move that the player makes. Notes: player is to click the starting square and the ending square to make the move. Once they pick a piece, they must move that one.
     *
     * @param view
     * @throws IOException
     */
    public void onClick(View view) throws IOException {
        if (firstSelected == null) { //this is the first time they are touching the piece they want to move
            firstSelected = (ImageButton) view;
            String location = view.getResources().getResourceName(firstSelected.getId());
            starting = location.substring(location.length()-2);
            if (firstSelected.getDrawable()==null || !game.getPieceAt(game.position(starting)).color.equalsIgnoreCase(game.currentPlayer)) {
                firstSelected = null;
                Toast.makeText(getApplicationContext(), "Please select a valid piece to move.", Toast.LENGTH_SHORT).show();
            }
        } else { //this is the place they want to move the piece to
            lastSelected = (ImageButton) view;
            String location = view.getResources().getResourceName(lastSelected.getId());
            ending = location.substring(location.length()-2);
            Point[] move = game.move(starting, ending);
            if (game.isValidMove(move)) {
                makeMove(move);
                firstSelected = null;
                lastSelected = null;
                starting = null;
                ending = null;
            } else {
                Toast.makeText(getApplicationContext(), "Illegal move, try again.", Toast.LENGTH_SHORT).show();
                lastSelected = null;
                ending = null;
            }
        }
    }


    /**
     * Makes the actual move involving the logic for castling, enpassant, promotion, and checkmate.
     *
     * @param move
     * @throws IOException
     */
    public void makeMove(Point[] move) throws IOException{
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
        firstSelected = null;
        starting = null;
        lastSelected = null;
        ending = null;
        displayBoard(game.pieces);
        this.undoEnabled = true;
        undo.setEnabled(undoEnabled);
        game.printRecord();
        if (game.checkmate()) {
            String winner = (game.currentPlayer.equalsIgnoreCase("black")) ? "White":"Black";
            Toast.makeText(getApplicationContext(), winner + " has won. Game has ended.", Toast.LENGTH_SHORT).show();
            addGametoRecord();
        }
    }


    public void addGametoRecord() throws IOException {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Game");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                if (isUnique(m_Text.trim())) {
                    Record r = new Record(m_Text.trim(), game.record);
                    recordList.add(r);
                    try {
                        Serialize.writeApp(recordList, getApplicationContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    switchScreens();
                } else {
                    Toast.makeText(getApplicationContext(), "Saved game with that name already exists. Please enter a new name!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void switchScreens() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public boolean isUnique(String name) {
        for (Record r: this.recordList) {
            if (r.getName().trim().equalsIgnoreCase(name)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Displays the board for the players
     *
     * @param pieces
     */
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

    /**
     * Clears the current board to be repopulated with the new pieces after a move is made.
     */
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
                    System.out.println("there is no image here!");
                }
            }
        }
    }


    /**
     * Allows the app to find the Image Button that is clicked.
     *
     * @param id
     * @return
     */
    public ImageButton findButtonById(String id) {
        return (ImageButton)findViewById(getResources().getIdentifier(id, "id", this.getPackageName()));
    }


    /**
     * Allows the app to find the corresponding piece icon to be used when displaying the board.
     *
     * @param p
     * @return
     */
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
