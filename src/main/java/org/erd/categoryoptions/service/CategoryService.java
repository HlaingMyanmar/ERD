package org.erd.categoryoptions.service;


import org.erd.categoryoptions.impl.Categorydb;
import org.erd.categoryoptions.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private Categorydb categorydb;


    public CategoryService(Categorydb categorydb) {
        this.categorydb = categorydb;
    }


    public List<Category> getCategories() {
        return categorydb.getAllData();
    }

    public Category getCategory(int id) {

        return categorydb.findDataById(id);
    }

    public boolean addCategory(Category category) {
        return categorydb.insertData(category);
    }

    public boolean updateCategory(Category category) {
        return categorydb.updateData(category);
    }
    public boolean deleteCategory(int id) {
        return categorydb.deleteDataById(id);
    }




}
