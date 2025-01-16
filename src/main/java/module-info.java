module com.example.chessbotter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chessbotter to javafx.fxml;
    exports com.example.chessbotter;
}