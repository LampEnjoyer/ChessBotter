package com.example.chessbotter;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.Objects;

import java.util.List;

public abstract class ChessPiece {
    protected boolean isWhite;
    protected ImageView imageView;
    protected Position position;

    public static class Position{
        int row;
        int col;

        public Position(int row, int col){
            this.row = row;
            this.col = col;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChessPiece.Position position = (ChessPiece.Position) o;
            return row == position.row && col == position.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    public ChessPiece(boolean isWhite){
        this.isWhite = isWhite;
        loadImage();
    }

    public ImageView getImageView(){
        return imageView;
    }

    public Position getPosition(){
        return position;
    }

    public boolean isWhite(){
        return isWhite;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public abstract boolean isValidPosition(Position newPos);

    public abstract List<Position> possibleMoves(ChessPiece[][] pieces);

    protected abstract String getImageName();

    private void loadImage() {
        try {
            String imagePath = "/images/" + getImageName() + ".png";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imageView = new ImageView(image);
            imageView.setFitHeight(60);
            imageView.setFitWidth(60);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Could not load image for " + getClass().getSimpleName());
            e.printStackTrace();
        }
    }

    public abstract List<Position> getLegalMoves(ChessPiece[][] pieces);

    public abstract int rowDifference(Position pos);

    public abstract int colDifference(Position pos);



}
