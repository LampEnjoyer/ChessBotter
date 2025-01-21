package com.example.chessbotter;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece{

    public Bishop(boolean isWhite){
        super(isWhite);
    }

    @Override
    public boolean isValidPosition(Position pos){
        return pos.row < 8 && pos.row >=0 && pos.col >= 0 && pos.col < 8;
    }


    @Override
    public List<Position> possibleMoves(ChessPiece[][] pieces) {
        List<Position> moveList = new ArrayList<>();
        int [][] directions = { {-1,-1} , {-1,1}, {1,1} , {1,-1} };
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

    @Override
    protected String getImageName() {
        return (isWhite ? "white" : "black") + "_bishop";
    }

    @Override
    public int rowDifference(Position pos) {
        return Math.abs(this.getPosition().row - pos.row);
    }

    @Override
    public int colDifference(Position pos) {
        return Math.abs(this.getPosition().col - pos.col);
    }
}
