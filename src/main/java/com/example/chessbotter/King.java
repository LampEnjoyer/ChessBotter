package com.example.chessbotter;

import java.util.List;

public class King extends ChessPiece{
    private boolean canCastle;
    private boolean inCheck;
    public King(boolean isWhite){
        super(isWhite);
    }

    @Override
    public boolean validateMove(ChessPiece Piece, Position newPos, ChessPiece[][] pieces) {
        Position oldPos = Piece.getPosition();
        int rowDiff = rowDifference(newPos);
        int colDiff = colDifference(newPos);
        ChessPiece targetPiece = pieces[newPos.row][newPos.col];
        if(targetPiece != null && targetPiece.isWhite == isWhite){
            return false;
        }
        if((rowDiff > 1 || colDiff > 1) || (rowDiff == 0 && colDiff == 0)){
            return false;
        }
        return true;
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
