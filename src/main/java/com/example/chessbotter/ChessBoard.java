package com.example.chessbotter;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ChessBoard {
    private ChessPiece [][] pieces;
    private final GridPane gridPane;
    public boolean gameOver = false;

    public ChessBoard(GridPane gridPane){
        this.gridPane = gridPane;
        pieces = new ChessPiece[8][8];
        createBoard();
        initializePieces();
    }

    private void createBoard(){
        MouseEventHandler handle = new MouseEventHandler(this,gridPane,pieces);
        for(int i = 0; i <8; i++){
            for(int j = 0; j<8; j++){
                StackPane pane = new StackPane();
                String color = (i + j) % 2 == 0 ? "chess-square-white" : "chess-square-black";
                pane.getStyleClass().add(color);
                pane.setPrefSize(60,60);
                gridPane.add(pane,i,j);
                pane.setOnMouseClicked(handle);
                pane.setOnMousePressed(handle);
                pane.setOnMouseDragged(handle);
                pane.setOnMouseDragReleased(handle);
            }
        }
    }

    private void initializePieces(){
        setPiece(0,0, new Rook(false));
        setPiece(0,1,new Knight(false));
        setPiece(0,2, new Bishop(false));
        setPiece(0,3,new Queen(false));
        setPiece(0,4,new King(false));
        setPiece(0,5, new Bishop(false));
        setPiece(0,6,new Knight(false));
        setPiece(0,7, new Rook(false));
        for(int col = 0; col<8; col++){
            setPiece(1,col,new Pawn(false));
            setPiece(6,col, new Pawn(true));
        }
        setPiece(7,0, new Rook(true));
        setPiece(7,1,new Knight(true));
        setPiece(7,2, new Bishop(true));
        setPiece(7,3,new Queen(true));
        setPiece(7,4,new King(true));
        setPiece(7,5, new Bishop(true));
        setPiece(7,6,new Knight(true));
        setPiece(7,7, new Rook(true));
        updateDisplay();
    }

    private void setPiece(int row, int col, ChessPiece piece){
        pieces[row][col] = piece;
        piece.setPosition(new ChessPiece.Position(row,col));
    }

    private StackPane getSquareAt(int row, int col) {
        for(Node node : gridPane.getChildren()){
            if (node instanceof StackPane &&
                    GridPane.getRowIndex(node) == row &&
                    GridPane.getColumnIndex(node) == col) {
                return (StackPane) node;
            }
        }

        return null;
    }

    public void updateDisplay(){
        for (Node node : gridPane.getChildren()) {
            if (node instanceof StackPane) {
                ((StackPane) node).getChildren().clear();
            }
        }
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                if(pieces[i][j] != null){
                    StackPane pane = getSquareAt(i,j);
                    if(pane != null) {
                        ImageView pieceImage = pieces[i][j].getImageView();
                        pieceImage.setFitHeight(60);
                        pieceImage.setFitWidth(60);
                        pane.getChildren().add(pieceImage);
                    }
                }
            }
        }
    }

}
