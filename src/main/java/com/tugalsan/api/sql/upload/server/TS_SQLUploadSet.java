package com.tugalsan.api.sql.upload.server;

import com.tugalsan.api.executable.client.*;
import com.tugalsan.api.sql.where.server.*;

public class TS_SQLUploadSet {

    public TS_SQLUploadSet(TS_SQLUploadExecutor executor) {
        this.executor = executor;
    }
    private TS_SQLUploadExecutor executor;

    public int whereGroupAnd(TGS_ExecutableType1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsAnd(groups);
        return executor.execute();
    }

    public int whereGroupOr(TGS_ExecutableType1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsOr(groups);
        return executor.execute();
    }

    public int whereConditionAnd(TGS_ExecutableType1<TS_SQLWhereConditions> conditions) {
        return whereGroupAnd(where -> where.conditionsAnd(conditions));
    }

    public int whereConditionOr(TGS_ExecutableType1<TS_SQLWhereConditions> conditions) {
        return whereGroupOr(where -> where.conditionsOr(conditions));
    }

    public long whereConditionNone() {
        return executor.execute();
    }
}
