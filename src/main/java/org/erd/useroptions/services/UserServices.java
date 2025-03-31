package org.erd.useroptions.services;

import org.erd.useroptions.impls.Usersdb;
import org.erd.useroptions.models.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    private Usersdb usersdb;

    public UserServices(Usersdb usersdb) {
        this.usersdb = usersdb;
    }

    public List<Users> getAllData(){
        return usersdb.getAllData();
    }

    public boolean addUser(Users users){

        return usersdb.insertData(users);
    }

    public boolean deleteUser(int id){

        return usersdb.deleteDataById(id);
    }
    public boolean updateUser(Users users){
        return usersdb.updateData(users);
    }

}
