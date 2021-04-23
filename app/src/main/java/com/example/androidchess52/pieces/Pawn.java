package com.example.androidchess52.pieces;

import android.graphics.Point;

import com.example.androidchess52.board.Board;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class extends the abstract class piece and is the class that initializes pawn
 * objects and has its necessary functions which were inherited from the abstract
 * class.
 *
 * @author Chiraag Rekhari and Jahnavi Bavuluri
 */
public class Pawn extends Piece{

    /**
     * variable that is toggled true/false depending on if the current
     * pawn can be enpassed by another pawn
     */
    public boolean enpassant;

    /**
     * The constructor calls the superclasses constructor which sets the color,x,and y.
     * The name is set to wp or bp depending if the color is black or white.
     *
     * @param color			the color of the pawn
     * @param x				the x location of the pawn on the board
     * @param y				the y location of the pawn on the board
     * @param enpassant		set to true if the current pawn can be enpassed by
     * 						another pawn otherwise is false.
     */
    public Pawn(String color, int x, int y, boolean enpassant) {
        super(color,x,y);
        if (color.equals("white"))
            name = "wp ";
        else
            name = "bp ";
        this.enpassant = enpassant;
    }

    /**
     * Getter method that returns the name of the pawn
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method that returns the value of the boolean variable enpassant
     *
     * @return	true or false depending on the pawns enpassant variable
     */
    public boolean getEnpassant() {
        return enpassant;
    }

    /**
     * Setter method that sets the enpassant variable to the parameter given.
     *
     * @param b		the boolean value which enpassant variable will be set to
     */
    public void setEnpassant(boolean b) {
        this.enpassant = b;
    }

    /**
     * This method populates the getMoves ArrayList with the valid moves for
     * a particular pawn object on the board b that is passed in.
     * (Does not include capturing)
     *
     * @param b			current board object that is being played on
     * @param getMoves	ArrayList that stores all the valid moves for a
     * 					particular pawn object
     */
    public void basicMovement(Board b, ArrayList<Point> getMoves) {
        //checks for the moves where the pawn can move up by one or two steps
        int x = location.x;
        int y = location.y;

        if(this.color.equals("white") && (x == 6)) {
            Point p = new Point(5,y);
            Point p2 = new Point(4,y);
            if (b.getPieceAt(p) == null){
                getMoves.add(p);
            }
            if (b.getPieceAt(p) == null && b.getPieceAt(p2) == null){
                getMoves.add(p2);
            }
        }else if(this.color.equals("white") && !(x == 6) && (x!=0)) {
            Point p3 = new Point(x-1,y);
            if (b.getPieceAt(p3) == null){
                getMoves.add(p3);
            }
        }

        if(this.color.equals("black") && (x == 1)) {
            Point p = new Point(2,y);
            Point p2 = new Point(3,y);
            if (b.getPieceAt(p) == null){
                getMoves.add(p);
            }
            if (b.getPieceAt(p) == null && b.getPieceAt(p2) == null){
                getMoves.add(p2);
            }
        }else if(this.color.equals("black") && !(x == 1) && (x!=7)) {
            Point p3 = new Point(x+1,y);
            if (b.getPieceAt(p3) == null){
                getMoves.add(p3);
            }
        }
    }

