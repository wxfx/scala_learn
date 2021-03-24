package com.next.interview.framework.dao.impl;

import com.next.interview.framework.dao.BaseDAO;
import com.next.interview.framework.utils.DBUtils;
import com.next.interview.framework.utils.Tools;
import com.sun.corba.se.impl.oa.toa.TOA;
import jdk.management.resource.internal.TotalResourceContext;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BaseDAOImpl implements BaseDAO {
    /**
     * 入参T是User
     * insert into t_user(name, age, birth_day) values(?,?,?);
     */
    @Override
    public <T> Serializable save(T t)
    {
        StringBuilder builder = new StringBuilder("insert into ");
        String table = Tools.getTable(t.getClass());
        builder.append(table + " (");

        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields){
            if(!field.getName().equals("id")){
                String column = Tools.getColumn(field);
                builder.append(column + ",");
            }
        }
        builder.deleteCharAt(builder.toString().length() - 1).append(") values (");
        //System.out.println(builder.toString());

        for(Field field : fields){
            if(!field.getName().equals("id")){
                builder.append("?,");
            }
        }
        builder.deleteCharAt(builder.toString().length() - 1).append(")");
        //System.out.println(builder.toString());

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int index = 1;
        try {
            connection = DBUtils.getConnection();
            //pstmt = connection.prepareStatement(builder.toString());
            pstmt = connection.prepareStatement(builder.toString(), new String[]{"id"});
            for (Field field : fields) {
                if (!field.getName().equals("id")) {
                    String getMethod = Tools.getMethod(field);
                    Method method = clazz.getDeclaredMethod(getMethod);
                    Object obj = method.invoke(t);
                    pstmt.setObject(index++, obj);
                }
            }

            int rowCount = pstmt.executeUpdate();
            System.out.println("rowCount:" + rowCount);
            if (rowCount > 0) {
                rs = pstmt.getGeneratedKeys();
                rs.next();
                return (Serializable) rs.getObject(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
