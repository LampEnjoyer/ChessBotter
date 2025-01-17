package com.example.chessbotter;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece{
    private boolean firstMove = true;
    public Pawn(boolean isWhite){
        super(isWhite);
    }
    @Override
    public boolean isValidPosition(Position pos){
        return pos.row < 8 && pos.row >=0 && pos.col >= 0 && pos.col < 8;
    }

    @Override
    public List<Position> possibleMoves(ChessPiece[][] pieces){
        List<Position> moveList = new ArrayList<>();
        int direction = isWhite ? -1 : 1;
        int row = position.row + direction;
        int col = position.col;
        if(pieces[row][col] == null){
            moveList.add(new Position(row,col));
        }
        if(firstMove && pieces[row + direction][col] == null){
            moveList.add(new Position(row+direction,col));
        }
        if(col+1 < 8 && pieces[row][col + 1] != null && pieces[row][col + 1].isWhite != isWhite){
            moveList.add(new Position(row,col+1));
        }
        if(col-1 > 0 && pieces[row][col - 1] != null && pieces[row][col - 1].isWhite != isWhite){
            moveList.add(new Position(row,col-1));
        }
        return moveList;
    }
    protected String getImageName() {
        return (isWhite ? "white" : "black") + "_pawn";
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
