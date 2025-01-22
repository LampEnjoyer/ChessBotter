package com.example.chessbotter;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.List;

public class MouseEventHandler implements EventHandler<MouseEvent> {
    private final GridPane gridPane;
    private final ChessBoard chessBoard;
    private final ChessPiece [][] pieces;
    private ChessPiece selectedPiece;
    private boolean firstClick;
    private int turnCounter = 0;

    public MouseEventHandler(ChessBoard chessBoard, GridPane gridPane, ChessPiece[][] pieces) {
        this.gridPane = gridPane;
        this.chessBoard = chessBoard;
        this.pieces = pieces;
        this.firstClick = true;
    }

    @Override
    public void handle(MouseEvent e) {
        if(chessBoard.gameOver){
            System.out.println("Game Over");
            return;
        }
        if(e.getEventType() == MouseEvent.MOUSE_CLICKED ){
            mouseClicked(e);
        }
    }

    private void mouseClicked(MouseEvent e) { // Function for two click movement
        StackPane pane = (StackPane) e.getSource();
        int row = gridPane.getRowIndex(pane);
        int col = gridPane.getColumnIndex(pane);
        if (firstClick) { //Stuff for first click
            if (!pane.getChildren().isEmpty()) {
                System.out.println("Piece selected at " + row + " , " + col);
                selectedPiece = pieces[row][col];
                List<ChessPiece.Position> moveList = selectedPiece.getLegalMoves(pieces);
                printPossibleMoves(moveList);
                if (selectedPiece.isWhite != (turnCounter % 2 == 0)) { // Checking to see current turn
                    System.out.println("Not your piece");
                    return;
                }
                firstClick = false;
            } else {
                System.out.println("Empty");
            }
        } else {
            System.out.println("Piece moved to " + row + " , " + col);
            int oldRow = selectedPiece.getPosition().row;
            int oldCol = selectedPiece.getPosition().col;
            List<ChessPiece.Position> moveList = selectedPiece.getLegalMoves(pieces);
            if (moveList.contains(new ChessPiece.Position(row, col))) {
                if(selectedPiece instanceof Pawn){ //Making sure pawns can't move double again
                    ((Pawn) selectedPiece).firstMove = false;
                    if(row == 0 || row == 7){ //Promoting to Queen
                        selectedPiece = new Queen(selectedPiece.isWhite);
                        selectedPiece.position = new ChessPiece.Position(row,col);
                    }
                }
                if(selectedPiece instanceof King && Math.abs(selectedPiece.colDifference(new ChessPiece.Position(row,col))) == 2){ //Castling logic
                    boolean kingSide = selectedPiece.position.col < col;
                    int originalRookColumn = kingSide ? 7 : 0; //Deciding which rook it is
                    int newRookColumn = kingSide ? 5 : 3; //Finding the new column of the Rook depending on what side we castled
                    ChessPiece rook = pieces[row][originalRookColumn];
                    pieces[row][originalRookColumn] = null; //Making the rook slot empty
                    pieces[row][newRookColumn] = rook;
                    rook.position = new ChessPiece.Position(row, newRookColumn);
                }
                //Performing the move
                pieces[oldRow][oldCol] = null;
                selectedPiece.getPosition().row = row;
                selectedPiece.getPosition().col = col;
                pieces[row][col] = selectedPiece;
                chessBoard.updateDisplay();
                turnCounter++;
            }else{
                System.out.println("Not a valid move");
            }
            if (isKinginCheck(!selectedPiece.isWhite)) {
                System.out.println("Check");
            }
            if (isCheckMate(!selectedPiece.isWhite)){
                System.out.println("Mate");
                chessBoard.gameOver = true;
            }
            firstClick = true;
        }
    }


    private void printPossibleMoves(List<ChessPiece.Position> list){
        System.out.println("Possible Moves: ");
        for(ChessPiece.Position pos : list){
           System.out.println(pos.row + " , " + pos.col);
        }
    }

    private boolean isKinginCheck(boolean isWhite){
        ChessPiece.Position kingPos = null; //Finding king position
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                ChessPiece piece = pieces[i][j];
                if(piece != null && piece instanceof King && piece.isWhite == isWhite){
                    kingPos = new ChessPiece.Position(i,j);
                    break; //breaking inner loop when we find king
                }
            }
            if(kingPos != null){break;} //breaking outer loop when we find king
        }
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                ChessPiece piece = pieces[i][j];
                if(piece != null && piece.isWhite != isWhite) {
                    if(piece.possibleMoves(pieces).contains(kingPos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean isCheckMate(boolean isWhite){
        for(int i = 0; i<8; i++){
            for(int j =0; j<8; j++){
                if(pieces[i][j] != null && pieces[i][j].isWhite == isWhite){
                    if(!pieces[i][j].getLegalMoves(pieces).isEmpty()){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
