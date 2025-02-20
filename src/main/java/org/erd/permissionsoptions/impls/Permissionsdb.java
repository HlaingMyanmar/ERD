package org.erd.permissionsoptions.impls;


import org.erd.dao.DataAccessObject;
import org.erd.permissionsoptions.models.Permissions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class Permissionsdb implements DataAccessObject<Permissions> {

    private SessionFactory sessionFactory;

    public Permissionsdb(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Permissions> getAllData() {

        try(Session session = sessionFactory.openSession()) {

            return session.createQuery("from Permissions", Permissions.class).list();
        }

    }

    @Override
    public Permissions findDataById(int id) {

        try(Session session = sessionFactory.openSession()) {
            return session.get(Permissions.class, id);
        }

    }

    @Override
    public boolean insertData(Permissions permissions) {

        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.persist(permissions);
            session.getTransaction().commit();
            return true;

        }catch(Exception e) {

            transaction.rollback();
            return false;
        }

    }

    @Override
    public boolean updateData(Permissions permissions) {

        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(permissions);
            session.getTransaction().commit();
            return true;
        }catch(Exception e) {
            transaction.rollback();
            return false;
        }

    }

    @Override
    public boolean deleteData(Permissions permissions) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(permissions);
            session.getTransaction().commit();
            return true;
        }catch(Exception e) {

            transaction.rollback();
            return false;
        }

    }

    @Override
    public boolean deleteDataById(int id) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {

            Permissions permissions = session.get(Permissions.class, id);
            transaction = session.beginTransaction();
            session.remove(permissions);
            session.getTransaction().commit();
            return true;

        }catch(Exception e) {
            transaction.rollback();
            return false;
        }

    }
}
