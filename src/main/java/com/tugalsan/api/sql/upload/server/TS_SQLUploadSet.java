package com.tugalsan.api.sql.upload.server;

import com.tugalsan.api.runnable.client.*;
import com.tugalsan.api.sql.conn.server.TS_SQLConnStmtUpdateResult;
import com.tugalsan.api.sql.where.server.*;

public class TS_SQLUploadSet {

    public TS_SQLUploadSet(TS_SQLUploadExecutor executor) {
        this.executor = executor;
    }
    private TS_SQLUploadExecutor executor;

    public TS_SQLConnStmtUpdateResult whereGroupAnd(TGS_RunnableType1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsAnd(groups);
        return executor.run();
    }

    public TS_SQLConnStmtUpdateResult whereGroupOr(TGS_RunnableType1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsOr(groups);
        return executor.run();
    }

    public TS_SQLConnStmtUpdateResult whereConditionAnd(TGS_RunnableType1<TS_SQLWhereConditions> conditions) {
        return whereGroupAnd(where -> where.conditionsAnd(conditions));
    }

    public TS_SQLConnStmtUpdateResult whereConditionOr(TGS_RunnableType1<TS_SQLWhereConditions> conditions) {
        return whereGroupOr(where -> where.conditionsOr(conditions));
    }

    public TS_SQLConnStmtUpdateResult whereConditionNone() {
        return executor.run();
    }
}
