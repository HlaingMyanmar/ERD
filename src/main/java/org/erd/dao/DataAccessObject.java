package org.erd.dao;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DataAccessObject<T>{



    public List<T> getAllData();

    public T findDataById(int id);

    public boolean insertData(T t);

    public boolean updateData(T t);

    public boolean deleteData(T t);

    public boolean deleteDataById(int id);







}
