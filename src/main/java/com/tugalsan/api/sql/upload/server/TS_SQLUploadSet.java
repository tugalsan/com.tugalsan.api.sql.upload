package com.tugalsan.api.sql.upload.server;

import com.tugalsan.api.runnable.client.*;
import com.tugalsan.api.sql.conn.server.TS_SQLConnStmtUpdateResult;
import com.tugalsan.api.sql.where.server.*;
import com.tugalsan.api.union.client.TGS_UnionExcuse;

public class TS_SQLUploadSet {

    public TS_SQLUploadSet(TS_SQLUploadExecutor executor) {
        this.executor = executor;
    }
    private final TS_SQLUploadExecutor executor;

    public TGS_UnionExcuse<TS_SQLConnStmtUpdateResult> whereGroupAnd(TGS_RunnableType1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsAnd(groups);
        return executor.run();
    }

    public TGS_UnionExcuse<TS_SQLConnStmtUpdateResult> whereGroupOr(TGS_RunnableType1<TS_SQLWhereGroups> groups) {
        executor.where = TS_SQLWhereUtils.where();
        executor.where.groupsOr(groups);
        return executor.run();
    }

    public TGS_UnionExcuse<TS_SQLConnStmtUpdateResult> whereConditionAnd(TGS_RunnableType1<TS_SQLWhereConditions> conditions) {
        return whereGroupAnd(where -> where.conditionsAnd(conditions));
    }

    public TGS_UnionExcuse<TS_SQLConnStmtUpdateResult> whereConditionOr(TGS_RunnableType1<TS_SQLWhereConditions> conditions) {
        return whereGroupOr(where -> where.conditionsOr(conditions));
    }

    public TGS_UnionExcuse<TS_SQLConnStmtUpdateResult> whereConditionNone() {
        return executor.run();
    }
}
