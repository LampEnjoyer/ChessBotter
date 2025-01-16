package com.example.chessbotter;

import java.util.List;

public class Knight extends ChessPiece{

    public Knight(boolean isWhite){
        super(isWhite);
    }

    @Override
    public boolean validateMove(ChessPiece Piece, Position newPos, ChessPiece[][] board) {
        return false;
    }

    @Override
    public List<Position> possibleMoves(ChessPiece[][]pieces) {
        return List.of();
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
}
