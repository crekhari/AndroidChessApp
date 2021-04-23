package com.example.androidchess52.pieces;

import android.graphics.Point;

import com.example.androidchess52.board.Board;

import java.io.Serializable;
import java.util.*;

/**
 * This abstract super class defines common methods that all the pieces
 * on the chess board need to implement in order to work.
 * <p>
 * This class implements the Serializable interface that is used for
 * making deep copies of the board.
 *
 * @author Chiraag Rekhari and Jahnavi Bavuluri
 */
public abstract class Piece implements Serializable{

    /**
     * Contains the color of the piece -- either white or black.
     */
    public String color;
    /**
     * Contains the location of the piece on the board as a Point.
     */
    public Point location;
    /**
     * Contains the name of the piece which is used to display on the board.
     */
    public String name;

    /**
     * Initializes the color of the pieces, the name of the piece,
     * along with the x and y location of the piece,
     * which is then used to define where a particular piece sits on the chess board.
     *
     * @param color		the color of the piece -- either black or white
     * @param x			the x location of the piece on the board
     * @param y			the y location of the piece on the board
     */
    public Piece (String color, int x, int y) {
        this.color = color;
        this.location = new Point(x,y);
        name = "";
    }

    /**
     * Returns the color of the wanted piece.
     *
     * @param p		the piece that you want the color of
     * @return		the color of the given piece
     */
    public String color(Piece p) {
        return color;
    }

    /**
     * Abstract method to be implemented in each sub class of Piece --
     * returns the name of the Piece
     *
     * @return		the name of the Piece to be drawn in the board
     */
    public abstract String getName();

    /**
     * Abstract method to be implemented in each sub class of Piece --
     * returns the valid Points that a piece can move to on the board
     *
     * @param b			the board that the Piece is on
     * @param check		checks valid Points that does not put own king in check
     * @return			an ArrayList of Points that the given Piece can move to
     */
    public abstract ArrayList<Point> getMoves(Board b, boolean check);

}
