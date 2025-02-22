package org.erd.rolepermissionsoptions.impls;

import org.erd.dao.DataAccessObject;
import org.erd.rolepermissionsoptions.models.RolePermissions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class Roleperdb implements DataAccessObject<RolePermissions> {


    private SessionFactory sessionFactory;

    public Roleperdb(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<RolePermissions> getAllData() {

        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from RolePermissions").list();
        }

    }

    @Override
    public RolePermissions findDataById(int id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(RolePermissions.class, id);
        }


    }

    @Override
    public boolean insertData(RolePermissions rolePermissions) {

        Transaction tx = null;

        try(Session session = sessionFactory.openSession()){

            tx = session.beginTransaction();
            session.persist(rolePermissions);
            session.getTransaction().commit();
            return true;

        }catch(Exception e){

            tx.rollback();
            return false;

        }

    }

    @Override
    public boolean updateData(RolePermissions rolePermissions) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            session.merge(rolePermissions);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){

            tx.rollback();
            return false;
        }

    }

    @Override
    public boolean deleteData(RolePermissions rolePermissions) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            session.remove(rolePermissions);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){

            tx.rollback();
            return false;
        }

    }

    @Override
    public boolean deleteDataById(int id) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            tx = session.beginTransaction();
            RolePermissions rolePermissions = session.get(RolePermissions.class, id);
            session.remove(rolePermissions);
            session.getTransaction().commit();
            return true;
        }catch(Exception e){
            tx.rollback();
            return false;
        }

    }
}
