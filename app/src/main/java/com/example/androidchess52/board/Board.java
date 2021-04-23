package com.example.androidchess52.board;

import android.graphics.Point;

import com.example.androidchess52.pieces.Bishop;
import com.example.androidchess52.pieces.King;
import com.example.androidchess52.pieces.Knight;
import com.example.androidchess52.pieces.Pawn;
import com.example.androidchess52.pieces.Piece;
import com.example.androidchess52.pieces.Queen;
import com.example.androidchess52.pieces.Rook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
    /**
     * Contains all of the Pieces on the board that are
     * currently in play.
     */
    public ArrayList<Piece> pieces;

    /**
     * Contains the color of the current player --
     * either white or black.
     */
    public String currentPlayer;

    /**
     * Contains the King piece that is currently
     * in check, null if there is no King in check.
     */
    public Piece kingInCheck = null;

    public Board previousMove = null;

    /**
     * Creates a Board object that initializes the
     * pieces on the board as well as makes the current
     * player white.
     */
    public Board() {
        currentPlayer = "White";
        pieces = new ArrayList<Piece>();
        pieces.add(new Pawn("white", 6,0,false));
        pieces.add(new Pawn("white", 6,1,false));
        pieces.add(new Pawn("white", 6,2,false));
        pieces.add(new Pawn("white", 6,3,false));
        pieces.add(new Pawn("white", 6,4,false));
        pieces.add(new Pawn("white", 6,5,false));
        pieces.add(new Pawn("white", 6,6,false));
        pieces.add(new Pawn("white", 6,7,false));

        pieces.add(new Rook("white", 7,0,true));
        pieces.add(new Rook("white", 7,7,true));
        pieces.add(new Knight("white", 7,1));
        pieces.add(new Knight("white", 7,6));
        pieces.add(new Bishop("white", 7,2));
        pieces.add(new Bishop("white", 7,5));
        pieces.add(new Queen("white",7,3));
        pieces.add(new King("white", 7,4, true));

        pieces.add(new Pawn("black", 1,0,false));
        pieces.add(new Pawn("black", 1,1,false));
        pieces.add(new Pawn("black", 1,2,false));
        pieces.add(new Pawn("black", 1,3,false));
        pieces.add(new Pawn("black", 1,4,false));
        pieces.add(new Pawn("black", 1,5,false));
        pieces.add(new Pawn("black", 1,6,false));
        pieces.add(new Pawn("black", 1,7,false));

        pieces.add(new Rook("black", 0,0,true));
        pieces.add(new Rook("black", 0,7,true));
        pieces.add(new Knight("black", 0,1));
        pieces.add(new Knight("black", 0,6));
        pieces.add(new Bishop("black", 0,2));
        pieces.add(new Bishop("black", 0,5));
        pieces.add(new Queen("black",0,3));
        pieces.add(new King("black", 0,4,true));


    }

//------------------------------------------------------deepCopy-------------------------------------------------------
    /**
     * Makes a deep copy of the board - including the
     * individual pieces on the board, their location,
     * and color.
     * <p>
     * The Serializable interface is used for this method.
     *
     * @return				a deep copy of the current board
     * @throws Exception	throws an exception by the serialization class,
     * 						which is a subclass of IOException
     */
    public Board deepCopy() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(this);

        ByteArrayInputStream bis = new   ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        Board copied = (Board) in.readObject();

        return copied;
    }

//------------------------------------------------------removePiece-------------------------------------------------------
    /**
     * Removes the specified piece from the board by removing it
     * from the pieces ArrayList.
     *
     * @param p		the Piece to be removed from the board
     */
    public void removePiece (Piece p) {
        pieces.remove(p);
    }
//------------------------------------------------------addPiece-------------------------------------------------------
    /**
     * Adds the specified piece to the board by adding it
     * to the pieces ArrayList.
     *
     * @param p		the Piece to be added to the board
     */
    public void addPiece (Piece p) {
        pieces.add(p);
    }
