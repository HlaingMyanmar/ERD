package org.erd.roleoptions.services;



import org.erd.roleoptions.impls.Rolesdb;
import org.erd.roleoptions.models.Roles;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {


    private Rolesdb rolesdb;

    public RoleService(Rolesdb rolesdb) {
        this.rolesdb = rolesdb;
    }

    public List<Roles> getRoleAllData(){

        return rolesdb.getAllData();

    }

    public boolean addRole(Roles role){

        return rolesdb.insertData(role);

    }

}
