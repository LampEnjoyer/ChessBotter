package com.example.chessbotter;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
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
        if(e.getEventType() == MouseEvent.MOUSE_CLICKED ){
            mouseClicked(e);
        }
    }

    private void mouseClicked(MouseEvent e){ // Function for two click movement
        StackPane pane = (StackPane) e.getSource();
        int row = gridPane.getRowIndex(pane);
        int col = gridPane.getColumnIndex(pane);
        if(firstClick){ //Stuff for first click
            if(!pane.getChildren().isEmpty()){
                System.out.println("Piece selected at " + row + " , " + col);
                selectedPiece = pieces[row][col];
                List<ChessPiece.Position> moveList = selectedPiece.possibleMoves(pieces);
                printPossibleMoves(moveList);
                if(selectedPiece.isWhite != (turnCounter % 2 == 0)){ // Checking to see current turn
                    System.out.println("Not your piece");
                    return;
                }
                firstClick = false;
            }else{
                System.out.println("Empty");
            }
        }else{
            System.out.println("Piece moved to " + row + " , " + col);
            int oldRow = selectedPiece.getPosition().row;
            int oldCol = selectedPiece.getPosition().col;
            List<ChessPiece.Position> moveList = selectedPiece.possibleMoves(pieces);
            if(moveList.contains(new ChessPiece.Position(row,col))){
                //Making move temporarily
                pieces[oldRow][oldCol] = null;
                selectedPiece.getPosition().row = row;
                selectedPiece.getPosition().col = col;
                ChessPiece capturedPiece = pieces[row][col]; //Storing captured piece in case we move our king into check
                pieces[row][col] = selectedPiece;
                if(isKinginCheck(selectedPiece.isWhite)){
                    //undo Move
                    pieces[oldRow][oldCol] = selectedPiece;
                    selectedPiece.getPosition().row = oldRow;
                    selectedPiece.getPosition().col = oldCol;
                    pieces[row][col] = capturedPiece;
                    System.out.println("Move would put king in check");
                    selectedPiece = null;
                }else {
                    chessBoard.updateDisplay();
                    turnCounter++;
                    if(isKinginCheck(!selectedPiece.isWhite)){
                        System.out.println("Check!");
                    }
                }
            }else{
                selectedPiece = null;
                System.out.println("Not a Valid Move");
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
            if(kingPos != null){break;} //brealomg outer loop when we find king
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
}
