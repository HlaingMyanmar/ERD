package org.erd.rolepermissionsoptions.services;

import org.erd.rolepermissionsoptions.impls.Roleperdb;
import org.erd.rolepermissionsoptions.models.RolePermissions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleperService {

    private Roleperdb roleperdb;

    public RoleperService(Roleperdb roleperdb) {
        this.roleperdb = roleperdb;
    }

    public List<RolePermissions> getRolePerAllData(){

        return roleperdb.getAllData();
    }

    public boolean addRolePer(RolePermissions roleper){

        return roleperdb.insertData(roleper);
    }

    public boolean deleteRolePer(RolePermissions roleper){
        return roleperdb.deleteData(roleper);
    }

    public boolean updateRolePer(RolePermissions roleper){
        return roleperdb.updateData(roleper);
    }

    public boolean deleteByID(int id){

        return roleperdb.deleteDataById(id);
    }
}
