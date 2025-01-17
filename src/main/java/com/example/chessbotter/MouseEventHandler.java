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
                pieces[oldRow][oldCol] = null;
                selectedPiece.getPosition().row = row;
                selectedPiece.getPosition().col = col;
                pieces[row][col] = selectedPiece;
                chessBoard.updateDisplay();
                turnCounter++;
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
}
