module UI {
        requires javafx.controls;
        requires javafx.fxml;
        requires javafx.base;
        requires javafx.graphics;

    opens UI to javafx.fxml, javafx.controls, javafx.base, javafx.graphics;
    exports UI;

    opens domain to javafx.fxml, javafx.controls, javafx.base, javafx.graphics;
    exports domain;

    opens service to javafx.fxml, javafx.controls, javafx.base, javafx.graphics;
    exports service;

    opens repository to javafx.fxml, javafx.controls, javafx.base, javafx.graphics;
    exports repository;
            }