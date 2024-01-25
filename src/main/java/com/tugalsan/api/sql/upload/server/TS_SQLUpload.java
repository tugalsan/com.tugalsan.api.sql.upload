package com.tugalsan.api.sql.upload.server;

import java.io.*;
import java.nio.file.*;
import com.tugalsan.api.sql.conn.server.*;
import com.tugalsan.api.unsafe.client.*;

public class TS_SQLUpload {

    public TS_SQLUpload(TS_SQLConnAnchor anchor, CharSequence tableName) {
        executor = new TS_SQLUploadExecutor(anchor, tableName);
    }
    private final TS_SQLUploadExecutor executor;

    public TS_SQLUploadSet setFile(CharSequence columnName, Path file) {
        return TGS_UnSafe.call(() -> setInputStream(columnName, Files.newInputStream(file), Files.size(file)));
    }

    public TS_SQLUploadSet setInputStream(CharSequence columnName, InputStream is) {
        return setInputStream(columnName, is, 0L);
    }

    public TS_SQLUploadSet setInputStream(CharSequence columnName, InputStream is, long size) {
        executor.set.value0 = columnName.toString();
        executor.set.value1 = is;
        executor.set.value2 = size;
        return new TS_SQLUploadSet(executor);
    }
}
