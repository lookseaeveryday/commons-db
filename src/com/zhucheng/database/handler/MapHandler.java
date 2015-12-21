package com.zhucheng.database.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.zhucheng.database.ResultSetHandler;
import com.zhucheng.database.RowProcessor;


public class MapHandler implements ResultSetHandler<Map<String, Object>> {

    private final RowProcessor convert;

    public MapHandler() {
        this(ArrayHandler.ROW_PROCESSOR);
    }

    public MapHandler(RowProcessor convert) {
        super();
        this.convert = convert;
    }

    @Override
    public Map<String, Object> handle(ResultSet rs) throws SQLException {
        return rs.next() ? this.convert.toMap(rs) : null;
    }

}
