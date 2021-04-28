package com.example.androidchess52;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidchess52.board.Board;
import com.example.androidchess52.pieces.Piece;
import com.example.androidchess52.pieces.Point;
import com.example.androidchess52.pieces.Queen;

import java.util.ArrayList;

public class RecordGame extends AppCompatActivity {

    public ArrayList<Point[]> moves;
    public Board game;
    int pointer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_game);

        moves = new ArrayList<Point[]>();
        game = new Board();
        pointer = 0;
        makeGame();
        displayBoard(game.pieces);
    }

    public void makeGame() {
        moves.add(new Point[]{new Point(6,4), new Point(4,4)});
        moves.add(new Point[]{new Point(1,5), new Point(3,5)});
        moves.add(new Point[]{new Point(6,3), new Point(4,3)});
        moves.add(new Point[]{new Point(1,6), new Point(3,6)});
        moves.add(new Point[]{new Point(7,3), new Point(3,7)});
    }

    public void nextMove(View view) {
        makeMove(moves.get(pointer));
        pointer++;
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
                    System.out.println("there is no image here!");
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
