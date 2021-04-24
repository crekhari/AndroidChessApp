package com.example.androidchess52.pieces;

import com.example.androidchess52.board.Board;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class extends the abstract class piece and
 * is the class that initializes queen objects and
 * has its necessary functions which were inherited
 * from the abstract class.
 *
 * @author Chiraag Rekhari and Jahnavi Bavuluri
 */
public class Queen extends Piece {

    /**
     * The constructor calls the superclasses constructor which sets the color,x,and y.
     * The name is set to wQ or bQ depending if the color is black or white.
     *
     * @param color		the color of the queen
     * @param x			the x location of the queen on the board
     * @param y			the y location of the queen on the board
     */
    public Queen(String color, int x, int y) {
        super(color, x, y);
        if (color.equals("white"))
            name = "wQ ";
        else
            name = "bQ ";
    }

    /**
     * Getter method that returns the name of the queen
     */
    public String getName() {
        return name;
    }

    /**
     * This method populates the getMoves ArrayList with the
     * up, down, left, and right valid moves for a particular
     * queen object on the board b that is passed in.
     *
     * @param b			current board object that is being played on
     * @param getMoves	ArrayList that stores all the valid
     * 					moves for a particular queen object
     */
    public void lineMovement (Board b, ArrayList<Point> getMoves) {
        //adds vertical and horizontal moves to the list
        int x = location.x;
        int y = location.y;

        //down
        for(int i=x+1; i<8; i++){
            Point p = new Point(i,y);
            if (b.getPieceAt(p) == null){
                getMoves.add(p);
            } else if (!(b.getPieceAt(p).color.equals(this.color))) {
                getMoves.add(p);
                break;
            } else {;
                break;
            }
        }

        //up
        for(int i=x-1; i>=0; i--){
            Point p = new Point(i,y);
            if (b.getPieceAt(p) == null){
                getMoves.add(p);
            } else if (!(b.getPieceAt(p).color.equals(this.color))) {
                getMoves.add(p);
                break;
            } else {
                break;
            }
        }

        //right
        for(int j=y+1; j<8; j++){
            Point p = new Point(x,j);
            if (b.getPieceAt(p) == null){
                getMoves.add(p);
            } else if (!(b.getPieceAt(p).color.equals(this.color))) {
                getMoves.add(p);
                break;
            } else {
                break;
            }
        }

        //left
        for(int j=y-1; j>=0; j--){
            Point p = new Point(x,j);
            if (b.getPieceAt(p) == null){
                getMoves.add(p);
            } else if (!(b.getPieceAt(p).color.equals(this.color))) {
                getMoves.add(p);
                break;
            } else {
                break;
            }
        }
    }

    /**
     * This method populates the getMoves ArrayList with the diagonal
     * valid moves for a particular queen object on the board b
     * that is passed in.
     *
     * @param b			current board object that is being played on
     * @param getMoves	ArrayList that stores all the valid
     * 					moves for a particular queen object
     */
    public void diagMovement (Board b, ArrayList<Point> getMoves) {
        //adds diagonal moves to the list
        int x = location.x;
        int y = location.y;

        //right down
        int i = x+1;
        int j = y+1;
        while (i<8 && j<8) {
            Point p = new Point(i,j);
            if (b.getPieceAt(p) == null) {
                getMoves.add(p);
            } else if (!(b.getPieceAt(p).color.equals(this.color))) {
                getMoves.add(p);
                break;
            } else {
                break;
            }
            i++;
            j++;
        }

        //right up
        i = x+1;
        j = y-1;
        while (i<8 && j>=0) {
            Point p = new Point(i,j);
            if (b.getPieceAt(p) == null) {
                getMoves.add(p);
            } else if (!(b.getPieceAt(p).color.equals(this.color))) {
                getMoves.add(p);
                break;
            } else {
                break;
            }
            i++;
            j--;
        }

        //left down
        i = x-1;
        j = y+1;
        while (i>=0 && j<8) {
            Point p = new Point(i,j);
            if (b.getPieceAt(p) == null) {
                getMoves.add(p);
            } else if (!(b.getPieceAt(p).color.equals(this.color))) {
                getMoves.add(p);
                break;
            } else {
                break;
            }
            i--;
            j++;
        }

        //left up
        i = x-1;
        j = y-1;
        while (i>=0 && j>=0) {
            Point p = new Point(i,j);
            if (b.getPieceAt(p) == null) {
                getMoves.add(p);
            } else if (!(b.getPieceAt(p).color.equals(this.color))) {
                getMoves.add(p);
                break;
            } else {
                break;
            }
            i--;
            j--;
        }
    }

    /**
     * This method calls diagMovement and LineMovement to populate
     * the ArrayList Moves and checks if each move puts its own
     * king in check.
     */
    public ArrayList<Point> getMoves(Board b, boolean check) {
        //calls the above methods and finally checks if making this move will put its own king in check
        ArrayList<Point> moves = new ArrayList<Point>();
        lineMovement(b,moves);
        diagMovement(b,moves);

        Iterator<Point> iter = moves.iterator();

        if (check) {
            while (iter.hasNext()) {
                Point p = iter.next();
                Board helper = b.tryMove(new Point[] {this.location, p});
                if (helper.kingInCheck()) {
                    iter.remove();
                }
            }
        }

        return moves;
    }

}

