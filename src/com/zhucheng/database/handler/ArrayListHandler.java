package com.zhucheng.database.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.zhucheng.database.RowProcessor;


public class ArrayListHandler extends AbstractListHandler<Object[]> {

    private final RowProcessor convert;

    public ArrayListHandler() {
        this(ArrayHandler.ROW_PROCESSOR);
    }

    public ArrayListHandler(RowProcessor convert) {
        super();
        this.convert = convert;
    }


    @Override
    protected Object[] handleRow(ResultSet rs) throws SQLException {
        return this.convert.toArray(rs);
    }

}
