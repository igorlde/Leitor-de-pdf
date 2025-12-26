module com.example.leitorpdfwithgui20 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires pdfbox.app;
    requires java.desktop;
    requires javafx.swing;
    opens entity to javafx.graphics, javafx.fxml;

    opens com.example.leitorpdfwithgui20 to javafx.fxml;
    exports com.example.leitorpdfwithgui20;
}