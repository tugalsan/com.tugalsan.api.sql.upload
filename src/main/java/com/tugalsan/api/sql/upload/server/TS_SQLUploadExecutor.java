package com.tugalsan.api.sql.upload.server;

import java.io.*;
import java.sql.*;
import com.tugalsan.api.sql.order.server.*;
import com.tugalsan.api.sql.conn.server.*;
import com.tugalsan.api.sql.sanitize.server.*;
import com.tugalsan.api.sql.update.server.*;
import com.tugalsan.api.sql.where.server.*;
import com.tugalsan.api.union.client.TGS_UnionExcuse;

public class TS_SQLUploadExecutor {

    public TS_SQLUploadExecutor(TS_SQLConnAnchor anchor, CharSequence tableName) {
        this.anchor = anchor;
        this.tableName = tableName;
    }
    final public TS_SQLConnAnchor anchor;
    final public CharSequence tableName;

    public TS_SQLUploadConfig set = null;
    public TS_SQLWhere where = null;
    public TS_SQLOrder order = null;

    private String set_toString() {
        TS_SQLSanitizeUtils.sanitize(set.columnName());
        return set.columnName().concat(" = ?");
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

    private TGS_UnionExcuse<Integer> set_fill(PreparedStatement stmt, int offset) {
        try (var is = set.is()) {
            var newOffset = offset;
            if (set.size() == null || set.size() == 0L) {
                stmt.setBinaryStream(newOffset++, is);
            } else {
                stmt.setBinaryStream(newOffset++, is, set.size());
            }
            return TGS_UnionExcuse.of(newOffset);
        } catch (IOException | SQLException ex) {
            return TGS_UnionExcuse.ofExcuse(ex);
        }
    }

    public TGS_UnionExcuse<TS_SQLConnStmtUpdateResult> run() {
        var wrap = new Object() {
            TGS_UnionExcuse<Integer> u_setFill;
            TGS_UnionExcuse<Integer> u_whereFill;
            TGS_UnionExcuse<TS_SQLConnStmtUpdateResult> u_update;
        };
        wrap.u_update = TS_SQLUpdateStmtUtils.update(anchor, toString(), fillStmt -> {
            wrap.u_setFill = set_fill(fillStmt, 0);
            wrap.u_whereFill = where.fill(fillStmt, wrap.u_setFill.value());
        });
        if (wrap.u_setFill != null && wrap.u_setFill.isExcuse()) {
            return wrap.u_setFill.toExcuse();
        }
        if (wrap.u_whereFill != null && wrap.u_whereFill.isExcuse()) {
            return wrap.u_whereFill.toExcuse();
        }
        return wrap.u_update;
    }
}
