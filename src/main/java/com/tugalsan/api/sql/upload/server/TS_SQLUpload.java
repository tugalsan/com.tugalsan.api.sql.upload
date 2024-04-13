package com.tugalsan.api.sql.upload.server;

import java.io.*;
import java.nio.file.*;
import com.tugalsan.api.sql.conn.server.*;
import com.tugalsan.api.union.client.TGS_UnionExcuse;

public class TS_SQLUpload {

    public TS_SQLUpload(TS_SQLConnAnchor anchor, CharSequence tableName) {
        executor = new TS_SQLUploadExecutor(anchor, tableName);
    }
    private final TS_SQLUploadExecutor executor;

    public TGS_UnionExcuse<TS_SQLUploadSet> setFile(CharSequence columnName, Path file) {
        try {
            return TGS_UnionExcuse.of(setInputStream(columnName, Files.newInputStream(file), Files.size(file)));
        } catch (IOException ex) {
            return TGS_UnionExcuse.ofExcuse(ex);
        }
    }

    public TS_SQLUploadSet setInputStream(CharSequence columnName, InputStream is) {
        return setInputStream(columnName, is, 0L);
    }

    public TS_SQLUploadSet setInputStream(CharSequence columnName, InputStream is, long size) {
        executor.set = new TS_SQLUploadConfig(columnName.toString(), is, size);
        return new TS_SQLUploadSet(executor);
    }
}
