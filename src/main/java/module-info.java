module com.tugalsan.api.sql.upload {
    requires java.sql;
    requires com.tugalsan.api.tuple;
    requires com.tugalsan.api.unsafe;
    requires com.tugalsan.api.function;
    requires com.tugalsan.api.sql.sanitize;
    requires com.tugalsan.api.sql.update;
    requires com.tugalsan.api.sql.where;
    requires com.tugalsan.api.sql.order;
    requires com.tugalsan.api.sql.conn;
    exports com.tugalsan.api.sql.upload.server;
}