//------------------------------------------------------getPieceAt-------------------------------------------------------
    /**
     * Gets the piece from the specified location on the board.
     *
     * @param p		a Point which specifies a location on the board
     * @return		the Piece that is at Point p on the board
     */
    public Piece getPieceAt (Point p) {
        for (Piece piece:pieces) {
            if (piece.location.equals(p)) {
                return piece;
            }
        }
        return null;
    }

//------------------------------------------------------kingInCheck-------------------------------------------------------
    /**
     * Used to check if the current players king is in check.
     * <p>
     * This method is implemented inside each Piece class when
     * finding the valid moves a piece can make on a given board.
     * If this method returns true, that move is deleted from the
     * valid moves ArrayList as making this move will put the current
     * player's own king in check.
     *
     * @return		true if the current players king is in check,
     * 				false otherwise
     */
    public boolean kingInCheck () { //checks if the current players king is in check
        for (Piece p: pieces) {
            //when this method is called, a move is already made which is why we are checking
            //if the piece p is the same color as the current player
            //(they are going to be opposite)
            if ((p.color).equals(currentPlayer.toLowerCase())) {
                for (Point point: p.getMoves(this, false)) {
                    //get the basic moves of this piece which is why false is being passed in
                    if (getPieceAt(point) instanceof King) {
                        //king will be captured
                        if (!getPieceAt(point).color.equals(currentPlayer.toLowerCase())) {
                            //if the piece is a King and is the NOT same color as the current player this board is invalid
                            //(this is because the current player is actually the opposite so !opposite equals the same color as the player)
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

//------------------------------------------------------check-------------------------------------------------------
    /**
     * Checks the board if the current players king is in
     * check as a result of the opposing team and returns the King
     * in check, if one exists.
     *
     * @return		the King piece in check if one exists
     */
    public Piece check() {
        for (Piece p: pieces) {
            //go through opposing teams pieces and check if currentPlayer king is in check
            if (!((p.color).equals(currentPlayer.toLowerCase()))) {
                for (Point point:p.getMoves(this, false)) {
                    if ((getPieceAt(point) instanceof King) && (getPieceAt(point)).color.equals(currentPlayer.toLowerCase())) {
                        kingInCheck = getPieceAt(point);
                        return getPieceAt(point);
                    }
                }
            }
        }
        return null;
    }


//------------------------------------------------------letterToNumber-------------------------------------------------------
    /**
     * Converts a string into the corresponding column number.
     * <p>
     * Used for user input. Letter 'a' corresponds to column 0,
     * letter 'b' corresponds to column 1 and so on. Used for
     * consistency between all Piece locations and Points.
     *
     * @param s		the string to be converted into the corresponding column
     * @return		the column number that String s corresponds to
     */
    public int letterToNumber(String s) {
        switch (s) {
            case "a":
                return 0;
            case "b":
                return 1;
            case "c":
                return 2;
            case "d":
                return 3;
            case "e":
                return 4;
            case "f":
                return 5;
            case "g":
                return 6;
            case "h":
                return 7;
        }
        return -1;
    }

//------------------------------------------------------move-------------------------------------------------------
    /**
     * Converts the users' string input into an array of Points that
     * constitutes the move that the player is trying to make.
     * <p>
     * The first Point in the Points array is the starting location
     * and the second Point is the ending location.
     *
     * @param start		the String starting location of the move as input by the user
     * @param end		the String ending location of the move as input by the user
     * @return			a Points array of the move where the first Point in the array
     * 					is the starting location and the second Point is the ending location
     */
    public Point[] move(String start, String end) {
        //takes the user's input and converts them into Points
        Point startPoint = new Point();
        Point endPoint = new Point();
        Point[] m = new Point[2];

        startPoint.y = this.letterToNumber(start.substring(0,1));
        endPoint.y = this.letterToNumber(end.substring(0,1));

        startPoint.x = 7-(Integer.parseInt(start.substring(1,2)))+1;
        endPoint.x = 7-(Integer.parseInt(end.substring(1,2)))+1;

        m[0] = startPoint;
        m[1] = endPoint;

        return m;
    }

//------------------------------------------------------isValidMove-------------------------------------------------------
    /**
     * Checks the validity of the move and returns true if the move
     * can be made.
     * <p>
     * This method checks 4 criteria: that there exists a Piece
     * on the board at the starting position, that the move is
     * a valid point within the range of the board, that the
     * piece being moved is of the current player's color,
     * and that the move is within the Pieces valid moves.
     *
     * @param points	the Points array that stores the starting
     * 					location of the move and the ending location
     * 					of the move
     * @return			true of this move can be made, false otherwise
     */
    //checks if the move is valid
    public boolean isValidMove(Point[] points) {
        Piece moving = this.getPieceAt(points[0]);
        if (moving == null)
            return false;
        if (points[1].x > 7 || points[1].x < 0 || points[1].y < 0 || points[1].y > 7)
            return false;
        if (!(moving.color).equals(currentPlayer.toLowerCase())) {
            return false;
        } if (!(moving.getMoves(this, true).contains(points[1]))) {
            return false;
        }
        return true;
    }

//------------------------------------------------------tryMove-------------------------------------------------------
    /**
     * Tries a given move on a copy of the current board to check if
     * the move is a valid move -- i. e. the current players
     * king is not in check.
     * <p>
     * This method is used in each Piece class to return a
     * helper board and call the kingInCheck() method on it.
     *
     * @param points	the move that is being performed
     * @return			a copy of the current board with the
     * 					move performed
     */
    public Board tryMove(Point[] points) {
        try {
            Board copy = this.deepCopy();
            copy.makeMove(points);
            return copy;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

//------------------------------------------------------makeMove-------------------------------------------------------
    /**
     * Makes a given move on a board.
     * <p>
     * The move can be made either on a deep copy of the board or
     * on the actual board itself. The move also handles captures.
     *
     * @param points	the move that is being made on the board
     */
    public void makeMove(Point[] points) {
        try {
            previousMove = this.deepCopy();
            //System.out.println("This is the previous move!--------------------");
            //previousMove.drawBoard();
            Piece pieceOnBoard = this.getPieceAt(points[0]); //this is the piece we are moving
            if (this.getPieceAt(points[1]) != null) { //this IS a capture
                pieces.remove(this.getPieceAt(points[1])); //remove the captured piece
            }
            pieceOnBoard.location = points[1]; //move piece to end

            if (currentPlayer.equals("White"))
                currentPlayer = "Black";
            else
                currentPlayer = "White";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//-------------------------------------------------------undoMove--------------------------------------------------------

    public void undoMove() {
        if (previousMove == null) {
            //move cannot be undone as there are no moves done or one undo has already been done
            System.out.println("Illegal move, try again \n" + this.currentPlayer + "\'s move: ");
        } else {
            this.pieces = previousMove.pieces;
            this.currentPlayer = previousMove.currentPlayer;
            this.kingInCheck = previousMove.kingInCheck;
            //to fix undo, just make this.previous move null!!!!!!!!!!!!
            this.previousMove = previousMove.previousMove;
            this.drawBoard();
        }
    }

    //------------------------------------------------------aiMove---------------------------------------------------------
    //this method just returns a valid that will be used as the given move in the main chess class
    public Point[] aiMove() {
        //first get a random piece of the current player
        Piece random = null;
        ArrayList<Point> randomMoves = null;
        for (Piece p: pieces) {
            //makes sure the piece is the same color as the current player and that it has valid moves
            if (p.color.equals(this.currentPlayer.toLowerCase()) && !p.getMoves(this, true).isEmpty()) {
                random = p;
                randomMoves = p.getMoves(this, true);
                break;
            }
        }

        Point[] aimove = new Point[2];
        aimove[0] = random.location;
        aimove[1] = randomMoves.get(0);

        return aimove;

    }

//------------------------------------------------------firstMove-------------------------------------------------------
    /**
     * Changes the castling boolean value of a
     * Rook or King the first time it is moved.
     * <p>
     * Used for the castling implementation. If
     * a Rook or King has moved, it can no longer
     * castle.
     *
     * @param points	the move that is being performed
     * 					on the board
     */
    public void firstMove(Point[] points) {
        Piece pieceOnBoard = this.getPieceAt(points[0]);
        if (pieceOnBoard instanceof Rook) {
            ((Rook) pieceOnBoard).setCastling(false);
        }

        //if king is being moved, a new king is add to account for the false castling boolean
        if (pieceOnBoard instanceof King) {
            ((King) pieceOnBoard).setCastling(false);;
        }
    }

//-----------------------------------------------------doCastle-------------------------------------------------------------
    /**
     * Checks if the move being performed is castling
     * and moves the rook to the specified location
     * as according to castling rules.
     *
     * @param points	the move that is being made
     * 					on the board -- must be a
     * 					castling move for this method
     * 					to change the current board
     */
    public void doCastle(Point[] points) {
        //white king castling to right side
        if (points[0].equals(new Point(7,4)) && points[1].equals(new Point(7,6))) {
            Piece rook = this.getPieceAt(new Point(7,7));
            rook.location = new Point(7,5);
            //white king castling left
        } else if (points[0].equals(new Point(7,4)) && points[1].equals(new Point(7,2))){
            Piece rook = this.getPieceAt(new Point(7,0));
            rook.location = new Point(7,3);
            //black king castling right
        } else if (points[0].equals(new Point(0,4)) && points[1].equals(new Point(0,6))) {
            Piece rook = this.getPieceAt(new Point(0,7));
            rook.location = new Point(0,5);
            //black king castling left
        } else if (points[0].equals(new Point(0,4)) && points[1].equals(new Point(0,2))) {
            Piece rook = this.getPieceAt(new Point(0,0));
            rook.location = new Point(0,3);
        }
    }

//------------------------------------------------------checkPromotion-------------------------------------------------------
    /**
     * Checks if the move being performed
     * is a pawn promotion.
     *
     * @param points	the move being made on the board
     * @return			true if this move is a pawn promotion,
     * 					false otherwise
     */
    public boolean checkPromotion(Point[] points) {
        if(this.getPieceAt(points[0]) instanceof Pawn && points[0].x == 1 && ((this.getPieceAt(points[0])).color).equals("white")) {
            return true;
        } else if((this.getPieceAt(points[0]) instanceof Pawn && points[0].x == 6 && ((this.getPieceAt(points[0])).color).equals("black"))) {
            return true;
        } else {
            return false;
        }
    }

//------------------------------------------------------promotion-------------------------------------------------------
    /**
     * Actually performs the pawn promotion by adding a new
     * Piece to the pieces ArrayList and deleting the old
     * pawn.
     * <p>
     * Also changes the current player of the board.
     *
     * @param promotionPiece	the Piece that the user
     * 							wants to promote the pawn
     * 							to -- can either be Q,N,B,R
     * @param points			the move being made on the board
     */
    public void promotion(Piece promotionPiece, Point[] points) {
        try {
            this.previousMove = this.deepCopy();
            if(this.getPieceAt(points[1]) == null) {
                pieces.remove(this.getPieceAt(points[0]));
                pieces.add(promotionPiece);
                promotionPiece.location = points[1];
            }else {
                pieces.remove(this.getPieceAt(points[1]));
                pieces.remove(this.getPieceAt(points[0]));
                pieces.add(promotionPiece);
                promotionPiece.location = points[1];
            }
            if (currentPlayer.equals("White"))
                currentPlayer = "Black";
            else
                currentPlayer = "White";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//------------------------------------------------------checkmate-------------------------------------------------------
    /**
     * Checks if the current board has a player in checkmate.
     *
     * @return	true if the board is in checkmate, false otherwise
     */
    public boolean checkmate() {

        ArrayList<Point> white = new ArrayList<Point>();
        ArrayList<Point> black = new ArrayList<Point>();

        for (Piece p:pieces) {
            if (p.color.equals("white")) {
                for (Point point: p.getMoves(this, true)) {
                    white.add(point);
                }
            }
            else
                for (Point point: p.getMoves(this, true)) {
                    black.add(point);
                }
        }

        return (white.size() == 0 || black.size() == 0) ? true:false;
    }

//------------------------------------------------------enpassant-------------------------------------------------------
    /**
     * Changes the enpassant conditions on every turn.
     * <p>
     * Sets all enpassant fields to false for the current
     * player to reinitialize the pawns and based on the move
     * changes the enpassant fields of the pawn.
     *
     * @param points	the move being made on the board
     */
    public void enpassant(Point[] points) {
        for(Piece p: pieces) {
            if(p instanceof Pawn && p.color.equals(currentPlayer.toLowerCase())) {
                ((Pawn) p).setEnpassant(false);
            }
        }

        Piece pawn = getPieceAt(points[0]);
        if(pawn instanceof Pawn && pawn.color.equals("white")) {
            if(points[1].x==4 && points[0].x == 6) { //double jump
                ((Pawn) pawn).setEnpassant(true);
            }
        }

        if(pawn instanceof Pawn && pawn.color.equals("black")) {
            if(points[1].x==3 && points[0].x == 1) { //double jump
                ((Pawn) pawn).setEnpassant(true);
            }
        }
    }

//--------------------------------------------------------doEnpassant---------------------------------------------------
    /**
     * Handles the enpassant move if the given move
     * corresponds to an enpassant.
     *
     * @param points	the move being made on the board
     */
    public void doEnpassant(Point[] points) {
        try {
            previousMove = this.deepCopy();
            Piece pawn = getPieceAt(points[0]);
            //change pawn location to the end point
            pawn.location = points[1];
            //remove piece in the same row as pawn and same col as the end move
            this.removePiece(this.getPieceAt(new Point(points[0].x,points[1].y)));

            if (currentPlayer.equals("White"))
                currentPlayer = "Black";
            else
                currentPlayer = "White";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//---------------------------------------------------checkEnpassant----------------------------------
    /**
     * Checks if a given move corresponds to an
     * enpassant move.
     *
     * @param points	the move being made on the board
     * @return			true if the given move is enpassant,
     * 					false otherwise
     */
    public boolean checkEnpassant(Point[] points) {
        //if the piece being moved is not a pawn, enpassant cannot happen
        if (!(this.getPieceAt(points[0]) instanceof Pawn)) return false;
        //if the location being moved to is not null then this is not enpassant
        if (this.getPieceAt(points[1]) != null) return false;
        if (points[0].y == points[1].y) return false;

        return true;
    }


//------------------------------------------------------drawBoard-------------------------------------------------------
    /**
     * Prints out the board with the names
     * of each piece in its corresponding
     * location.
     * <p>
     * Also checks for check and checkmate
     * and prints out the appropriate messages
     * should those conditions be satisfied.
     */
    public void drawBoard() {
        System.out.println();
        String[][] board = new String[8][8];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                for (Piece p: pieces) {
                    if (p.location.equals(new Point(i,j))) {
                        board[i][j] = p.getName();
                        break;
                    }
                }
                if (board[i][j] == null) {
                    if ((i+j)%2==1)
                        board[i][j] = "## ";
                    else
                        board[i][j] = "   ";
                }
            }
        }

        for (int i = 0; i<8; i++) {
            for (int j = 0; j<8; j++) {
                System.out.print(board[i][j]);
            }
            System.out.print(7-i+1);

            System.out.println();
        }

        System.out.println(" a  b  c  d  e  f  g  h ");
        System.out.println();

        if (this.check() != null && !this.checkmate())
            System.out.println("Check");
        if (!this.checkmate())
            System.out.print(currentPlayer + "\'s move: ");
    }


}
