module com.example.leitorpdfwithgui20 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires pdfbox.app;


    opens com.example.leitorpdfwithgui20 to javafx.fxml;
    exports com.example.leitorpdfwithgui20;
}