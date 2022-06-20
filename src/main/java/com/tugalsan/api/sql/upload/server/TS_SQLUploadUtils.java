package com.tugalsan.api.sql.upload.server;

import com.tugalsan.api.sql.conn.server.TS_SQLConnAnchor;
import java.io.File;

public class TS_SQLUploadUtils {

    public static TS_SQLUpload upload(TS_SQLConnAnchor anchor, CharSequence tableName) {
        return new TS_SQLUpload(anchor, tableName);
    }

    public static void test() {
        TS_SQLUploadUtils
                .upload(null, "tn")
                .setFile("colName", new File("").toPath())
                .whereConditionAnd(conditions -> conditions.lngEq("", 0));
    }
}