    /**
     * This method checks the immediate left or right of a pawn and
     * checks the following: the correct row for enpassant, pawn
     * to the left or right, pawn with the enpassant variable set
     * to true, pawn is of a different color. If these are true then
     * that point is added to the ArrayList.
     *
     * @param b			current board object that is being played on
     * @param getMoves	ArrayList that stores all the valid moves
     * 					for a particular pawn object
     */
    public void empassant(Board b, ArrayList<Point> getMoves) {
        //checks if the pawn has an opportunity for enpassant
        int row = this.location.x;
        int col = this.location.y;

        //check to the immediate right and left of this pawn
//--------------------------------------------------white--------------------------------------------------
        if (row == 3) {
            //edge case when pawn in 0th col
            if (col == 0) {
                Piece p = b.getPieceAt(new Point(row, col+1));
                if (p!= null
                        && p instanceof Pawn
                        && !(p.color).equals(this.color)
                        && ((Pawn) p).getEnpassant()) {
                    getMoves.add(new Point(row-1, col+1));
                }
                //edge case when pawn is in 7th row
            } else if (col == 7) {
                Piece p = b.getPieceAt(new Point(row, col-1));
                if (p!= null
                        && p instanceof Pawn
                        && !(p.color).equals(this.color)
                        && ((Pawn) p).getEnpassant()) {
                    getMoves.add(new Point(row-1, col-1));
                }
            } else {
                Piece right = b.getPieceAt(new Point(row, col+1));
                Piece left = b.getPieceAt(new Point(row, col-1));
                if (right!= null
                        && right instanceof Pawn
                        && !(right.color).equals(this.color)
                        && ((Pawn) right).getEnpassant()) {
                    getMoves.add(new Point(row-1, col+1));
                }
                if (left!= null
                        && left instanceof Pawn
                        && !(left.color).equals(this.color)
                        && ((Pawn) left).getEnpassant()) {
                    getMoves.add(new Point(row-1, col-1));
                }
            }
        }

//---------------------------------------------------black---------------------------------------------------
        if (row == 4) {
            if (col == 0) {
                Piece p = b.getPieceAt(new Point(row, col+1));
                if (p!= null
                        && p instanceof Pawn
                        && !(p.color).equals(this.color)
                        && ((Pawn) p).getEnpassant()) {
                    getMoves.add(new Point(row+1, col+1));
                }
                //edge case when pawn is in 7th row
            } else if (col == 7) {
                Piece p = b.getPieceAt(new Point(row, col-1));
                if (p!= null
                        && p instanceof Pawn
                        && !(p.color).equals(this.color)
                        && ((Pawn) p).getEnpassant()) {
                    getMoves.add(new Point(row+1, col-1));
                }
            } else {
                Piece right = b.getPieceAt(new Point(row, col+1));
                Piece left = b.getPieceAt(new Point(row, col-1));
                if (right!= null
                        && right instanceof Pawn
                        && !(right.color).equals(this.color)
                        && ((Pawn) right).getEnpassant()) {
                    getMoves.add(new Point(row+1, col+1));
                }
                if (left!= null
                        && left instanceof Pawn
                        && !(left.color).equals(this.color)
                        && ((Pawn) left).getEnpassant()) {
                    getMoves.add(new Point(row+1, col-1));
                }
            }
        }
    }

    /**
     * This method checks the immediate diagonal left and right to see if
     * there is a piece of the opposite color and if so then that point
     * is added to the ArrayList.
     *
     * @param b			current board object that is being played on
     * @param getMoves	ArrayList that stores all the valid moves for a
     * 					particular pawn object
     */
    public void capture (Board b, ArrayList<Point> getMoves) {
        //checks if the pawn can capture a piece
        int x = location.x;
        int y = location.y;
        //------------------------------------white capture--------------------------------------------------
        if(this.color.equals("white") && ((y != 0) && (y != 7))){
            Point left = new Point(x-1,y-1);
            Point right = new Point(x-1,y+1);
            if((b.getPieceAt(left) != null) && ((b.getPieceAt(left)).color.equals("black"))) {
                getMoves.add(left);
            }
            if((b.getPieceAt(right) != null) && ((b.getPieceAt(right)).color.equals("black"))) {
                getMoves.add(right);
            }
        }
        if(this.color.equals("white") && ((y == 0))) {
            Point right = new Point(x-1,y+1);
            if((b.getPieceAt(right) != null) && ((b.getPieceAt(right)).color.equals("black"))) {
                getMoves.add(right);
            }
        }
        if(this.color.equals("white") && ((y == 7))) {
            Point left = new Point(x-1,y-1);
            if((b.getPieceAt(left) != null) && ((b.getPieceAt(left)).color.equals("black"))) {
                getMoves.add(left);
            }
        }
//------------------------------------black capture--------------------------------------------------
        if(this.color.equals("black") && ((y != 0) && (y != 7))){
            Point left = new Point(x+1,y-1);
            Point right = new Point(x+1,y+1);
            if((b.getPieceAt(left) != null) && ((b.getPieceAt(left)).color.equals("white"))) {
                getMoves.add(left);
            }
            if((b.getPieceAt(right) != null) && ((b.getPieceAt(right)).color.equals("white"))) {
                getMoves.add(right);
            }
        }
        if(this.color.equals("black") && ((y == 0))){
            Point right = new Point(x+1,y+1);
            if((b.getPieceAt(right) != null) && ((b.getPieceAt(right)).color.equals("white"))) {
                getMoves.add(right);
            }
        }
        if(this.color.equals("black") && ((y == 7))) {
            Point left = new Point(x+1,y-1);
            if((b.getPieceAt(left) != null) && ((b.getPieceAt(left)).color.equals("white"))) {
                getMoves.add(left);
            }
        }
    }

    /**
     * This method calls capture, enpassant, and basicMovement to populate the
     * ArrayList Moves and checks if each move puts its own king in check.
     */
    public ArrayList<Point> getMoves(Board b, boolean check) {
        //calls the above methods and finally checks if making this move will put its own king in check
        ArrayList<Point> moves = new ArrayList<Point>();
        basicMovement(b, moves);
        capture(b, moves);
        empassant(b, moves);

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
