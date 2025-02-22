package org.erd.permissionsoptions.services;

import org.erd.permissionsoptions.impls.Permissionsdb;
import org.erd.permissionsoptions.models.Permissions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionsService {

    private Permissionsdb permissionsdb;

    public PermissionsService(Permissionsdb permissionsdb) {
        this.permissionsdb = permissionsdb;
    }

    public List<Permissions> getAllData(){

        return permissionsdb.getAllData();
    }

    public boolean addPermissions(Permissions permissions){

        return permissionsdb.insertData(permissions);
    }

    public boolean updatePermissions(Permissions permissions){
        return permissionsdb.updateData(permissions);
    }

    public boolean deleteByID(int id){

        return permissionsdb.deleteDataById(id);

    }
}
