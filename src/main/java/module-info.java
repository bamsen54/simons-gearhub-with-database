module com.simon {

    requires java.sql;
    requires java.naming;
    requires java.persistence;
    requires org.hibernate.orm.core;

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.simon.entity to org.hibernate.orm.core;
    opens com.simon to javafx.fxml, javafx.graphics;

    exports com.simon;
}