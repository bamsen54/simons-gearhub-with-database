module com.simon {
    requires javafx.controls;
    requires java.sql;
    requires java.naming;

    requires org.hibernate.orm.core;
    requires java.persistence;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;

    opens com.simon.entity to org.hibernate.orm.core, javafx.base;

    opens com.simon to javafx.graphics, org.hibernate.orm.core;

    exports com.simon;
}