package com.example.androidchess52.pieces;

import com.example.androidchess52.board.Board;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Rook class creates a Rook object that extends the abstract
 * type Piece. It takes care of the basic rook movement as well as the
 * castling move.
 *
 * @author Chiraag Rekhari and Jahnavi Bavuluri
 */
public class Rook extends Piece{

    /**
     * variable that is toggled true/false depending on if
     * the current rook can be castled
     */
    public boolean castling;

    /**
     * The constructor calls the superclasses constructor which sets the color,x,and y.
     * The name is set to wR or bR depending if the color is black or white.
     *
     * @param color		the color of the rook
     * @param x			the x location of the rook on the board
     * @param y			the y location of the rook on the board
     * @param castling	set to true if the current rook can be castled
     */
    public Rook(String color, int x, int y, boolean castling) {
        super(color,x,y);
        this.castling = castling;
        if (color.equals("white"))
            name = "wR ";
        else
            name = "bR ";
    }

    /**
     * Getter method that returns the name of the rook.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method that sets the castling variable to the parameter given.
     *
     * @param b		the boolean value which castling variable will be set to
     */
    public void setCastling(boolean b) {
        this.castling = b;
    }

    /**
     * This method populates the getMoves ArrayList with the valid
     * moves for a particular rook object on the board b that is passed in.
     *
     * @param b			current board object that is being played on
     * @param getMoves	ArrayList that stores all the valid moves for
     * 					a particular rook object
     */
    public void basicMovement(Board b, ArrayList<Point> getMoves) {
        //checks the basic horizontal and vertical movements of the rook
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

