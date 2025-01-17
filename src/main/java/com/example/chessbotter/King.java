package com.example.chessbotter;

import java.util.List;

public class King extends ChessPiece{
    private boolean canCastle;
    private boolean inCheck;
    public King(boolean isWhite){
        super(isWhite);
    }

    @Override
    public boolean isValidPosition(Position pos){
        return pos.row < 8 && pos.row >=0 && pos.col >= 0 && pos.col < 8;
    }

    @Override
    public List<Position> possibleMoves(ChessPiece[][] pieces) {
        return List.of();
    }

    @Override
    protected String getImageName() {
        return (isWhite ? "white" : "black") + "_king";
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
