package com.example.androidchess52.pieces;

import android.graphics.Point;

import com.example.androidchess52.board.Board;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class extends the abstract class piece and is
 * the class that initializes knight objects and has
 * its necessary functions which were inherited from
 * the abstract class.
 *
 * @author Chiraag Rekhari and Jahnavi Bavuluri
 */
public class Knight extends Piece{

    /**
     * The constructor calls the superclasses constructor which sets the color,x,and y.
     * The name is set to wN or bN depending if the color is black or white.
     *
     * @param color		the color of the knight
     * @param x			the x location of the knight on the board
     * @param y			the y location of the knight on the board
     */
    public Knight(String color, int x, int y) {
        super(color, x, y);
        if (color.equals("white"))
            name = "wN ";
        else
            name = "bN ";
    }

    /**
     * Getter method that returns the name of the knight.
     */
    public String getName() {
        return name;
    }

    /**
     * This method populates the getMoves ArrayList with the
     * valid moves for a particular knight object on the board
     * b that is passed in.
     *
     * @param b			current board object that is being played on
     * @param getMoves	ArrayList that stores all the valid moves
     * 					for a particular knight object
     */
    public void basicMovement (Board b, ArrayList<Point> getMoves) {
        //adds knight moves to the list
        int x = location.x;
        int y = location.y;

        Point TwoUpOneRight = new Point(x-2,y+1);
        Point TwoUpOneLeft = new Point(x-2,y-1);
        Point TwoDownOneRight = new Point(x+2,y+1);
        Point TwoDownOneLeft = new Point(x+2,y-1);

        Point OneUpTwoRight = new Point(x-1,y+2);
        Point OneUpTwoLeft = new Point(x-1,y-2);
        Point OneDownTwoRight = new Point(x+1,y+2);
        Point OneDownTwoLeft = new Point(x+1,y-2);

        getMoves.add(TwoUpOneRight);
        getMoves.add(TwoUpOneLeft);
        getMoves.add(TwoDownOneRight);
        getMoves.add(TwoDownOneLeft);

        getMoves.add(OneUpTwoRight);
        getMoves.add(OneUpTwoLeft);
        getMoves.add(OneDownTwoRight);
        getMoves.add(OneDownTwoLeft);

        Iterator<Point> iter = getMoves.iterator();

        while (iter.hasNext()) {
            Point p = iter.next();

            if (p.x>7 || p.x<0 || p.y>7 ||p.y<0)
                iter.remove();
        }


        Iterator<Point> iter2 = getMoves.iterator();

        while (iter2.hasNext()) {
            Point p = iter2.next();

            if(this.color.equals("white")) {
                if((b.getPieceAt(p) != null) && ((b.getPieceAt(p)).color).equals("white")){
                    iter2.remove();
                }
            }
            if(this.color.equals("black")) {
                if (b.getPieceAt(p) != null && ((b.getPieceAt(p)).color).equals("black")){
                    iter2.remove();
                }
            }
        }
    }

    /**
     * This method calls basicMovement to populate the ArrayList
     * Moves and checks if each move puts its own king in check.
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

