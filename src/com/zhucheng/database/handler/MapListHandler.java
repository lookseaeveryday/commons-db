package com.zhucheng.database.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.zhucheng.database.RowProcessor;


public class MapListHandler extends AbstractListHandler<Map<String, Object>> {

    private final RowProcessor convert;

    public MapListHandler() {
        this(ArrayHandler.ROW_PROCESSOR);
    }

    public MapListHandler(RowProcessor convert) {
        super();
        this.convert = convert;
    }

    @Override
    protected Map<String, Object> handleRow(ResultSet rs) throws SQLException {
        return this.convert.toMap(rs);
    }

}
