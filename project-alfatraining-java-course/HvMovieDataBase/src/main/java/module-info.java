module de.hvent.hvmoviedatabase {

    requires java.sql;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens de.hvent.hvmoviedatabase to javafx.fxml;
    exports de.hvent.hvmoviedatabase;

    exports de.hvent.hvmoviedatabase.gui.controller;
    opens de.hvent.hvmoviedatabase.gui.controller to javafx.fxml;

    exports de.hvent.hvmoviedatabase.gui.menu;
    opens de.hvent.hvmoviedatabase.gui.menu to javafx.fxml;
}