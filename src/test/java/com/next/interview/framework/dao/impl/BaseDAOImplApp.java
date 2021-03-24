package com.next.interview.framework.dao.impl;

import com.next.interview.framework.domain.User;
import org.junit.Test;

import java.io.Serializable;
import java.util.Date;

public class BaseDAOImplApp {
    @Test
    public void test01(){
        BaseDAOImpl baseDAO = new BaseDAOImpl();
        User user = new User("PK", 18, new Date());
        Serializable id = baseDAO.save(user);
        System.out.println("id: " + id);
        for(int i=0; i<10; i++){
            user = new User("PK" + i, i, new Date());
            id = baseDAO.save(user);
            System.out.println("id: " + id);
        }
    }
}
