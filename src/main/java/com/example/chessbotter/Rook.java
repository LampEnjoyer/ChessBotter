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
                ChessPiece targetPiece = pieces[row][col];
                if(targetPiece == null){
                    moveList.add(new Position(row,col));
                }else{
                    if(targetPiece.isWhite != isWhite()){
                        moveList.add(new Position(row,col));
                    }
                    break;
                }
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

    @Override
    public boolean isValidPosition(Position pos){
        return pos.row < 8 && pos.row >=0 && pos.col >= 0 && pos.col < 8;
    }


}
