module org.erd {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires spring.context;
    requires org.hibernate.orm.core;
    requires spring.beans;
    requires jakarta.persistence;
    requires java.naming;
    requires com.zaxxer.hikari;
    requires spring.tx;
    requires spring.orm;
    requires spring.core;
    requires jakarta.validation;
    requires org.hibernate.validator;
    requires spring.jdbc;
    requires org.apache.poi.ooxml;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.jfoenix;
    requires java.desktop;
    requires static lombok;
    requires org.slf4j;
    requires jakarta.transaction;
    requires jakarta.cdi;
    requires jfxtras.controls;

    opens org.erd to javafx.fxml, spring.core, spring.beans, org.hibernate.orm.core;
    exports org.erd;

    opens org.erd.Configuration to javafx.fxml, spring.core, spring.beans, org.hibernate.orm.core;
    exports org.erd.Configuration;

    opens org.erd.roleoptions.models to javafx.fxml, spring.core, spring.beans, org.hibernate.orm.core,org.hibernate.validator;
    exports org.erd.roleoptions.models;

    opens org.erd.roleoptions.controllers to javafx.fxml, spring.core, spring.beans, org.hibernate.orm.core,org.hibernate.validator;
    exports org.erd.roleoptions.controllers;

    opens org.erd.roleoptions.impls to javafx.fxml, spring.core, spring.beans, org.hibernate.orm.core,org.hibernate.validator;
    exports org.erd.roleoptions.impls;

    opens org.erd.roleoptions.services to javafx.fxml, spring.core, spring.beans, org.hibernate.orm.core,org.hibernate.validator;
    exports org.erd.roleoptions.services;

}