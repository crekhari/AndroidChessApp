package com.example.androidchess52.pieces;

import com.example.androidchess52.board.Board;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class extends the abstract class piece and is the
 * class that initializes king objects and has its
 * necessary functions which were inherited from the
 * abstract class.
 *
 * @author Chiraag Rekhari and Jahnavi Bavuluri
 */
public class King extends Piece{

    /**
     * variable that is toggled true/false
     * depending on if the current king can be castled
     */
    public boolean castling;

    /**
     * The constructor calls the superclasses constructor which sets the color,x,and y.
     * The name is set to wK or bK depending if the color is black or white.
     *
     * @param color		the color of the king
     * @param x			the x location of the king on the board
     * @param y			the y location of the king on the board
     * @param castling	set to true if the current king can be castled
     */
    public King(String color, int x, int y, boolean castling) {
        super(color, x, y);
        if (color.equals("white"))
            name = "wK ";
        else
            name = "bK ";

        this.castling = castling;
    }

    /**
     * Getter method that returns the name of the king
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
     * This method populates the getMoves ArrayList with the
     * valid moves for a particular king object on the board
     * b that is passed in.
     *
     * @param b			current board object that is being played on
     * @param getMoves	ArrayList that stores all the valid moves
     * 					for a particular king object
     */
    public void basicMovement (Board b, ArrayList<Point> getMoves) {
        int x = location.x;
        int y = location.y;
        Point up;
        Point down;
        Point right;
        Point left;

        Point upRight;
        Point upLeft;
        Point downRight;
        Point downLeft;

        if(this.color.equals("white")) {
            up = new Point(x-1,y);
            down = new Point(x+1,y);
            right = new Point(x,y+1);
            left = new Point(x,y-1);

            upRight = new Point(x-1,y+1);
            upLeft = new Point(x-1,y-1);
            downRight = new Point(x+1,y+1);
            downLeft = new Point(x+1,y-1);

        }else {
            up = new Point(x-1,y);
            down = new Point(x+1,y);
            right = new Point(x,y+1);
            left = new Point(x,y-1);

            upRight = new Point(x-1,y+1);
            upLeft = new Point(x-1,y-1);
            downRight = new Point(x+1,y+1);
            downLeft = new Point(x+1,y-1);
        }

        getMoves.add(up);
        getMoves.add(down);
        getMoves.add(right);
        getMoves.add(left);
        getMoves.add(upRight);
        getMoves.add(upLeft);
        getMoves.add(downRight);
        getMoves.add(downLeft);


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
     * This method adds valid castling moves to the getMoves
     * ArrayList if the king and rook can castle.
     *
     * @param b			current board object that is being played on
     * @param moves		ArrayList that stores all the valid
     * 					moves for a particular king object
     */
    public void castling(Board b, ArrayList<Point> moves) {
        //check if king has not moved
        if (!this.castling) return;

        //check if king is NOT in check
        if (b.kingInCheck!= null && b.kingInCheck.equals(this)) return;

        //check that rooks are the same color and have not moved yet
        ArrayList<Piece> rooksThatCanCastle = new ArrayList<Piece>();
        for (Piece p:b.pieces) {
            if (p instanceof Rook && p.color.equals(this.color) && ((Rook) p).castling) {
                rooksThatCanCastle.add(p);
            }
        }
        if (rooksThatCanCastle.isEmpty()) return;


        //checks that the squares between the rook and king are EMPTY
        Iterator<Piece> iter = rooksThatCanCastle.iterator();
        while (iter.hasNext()) {
            Piece p = iter.next();
            if (p.location.y == 0) {
                if (b.getPieceAt(new Point(p.location.x, 1))!=null
                        || b.getPieceAt(new Point(p.location.x, 2))!=null
                        || b.getPieceAt(new Point(p.location.x, 3))!=null) {

                    iter.remove();
                }
            } else if (p.location.y == 7) {
                if (b.getPieceAt(new Point(p.location.x, 5))!=null
                        || b.getPieceAt(new Point(p.location.x, 6))!=null ) {
                    iter.remove();
                }
            }
        }
        if (rooksThatCanCastle.isEmpty()) return;


        //check if none of the squares that the king is passing through is in check

        Iterator<Piece> iter2 = rooksThatCanCastle.iterator();
        while(iter2.hasNext()) {
            Piece p = iter2.next();
            if (p.location.y == 0) {
                for (int i = 1; i<4; i++) {
                    if (this.kingInCheck(b,new Point(this.location.x, i) )) {
                        iter2.remove();
                        break;
                    }
                }
            } else if (p.location.y == 7) {
                for (int i = 5; i<7; i++) {
                    if (this.kingInCheck(b, new Point(this.location.x, i))) {
                        iter2.remove();
                        break;
                    }
                }
            }
        }
        if (rooksThatCanCastle.isEmpty()) return;

        //checks that an opponents king does not put the blocks in check
        Iterator<Piece> iter4 = rooksThatCanCastle.iterator();
        while (iter4.hasNext()) {
            Piece p = iter4.next();
            //bottom left rook
            if(p.location.y == 0 && p.location.x == 7) {
                if(b.getPieceAt(new Point(6,2)) instanceof King || b.getPieceAt(new Point(6,1)) instanceof King) {
                    iter4.remove();
                }
            }
            //bottom right rook
            if(p.location.y == 7 && p.location.x == 7) {
                if(b.getPieceAt(new Point(6,6)) instanceof King) {
                    iter4.remove();
                }
            }
            //top left rook
            if(p.location.y == 0 && p.location.x == 0) {
                if(b.getPieceAt(new Point(1,1)) instanceof King || b.getPieceAt(new Point(1,2)) instanceof King) {
                    iter4.remove();
                }
            }
            //top right rook
            if(p.location.y == 7 && p.location.x == 0) {
                if(b.getPieceAt(new Point(1,6)) instanceof King) {
                    iter4.remove();
                }
            }
        }

        //FINALLY add the castling point to the array list moves
        Iterator<Piece> iter3 = rooksThatCanCastle.iterator();
        while(iter3.hasNext()) {
            Piece p = iter3.next();
            if (p.location.y == 0) {
                moves.add(new Point(this.location.x, 2));
            } else if (p.location.y == 7) {
                moves.add(new Point(this.location.x, 6));
            }
        }

    }

    /**
     * Checks if the opposing pieces puts the current players
     * king in check.
     * <p>
     * This varies from the board method as this method does not
     * include the King pieces. It checks solely for check on the
     * other pieces.
     *
     * @param b			current board object that is being played on
     * @param point		the location that is being checked to see if it
     * 					is in check
     * @return			true if the point is in check, false otherwise
     */
    public boolean kingInCheck(Board b, Point point) {
        ArrayList<Piece> opposingPieces = new ArrayList<Piece>();
        for (Piece p: b.pieces) {
            if (!p.color.equals(this.color) && !(p instanceof King)) {
                opposingPieces.add(p);
            }
        }

        for (Piece p: opposingPieces) {
            for (Point point1 : p.getMoves(b,false)) {
                if (point1.equals(point))
                    return true;
            }
        }
        return false;
    }

    /**
     * This method calls castling and basicMovement to
     * populate the ArrayList Moves and checks if each
     * move puts its own king in check
     */
    public ArrayList<Point> getMoves(Board b, boolean check) {
        //calls the above methods and finally checks if making this move will put its own king in check
        ArrayList<Point> moves = new ArrayList<Point>();
        basicMovement(b, moves);
        castling(b, moves);
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

