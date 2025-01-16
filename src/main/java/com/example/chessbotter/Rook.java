package com.example.chessbotter;

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece{
    private boolean canCastle;
    public Rook(boolean isWhite){
        super(isWhite);
    }

    @Override
    public boolean validateMove(ChessPiece Piece, Position newPos, ChessPiece[][] pieces) {
//        Position oldPos = Piece.getPosition();
//        ChessPiece targetPiece = pieces[newPos.row][newPos.col];
//        if(targetPiece.isWhite == isWhite && !(targetPiece instanceof King)){ //Own piece besides King
//            return false;
//        }
//        if(newPos.row != oldPos.row && newPos.col != oldPos.col){ //Can't move diagonally
//            return false;
//        }
//        int rowDiff = rowDifference(newPos);
//        int colDiff = rowDifference(newPos);

        return true;
    }

    @Override
    public List<Position> possibleMoves(ChessPiece[][] pieces) {
        List<Position> moveList = new ArrayList<>();
        int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        for(int [] dir : directions){
            int row = position.row;
            int col = position.col;
            while(true){
                row += dir[0];
                col += dir[1];
                if(!isValidPosition(new Position(row,col))){
                    break;
                }
                if(pieces[row][col] == null){
                    moveList.add(new Position(row,col));
                }
                if(pieces[row][col].isWhite != this.isWhite){
                    moveList.add(new Position(row,col));
                }
                break; //Can't move through pieces
            }
        }
        return moveList;
    }

    protected String getImageName() {
        return (isWhite ? "white" : "black") + "_rook";
    }
    @Override
    public int rowDifference(Position pos) {
        return Math.abs(this.getPosition().row - pos.row);
    }

    @Override
    public int colDifference(Position pos) {
        return Math.abs(this.getPosition().col - pos.col);
    }

    private boolean isValidPosition(Position pos){
        return pos.row < 8 && pos.row >=0 && pos.col >= 0 && pos.col < 8;
    }


}
