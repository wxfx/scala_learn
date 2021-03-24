package com.next.interview.framework.dao;

import java.io.Serializable;

public interface BaseDAO {
    <T> Serializable save(T t);
}
