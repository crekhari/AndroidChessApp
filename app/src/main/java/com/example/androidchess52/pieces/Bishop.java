package com.example.androidchess52.pieces;

import android.graphics.Point;

import com.example.androidchess52.board.Board;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class extends the abstract class piece and is the class that
 * initializes bishop objects and has its necessary functions
 * which were inherited from the abstract class.
 *
 * @author Chiraag Rekhari and Jahnavi Bavuluri
 */
public class Bishop extends Piece {

    /**
     * The constructor calls the superclasses constructor which sets the color,x,and y.
     * The name is set to wB or bB depending if the color is black or white.
     *
     * @param color		the color of the bishop
     * @param x			the x location of the bishop on the board
     * @param y			the y location of the bishop on the board
     */
    public Bishop(String color, int x, int y) {
        super(color, x, y);
        if (color.equals("white"))
            name = "wB ";
        else
            name = "bB ";
    }

    /**
     * Getter method that returns the name of the bishop
     */
    public String getName() {
        return name;
    }

    /**
     * This method populates the getMoves ArrayList with the valid
     * moves for a particular bishop object on the board b that
     * is passed in.
     *
     * @param b			current board object that is being played on
     * @param getMoves	ArrayList that stores all the valid moves
     * 					for a particular bishop object

     */
    public void basicMovement (Board b, ArrayList<Point> getMoves) {
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
     * This method calls basicMovement to populate the ArrayList
     * Moves and checks if each move puts its own king in check
     */
    public ArrayList<Point> getMoves(Board b, boolean check) {
        //calls the above methods and finally checks if making this move will put its own king in check
        ArrayList<Point> moves = new ArrayList<Point>();
        basicMovement(b, moves);

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
