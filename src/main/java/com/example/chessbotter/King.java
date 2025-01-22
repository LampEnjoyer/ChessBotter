package com.example.chessbotter;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece{
    public boolean canCastle;
    public King(boolean isWhite){
        super(isWhite);
    }

    @Override
    public boolean isValidPosition(Position pos){
        return pos.row < 8 && pos.row >=0 && pos.col >= 0 && pos.col < 8;
    }

    @Override
    public List<Position> possibleMoves(ChessPiece[][] pieces) {
        List<Position> moveList = new ArrayList<>();
        int [][] directions = { {-1,-1} , {-1,1}, {1,1} , {1,-1} , {0,1}, {0,-1}, {1,0}, {-1,0}};
        int row = position.row;
        int col = position.col;
        for(int [] dir : directions){
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if(isValidPosition(new Position(newRow,newCol)) && (pieces[newRow][newCol] == null || pieces[newRow][newCol].isWhite != isWhite)){
                moveList.add(new Position(newRow,newCol));
            }
        }
        if(!canCastle){
            if(canCastle(pieces, true)){ //King side castling
                moveList.add(new Position(position.row, position.col + 2));
            }if(canCastle(pieces, false)){ // Queen side castling
                moveList.add(new Position(position.row, position.col - 2));
            }
        }
        return moveList;
    }
    @Override
    public List<Position> getLegalMoves(ChessPiece[][] pieces){
        List<Position> moveList = possibleMoves(pieces);
        List<Position> legalMoves = new ArrayList<>();
        for(Position move : moveList){
            int oldRow = position.row;
            int oldCol = position.col;
            ChessPiece capturedPiece = pieces[move.row][move.col];
            pieces[oldRow][oldCol] = null; //Simulating the move
            position.row = move.row;
            position.col = move.col;
            pieces[move.row][move.col] = this;
            if(!isKingInCheck(pieces)) {
                legalMoves.add(move);
            }
            pieces[oldRow][oldCol] = this; //undoing the move
            position.row = oldRow;
            position.col = oldCol;
            pieces[move.row][move.col] = capturedPiece;
        }
        return legalMoves;
    }

    private boolean isKingInCheck(ChessPiece [][] pieces){
        ChessPiece.Position kingPos = null; //Finding king position
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                ChessPiece piece = pieces[i][j];
                if(piece != null && piece instanceof King && piece.isWhite == isWhite){
                    kingPos = new ChessPiece.Position(i,j);
                    break; //breaking inner loop when we find king
                }
            }
            if(kingPos != null){break;} //brealomg outer loop when we find king
        }
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                ChessPiece piece = pieces[i][j];
                if(piece != null && piece.isWhite != isWhite) {
                    if(piece.possibleMoves(pieces).contains(kingPos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canCastle(ChessPiece[][] pieces, boolean kingSide){
        int row = position.row;
        int rookCol = kingSide ? 7 : 0;
        int step = kingSide ? 1 : -1;
        if (!(pieces[row][rookCol] instanceof Rook) || ((Rook) pieces[row][rookCol]).canCastle || pieces[row][rookCol].isWhite != isWhite) {
            return false;
        }
        for (int col = position.col + step; col != rookCol; col += step) { //Making sure nothing is in between them
            if (pieces[row][col] != null) {
                return false;
            }
        }
        return true;
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
