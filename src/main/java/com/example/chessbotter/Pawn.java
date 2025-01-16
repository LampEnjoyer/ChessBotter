package com.example.chessbotter;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece{
    private boolean firstMove = true;
    public Pawn(boolean isWhite){
        super(isWhite);
    }
    @Override
    public boolean validateMove(ChessPiece Piece, Position newPos, ChessPiece[][] pieces) {
        Position oldPos = Piece.getPosition();
        int rowDiff = rowDifference(newPos);
        int colDiff = colDifference(newPos);
        ChessPiece targetPiece = pieces[newPos.row][newPos.col];
        if(rowDiff == 0 && colDiff == 0){ //Same piece
            return false;
        }
        int direction = isWhite ? -1 : 1;
        int maxMove = firstMove ? 2 : 1;
        if((newPos.row - oldPos.row) * direction <= 0){ // Backwards
            return false;
        }
        if(rowDiff > maxMove || colDiff > 1){ //Tile Limitations
            return false;
        }
        if(targetPiece != null && targetPiece.isWhite == Piece.isWhite){ //Can't capture its own
            return false;
        }
        if( (colDiff == 0 ^ targetPiece == null)){
            return false; //Can't move diagonally without capturing and can't move forward to capture
        }
        if(rowDiff == 2 && pieces[newPos.row - direction][newPos.col] != null){ // Can't jump over something when moving two
            return false;
        }
        firstMove = false;
        return true;
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
