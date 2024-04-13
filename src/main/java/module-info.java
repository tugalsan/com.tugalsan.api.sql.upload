module com.tugalsan.api.sql.upload {
    requires java.sql;
    requires com.tugalsan.api.union;
    requires com.tugalsan.api.runnable;
    requires com.tugalsan.api.sql.sanitize;
    requires com.tugalsan.api.sql.update;
    requires com.tugalsan.api.sql.where;
    requires com.tugalsan.api.sql.order;
    requires com.tugalsan.api.sql.conn;
    exports com.tugalsan.api.sql.upload.server;
}
