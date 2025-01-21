package com.example.chessbotter;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece{

    public Knight(boolean isWhite){
        super(isWhite);
    }


    @Override
    public List<Position> possibleMoves(ChessPiece[][]pieces) {
        List<Position> moveList = new ArrayList<>();
        int [][] directions = { {1,2} , {1,-2} , {2,1} , {2,-1} , {-1,2} , {-1,-2} , {-2,1} , {-2,-1} };
        int row = position.row;
        int col = position.col;
        for(int [] dir : directions){
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if(isValidPosition(new Position(newRow,newCol)) && (pieces[newRow][newCol] == null || pieces[newRow][newCol].isWhite != isWhite)){
                moveList.add(new Position(newRow,newCol));
            }
        }
        return moveList;
    }
    protected String getImageName() {
        return (isWhite ? "white" : "black") + "_knight";
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
