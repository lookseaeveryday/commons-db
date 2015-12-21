package com.zhucheng.database.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.zhucheng.database.BasicRowProcessor;
import com.zhucheng.database.ResultSetHandler;
import com.zhucheng.database.RowProcessor;


public class ArrayHandler implements ResultSetHandler<Object[]> {

    static final RowProcessor ROW_PROCESSOR = new BasicRowProcessor();

    private final RowProcessor convert;

    public ArrayHandler() {
        this(ROW_PROCESSOR);
    }

    public ArrayHandler(RowProcessor convert) {
        super();
        this.convert = convert;
    }

    @Override
    public Object[] handle(ResultSet rs) throws SQLException {
        return rs.next() ? this.convert.toArray(rs) : null;
    }

}
