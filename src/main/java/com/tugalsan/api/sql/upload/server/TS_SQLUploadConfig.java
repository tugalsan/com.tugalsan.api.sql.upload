package com.tugalsan.api.sql.upload.server;

import java.io.InputStream;

public record TS_SQLUploadConfig(String columnName, InputStream is, Long size) {
    
}
