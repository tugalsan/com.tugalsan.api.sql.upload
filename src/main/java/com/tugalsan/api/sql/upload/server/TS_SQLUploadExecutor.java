package com.tugalsan.api.sql.upload.server;

import com.tugalsan.api.function.client.maythrowexceptions.checked.TGS_FuncMTCUtils;
import java.io.*;
import java.sql.*;
import com.tugalsan.api.tuple.client.*;
import com.tugalsan.api.sql.order.server.*;
import com.tugalsan.api.sql.conn.server.*;
import com.tugalsan.api.sql.sanitize.server.*;
import com.tugalsan.api.sql.update.server.*;
import com.tugalsan.api.sql.where.server.*;


public class TS_SQLUploadExecutor {

    public TS_SQLUploadExecutor(TS_SQLConnAnchor anchor, CharSequence tableName) {
        this.anchor = anchor;
        this.tableName = tableName;
    }
    final public TS_SQLConnAnchor anchor;
    final public CharSequence tableName;

    public TGS_Tuple3<String, InputStream, Long> set = new TGS_Tuple3();
    public TS_SQLWhere where = null;
    public TS_SQLOrder order = null;

    private String set_toString() {
        TS_SQLSanitizeUtils.sanitize(set.value0);
        return set.value0.concat(" = ?");
    }

    @Override
    public String toString() {
        var sb = new StringBuilder("UPDATE ").append(tableName).append(" SET ").append(set_toString());
        if (where != null) {
            sb.append(" ").append(where);
        }
        if (order != null) {
            sb.append(" ").append(order);
        }
        return sb.toString();
    }

    private int set_fill(PreparedStatement stmt, int offset) {
        return TGS_FuncMTCUtils.call(() -> {
            var newOffset = offset;
            try (var is = set.value1) {
                if (set.value2 == null || set.value2 == 0L) {
                    stmt.setBinaryStream(newOffset++, is);
                } else {
                    stmt.setBinaryStream(newOffset++, is, set.value2);
                }
            }
            return newOffset;
        });
    }

    public TS_SQLConnStmtUpdateResult run() {
        return TGS_FuncMTCUtils.call(() -> {
            try (var is = set.value1) {
                return TS_SQLUpdateStmtUtils.update(anchor, toString(), fillStmt -> {
                    var idx = set_fill(fillStmt, 0);
                    where.fill(fillStmt, idx);
                });
            }
        });
    }
}
