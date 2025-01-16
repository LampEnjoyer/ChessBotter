package com.example.chessbotter;

import java.util.List;

public class Bishop extends ChessPiece{

    public Bishop(boolean isWhite){
        super(isWhite);
    }

    @Override
    public boolean validateMove(ChessPiece Piece, Position newPos, ChessPiece[][] board) {
        return false;
    }


    @Override
    public List<Position> possibleMoves(ChessPiece[][] board) {
        return List.of();
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
