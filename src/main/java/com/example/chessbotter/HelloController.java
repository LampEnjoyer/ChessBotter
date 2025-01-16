package com.example.chessbotter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private GridPane gridPane;
    private ChessBoard board;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        board = new ChessBoard(gridPane);
    }
}